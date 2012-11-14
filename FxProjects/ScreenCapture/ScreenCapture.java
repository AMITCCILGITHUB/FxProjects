import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;

public class ScreenCapture extends Application {

	private static ScreenCapture screenCapture;
	private Stage stage;
	public Rectangle screenBounds;
	private Rebounder rebounder;
	private GlassPane glassPane;

	public static ScreenCapture getScreenCapture() {

		return screenCapture;
	}

	@Override
	public void start(Stage primaryStage) {

		screenCapture = this;

		stage = primaryStage;
		Group group = new Group();
		Scene scene = new Scene(group);
		scene.setFill(Color.TRANSPARENT);
		scene.setCursor(Cursor.CROSSHAIR);

		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();

		screenBounds = new Rectangle(
				Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary()
						.getBounds().getHeight());

		KeyPane keyPane = new KeyPane(primaryStage, rebounder = new Rebounder());
		glassPane = new GlassPane();
		glassPane.setShape(rebounder.shapeBuilder(null), true);

		group.getChildren().addAll(
				new Node[] { (Node) keyPane, (Node) glassPane,
						(Node) rebounder.getLasso() });

		scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {

				glassPane.setShape(
						rebounder.shapeBuilder(rebounder.start(me.getX(),
								me.getY())), false);
			}
		});

		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {

				glassPane.setShape(rebounder.shapeBuilder(rebounder.rebound(
						me.getX() + 1, me.getY() + 1)), false);
			}
		});

		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {

				if (!rebounder.isStopped()) {
					capture(rebounder.stop(me.getX() + 1, me.getY() + 1));
				}
			}
		});
	}

	public void capture(final Rectangle finished) {

		stage.setWidth(0);
		stage.setHeight(0);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				if ((((finished.getWidth() - 1) * (finished.getHeight() - 1)) > 0.0d)) {
					try {
						Robot robot = new Robot();
						BufferedImage img = robot
								.createScreenCapture(new java.awt.Rectangle(
										(int) finished.getX(), (int) finished
												.getY(), (int) finished
												.getWidth() - 1, (int) finished
												.getHeight() - 1));
						File folder = new File(System.getProperty("user.home"),
								"snapshots");
						folder.mkdirs();
						File file = File.createTempFile("jfx2_screen_capture",
								".jpg", folder);
						ImageIO.write(img, "jpg", file);
					} catch (Exception ex) {
						Logger.getLogger(ScreenCapture.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						glassPane.setShape(rebounder.shapeBuilder(null), true);
						stage.setWidth(screenBounds.getWidth());
						stage.setHeight(screenBounds.getHeight());
					}
				});
			}
		});
	}

	public static void main(String[] args) {

		launch(args);
	}
}
