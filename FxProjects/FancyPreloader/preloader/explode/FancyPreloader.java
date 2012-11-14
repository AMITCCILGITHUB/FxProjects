/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package preloader.explode;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author msi
 */
public class FancyPreloader extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);
	}

	Group g = new Group();
	ImageView imgView;
	Image image;
	Button btn;

	@Override
	public void init() {

	}

	public void animate() {

		Timeline time = new Timeline();
		time.getKeyFrames().add(
				new KeyFrame(Duration.millis(0), new KeyValue(g
						.opacityProperty(), 1)));
		for (Node n : g.getChildren()) {
			time.getKeyFrames().add(
					new KeyFrame(Duration.millis(800), new KeyValue(n
							.layoutXProperty(), (Math.random() * 2000) - 1000),
							new KeyValue(n.layoutYProperty(),
									(Math.random() * 2000) - 1000)
					// new
					// KeyValue(((Rotate)n.getTransforms().get(1)).angleProperty(),(Math.random()*360))
					)

			);
		}
		time.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(800), new KeyValue(
						g.opacityProperty(), 1)),
				new KeyFrame(Duration.millis(1000),
						new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {

								for (Node n : g.getChildren()) {
									n.setLayoutX(0);
									n.setLayoutY(0);
									g.setOpacity(1);
									btn.setVisible(true);

									imgView.setVisible(true);
									g.setVisible(false);
								}
							}

						}, new KeyValue(g.opacityProperty(), 0))

		);

		time.play();
	}

	ArrayList<KeyFrame> frame = new ArrayList<KeyFrame>();

	@Override
	public void start(Stage primaryStage) {

		btn = new Button();
		btn.setText("Click Me :)");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				btn.setVisible(false);

				imgView.setVisible(false);
				g.setVisible(true);

				if (g.getChildren().size() == 0) {
					for (int i = 0; i < 100; i++) {
						ImageView r = new ImageView();
						r.setImage(image);
						r.getTransforms().addAll(
								new Rotate(Math.random() * 360, Rotate.Y_AXIS),
								new Rotate(Math.random() * 360, Rotate.X_AXIS),
								new Rotate(Math.random() * 360, Rotate.Z_AXIS)

						);
						Rectangle2D rec = new Rectangle2D(Math.random() * 1000,
								Math.random() * 1000, Math.random() * 100, Math
										.random() * 100);
						r.setViewport(rec);

						g.getChildren().add(r);
					}
				}
				animate();

			}
		});
		Stop[] stops = new Stop[] { new Stop(0, Color.web("#00254f")),
				new Stop(0.5, Color.web("#79b488")),
				new Stop(1, Color.web("#f3e586"))

		};
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true,
				CycleMethod.NO_CYCLE, stops);

		StackPane root = new StackPane();
		image = new Image(getClass().getResource("img.png").toExternalForm());
		imgView = new ImageView(image);
		imgView.setPreserveRatio(true);
		imgView.setFitWidth(250);
		imgView.setLayoutX(0);
		imgView.setLayoutY(0);
		root.getChildren().addAll(imgView, btn, g);

		Scene scene = new Scene(root, 500, 250, lg);

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
