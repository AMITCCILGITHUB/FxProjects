import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlipBox extends Application {

	private DoubleProperty sideAngle = new SimpleDoubleProperty(0);

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Flip Box");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 500, 500);
		scene.setCamera(new PerspectiveCamera());

		Rotate sideRotate = RotateBuilder.create().pivotX(0).pivotY(100)
				.pivotZ(0).axis(new Point3D(1, 0, 0)).build();
		sideRotate.angleProperty().bind(sideAngle);

		Button rec = ButtonBuilder.create().prefWidth(100).prefHeight(100)
				.transforms(sideRotate).build();

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								sideAngle, 0)),
						new KeyFrame(Duration.seconds(1), new KeyValue(
								sideAngle, 45)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								sideAngle, 90)),
						new KeyFrame(Duration.seconds(3), new KeyValue(
								sideAngle, 135)),
						new KeyFrame(Duration.seconds(4), new KeyValue(
								sideAngle, 180)))
				.cycleCount(Timeline.INDEFINITE).build().play();

		root.getChildren().addAll(rec);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
