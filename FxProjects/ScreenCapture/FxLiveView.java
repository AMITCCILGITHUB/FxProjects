import java.awt.Robot;
import java.awt.image.BufferedImage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class FxLiveView extends Application {

	private boolean stopped = false;
	private ImageView imageView = new ImageView();

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("LiveFx");
		final Group root = new Group();
		final Scene scene = new Scene(root, 500, 500);

		root.getChildren().addAll(imageView);

		primaryStage.setScene(scene);
		primaryStage.show();

		new Thread(t).start();
	}

	Task<Object> t = new Task<Object>() {

		@Override
		protected Object call() {

			while (!stopped) {
				try {
					Robot ro = new Robot();
					BufferedImage im = ro
							.createScreenCapture(new java.awt.Rectangle(0, 0,
									500, 500));
					final WritableImage image = new WritableImage(500, 500);
					SwingFXUtils.toFXImage(im, image);

					// Calling the JavaFX Thread to update javafx control
					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							// This updates the imageview to newly created Image
							imageView.setImage(image);
						}
					});

					// Sleep for 100 millisecond
					Thread.sleep(100);

				} catch (Exception ex) {
					// print stack trace or do other stuffs
				}
			}
			return null;
		}

	};

	public static void main(String[] args) {

		launch(args);
	}
}
