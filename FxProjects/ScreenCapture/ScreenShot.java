import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScreenShot extends Application {

	@Override
	public void start(Stage primaryStage) {

		Group group = new Group();
		Scene scene = new Scene(group);
		scene.setFill(Color.TRANSPARENT);
		scene.setCursor(Cursor.CROSSHAIR);

		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
