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
package au.notzed.fxperiment;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import au.notzed.jjmpeg.AVPlane;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.exception.AVInvalidCodecException;
import au.notzed.jjmpeg.exception.AVInvalidStreamException;
import au.notzed.jjmpeg.io.JJMediaReader;
import au.notzed.jjmpeg.io.JJMediaReader.JJReaderVideo;

/**
 * 
 * @author notzed
 */
public class VideoThread extends Thread {

	final static String tag = "videoThread";
	String path;
	boolean cancelled = false;
	WritableImage image;
	int width;
	int height;
	// For use by parent
	ImageView imageView;

	public VideoThread(String path) {

		this.path = path;
	}

	/**
	 * too lazy for callback stuff, override this in subclass for state
	 * transition
	 * 
	 * @param image
	 */
	public void imageCreated(WritableImage image) {

	}

	public void cancel() throws InterruptedException {

		cancelled = true;
		interrupt();
		// join();
	}

	ByteBuffer frameCopy;

	@Override
	public void run() {

		int framecount = 2;

		// If we only have one frame (i.e. a still image), just run once and
		// quit.
		while (!cancelled && framecount > 1) {
			long start = -1;
			long pts0 = -1;

			JJMediaReader mr = null;

			try {
				mr = new JJMediaReader(path);
				JJReaderVideo vs = mr.openFirstVideoStream();

				// width = vs.getWidth();
				// height = vs.getHeight();
				width = 256;
				height = 144;

				if (image == null) {
					image = new WritableImage(width, height);
					Platform.runLater(new Runnable() {

						@Override
						public void run() {

							imageCreated(image);
						}
					});
					frameCopy = ByteBuffer.allocateDirect(width * height * 4)
							.order(ByteOrder.nativeOrder());
				}

				vs.setOutputFormat(au.notzed.jjmpeg.PixelFormat.PIX_FMT_BGRA,
						width, height);

				framecount = 0;

				while (!cancelled && mr.readFrame() != null) {
					try {
						AVPlane data = vs.getOutputFrame().getPlaneAt(0,
								au.notzed.jjmpeg.PixelFormat.PIX_FMT_ARGB,
								width, height);

						framecount++;
						// So ... we need to copy the frame I guess (or do some
						// go to sleep thing until the gui thread returns)
						synchronized (frameCopy) {
							frameCopy.put(data.data);
							data.data.rewind();
							frameCopy.rewind();
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {

								// TODO: race on thread exit
								synchronized (frameCopy) {
									image.getPixelWriter()
											.setPixels(
													0,
													0,
													width,
													height,
													PixelFormat
															.getIntArgbPreInstance(),
													frameCopy.asIntBuffer(),
													width);
								}
							}
						});

						long pts = vs.convertPTS(mr.getPTS());
						long now = System.currentTimeMillis();

						if (start == -1) {
							start = now;
							pts0 = pts;
						} else {
							long diff;

							diff = (pts - pts0) - (now - start);
							diff = Math.min(diff, 1000);
							if (diff > 0)
								sleep(diff);
						}
					} catch (InterruptedException ex) {
						break;
					}
				}

			} catch (AVInvalidStreamException ex) {
				Logger.getLogger(tag).log(Level.SEVERE, null, ex);
			} catch (AVIOException ex) {
				Logger.getLogger(tag).log(Level.SEVERE, null, ex);
			} catch (AVInvalidCodecException ex) {
				Logger.getLogger(tag).log(Level.SEVERE, null, ex);
			} finally {
				if (mr != null)
					mr.dispose();
			}
		}
	}
}
