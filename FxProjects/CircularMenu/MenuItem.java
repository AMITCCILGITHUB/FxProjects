import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.util.Duration;

public class MenuItem extends StackPane {

	SimpleDoubleProperty opacity = new SimpleDoubleProperty(0);
	SimpleDoubleProperty offset = new SimpleDoubleProperty(0);
	SimpleDoubleProperty translateX = new SimpleDoubleProperty(0);
	SimpleDoubleProperty translateY = new SimpleDoubleProperty(0);
	SimpleDoubleProperty rotationAngle = new SimpleDoubleProperty(0);

	Timeline timeLine;
	Timeline revTimeLine;

	public MenuItem(final MenuUtils.MenuType menuType, String menuIgame,
			double centerX, double centerY, double radius, double angle) {

		if (menuType == MenuUtils.MenuType.CENTER)
			opacity.setValue(1);

		translateX.bind(offset.multiply(Math.cos(Math.toRadians(angle))));
		translateY.bind(offset.negate().multiply(
				Math.sin(Math.toRadians(angle))));

		Rotate rotate = RotateBuilder.create().pivotX(centerX).pivotY(centerY)
				.build();
		rotate.angleProperty().bind(rotationAngle);

		MenuItemView menuItem = new MenuItemView(menuIgame, centerX, centerY,
				radius);
		menuItem.translateXProperty().bind(translateX);
		menuItem.translateYProperty().bind(translateY);
		menuItem.getTransforms().add(rotate);
		menuItem.opacityProperty().bind(opacity);

		timeLine = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0),
								new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {

										if (menuType == MenuUtils.MenuType.LEAF)
											opacity.setValue(1);
									}

								}, new KeyValue(offset, 0), new KeyValue(
										rotationAngle, 0)),
						new KeyFrame(
								Duration.seconds(MenuUtils.DURATION),
								new KeyValue(
										offset,
										(menuType == MenuUtils.MenuType.CENTER ? 0
												: MenuUtils.OFFSET)),
								new KeyValue(rotationAngle, MenuUtils.ROTATION)))
				.build();

		revTimeLine = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(offset,
								(menuType == MenuUtils.MenuType.CENTER ? 0
										: MenuUtils.OFFSET)), new KeyValue(
								rotationAngle, MenuUtils.ROTATION)),
						new KeyFrame(Duration.seconds(MenuUtils.DURATION),
								new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {

										if (menuType == MenuUtils.MenuType.LEAF)
											opacity.setValue(0);
									}

								}, new KeyValue(offset, 0), new KeyValue(
										rotationAngle, 0))).build();

		setMaxWidth(radius);
		setMaxHeight(radius);
		getChildren().addAll(menuItem);
	}

	public void startAnimation() {

		timeLine.play();
	}

	public void startReverseAnimation() {

		revTimeLine.play();
	}

	public void stopAnimation() {

		timeLine.stop();
	}

	public void stopReverseAnimation() {

		revTimeLine.stop();
	}
}
