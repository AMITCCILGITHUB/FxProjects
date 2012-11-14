import java.net.URL;
import java.util.Map;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.MapChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class FxMediaPlayer extends Application {

	ImageView img = null;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Mdeia");
		final Group root = new Group();
		final Scene scene = new Scene(root, 500, 500);

		img = new ImageView();

		URL resource = getClass().getResource("Sleep Away.mp3");
		final Media media = new Media(resource.toString());
		media.getMetadata().addListener(
				new MapChangeListener<String, Object>() {

					@Override
					public void onChanged(
							Change<? extends String, ? extends Object> arg0) {

						if (arg0.wasAdded()) {
							handleMetaData(arg0.getKey(), arg0.getValueAdded());
						}

						Set<Map.Entry<String, Object>> entries = media
								.getMetadata().entrySet();
						for (Map.Entry<String, Object> entry : entries) {
							String key = entry.getKey();
							Object value = entry.getValue();
							System.out.println("Key : " + key + " Value : "
									+ value);
						}

					}

				});

		MediaPlayer player = new MediaPlayer(media);
		player.play();

		root.getChildren().add(img);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}

	private void handleMetaData(String key, Object value) {

		if (key.equalsIgnoreCase("album")) {
			System.out.println("Album : " + value.toString());
		} else if (key.equalsIgnoreCase("artist")) {
			System.out.println("Artist : " + value.toString());
		} else if (key.equalsIgnoreCase("title")) {
			System.out.println("Title : " + value.toString());
		} else if (key.equalsIgnoreCase("year")) {
			System.out.println("Year : " + value.toString());
		} else if (key.equalsIgnoreCase("image")) {
			img.setImage((Image) value);
		}
	}
}
