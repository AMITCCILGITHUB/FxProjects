import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author jpgough
 */
public class FuturesJavaFXExample extends Application {

	public static void main(String[] args) {

		Application.launch(FuturesJavaFXExample.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader
				.load(getClass().getResource("FutureView.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(
				getClass().getResource("FutureManager.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}