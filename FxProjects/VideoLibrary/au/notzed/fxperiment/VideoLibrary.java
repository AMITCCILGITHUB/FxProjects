/*
 *  Copyright (c) 2012 Michael Zucchi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package au.notzed.fxperiment;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import au.notzed.jjmpeg.AVCodecContext;
import au.notzed.jjmpeg.exception.AVException;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.io.JJMediaReader;
import au.notzed.jjmpeg.io.JJMediaReader.JJReaderStream;

/**
 * This is a "simple" experiment of a long list which loads it's animated
 * content asynchronously.
 * 
 * @author notzed
 */
public class VideoLibrary extends Application {

	final static String tag = "VideoLibrary";
	FlowPane flow;
	ScrollPane scroll;
	VisibilityManager vbm;

	@Override
	public void start(Stage primaryStage) {

		StackPane root = new StackPane();

		flow = new FlowPane(Orientation.HORIZONTAL);
		scroll = new ScrollPane();
		scroll.setContent(flow);

		root.getChildren().add(scroll);

		vbm = new VisibilityManager(256, 144);
		vbm.connect(scroll, flow);

		Scene scene = new Scene(root, 256 * 4 + 32, 144 * 4);

		primaryStage.setTitle("Video Library!");
		primaryStage.setScene(scene);
		primaryStage.show();

		Preferences p = Preferences.userNodeForPackage(VideoLibrary.class);
		String dir = p.get("video.drawer", null);
		if (dir == null) {

			DirectoryChooser dc = new DirectoryChooser();
			dc.setTitle("Select root search path");
			File file = dc.showDialog(null);

			if (file == null) {
				Platform.exit();
				return;
			}

			dir = file.getAbsolutePath();
			p.put("video.drawer", dir);
		}

		Thread th = new Thread(new DirectoryScanner(dir));
		th.setDaemon(true);
		th.start();
	}

	public static void main(String[] args) {

		launch(args);
	}

	class DirectoryScanner extends Task<Object> implements FileVisitor<Path> {

		String root;

		public DirectoryScanner(String root) {

			this.root = root;
		}

