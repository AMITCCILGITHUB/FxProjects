/*
 *  Copyright (c) 2012 Michael Zucchi
 *
 *  This programme is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This programme is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this programme.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author notzed
 */
public class FXSlideZ extends Application {

	double rate = 1;
	StackPane root;
	Node current;
	Node next;
	//
	double width = 800;
	double height = 600;
	ImageLoader loader;

	@Override
	public void start(Stage primaryStage) {

		loader = new ResourceLoader();
		loader.start();

		root = new StackPane();
		root.setStyle("-fx-background-color: #000000;");

		Scene scene = new Scene(root, width, height);

		primaryStage.setTitle("Photos");
		primaryStage.setScene(scene);
		// primaryStage.setFullScreen(true);
		primaryStage.show();

		// width = 0;//(int) scene.getWidth();
		// height = 0;//(int) scene.getHeight();

		Image image = loader.getNextImage();

		if (image != null)
			startImage(image);
	}

	Interpolator dropin = Interpolator.SPLINE(0, 0.5, 0.2, 1);
	Random rand = new Random();
	Random brand = new Random();

	Node startImageBlocks(Image image) {

		// break image into blocks and animate them separately
		double w = image.getWidth();
		double h = image.getHeight();
		int tilesx = 16;
		int tilesy = 12;

		Duration time = Duration.seconds(1);

		int delaytype = rand.nextInt(8);
		int transtype = rand.nextInt(6);
		int rotatetype = rand.nextInt(4);
		int scaletype = rand.nextInt(3);

		ParallelTransition pt = new ParallelTransition();
		Pane g = new Pane();
		ObservableList<Node> childs = g.getChildren();
		for (int y = 0; y < tilesy; y++) {
			for (int x = 0; x < tilesx; x++) {
				ImageView iv = new ImageView(image);
				iv.setViewport(new Rectangle2D(x * w / tilesx, y * h / tilesy,
						w / tilesx, h / tilesy));
				iv.relocate(x * w / tilesx, y * h / tilesy);
				// iv.setFitWidth(w/tilesx);
				// iv.setFitHeight(h/tilesy);
				childs.add(iv);

				Duration delay = Duration.ZERO;

				switch (delaytype) {
					case 0:
						break;
					case 1:
						delay = Duration.millis(20 * (x + y * 4));
						break;
					case 2: { // grow out from centre
						int xx = x - tilesx / 2;
						int yy = y - tilesy / 2;
						double d = Math.sqrt(xx * xx + yy * yy);
						delay = Duration.millis(d * 150);
						break;
					}
					case 3: { // grow in to centre
						int xx = x - tilesx / 2;
						int yy = y - tilesy / 2;
						double d = Math.sqrt(tilesx * tilesx / 4 + tilesy
								* tilesy / 4)
								- Math.sqrt(xx * xx + yy * yy);
						delay = Duration.millis(d * 150);
						break;
					}
					case 4: // sweep right
						delay = Duration.millis(40 * x);
						break;
					case 5: // sweep down
						delay = Duration.millis(40 * y);
						break;
					case 6: // sweep left
						delay = Duration.millis(40 * (tilesx - x - 1));
						break;
					case 7: // sweep up
						delay = Duration.millis(40 * (tilesy - y - 1));
						break;
				}

				switch (transtype) {
					case 1: { // from left
						TranslateTransition t = new TranslateTransition(time,
								iv);
						iv.setTranslateX(-width);
						t.setFromX(-width);
						t.setToX(0);
						// t.setInterpolator(Interpolator.EASE_OUT);
						t.setInterpolator(dropin);
						t.setDelay(delay);
						pt.getChildren().add(t);
						break;
					}
					case 2: { // from right
						TranslateTransition t = new TranslateTransition(time,
								iv);
						iv.setTranslateX(width);
						t.setFromX(width);
						t.setToX(0);
						// t.setInterpolator(Interpolator.EASE_OUT);
						t.setInterpolator(dropin);
						t.setDelay(delay);
						pt.getChildren().add(t);
						break;
					}
					case 3: { // from top
						TranslateTransition t = new TranslateTransition(time,
								iv);
						iv.setTranslateY(-height);
						t.setFromY(-height);
						t.setToY(0);
						// t.setInterpolator(Interpolator.EASE_OUT);
						t.setInterpolator(dropin);
						t.setDelay(delay);
						pt.getChildren().add(t);
						break;
					}
					case 4: { // from bottom
						TranslateTransition t = new TranslateTransition(time,
								iv);
						iv.setTranslateY(height);
						t.setFromY(height);
						t.setToY(0);
						// t.setInterpolator(Interpolator.EASE_OUT);
						t.setInterpolator(dropin);
						t.setDelay(delay);
						pt.getChildren().add(t);
						break;
					}
					case 5: { // random
						TranslateTransition t = new TranslateTransition(time,
								iv);
						double fx = rand.nextDouble() * w * 2 - w;
						double fy = rand.nextDouble() * w * 2 - w;

						iv.setTranslateX(fx);
						iv.setTranslateY(fx);
						t.setFromX(fx);
						t.setFromY(fy);
						t.setToX(0);
						t.setToY(0);
						// t.setInterpolator(Interpolator.EASE_OUT);
						t.setInterpolator(dropin);
						t.setDelay(delay);
						pt.getChildren().add(t);
						break;
					}
				}

				switch (rotatetype) {
					case 1:
					case 2: {
						RotateTransition rt = new RotateTransition(time, iv);
						double angle;

						angle = 360 * 2;
						if (rotatetype == 2)
							angle = -angle;

						rt.setFromAngle(angle);
						iv.setRotate(angle);
						rt.setToAngle(0);
						rt.setDelay(delay);
						pt.getChildren().add(rt);
						break;
					}
					case 3: {
						RotateTransition rt = new RotateTransition(time, iv);
						double angle;

						angle = rand.nextDouble() * 360 * 2 - 360 * 1;
						rt.setFromAngle(angle);
						iv.setRotate(angle);
						rt.setToAngle(0);
						rt.setDelay(delay);
						pt.getChildren().add(rt);
						break;
					}
				}

				switch (scaletype) {
					case 1:
					case 2: {
						ScaleTransition st = new ScaleTransition(time, iv);
						st.setInterpolator(dropin);
						if (scaletype == 1) {
							st.setFromX(4);
							st.setFromY(4);
						} else {
							st.setFromX(0.1);
							st.setFromY(0.1);
						}
						st.setToX(1);
						st.setToY(1);
						st.setDelay(delay);
						pt.getChildren().add(st);
						break;
					}
				}

				iv.setOpacity(0);
				FadeTransition ft = new FadeTransition(Duration.seconds(0.3),
						iv);
				ft.setFromValue(0);
				ft.setToValue(1);
				ft.setDelay(delay);
				pt.getChildren().add(ft);

			}
		}
		pt.play();
		pt.setRate(rate);

		return g;
	}

