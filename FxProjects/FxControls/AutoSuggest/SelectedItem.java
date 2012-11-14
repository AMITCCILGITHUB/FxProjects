package AutoSuggest;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class SelectedItem extends Region {

	private int sidemargin = 12;

	private Label text;
	private Button close;

	public SelectedItem(final int index, final String textValue,
			final AutoSuggestBox autoSuggestBox) {

		setPrefSize(80, 24);
		getStyleClass().add("selecteditem");

		text = LabelBuilder.create().text(textValue).build();
		close = ButtonBuilder.create().text("x").prefWidth(24).prefHeight(24)
				.build();

		final Timeline tl = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.millis(0), new KeyValue(
								opacityProperty(), 1), new KeyValue(
								scaleXProperty(), 1.2), new KeyValue(
								scaleYProperty(), 1.2)),
						new KeyFrame(Duration.millis(160), new KeyValue(
								opacityProperty(), 0), new KeyValue(
								scaleXProperty(), 1.0), new KeyValue(
								scaleYProperty(), 1.0)),
						new KeyFrame(Duration.millis(200),
								new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent arg0) {

										autoSuggestBox.getChildren().remove(
												index);

									}

								})).build();

		close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				tl.play();

			}

		});

		getChildren().addAll(text, close);

	}

	@Override
	protected void layoutChildren() {

		text.resizeRelocate(sidemargin, 0, getWidth() - close.getPrefWidth()
				- 2 * sidemargin, getHeight());
		close.resizeRelocate(getWidth() - close.getPrefWidth(), 0,
				close.getPrefWidth(), getHeight());
	}
}
