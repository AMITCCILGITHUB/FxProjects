import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @reference: http://sett.ociweb.com/sett/settMay2012.html
 */
public class Cube extends Application {

	private DoubleProperty side2Angle = new SimpleDoubleProperty();
	private DoubleProperty side3Angle = new SimpleDoubleProperty();
	private DoubleProperty side4Angle = new SimpleDoubleProperty();
	private DoubleProperty side5Angle = new SimpleDoubleProperty();
	private DoubleProperty side6TranslateZ = new SimpleDoubleProperty();
	private DoubleProperty rootAngle = new SimpleDoubleProperty();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Cube");
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

		Distant light = new Distant();
		light.setAzimuth(-135.0f);

		Lighting l = new Lighting();
		l.setLight(light);
		l.setSurfaceScale(5.0f);

		final Rectangle side1 = RectangleBuilder.create().x(-100).y(-100)
				.width(200).height(200).fill(Color.RED).effect(l).build();

		final Rotate side2Rotate = RotateBuilder.create().pivotX(-100)
				.pivotY(-100).pivotZ(0).axis(Rotate.Y_AXIS).build();
		side2Rotate.angleProperty().bind(side2Angle);

		final Rectangle side2 = RectangleBuilder.create().x(-100).y(-100)
				.width(200).height(200).fill(Color.GREEN)
				.transforms(side2Rotate).build();

		final Rotate side3Rotate = RotateBuilder.create().pivotX(100)
				.pivotY(-100).pivotZ(0).axis(new Point3D(-1, 0, 0)).build();
		side3Rotate.angleProperty().bind(side3Angle);

		final Rectangle side3 = RectangleBuilder.create().x(-100).y(-100)
				.width(200).height(200).fill(Color.BLUE)
				.transforms(side3Rotate).build();

		final Rotate side4Rotate = RotateBuilder.create().pivotX(100)
				.pivotY(100).pivotZ(0).axis(new Point3D(0, -1, 0)).build();
		side4Rotate.angleProperty().bind(side4Angle);

		final Rectangle side4 = RectangleBuilder.create().x(-100).y(-100)
				.width(200).height(200).fill(Color.CYAN)
				.transforms(side4Rotate).build();

		final Rotate side5Rotate = RotateBuilder.create().pivotX(-100)
				.pivotY(100).pivotZ(0).axis(Rotate.X_AXIS).build();
		side5Rotate.angleProperty().bind(side5Angle);

		final Rectangle side5 = RectangleBuilder.create().x(-100).y(-100)
				.width(200).height(200).fill(Color.MAGENTA)
				.transforms(side5Rotate).build();

		final Rectangle side6 = RectangleBuilder.create().x(-100).y(-100)
				.width(200).height(200).fill(Color.YELLOW).build();

		side6.translateZProperty().bind(side6TranslateZ);

		final Rotate rootRotate = RotateBuilder.create().pivotX(0).pivotY(0)
				.pivotZ(-100).axis(new Point3D(1, 1, 1)).build();
		rootRotate.angleProperty().bind(rootAngle);

		Group g = GroupBuilder.create()
				.children(side1, side6, side2, side3, side4, side5)
				.translateX(250).translateY(250).translateZ(-100)
				.transforms(rootRotate).build();

		return GroupBuilder.create().children(g).effect(l).build();
	}

	private void animate() {

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle, 0),
								new KeyValue(side4Angle, 0), new KeyValue(
										side5Angle, 0), new KeyValue(
										side6TranslateZ, 0), new KeyValue(
										rootAngle, -30)),
						new KeyFrame(Duration.seconds(1), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle, 0),
								new KeyValue(side4Angle, 0), new KeyValue(
										side5Angle, 90), new KeyValue(
										side6TranslateZ, 0), new KeyValue(
										rootAngle, 30)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle, 0),
								new KeyValue(side4Angle, 90), new KeyValue(
										side5Angle, 90), new KeyValue(
										side6TranslateZ, 0), new KeyValue(
										rootAngle, 90)),
						new KeyFrame(Duration.seconds(3), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle, 90),
								new KeyValue(side4Angle, 90), new KeyValue(
										side5Angle, 90), new KeyValue(
										side6TranslateZ, 0), new KeyValue(
										rootAngle, 150)),
						new KeyFrame(Duration.seconds(4), new KeyValue(
								side2Angle, 90), new KeyValue(side3Angle, 90),
								new KeyValue(side4Angle, 90), new KeyValue(
										side5Angle, 90), new KeyValue(
										side6TranslateZ, 0), new KeyValue(
										rootAngle, 210)),
						new KeyFrame(Duration.seconds(5), new KeyValue(
								side2Angle, 90), new KeyValue(side3Angle, 90),
								new KeyValue(side4Angle, 90), new KeyValue(
										side5Angle, 90), new KeyValue(
										side6TranslateZ, -200), new KeyValue(
										rootAngle, 330))).build().play();
	}
}
