import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.GroupBuilder;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @reference: http://sett.ociweb.com/sett/settMay2012.html
 */
public class DepthBuffer extends Application {

	private DoubleProperty translateZForNode1 = new SimpleDoubleProperty();
	private DoubleProperty translateZForNode2 = new SimpleDoubleProperty();
	private DoubleProperty translateZForNode3 = new SimpleDoubleProperty();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Depth Buffer");
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

		Rectangle node1 = RectangleBuilder.create().x(-150).y(-150)
				.translateZ(-100).width(200).height(200).fill(Color.RED)
				.build();

		Rectangle node2 = RectangleBuilder.create().x(-100).y(-100).width(200)
				.height(200).fill(Color.GREEN).build();

		Rectangle node3 = RectangleBuilder.create().x(-50).y(-50)
				.translateZ(100).width(200).height(200).fill(Color.BLUE)
				.build();

		node1.translateZProperty().bind(translateZForNode1);
		node2.translateZProperty().bind(translateZForNode2);
		node3.translateZProperty().bind(translateZForNode3);

		return GroupBuilder.create().children(node1, node2, node3)
				.translateX(250).translateY(250).build();
	}

	private void animate() {

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.autoReverse(true)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								translateZForNode1, -100), new KeyValue(
								translateZForNode2, -10), new KeyValue(
								translateZForNode3, 100)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								translateZForNode1, 100), new KeyValue(
								translateZForNode2, 90), new KeyValue(
								translateZForNode3, -100))).build().play();
	}
}