import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PhotoCube extends Application {

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Photo Cube");
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		final Group root = new Group();
		final Scene scene = new Scene(root, 512, 512);
		scene.setCamera(PerspectiveCameraBuilder.create().fieldOfView(30)
				.build());
		scene.setFill(Color.TRANSPARENT);

		PhotoNode cube = new PhotoNode();
		cube.setFocusTraversable(true);

		root.getChildren().addAll(cube);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
