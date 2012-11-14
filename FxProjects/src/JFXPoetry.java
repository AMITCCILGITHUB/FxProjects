import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class JFXPoetry extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Pippa's Song by Robert Browning");
		stage.setResizable(false);

		StackPane root = new StackPane();
		stage.setScene(new Scene(root, 500, 375));

		Image image = new Image(
				"http://farm1.static.flickr.com/39/121693644_75491b23b0.jpg");
		ImageView imageView = new ImageView(image);
		root.getChildren().add(imageView);

		Text text = new Text("The year's at the spring,\n"
				+ "And day's at the morn;\n" + "Morning's at seven;\n"
				+ "The hill-side's dew-pearled;\n"
				+ "The lark's on the wing;\n" + "The snail's on the thorn;\n"
				+ "God's in His heaven--\n" + "All's right with the world!");
		text.setFont(Font.font("Serif", FontWeight.BOLD, 30));
		text.setFill(Color.GOLDENROD);
		text.setEffect(DropShadowBuilder.create().radius(3).spread(0.5).build());
		text.setCache(true);
		root.getChildren().add(text);

		final TranslateTransition translate = TranslateTransitionBuilder
				.create().duration(Duration.seconds(24)).node(text)
				.fromY(image.getHeight()).toY(0)
				.interpolator(Interpolator.EASE_OUT).build();
		translate.play();

		final FadeTransition fade = FadeTransitionBuilder.create()
				.duration(Duration.seconds(5)).node(imageView).fromValue(0)
				.toValue(1).interpolator(Interpolator.EASE_OUT).build();
		fade.play();

		Media media = new Media(
				"http://video.fws.gov/sounds/35indigobunting.mp3");
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();

		Button play = new Button("Play Again");
		root.getChildren().add(play);
		play.visibleProperty().bind(
				translate.statusProperty().isEqualTo(Animation.Status.STOPPED));
		play.addEventHandler(ActionEvent.ACTION,
				new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						fade.playFromStart();
						translate.playFromStart();
						mediaPlayer.stop();
						mediaPlayer.play();
					}
				});

		stage.show();
	}
}
