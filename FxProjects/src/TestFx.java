import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

public class TestFx extends Application {

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Popup Example");
		final Group root = new Group();
		final Scene scene = new Scene(root, 300, 250);

		final VBox popupBOX = VBoxBuilder.create()
				.children(new Label("MY CONTENT")).visible(false).build();

		final Button firePopup = ButtonBuilder.create().text("FIRE POPUP")
				.build();
		firePopup.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				new PopupMenu(scene, popupBOX).openMenu();

			}
		});

		root.getChildren().addAll(popupBOX, firePopup);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
