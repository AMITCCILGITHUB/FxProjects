import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestFx3D extends Application {

	private DoubleProperty side2Angle = new SimpleDoubleProperty();
	private DoubleProperty side3Angle = new SimpleDoubleProperty();
	private DoubleProperty side4Angle = new SimpleDoubleProperty();
	private DoubleProperty side5Angle = new SimpleDoubleProperty();
	private DoubleProperty side6TranslateZ = new SimpleDoubleProperty();
	private DoubleProperty rootAngle = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Cube");
		final Group root = new Group();
		final Scene scene = new Scene(root, 500, 500);

		SideRotate sr2 = new SideRotate(-100, -100, 0, Rotate.Y_AXIS);
		sr2.angleProperty().bind(side2Angle);
		SideRotate sr3 = new SideRotate(100, -100, 0, new Point3D(-1, 0, 0));
		sr3.angleProperty().bind(side3Angle);
		SideRotate sr4 = new SideRotate(100, 100, 0, new Point3D(0, -1, 0));
		sr4.angleProperty().bind(side4Angle);
		SideRotate sr5 = new SideRotate(-100, 100, 0, Rotate.X_AXIS);
		sr5.angleProperty().bind(side5Angle);

		SideRotate srr = new SideRotate(0, 0, -10, new Point3D(1, 1, 1));
		srr.angleProperty().bind(rootAngle);

		CubeSide cs1 = new CubeSide(Color.RED);
		CubeSide cs2 = new CubeSide(Color.GREEN, sr2);
		CubeSide cs3 = new CubeSide(Color.BLUE, sr3);
		CubeSide cs4 = new CubeSide(Color.CYAN, sr4);
		CubeSide cs5 = new CubeSide(Color.MAGENTA, sr5);
		CubeSide cs6 = new CubeSide(Color.YELLOW);
		cs6.translateZProperty().bind(side6TranslateZ);

		Group g = GroupBuilder.create().children(cs1, cs2, cs3, cs4, cs5, cs6)
				.translateX(250).translateY(250).transforms(srr).build();

		root.getChildren().add(g);

		primaryStage.setScene(scene);
		primaryStage.show();

		animate();
	}

	public static void main(String[] args) {

		launch(args);
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
