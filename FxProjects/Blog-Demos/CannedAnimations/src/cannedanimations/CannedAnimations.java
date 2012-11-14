package CannedAnimations.src.cannedanimations;

import javafx.animation.PauseTransitionBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.com.fxexperience.javafx.animation.BounceInDownTransition;
import src.com.fxexperience.javafx.animation.BounceInLeftTransition;
import src.com.fxexperience.javafx.animation.BounceInRightTransition;
import src.com.fxexperience.javafx.animation.BounceInTransition;
import src.com.fxexperience.javafx.animation.BounceInUpTransition;
import src.com.fxexperience.javafx.animation.BounceOutDownTransition;
import src.com.fxexperience.javafx.animation.BounceOutLeftTransition;
import src.com.fxexperience.javafx.animation.BounceOutRightTransition;
import src.com.fxexperience.javafx.animation.BounceOutTransition;
import src.com.fxexperience.javafx.animation.BounceOutUpTransition;
import src.com.fxexperience.javafx.animation.BounceTransition;
import src.com.fxexperience.javafx.animation.FadeInDownBigTransition;
import src.com.fxexperience.javafx.animation.FadeInDownTransition;
import src.com.fxexperience.javafx.animation.FadeInLeftBigTransition;
import src.com.fxexperience.javafx.animation.FadeInLeftTransition;
import src.com.fxexperience.javafx.animation.FadeInRightBigTransition;
import src.com.fxexperience.javafx.animation.FadeInRightTransition;
import src.com.fxexperience.javafx.animation.FadeInTransition;
import src.com.fxexperience.javafx.animation.FadeInUpBigTransition;
import src.com.fxexperience.javafx.animation.FadeInUpTransition;
import src.com.fxexperience.javafx.animation.FadeOutDownBigTransition;
import src.com.fxexperience.javafx.animation.FadeOutDownTransition;
import src.com.fxexperience.javafx.animation.FadeOutLeftBigTransition;
import src.com.fxexperience.javafx.animation.FadeOutLeftTransition;
import src.com.fxexperience.javafx.animation.FadeOutRightBigTransition;
import src.com.fxexperience.javafx.animation.FadeOutRightTransition;
import src.com.fxexperience.javafx.animation.FadeOutTransition;
import src.com.fxexperience.javafx.animation.FadeOutUpBigTransition;
import src.com.fxexperience.javafx.animation.FadeOutUpTransition;
import src.com.fxexperience.javafx.animation.FlashTransition;
import src.com.fxexperience.javafx.animation.FlipInXTransition;
import src.com.fxexperience.javafx.animation.FlipInYTransition;
import src.com.fxexperience.javafx.animation.FlipOutXTransition;
import src.com.fxexperience.javafx.animation.FlipOutYTransition;
import src.com.fxexperience.javafx.animation.FlipTransition;
import src.com.fxexperience.javafx.animation.HingeTransition;
import src.com.fxexperience.javafx.animation.PulseTransition;
import src.com.fxexperience.javafx.animation.RollInTransition;
import src.com.fxexperience.javafx.animation.RollOutTransition;
import src.com.fxexperience.javafx.animation.RotateInDownLeftTransition;
import src.com.fxexperience.javafx.animation.RotateInDownRightTransition;
import src.com.fxexperience.javafx.animation.RotateInTransition;
import src.com.fxexperience.javafx.animation.RotateInUpLeftTransition;
import src.com.fxexperience.javafx.animation.RotateInUpRightTransition;
import src.com.fxexperience.javafx.animation.RotateOutDownLeftTransition;
import src.com.fxexperience.javafx.animation.RotateOutDownRightTransition;
import src.com.fxexperience.javafx.animation.RotateOutTransition;
import src.com.fxexperience.javafx.animation.RotateOutUpLeftTransition;
import src.com.fxexperience.javafx.animation.RotateOutUpRightTransition;
import src.com.fxexperience.javafx.animation.ShakeTransition;
import src.com.fxexperience.javafx.animation.SwingTransition;
import src.com.fxexperience.javafx.animation.TadaTransition;
import src.com.fxexperience.javafx.animation.WobbleTransition;
import src.com.fxexperience.javafx.util.GoogleWebFontHelper;

/*
 * Simple Demo application for the Canned animation transitions in 
 * FXExperience Controls
 * 
 * @author Jasper Potts
 */
public class CannedAnimations extends Application {

	private Button btn;

