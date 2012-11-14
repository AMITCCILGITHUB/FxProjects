import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RadialMenu extends Application {

	private int menuGap = 2;
	private int centerX = 150;
	private int centerY = 150;
	private int startAngle = 45;

	private SimpleIntegerProperty menuCount = new SimpleIntegerProperty(4);
	private SimpleIntegerProperty menuOuterRadius = new SimpleIntegerProperty(
			100);
	private SimpleIntegerProperty menuInnerRadius = new SimpleIntegerProperty(
			50);
	private DoubleProperty rotateAngle = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Radial Menu");
		Group root = new Group();
		Scene scene = new Scene(root, 300, 300, Color.WHEAT);

		Group menu = generateMenus();

		final Rotate menuRotate = RotateBuilder.create().pivotX(centerX)
				.pivotY(centerY).pivotZ(0).axis(Rotate.Y_AXIS).build();
		menuRotate.angleProperty().bind(rotateAngle);

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								rotateAngle, 0)),
						new KeyFrame(Duration.seconds(1), new KeyValue(
								rotateAngle, 90)),
						new KeyFrame(Duration.seconds(2), new KeyValue(
								rotateAngle, 180)),
						new KeyFrame(Duration.seconds(3), new KeyValue(
								rotateAngle, 90)),
						new KeyFrame(Duration.seconds(4), new KeyValue(
								rotateAngle, 0)))
				.cycleCount(Timeline.INDEFINITE).build().play();

		menu.getTransforms().add(menuRotate);

		root.getChildren().addAll(menu);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Group generateMenus() {

		Group menuGroup = new Group();

		int menuLength = 360 / menuCount.get() - menuGap;
		for (int i = 0; i < menuCount.get(); i++) {
			Arc outerArc = ArcBuilder
					.create()
					.centerX(centerX)
					.centerY(centerY)
					.radiusX(menuOuterRadius.get())
					.radiusY(menuOuterRadius.get())
					.type(ArcType.ROUND)
					.startAngle(startAngle)
					.length(menuLength)
					.transforms(
							RotateBuilder.create().pivotX(centerX)
									.pivotY(centerY).pivotZ(0)
									.angle(i * (menuLength + menuGap))
									.axis(Rotate.Z_AXIS).build()).build();

			Arc innerArc = ArcBuilder
					.create()
					.centerX(centerX)
					.centerY(centerY)
					.radiusX(menuInnerRadius.get())
					.radiusY(menuInnerRadius.get())
					.type(ArcType.ROUND)
					.startAngle(startAngle)
					.length(menuLength)
					.transforms(
							RotateBuilder.create().pivotX(centerX)
									.pivotY(centerY).pivotZ(0)
									.angle(i * (menuLength + menuGap))
									.axis(Rotate.Z_AXIS).build()).build();

			final Shape menuItem = Shape.subtract(outerArc, innerArc);
			menuItem.setFill(Color.SEAGREEN);

			menuItem.setOnMouseEntered(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {

					menuItem.setFill(Color.ALICEBLUE);
					menuItem.setCursor(Cursor.MOVE);
				}

			});

			menuItem.setOnMouseExited(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {

					menuItem.setFill(Color.SEAGREEN);
					menuItem.setCursor(Cursor.DEFAULT);

				}

			});

			menuGroup.getChildren().addAll(menuItem);

		}
		final Circle center = new Circle(centerX, centerY,
				menuInnerRadius.get() - 10);
		center.setFill(Color.SEAGREEN);

		center.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				center.setFill(Color.ALICEBLUE);
				center.setCursor(Cursor.MOVE);

			}

		});

		center.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				center.setFill(Color.SEAGREEN);
				center.setCursor(Cursor.DEFAULT);

			}

		});

		menuGroup.getChildren().addAll(center);

		return menuGroup;
	}

	public static void main(String[] args) {

		launch(args);
	}

}
