import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.StopBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Video3DBall extends Application {

	@Override
	public void start(Stage primaryStage) {

		double vidWidth = 1024;
		double vidHeight = 576;

		SimpleDoubleProperty X = new SimpleDoubleProperty(0);
		SimpleDoubleProperty Y = new SimpleDoubleProperty(1);
		SimpleDoubleProperty Z = new SimpleDoubleProperty(0);
		SimpleDoubleProperty VX = new SimpleDoubleProperty(230);
		SimpleDoubleProperty VY = new SimpleDoubleProperty(376);
		SimpleDoubleProperty S = new SimpleDoubleProperty();;

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.autoReverse(true)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(X, 100)),
						new KeyFrame(Duration.seconds(2.3), new KeyValue(X,
								924, Interpolator.LINEAR))).build().play();

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.autoReverse(true)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(Y, 100)),
						new KeyFrame(Duration.seconds(0.5), new KeyValue(Y,
								476, Interpolator.EASE_IN))).build().play();

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.autoReverse(true)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(Z, 40)),
						new KeyFrame(Duration.seconds(0.5), new KeyValue(Z,
								100, Interpolator.LINEAR))).build().play();

		TimelineBuilder
				.create()
				.cycleCount(Timeline.INDEFINITE)
				.autoReverse(true)
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(S, 0.5)),
						new KeyFrame(Duration.seconds(0.5), new KeyValue(S, 1,
								Interpolator.EASE_IN))).build().play();

		MediaComponent mediaBox = new MediaComponent(VX, VY);
		MediaComponentLeft mediaBoxLeft = new MediaComponentLeft(VX, VY);
		MediaComponentRight mediaBoxRight = new MediaComponentRight(VX, VY);
		MediaComponentCenter mediaBoxCenter = new MediaComponentCenter(VX, VY);

		Line line1 = LineBuilder.create().strokeWidth(3.0).stroke(Color.BLACK)
				.build();
		line1.startXProperty().bind(VX);
		line1.startYProperty().bind(VY);
		line1.endXProperty().set(0);
		line1.endYProperty().set(576);

		Line line2 = LineBuilder.create().strokeWidth(3.0).stroke(Color.BLACK)
				.build();
		line2.startXProperty().bind(VX);
		line2.startYProperty().bind(VY);
		line2.endXProperty().bind(VX);
		line2.endYProperty().set(0);

		Line line3 = LineBuilder.create().strokeWidth(3.0).stroke(Color.BLACK)
				.build();
		line3.startXProperty().bind(VX);
		line3.startYProperty().bind(VY);
		line3.endXProperty().bind(VX.negate().add(1024));
		line3.endYProperty().bind(VY);

		Line line4 = LineBuilder.create().strokeWidth(3.0).stroke(Color.BLACK)
				.build();
		line4.startXProperty().bind(VX.negate().add(1024));
		line4.startYProperty().bind(VY);
		line4.endXProperty().bind(VX.negate().add(1024));
		line4.endYProperty().set(0);

		Line line5 = LineBuilder.create().strokeWidth(3.0).stroke(Color.BLACK)
				.build();
		line5.startXProperty().bind(VX.negate().add(1024));
		line5.startYProperty().bind(VY);
		line5.endXProperty().set(1024);
		line5.endYProperty().set(576);

		Translate ellipseTranslate = new Translate();
		ellipseTranslate.xProperty().bind(X);
		ellipseTranslate.yProperty().bind(Z.multiply(3).add(376 * 2));

		Scale ellipseTransform = new Scale();
		ellipseTransform.xProperty().bind(S.add(Z).divide(100));
		ellipseTransform.yProperty().bind(S.add(Z).divide(100));

		Ellipse ellipse = EllipseBuilder
				.create()
				.centerX(0)
				.centerY(0)
				.radiusX(100)
				.radiusY(30)
				.fill(Color.BLACK)
				.fill(RadialGradientBuilder
						.create()
						.proportional(false)
						.radius(100)
						.centerX(0)
						.centerY(0)
						.focusDistance(0)
						.stops(StopBuilder.create().offset(0.0)
								.color(Color.web("#333333", 1.0)).build(),
								StopBuilder.create().offset(0.4)
										.color(Color.web("#444444", 1.0))
										.build(),
								StopBuilder.create().offset(1.0)
										.color(Color.web("#FFFFFF", 0.0))
										.build()).build())
				.transforms(ellipseTranslate, ellipseTransform).build();

		Translate circleTranslate = new Translate();
		circleTranslate.xProperty().bind(X);
		circleTranslate.yProperty().bind(Y.add(Z).subtract(100));

		Scale circleTransform = new Scale();
		ellipseTransform.xProperty().bind(Z.divide(100));
		ellipseTransform.yProperty().bind(Z.divide(100));

		Circle circle = CircleBuilder
				.create()
				.centerX(0)
				.centerY(0)
				.radius(100)
				.stroke(Color.BLACK)
				.fill(RadialGradientBuilder
						.create()
						.proportional(false)
						.radius(100)
						.centerX(0)
						.centerY(0)
						.focusDistance(35)
						.stops(StopBuilder.create().offset(0.0)
								.color(Color.WHITE).build(),
								StopBuilder.create().offset(0.85)
										.color(Color.RED).build(),
								StopBuilder.create().offset(1.0)
										.color(Color.BLACK).build()).build())
				.transforms(circleTranslate, circleTransform).build();

		Group root = GroupBuilder
				.create()
				.children(mediaBox, mediaBoxLeft, mediaBoxRight,
						mediaBoxCenter, line1, line2, line3, line4, line5,
						circle, ellipse).build();

		Scene scene = SceneBuilder.create().width(vidWidth).height(vidHeight)
				.root(root).build();

		primaryStage.setTitle("Media Player");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
