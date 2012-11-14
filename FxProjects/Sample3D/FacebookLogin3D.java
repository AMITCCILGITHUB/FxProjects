import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransformBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FacebookLogin3D extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("3D Web Browser");

		VBox gp = new VBox();
		final WebView webView = new WebView();
		final WebEngine webEngine = webView.getEngine();
		webEngine.load("http://google.com/");
		gp.setEffect(PerspectiveTransformBuilder.create().ulx(0.0).uly(0.0)
				.urx(300.0).ury(0.0).lrx(300.0).lry(600.0).llx(0.0).lly(600.0)
				.build());

		gp.getChildren().add(webView);

		VBox gp1 = new VBox();
		final WebView webView1 = new WebView();
		final WebEngine webEngine1 = webView1.getEngine();
		webEngine1.load("http://google.com/");

		gp1.setEffect(PerspectiveTransformBuilder.create().ulx(100.0).uly(50.0)
				.urx(300.0).ury(0.0).lrx(300.0).lry(600.0).llx(100.0)
				.lly(360.0).build());

		gp1.getChildren().add(webView1);

		final HBox root = new HBox();
		root.getChildren().addAll(gp1, gp);
		primaryStage.setScene(new Scene(root, 600, 600));
		primaryStage.show();
	}
}