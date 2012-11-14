package HighlightText;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;

public class HighlightTextNode extends AnchorPane {

	private SimpleDoubleProperty lineStartX = new SimpleDoubleProperty(0);
	private SimpleDoubleProperty lineEndX = new SimpleDoubleProperty(0);
	private Text highlightText;
	private Line underLine;
	private Timeline growUnderLine;
	private Timeline shrinkUnderLine;

	public HighlightTextNode(String textValue) {

		highlightText = TextBuilder.create().text(textValue)
				.textAlignment(TextAlignment.CENTER).build();

		underLine = LineBuilder.create().translateY(2).startY(0).endY(0)
				.stroke(Color.BLACK).strokeWidth(2).build();
		underLine.startXProperty().bind(lineStartX);
		underLine.endXProperty().bind(lineEndX);

		growUnderLine = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0),
								new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {

										underLine.setOpacity(1);
									}

								}, new KeyValue(lineStartX, 0,
										Interpolator.EASE_IN), new KeyValue(
										lineEndX, 0, Interpolator.EASE_IN)),
						new KeyFrame(Duration.seconds(1), new KeyValue(
								lineStartX, 0, Interpolator.EASE_OUT),
								new KeyValue(lineEndX, highlightText
										.getBoundsInLocal().getWidth(),
										Interpolator.EASE_OUT))).build();

		shrinkUnderLine = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(0), new KeyValue(
								lineStartX, 0, Interpolator.EASE_IN),
								new KeyValue(lineEndX, highlightText
										.getBoundsInLocal().getWidth(),
										Interpolator.EASE_IN)),
						new KeyFrame(Duration.seconds(1),
								new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {

										underLine.setOpacity(0);
									}

								}, new KeyValue(lineStartX, highlightText
										.getBoundsInLocal().getWidth(),
										Interpolator.EASE_OUT), new KeyValue(
										lineEndX, highlightText
												.getBoundsInLocal().getWidth(),
										Interpolator.EASE_OUT))).build();

		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				growUnderLine.play();

			}
		});

		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				shrinkUnderLine.play();

			}
		});

		AnchorPane.setTopAnchor(highlightText, 0.0);
		AnchorPane.setBottomAnchor(underLine, 0.0);
		getChildren().addAll(highlightText, underLine);
	}
}
