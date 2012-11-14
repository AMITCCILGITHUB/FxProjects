import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPathBuilder;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

public class RoundButtonExample extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	private DoubleBinding scaleFactor;

	@Override
	public void start(Stage primaryStage) {

		EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Platform.exit();
			}
		};

		HBox layout = new HBox(10);
		layout.getChildren().addAll(
				scaleTo(60, closeButton(textCloseIcon(), exitHandler)),
				scaleTo(90, closeButton(pathCloseIcon(), exitHandler)),
				scaleTo(120, closeButton(svgPathCloseIcon(), exitHandler)));

		layout.getStylesheets()
				.add(this.getClass().getResource("roundbutton.css")
						.toExternalForm());
		primaryStage.setScene(new Scene(layout));
		primaryStage.show();
	}

	private Region closeButton(Node closeIcon,
			EventHandler<ActionEvent> actionHandler) {

		final double BUTTON_SIZE = 30;

		StackPane closeButton = new StackPane();
		closeButton.getStyleClass().add("closebutton");
		Button button = new Button();
		button.setOnAction(actionHandler);

		closeIcon.setMouseTransparent(true);

		closeButton.getChildren().addAll(button, closeIcon);
		button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
		closeButton.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
		closeButton
				.setMaxSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE);
		return closeButton;
	}

	private Node textCloseIcon() {

		return TextBuilder.create().text("X").styleClass("closetext").build();
	}

	private Node pathCloseIcon() {

		Path path = new Path();
		path.getElements().addAll(new MoveTo(9, 9), new LineTo(21, 21),
				new MoveTo(9, 21), new LineTo(21, 9));
		path.getStyleClass().add("closeicon");

		return path;
	}

	private Node svgPathCloseIcon() {

		return SVGPathBuilder.create().content("M9 9 L21 21 M9 21 L21 9")
				.styleClass("closeicon").build();
	}

	private Group scaleTo(double size, Region n) {

		Group g = new Group(n);
		scaleFactor = (new SimpleDoubleProperty(size))
				.divide(n.widthProperty());

		n.scaleXProperty().bind(scaleFactor);
		n.scaleYProperty().bind(scaleFactor);

		return g;
	}
}