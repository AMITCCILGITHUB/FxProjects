package HighlightText;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HightlightText extends Application {

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Popup Example");
		final Group root = new Group();
		final Scene scene = new Scene(root, 300, 250);

		HighlightTextNode text = new HighlightTextNode("Amit Pandey");

		root.getChildren().add(text);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
