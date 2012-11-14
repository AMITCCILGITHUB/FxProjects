import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 
 * @author jpgough
 */
public class FutureController implements Initializable {

	private FutureManager futureManager;

	public FutureController() {

		futureManager = new FutureManager(this);
	}

	@FXML
	private TextArea logTextArea;

	@FXML
	private Slider duration;

	@FXML
	private TextField futureMessageTextField;

	@FXML
	private ProgressIndicator spinner;

	@FXML
	private void beginFuture(ActionEvent event) {

		int secs = (int) duration.getValue();
		appendToLogBox("Begin Future Task in " + secs);
		futureManager.createFuture(secs, futureMessageTextField.getText());
	}

	@FXML
	private void cancelFuture() {

		futureManager.cancel(true);
		appendToLogBox("Cancelled The Future");
	}

	@FXML
	private void getFuture() throws Exception {

		appendToLogBox(futureManager.get());
	}

	@FXML
	private void isFutureCancellable() {

	}

	@FXML
	private void isFutureDone() {

		appendToLogBox(Boolean.toString(futureManager.isDone()));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// TODO
	}

	private void appendToLogBox(String newMessage) {

		logTextArea.appendText("\n" + newMessage);
	}

	void showSpinner() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				spinner.setVisible(true);
			}
		});
	}

	void hideSpinner() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				spinner.setVisible(false);
			}
		});
	}
}