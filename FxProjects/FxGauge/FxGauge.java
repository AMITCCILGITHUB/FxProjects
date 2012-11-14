import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FxGauge extends Application {

	private DoubleProperty rotateAngle = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Fx Gauge");
		Pane root = new Pane();
		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().add(
				FxGauge.class.getResource("gauge.css").toExternalForm());

		Group gauge = drawGauge();

		Group ribbon = drawRibbon();

		Group center = drawArrow();

		Group texts = drawTexts();

		root.getChildren().addAll(gauge, ribbon, center, texts);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Group drawGauge() {
		Group gauge = new Group();

		Circle circle = new Circle(250, 250, 200);
		Rectangle rectangle = new Rectangle(50, 360, 400, 100);
		Shape radial = Shape.subtract(circle, rectangle);
		radial.getStyleClass().add("radial");

		Rectangle bottom = new Rectangle(80, 372, 340, 60);
		bottom.setArcWidth(12);
		bottom.setArcHeight(12);
		bottom.getStyleClass().add("bottom");

		gauge.getChildren().addAll(bottom, radial);

		return gauge;
	}

	private Group drawRibbon() {
		Group ribbon = new Group();
		for (int i = 0; i < 5; i++) {
			Arc outerArc = ArcBuilder.create().centerX(250).centerY(250)
					.radiusX(180).radiusY(180).startAngle(0).length(46)
					.type(ArcType.ROUND).fill(Color.CORAL).build();

			Arc innerArc = ArcBuilder.create().centerX(250).centerY(250)
					.radiusX(160).radiusY(160).startAngle(0).length(46)
					.type(ArcType.ROUND).fill(Color.CORAL).build();

			Shape arc = Shape.subtract(outerArc, innerArc);
			arc.getStyleClass().add("ribbon-" + i);

			arc.getTransforms().add(
					RotateBuilder.create().pivotX(innerArc.getCenterX())
							.pivotY(innerArc.getCenterY()).pivotZ(0)
							.axis(Rotate.Z_AXIS).angle(-165 + 49 * i).build());

			ribbon.getChildren().add(arc);
		}

		return ribbon;
	}

	private Group drawArrow() {
		Group arrowDial = new Group();

		Circle circle = new Circle(250, 250, 28);
		circle.getStyleClass().add("center");

		Line arrow = new Line(250, 250, 360, 250);
		arrow.setStrokeWidth(8);
		arrow.setStrokeType(StrokeType.CENTERED);
		arrow.getStyleClass().add("arrow");

		arrowDial.getChildren().addAll(arrow, circle);

		Rotate rotate = RotateBuilder.create().pivotX(250).pivotY(250)
				.pivotZ(0).axis(Rotate.Z_AXIS).build();

		arrowDial.getTransforms().add(rotate);
		rotate.angleProperty().bind(rotateAngle);

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								rotateAngle, -214)),
						new KeyFrame(Duration.seconds(6), new KeyValue(
								rotateAngle, 32)))
				.cycleCount(Timeline.INDEFINITE).autoReverse(true).build()
				.play();

		return arrowDial;
	}

	private Group drawTexts() {
		Group textValues = new Group();

		for (int i = 0; i <= 5; i++) {
			Text text = new Text(i * 2 + "0");
			text.setX(130);
			text.setTranslateX(250);
			text.setTranslateY(250);
			text.getStyleClass().add("text");
			text.getTransforms().add(new Rotate(-162 + 49 * (i - 1)));

			textValues.getChildren().addAll(text);
		}

		return textValues;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
