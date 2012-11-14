package AutoSuggest;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxMain extends Application {

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Fx Controls");
		VBox root = new VBox();
		Scene scene = new Scene(root, 500, 500);
		scene.setCamera(new PerspectiveCamera());

		scene.getStylesheets().addAll(
				FxMain.class.getResource("../style.css").toExternalForm());

		AutoSuggestBox asb = new AutoSuggestBox();
		root.getChildren().addAll(asb);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
