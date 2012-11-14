package src.com.fxexperience.tools.splineeditor;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.SceneBuilder;
import javafx.stage.Stage;
import src.com.fxexperience.tools.Tool;

/**
 * @author Jasper Potts
 */
public class SplineEditorStandalone extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		SplineEditor splineEditor = new SplineEditor();
		primaryStage.setScene(SceneBuilder
				.create()
				.root(splineEditor)
				.width(800)
				.height(600)
				.stylesheets(
						Tool.class.getResource("Tools.css").toExternalForm())
				.build());
		primaryStage.show();
	}
}