		@Override
		protected Object call() throws Exception {

			try {
				Files.walkFileTree(Paths.get(root), this);
			} catch (IOException ex) {
				Logger.getLogger(tag).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				System.out.println("complete");
			}

			return null;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path t, BasicFileAttributes bfa)
				throws IOException {

			if (isCancelled())
				return FileVisitResult.TERMINATE;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path t, BasicFileAttributes bfa)
				throws IOException {

			if (isCancelled())
				return FileVisitResult.TERMINATE;

			if (bfa.isRegularFile()) {
				JJMediaReader mr = null;
				try {
					final String name = t.toAbsolutePath().toString();

					System.out.println("Checking: " + name);

					// See if ffmpeg can decode it & is has a video stream
					mr = new JJMediaReader(name);

					for (JJReaderStream rs : mr.getStreams()) {
						if (rs.getType() == AVCodecContext.AVMEDIA_TYPE_VIDEO) {
							// Add this source
							Platform.runLater(new Runnable() {

								@Override
								public void run() {

									// Set mediapreviewpane if you dare ...
									AsyncImagePane video = new JJPreviewPane(
											name);
									// AsyncImagePane video = new
									// MediaPreviewPane(name);
									video.setPrefSize(256, 144);
									flow.getChildren().add(video);
								}
							});
							break;
						}
					}
				} catch (AVException ex) {
				} catch (AVIOException ex) {
				} finally {
					if (mr != null) {
						// workaround a bug in jjmpeg which has a null error
						try {
							mr.dispose();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path t, IOException ioe)
				throws IOException {

			if (isCancelled())
				return FileVisitResult.TERMINATE;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path t, IOException ioe)
				throws IOException {

			if (isCancelled())
				return FileVisitResult.TERMINATE;
			return FileVisitResult.CONTINUE;
		}
	}

	class BusySpinner extends Group {

		public BusySpinner() {

			ObservableList<Node> children = getChildren();

			Paint p = Color.FUCHSIA;
			for (int r = 0; r < 8; r++) {
				double R = r * Math.PI * 2 / 8;
				double x = Math.sin(R) * 24;
				double y = Math.cos(R) * 24;

				Circle dot = new Circle(x, y, 6, p);

				dot.setOpacity((9 - r) / 9.0);
				children.add(dot);
			}

			RotateTransition rt = new RotateTransition(Duration.seconds(1),
					this);

			rt.setFromAngle(0);
			rt.setToAngle(360);
			rt.setCycleCount(RotateTransition.INDEFINITE);
			rt.setInterpolator(Interpolator.LINEAR);

			rt.play();

		}
	}

	/**
	 * "loads" an image asynchronously, showing a busy spinner if it isn't ready
	 */
	class AsyncImagePane extends StackPane {

		boolean userVisible;

		public void clear() {

			getChildren().clear();
		}

		public void startLoading() {

			ImageView iv = new ImageView();
			iv.setFitWidth(256);
			iv.setFitHeight(144);
			// blah
			iv.setPreserveRatio(false);

			getChildren().add(iv);
		}

		public void setUserVisible(boolean userVisible) {

			if (this.userVisible != userVisible) {
				this.userVisible = userVisible;

				if (userVisible) {
					/*
					 * ImageView iv = new ImageView(); iv.setFitWidth(256);
					 * iv.setFitHeight(144); iv.setPreserveRatio(false);
					 * getChildren().add(iv);
					 */

					// getChildren().add(new BusySpinner());

					startVideo();
				} else {
					stopVideo();
					clear();
				}
			}
		}

		void startVideo() {

		}

		void stopVideo() {

		}
	}

	class JJPreviewPane extends AsyncImagePane {

		VideoThread vt;
		String url;
		BusySpinner bs;

		public JJPreviewPane(String url) {

			this.url = url;
		}

		void stopVideo() {

			if (vt != null) {
				try {
					System.out.println("Stopping play");
					vt.cancel();

					bs = null;
				} catch (InterruptedException ex) {
					Logger.getLogger(VideoLibrary.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				vt = null;
			}
		}

		void startVideo() {

			if (vt == null) {
				vt = new VideoThread(url) {

					@Override
					public void imageCreated(WritableImage image) {

						// getChildren().clear();

						ImageView iv = new ImageView(image);
						getChildren().add(iv);
						iv.setOpacity(0);

						FadeTransition ft = new FadeTransition(
								Duration.seconds(0.5), iv);
						ft.setFromValue(0);
						ft.setToValue(1);
						ft.play();

						ft.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent t) {

								getChildren().remove(bs);
								bs = null;
							}
						});
					}
				};

				bs = new BusySpinner();
				bs.setOpacity(0);

				FadeTransition ft = new FadeTransition(Duration.seconds(0.5),
						bs);
				ft.setFromValue(0);
				ft.setToValue(1);
				getChildren().add(bs);
				vt.start();
				ft.play();
			}
		}
	}

	/**
	 * Uses the mediaplayer. But unfortunately that uses gst ... well enough
	 * said. Managed to bring my whole machine to a halt in a few seconds.
	 */
	class MediaPreviewPane extends AsyncImagePane {

		Media media;
		MediaPlayer mp;
		String url;

		public MediaPreviewPane(String url) {

			this.url = "file://" + url;
		}

		void stopVideo() {

			if (mp != null) {
				mp.statusProperty().removeListener(mediaStatus);
				mp.stop();
				mp = null;
			}
		}

		void startVideo() {

			if (mp == null) {
				try {
					media = new Media(url);
					mp = new MediaPlayer(media);

					mp.setMute(true);
					mp.statusProperty().addListener(mediaStatus);
				} catch (MediaException mx) {
					mp = null;
				}
				// getChildren().add(new BusySpinner());
			}
		}

		ChangeListener<Status> mediaStatus = new ChangeListener<Status>() {

			@Override
			public void changed(ObservableValue<? extends Status> ov, Status t,
					Status t1) {

				System.out.println("media status " + t1);
				if (t1 == Status.READY) {

					getChildren().clear();

					MediaView mview = new MediaView(mp);

					mview.setSmooth(true);
					mview.setFitWidth(256);
					mview.setFitHeight(144);
					mview.setPreserveRatio(true);

					getChildren().add(mview);

					mp.play();
				}
			}
		};
	}

	/**
	 * Tracks which range of objects is visible and activates them
	 */
	class VisibilityManager implements Runnable, ChangeListener<Number>,
			EventHandler<ActionEvent> {

		private ScrollPane sp;
		private FlowPane fp;
		private final double iwidth;
		private final double iheight;
		private boolean pending = false;

		public VisibilityManager(double iwidth, double iheight) {

			this.iwidth = iwidth;
			this.iheight = iheight;

		}

		void connect(ScrollPane isp, FlowPane ifp) {

			sp = isp;
			fp = ifp;

			fp.heightProperty().addListener(this);
			fp.widthProperty().addListener(this);
			sp.viewportBoundsProperty().addListener(
					new ChangeListener<Bounds>() {

						@Override
						public void changed(
								ObservableValue<? extends Bounds> ov, Bounds t,
								Bounds t1) {

							fp.setPrefWidth(t1.getWidth());
						}
					});
			sp.vvalueProperty().addListener(this);
		}

		@Override
		public void run() {

			double top = sp.getVvalue();
			double vheight = sp.getViewportBounds().getHeight();
			double fheight = fp.getHeight();
			double fwidth = sp.getViewportBounds().getWidth();

			// Convert scroll position into cell coordinates
			// Number of cells visible in a row
			int cellw = (int) Math.floor(fwidth / iwidth);
			// ID of first visible cell
			int cell0 = (int) (Math
					.floor((top * (fheight - vheight)) / iheight)) * cellw;
			// ID of last visible cell + 1
			int celln = (int) (Math.ceil((top * (fheight - vheight) + vheight)
					/ iheight))
					* cellw;

			ObservableList<Node> list = fp.getChildrenUnmodifiable();

			int len = list.size();
			for (int i = 0; i < len; i++) {
				AsyncImagePane pb = (AsyncImagePane) list.get(i);

				pb.setUserVisible(i >= cell0 && i < celln);
			}
			pending = false;
		}

		@Override
		public void changed(ObservableValue<? extends Number> ov, Number t,
				Number t1) {

			if (!pending) {
				Platform.runLater(this);
				pending = true;
			}
		}

		/**
		 * Invoked on transition completion - clear transition
		 * 
		 * @param t
		 */
		@Override
		public void handle(ActionEvent t) {

			FadeTransition ft = (FadeTransition) t.getSource();

			ft.getNode().setUserData(null);
		}
	}

	@Override
	public void stop() throws Exception {

		super.stop();

		ObservableList<Node> list = flow.getChildrenUnmodifiable();

		int len = list.size();
		for (int i = 0; i < len; i++) {
			AsyncImagePane pb = (AsyncImagePane) list.get(i);

			pb.setUserVisible(false);
		}
	}
}
