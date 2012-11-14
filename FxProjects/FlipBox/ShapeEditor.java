import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ShapeEditor extends Application {

	protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
			1);

	public static void main(String[] args) {
		launch(args);

	}

	public void start(final Stage primaryStage) throws Exception {
		final Group pane = new Group();
		pane.translateXProperty().set(50);
		pane.translateYProperty().set(50);

		Scene scene = new Scene(pane);
		scene.setFill(Color.BLACK);

		primaryStage.setScene(scene);
		primaryStage.setWidth(550);
		primaryStage.setHeight(550);
		primaryStage.setX(0);
		primaryStage.setY(0);
		primaryStage.setTitle("C3 Shape Editor");
		primaryStage.show();

		final Path axis = PathBuilder
				.create()
				.elements(new MoveTo(0, 0), new LineTo(0, 100),
						new LineTo(-10, 90), new MoveTo(0, 100),
						new LineTo(10, 90), new MoveTo(0, 100),
						new LineTo(0, 3000),

						new MoveTo(0, 0), new LineTo(100, 0),
						new LineTo(90, -10), new MoveTo(100, 0),
						new LineTo(90, 10), new MoveTo(100, 0),
						new LineTo(3000, 0)).stroke(Color.WHITE).build();

		final Text origin = TextBuilder.create().text("(0,0)")
				.fill(Color.WHITE).x(-10).y(-5).build();

		final Text x = TextBuilder.create().text("X Axis").fill(Color.WHITE)
				.x(50).y(-5).build();

		final Text y = TextBuilder.create().text("Y Axis").fill(Color.WHITE)
				.x(-25).y(70).rotate(-90).build();

		this.executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						pane.getChildren().clear();

						Node dut = createShape();

						pane.getChildren().addAll(dut, axis, origin, x, y);
					}
				});
			}
		}, 200, 200, TimeUnit.MILLISECONDS);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent paramT) {
				executor.shutdownNow();
			}
		});
	}

	public Node createShape() {
		Rectangle rectangle = RectangleBuilder.create().x(50).y(100).width(250)
				.height(100).fill(Color.RED).stroke(Color.BLACK).build();

		Circle circle = CircleBuilder.create().centerX(150).centerY(100)
				.fill(Color.BLUE).radius(200).build();

		return new Group(circle, rectangle);
	}
}
