import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Note that this clock does not keep perfect time, but is close. It's main
 * purpose is to demonstrate various features of JavaFX.
 */
public class Clock extends Application {

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	public void start(final Stage stage) throws Exception {

		// construct the analogueClock pieces.
		final Circle face = new Circle(100, 100, 100);
		face.setId("face");
		final Label brand = new Label("Amit");
		brand.setId("brand");
		brand.layoutXProperty().bind(
				face.centerXProperty()
						.subtract(brand.widthProperty().divide(2)));
		brand.layoutYProperty().bind(
				face.centerYProperty().add(face.radiusProperty().divide(2)));
		final Line hourHand = new Line(0, 0, 0, -50);
		hourHand.setTranslateX(100);
		hourHand.setTranslateY(100);
		hourHand.setId("hourHand");
		final Line minuteHand = new Line(0, 0, 0, -75);
		minuteHand.setTranslateX(100);
		minuteHand.setTranslateY(100);
		minuteHand.setId("minuteHand");
		final Line secondHand = new Line(0, 15, 0, -88);
		secondHand.setTranslateX(100);
		secondHand.setTranslateY(100);
		secondHand.setId("secondHand");
		final Circle spindle = new Circle(100, 100, 5);
		spindle.setId("spindle");
		Group ticks = new Group();
		for (int i = 0; i < 12; i++) {
			Line tick = new Line(0, -83, 0, -93);
			tick.setTranslateX(100);
			tick.setTranslateY(100);
			tick.getStyleClass().add("tick");
			tick.getTransforms().add(new Rotate(i * (360 / 12)));
			ticks.getChildren().add(tick);
		}
		final Group analogueClock = new Group(face, brand, ticks, spindle,
				hourHand, minuteHand, secondHand);

		// construct the digitalClock pieces.
		final Label digitalClock = new Label();
		digitalClock.setId("digitalClock");

		// determine the starting time.
		Calendar calendar = GregorianCalendar.getInstance();
		final double seedSecondDegrees = calendar.get(Calendar.SECOND)
				* (360 / 60);
		final double seedMinuteDegrees = (calendar.get(Calendar.MINUTE) + seedSecondDegrees / 360.0)
				* (360 / 60);
		final double seedHourDegrees = (calendar.get(Calendar.HOUR) + seedMinuteDegrees / 360.0)
				* (360 / 12);

		// define rotations to map the analogueClock to the current time.
		final Rotate hourRotate = new Rotate(seedHourDegrees);
		final Rotate minuteRotate = new Rotate(seedMinuteDegrees);
		final Rotate secondRotate = new Rotate(seedSecondDegrees);
		hourHand.getTransforms().add(hourRotate);
		minuteHand.getTransforms().add(minuteRotate);
		secondHand.getTransforms().add(secondRotate);

		// the hour hand rotates twice a day.
		final Timeline hourTime = new Timeline(new KeyFrame(Duration.hours(12),
				new KeyValue(hourRotate.angleProperty(), 360 + seedHourDegrees,
						Interpolator.LINEAR)));

		// the minute hand rotates once an hour.
		final Timeline minuteTime = new Timeline(new KeyFrame(
				Duration.minutes(60), new KeyValue(
						minuteRotate.angleProperty(), 360 + seedMinuteDegrees,
						Interpolator.LINEAR)));

		// move second hand rotates once a minute.
		final Timeline secondTime = new Timeline(new KeyFrame(
				Duration.seconds(60), new KeyValue(
						secondRotate.angleProperty(), 360 + seedSecondDegrees,
						Interpolator.LINEAR)));

		// the digital clock updates once a second.
		final Timeline digitalTime = new Timeline(new KeyFrame(
				Duration.seconds(0), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent actionEvent) {

						Calendar calendar = GregorianCalendar.getInstance();
						String hourString = pad(2, '0',
								calendar.get(Calendar.HOUR) == 0 ? "12"
										: calendar.get(Calendar.HOUR) + "");
						String minuteString = pad(2, '0',
								calendar.get(Calendar.MINUTE) + "");
						String secondString = pad(2, '0',
								calendar.get(Calendar.SECOND) + "");
						String ampmString = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM"
								: "PM";
						digitalClock.setText(hourString + ":" + minuteString
								+ ":" + secondString + " " + ampmString);
					}
				}), new KeyFrame(Duration.seconds(1)));

		// time never ends.
		hourTime.setCycleCount(Animation.INDEFINITE);
		minuteTime.setCycleCount(Animation.INDEFINITE);
		secondTime.setCycleCount(Animation.INDEFINITE);
		digitalTime.setCycleCount(Animation.INDEFINITE);

		// start the analogueClock.
		digitalTime.play();
		secondTime.play();
		minuteTime.play();
		hourTime.play();

		stage.initStyle(StageStyle.TRANSPARENT);

		// add a glow effect whenever the mouse is positioned over the clock.
		final Glow glow = new Glow();
		analogueClock.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				analogueClock.setEffect(glow);
			}
		});
		analogueClock.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				analogueClock.setEffect(null);
			}
		});

		// fade out the scene and shut it down when the mouse is clicked on the
		// clock.
		analogueClock.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				analogueClock.setMouseTransparent(true);
				FadeTransition fade = new FadeTransition(Duration.seconds(1.2),
						analogueClock);
				fade.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent actionEvent) {

						stage.close();
					}
				});
				fade.setFromValue(1);
				fade.setToValue(0);
				fade.play();
			}
		});

		// layout the scene.
		final VBox layout = new VBox();
		layout.getChildren().addAll(analogueClock, digitalClock);
		layout.setAlignment(Pos.CENTER);
		final Scene scene = new Scene(layout, Color.TRANSPARENT);
		scene.getStylesheets().add(getResource("clock.css"));
		stage.setScene(scene);

		// allow the clock background to be used to drag the clock around.
		final Delta dragDelta = new Delta();
		layout.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				// record a delta distance for the drag and drop operation.
				dragDelta.x = stage.getX() - mouseEvent.getScreenX();
				dragDelta.y = stage.getY() - mouseEvent.getScreenY();
				scene.setCursor(Cursor.MOVE);
			}
		});
		layout.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				scene.setCursor(Cursor.HAND);
			}
		});
		layout.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				stage.setX(mouseEvent.getScreenX() + dragDelta.x);
				stage.setY(mouseEvent.getScreenY() + dragDelta.y);
			}
		});
		layout.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (!mouseEvent.isPrimaryButtonDown()) {
					scene.setCursor(Cursor.HAND);
				}
			}
		});
		layout.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (!mouseEvent.isPrimaryButtonDown()) {
					scene.setCursor(Cursor.DEFAULT);
				}
			}
		});

		// show the scene.
		stage.show();
	}

	private String pad(int fieldWidth, char padChar, String s) {

		StringBuilder sb = new StringBuilder();
		for (int i = s.length(); i < fieldWidth; i++) {
			sb.append(padChar);
		}
		sb.append(s);

		return sb.toString();
	}

	static String getResource(String path) {

		return Clock.class.getResource(path).toExternalForm();
	}

	// records relative x and y co-ordinates.
	class Delta {

		double x, y;
	}
}