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
public class LinearBars extends Application {

	private DoubleProperty translate = new SimpleDoubleProperty();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Hello JavaFX");
		stage.setScene(makeScene());
		stage.show();
		animate();
	}

	private Scene makeScene() {

		return SceneBuilder.create().width(500).height(500).root(createRoot())
				.camera(PerspectiveCameraBuilder.create().build()).build();
	}

	private Parent createRoot() {

		Rectangle node1 = RectangleBuilder.create().x(0).y(0).width(10)
				.height(10).fill(Color.RED).build();

		Rectangle node2 = RectangleBuilder.create().x(0).y(0).width(10)
				.height(10).fill(Color.GREEN).build();

		Rectangle node3 = RectangleBuilder.create().x(0).y(0).width(10)
				.height(10).fill(Color.BLUE).build();

		node1.translateXProperty().bind(translate);
		node2.translateYProperty().bind(translate);
		node3.translateZProperty().bind(translate);

		return GroupBuilder.create().children(node1, node2, node3)
				.translateX(250).translateY(250).build();
	}

	private void animate() {

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								translate, -250)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								translate, 250))).build().play();
	}
}