	public void startImage(Image image) {

		ObservableList<Node> c = root.getChildren();

		if (current != null)
			c.remove(current);

		current = next;
		next = null;

		next = startImageBlocks(image);
		next.setOpacity(1);

		c.add(next);

		Duration time = Duration.seconds(2);

		FadeTransition fadein = new FadeTransition(time, next);

		fadein.setFromValue(1);
		fadein.setToValue(1);

		FadeTransition fadeout = new FadeTransition(time, current);

		fadeout.setFromValue(1);
		fadeout.setToValue(0);

		ScaleTransition dropout = new ScaleTransition(time, current);
		dropout.setInterpolator(Interpolator.EASE_IN);
		dropout.setFromX(1);
		dropout.setFromY(1);
		dropout.setToX(0.5);
		dropout.setToY(0.5);

		// BlurTransition blurin = new BlurTransition(Duration.seconds(1),
		// next);
		// blurin.setInterpolator(Interpolator.EASE_OUT);

		PauseTransition delay = new PauseTransition(time);

		// SequentialTransition st = new SequentialTransition(new
		// ParallelTransition(dropout, fadein), delay);
		// SequentialTransition st = new SequentialTransition(new
		// ParallelTransition(fadein), delay);
		SequentialTransition st = new SequentialTransition(delay, delay);

		st.setRate(rate);

		st.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {

				Image image = loader.getNextImage();

				if (image != null)
					startImage(image);
			}
		});

		st.playFromStart();
	}

	@Override
	public void stop() throws Exception {

		super.stop();

		System.out.println("Cancelling");
		if (loader != null)
			loader.cancel();
	}

	/**
	 * The main() method is ignored in correctly deployed JavaFX application.
	 * main() serves only as fallback in case the application can not be
	 * launched through deployment artifacts, e.g., in IDEs with limited FX
	 * support. NetBeans ignores main().
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);
	}
}
