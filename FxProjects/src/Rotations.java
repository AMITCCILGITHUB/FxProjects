import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.GroupBuilder;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @reference: http://sett.ociweb.com/sett/settMay2012.html
 */
public class Rotations extends Application {

	private DoubleProperty translateZForNode1 = new SimpleDoubleProperty();
	private DoubleProperty translateZForNode2 = new SimpleDoubleProperty();
	private DoubleProperty translateZForNode3 = new SimpleDoubleProperty();
	private DoubleProperty angle = new SimpleDoubleProperty();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Rotations");
		stage.setScene(makeScene());
		stage.show();
		animate();
	}

	private Scene makeScene() {

		return SceneBuilder.create().width(500).height(500).root(createRoot())
				.camera(PerspectiveCameraBuilder.create().build())
				.depthBuffer(true).build();
	}

	private Parent createRoot() {

		Circle node1 = CircleBuilder.create().centerX(-50).centerY(-50)
				.radius(100).fill(Color.RED).build();

		Circle node2 = CircleBuilder.create().centerX(0).centerY(0).radius(100)
				.fill(Color.GREEN).build();

		Circle node3 = CircleBuilder.create().centerX(50).centerY(50)
				.radius(100).fill(Color.BLUE).build();

		node1.translateZProperty().bind(translateZForNode1);
		node2.translateZProperty().bind(translateZForNode2);
		node3.translateZProperty().bind(translateZForNode3);

		final Rotate rotate = RotateBuilder.create().pivotX(0).pivotY(0)
				.pivotZ(0).axis(new Point3D(1, 0, 0)).build();
		rotate.angleProperty().bind(angle);

		return GroupBuilder.create().children(node1, node2, node3)
				.translateX(250).translateY(250).transforms(rotate).build();
	}

	private void animate() {

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								translateZForNode1, -100), new KeyValue(
								translateZForNode2, -10), new KeyValue(
								translateZForNode3, 100),
								new KeyValue(angle, 0)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								translateZForNode1, 100), new KeyValue(
								translateZForNode2, 90), new KeyValue(
								translateZForNode3, -100), new KeyValue(angle,
								180)),
						new KeyFrame(Duration.seconds(4), new KeyValue(
								translateZForNode1, -100), new KeyValue(
								translateZForNode2, -10), new KeyValue(
								translateZForNode3, 100), new KeyValue(angle,
								360))

				).build().play();
	}
}