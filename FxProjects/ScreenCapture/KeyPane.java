import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

class KeyPane extends Group {

	public KeyPane(final Stage stage, final Rebounder rebounder) {

		final Rectangle keyEventPane = new Rectangle(
				ScreenCapture.getScreenCapture().screenBounds.getWidth(),
				ScreenCapture.getScreenCapture().screenBounds.getHeight());
		keyEventPane.setFill(Color.TRANSPARENT);
		keyEventPane.setStroke(null);
		keyEventPane.setMouseTransparent(true);
		keyEventPane.setFocusTraversable(true); // for keyPressed events
		keyEventPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {

				if (key.getCode() == KeyCode.ESCAPE) {
					stage.hide();
				} else if (key.getCode() == KeyCode.ENTER) {
					System.out.println("Enter Pressed");
					ScreenCapture.getScreenCapture()
							.capture(
									new Rectangle(ScreenCapture
											.getScreenCapture().screenBounds
											.getWidth() + 1, ScreenCapture
											.getScreenCapture().screenBounds
											.getHeight() + 1));
				}
			}
		});
		getChildren().add(keyEventPane);
	}
}