package AutoSuggest;
import javafx.scene.layout.HBox;

public class AutoSuggestBox extends HBox {

	public AutoSuggestBox() {

		super(8);

		setPrefHeight(32);

		getStyleClass().add("autosuggestbox");

		getChildren().addAll(new SelectedItem(0, "Test", this),
				new SelectedItem(1, "Test 2", this));
	}
}
