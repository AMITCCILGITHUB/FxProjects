import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.PolygonBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @reference: http://sett.ociweb.com/sett/settMay2012.html
 */
public class Tetrahedron extends Application {

	public static final double DIHEDRAL_ANGLE = 70.53;
	public static final double INRADIUS = 50 * Math.tan(DIHEDRAL_ANGLE / 2
			/ 180 * Math.PI);

	private DoubleProperty side2Angle = new SimpleDoubleProperty();
	private DoubleProperty side3Angle = new SimpleDoubleProperty();
	private DoubleProperty side4Angle = new SimpleDoubleProperty();
	private DoubleProperty rootAngle = new SimpleDoubleProperty();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Tetrahedron");
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

		double x[] = new double[3];
		double y[] = new double[3];
		double angle = 2 * Math.PI / 3;
		for (int i = 0; i < 3; i++) {
			x[i] = 100 * Math.cos(i * angle);
			y[i] = 100 * Math.sin(i * angle);
		}

		final Polygon side1 = PolygonBuilder.create()
				.points(x[0], y[0], x[1], y[1], x[2], y[2]).fill(Color.RED)
				.opacity(0.9).build();

		final Rotate side2Rotate = RotateBuilder.create().pivotX(x[0])
				.pivotY(y[0]).pivotZ(0)
				.axis(new Point3D(x[0] - x[1], y[0] - y[1], 0)).build();
		side2Rotate.angleProperty().bind(side2Angle);

		final Polygon side2 = PolygonBuilder.create()
				.points(x[0], y[0], x[1], y[1], x[2], y[2]).fill(Color.GREEN)
				.opacity(0.9).transforms(side2Rotate).build();

		final Rotate side3Rotate = RotateBuilder.create().pivotX(x[1])
				.pivotY(y[1]).pivotZ(0)
				.axis(new Point3D(x[1] - x[2], y[1] - y[2], 0)).build();
		side3Rotate.angleProperty().bind(side3Angle);

		final Polygon side3 = PolygonBuilder.create()
				.points(x[0], y[0], x[1], y[1], x[2], y[2]).fill(Color.BLUE)
				.opacity(0.9).transforms(side3Rotate).build();

		final Rotate side4Rotate = RotateBuilder.create().pivotX(x[2])
				.pivotY(y[2]).pivotZ(0)
				.axis(new Point3D(x[2] - x[0], y[2] - y[0], 0)).build();
		side4Rotate.angleProperty().bind(side4Angle);

		final Polygon side4 = PolygonBuilder.create()
				.points(x[0], y[0], x[1], y[1], x[2], y[2]).fill(Color.CYAN)
				.opacity(0.9).transforms(side4Rotate).build();

		final Rotate rootRotate = RotateBuilder.create().pivotX(0).pivotY(0)
				.pivotZ(-INRADIUS).axis(new Point3D(1, 1, 1)).build();
		rootRotate.angleProperty().bind(rootAngle);

		return GroupBuilder.create().children(side1, side2, side3, side4)
				.translateX(250).translateY(250).translateZ(-INRADIUS)
				.transforms(rootRotate).build();
	}

	private void animate() {

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle, 0),
								new KeyValue(side4Angle, 0), new KeyValue(
										rootAngle, -60)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle, 0),
								new KeyValue(side4Angle, DIHEDRAL_ANGLE),
								new KeyValue(rootAngle, 60)),
						new KeyFrame(Duration.seconds(4), new KeyValue(
								side2Angle, 0), new KeyValue(side3Angle,
								DIHEDRAL_ANGLE), new KeyValue(side4Angle,
								DIHEDRAL_ANGLE), new KeyValue(rootAngle, 180)),
						new KeyFrame(Duration.seconds(6), new KeyValue(
								side2Angle, DIHEDRAL_ANGLE), new KeyValue(
								side3Angle, DIHEDRAL_ANGLE), new KeyValue(
								side4Angle, DIHEDRAL_ANGLE), new KeyValue(
								rootAngle, 300))).build().play();
	}
}