	@Override
	public void start(Stage primaryStage) {

		GoogleWebFontHelper
				.loadFont("http://fonts.googleapis.com/css?family=Francois+One");
		GoogleWebFontHelper
				.loadFont("http://fonts.googleapis.com/css?family=Lato");

		VBox root = new VBox();
		ScrollPane scrollPane;
		root.setAlignment(Pos.CENTER);
		root.getChildren()
				.addAll(StackPaneBuilder
						.create()
						.id("animateTestArea")
						.children(
								btn = ButtonBuilder.create()
										.text("Dude this is a Button!")
										.id("animateTest").build())
						.padding(new Insets(80, 0, 50, 0)).build(),
						scrollPane = ScrollPaneBuilder
								.create()
								.content(
										VBoxBuilder
												.create()
												.spacing(10)
												.padding(new Insets(20))
												.fillWidth(true)
												.alignment(Pos.CENTER)
												.children(
														LabelBuilder
																.create()
																.text("Attention seekers")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Flash")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FlashTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Shake")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new ShakeTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Tada")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new TadaTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Swing")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new SwingTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Wobble")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new WobbleTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Pulse")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new PulseTransition(
																										btn)
																										.play();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Flippers")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Flip")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FlipTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Flip In X")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FlipInXTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Flip Out X")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FlipOutXTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Flip In Y")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FlipInYTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Flip Out Y")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FlipOutYTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Fading entrances")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Fade In")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Up")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInUpTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Down")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInDownTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInLeftTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInRightTransition(
																										btn)
																										.play();
																							}
																						})
																				.build())
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Fade In Up Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInUpBigTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Down Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInDownBigTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Left Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInLeftBigTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade In Right Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeInRightBigTransition(
																										btn)
																										.play();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Fading exits")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Fade Out")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Up")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutUpTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Down")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutDownTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutLeftTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutRightTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build())
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Fade Out Up Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutUpBigTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Down Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutDownBigTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Left Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutLeftBigTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Fade Out Right Big")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new FadeOutRightBigTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Bouncing entrances")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Bounce In")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceInTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce In Up")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceInUpTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce In Down")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceInDownTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce In Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceInLeftTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce In Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceInRightTransition(
																										btn)
																										.play();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Bouncing exits")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Bounce Out")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceOutTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce Out Up")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceOutUpTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce Out Down")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceOutDownTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce Out Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceOutLeftTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Bounce Out Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new BounceOutRightTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Rotating entrances")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Rotate In")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateInTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate In Down Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateInDownLeftTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate In Down Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateInDownRightTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate In Up Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateInUpLeftTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate In Up Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateInUpRightTransition(
																										btn)
																										.play();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Rotating exits")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Rotate Out")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateOutTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate Out Down Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateOutDownLeftTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate Out Down Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateOutDownRightTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate Out Up Left")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateOutUpLeftTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Rotate Out Up Right")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RotateOutUpRightTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build())
																.build(),
														LabelBuilder
																.create()
																.text("Specials")
																.styleClass(
																		"section-label")
																.build(),
														HBoxBuilder
																.create()
																.spacing(10)
																.maxWidth(
																		HBox.USE_PREF_SIZE)
																.children(
																		ButtonBuilder
																				.create()
																				.text("Hinge")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new HingeTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Roll In")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RollInTransition(
																										btn)
																										.play();
																							}
																						})
																				.build(),
																		ButtonBuilder
																				.create()
																				.text("Roll Out")
																				.onAction(
																						new EventHandler<ActionEvent>() {

																							@Override
																							public void handle(
																									ActionEvent t) {

																								new RollOutTransition(
																										btn)
																										.play();
																								bringBackAfter();
																							}
																						})
																				.build())
																.build())
												.build()).build());
		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		primaryStage.setScene(SceneBuilder
				.create()
				.width(800)
				.height(700)
				.root(root)
				.stylesheets("cannedanimations/LightTheme.css",
						"cannedanimations/CannedAnimations.css").build());
		primaryStage.setTitle("Canned Animations");
		primaryStage.show();
	}

	private void bringBackAfter() {

		PauseTransitionBuilder.create().duration(Duration.seconds(1.5))
				.onFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent t) {

						btn.setOpacity(1);
					}
				}).build().play();
	}

	/* @param args the command line arguments */
	public static void main(String[] args) {

		launch(args);
	}
}