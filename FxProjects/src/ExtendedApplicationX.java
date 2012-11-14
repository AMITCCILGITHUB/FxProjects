import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * 
 * @author Goran Lochert Class that extends Application class and adds some
 *         basic functionality that is needed in almost every JavaFX App, good
 *         for demos.
 */
public abstract class ExtendedApplicationX extends Application {

	protected double DEFAULT_APP_WIDTH = 1320;
	protected double DEFAULT_APP_HEIGHT = 600;
	protected Stage stage;
	protected ScrollPane root;
	protected Scene scene;

	@Override
	public void start(Stage primaryStage) {

		preSetup(primaryStage);
		setup();
		postSetup();
	}

	protected abstract void setup();

	protected void preSetup(Stage primaryStage) {

		stage = primaryStage;
		stage.setTitle(getAppTitle());
		root = new ScrollPane();
		// root.getStyleClass().add("root-pane"); // it is root by default since
		// b40
		scene = new Scene(root, getAppWidth(), getAppHeight());
		stage.setScene(scene);
		setupCss();
	}

	protected void postSetup() {

		stage.centerOnScreen();
		stage.show();
	}

	// override to change APP WIDTH
	protected double getAppWidth() {

		return DEFAULT_APP_WIDTH;
	}

	// override to change APP HEIGHT
	protected double getAppHeight() {

		return DEFAULT_APP_HEIGHT;
	}

	// override to set App Title
	protected String getAppTitle() {

		return getClass().getSimpleName();
	}

	// override to add CSS styles
	protected void setupCss() {

	}

	public static void launchWithPrelaunch(
			java.lang.Class<? extends Application> appClass,
			java.lang.String[] args) {

		ApplicationFx.prelaunch();
		launch(appClass, args);
	}
}