import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import org.w3c.dom.Node;

public class FacebookLogin extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Facebook Login Example");
		final WebView webView = new WebView();
		final WebEngine webEngine = webView.getEngine();
		webEngine.getLoadWorker().stateProperty()
				.addListener(new ChangeListener<State>() {

					@Override
					public void changed(ObservableValue<? extends State> arg0,
							State arg1, State arg2) {

						if (webEngine.getDocument() != null) {
							Node loginForm = webEngine.getDocument()
									.getElementById("login_form");
							Node email = webEngine.getDocument()
									.getElementById("email");
							Node pass = webEngine.getDocument().getElementById(
									"pass");
							if (email != null && pass != null
									&& loginForm != null) {
								((JSObject) email).setMember("value", "*****");
								((JSObject) pass).setMember("value", "******");
								((JSObject) loginForm).call("submit");
							}
						}
					}
				});
		webEngine.load("http://facebook.com/");

		final BorderPane root = new BorderPane();
		root.setCenter(webView);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}