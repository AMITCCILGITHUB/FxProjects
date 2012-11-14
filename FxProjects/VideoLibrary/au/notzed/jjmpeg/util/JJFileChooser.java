/*
 * Copyright (c) 2011 Michael Zucchi
 *
 * This file is part of jjmpeg, a java binding to ffmpeg's libraries.
 *
 * jjmpeg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jjmpeg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jjmpeg.  If not, see <http://www.gnu.org/licenses/>.
 */
package au.notzed.jjmpeg.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import au.notzed.jjmpeg.AVFormatContext;
import au.notzed.jjmpeg.PixelFormat;
import au.notzed.jjmpeg.io.JJMediaReader;
import au.notzed.jjmpeg.io.JJMediaReader.JJReaderVideo;

/**
 * Video loading file chooser which shows a preview and opens at a decent size
 * and layout.
 * 
 * @author notzed
 */
public class JJFileChooser extends JFileChooser {

	private static final long serialVersionUID = 1L;

	class VideoInfo extends JPanel implements PropertyChangeListener,
			AncestorListener {

		private static final long serialVersionUID = 1L;
		File file;
		JLabel icon;
		JLabel info;
		ImageIcon iicon;
		BufferedImage preview;

		public VideoInfo(JFileChooser fc) {

			super(new BorderLayout());
			// setPreferredSize(new Dimension(256, 256));
			fc.addPropertyChangeListener(this);

			setBorder(new EmptyBorder(0, 8, 0, 0));

			preview = new BufferedImage(256, 256, BufferedImage.TYPE_3BYTE_BGR);

			iicon = new ImageIcon(preview);
			icon = new JLabel(iicon);
			// icon.setHorizontalAlignment(icon.CENTER);
			// icon.setVerticalAlignment(icon.CENTER);
			icon.setPreferredSize(new Dimension(256, 256));
			icon.setMinimumSize(new Dimension(256, 256));
			icon.setMaximumSize(new Dimension(256, 256));
			icon.setHorizontalTextPosition(SwingConstants.CENTER);
			icon.setVerticalTextPosition(SwingConstants.CENTER);
			add(icon, BorderLayout.CENTER);

			info = new JLabel();
			JScrollPane sp = new JScrollPane(info);
			// sp.setPreferredSize(new Dimension(256, 180));
			// sp.setMaximumSize(new Dimension(256, -1));
			info.setPreferredSize(new Dimension(256, 180));
			info.setMaximumSize(new Dimension(256, -1));
			info.setVerticalAlignment(SwingConstants.TOP);
			add(sp, BorderLayout.SOUTH);

			fc.addAncestorListener(this);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			String name = evt.getPropertyName();

			if (name.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
				file = (File) evt.getNewValue();
				update();
			}
		}

		SwingWorker<?, ?> loadIcon;
		AsyncVideoLoader ail;

		void update() {

			if (ail != null) {
				ail.abort();
				ail = null;
			}
			if (file == null) {
				// icon.setIcon(null);
			} else {
				ail = new AsyncVideoLoader(file, icon, info);
				ail.execute();
			}
		}

		public void ancestorAdded(AncestorEvent event) {

		}

		public void ancestorRemoved(AncestorEvent event) {

			if (ail != null) {
				ail.abort();
			}
			ail = null;
		}

		public void ancestorMoved(AncestorEvent event) {

		}

		class AsyncVideoLoader implements Runnable {

			File image;
			Thread worker;
			ImageReader reader;
			JLabel thumb;
			ImageInputStream iis;
			boolean cancelled;
			private final JLabel info;

			public AsyncVideoLoader(File image, JLabel thumb, JLabel info) {

				this.image = image;
				this.thumb = thumb;
				this.info = info;

				worker = new Thread(this);
			}

			public void execute() {

				worker.start();
			}

			public void abort() {

				try {
					cancelled = true;
					if (worker.isAlive()) {
						// System.out.println("aborting thread");
						worker.interrupt();
						worker.join();
						// System.out.println(" .. done");
					}
				} catch (InterruptedException ex) {
					Logger.getLogger(AsyncVideoLoader.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}

			Runnable setIcon = new Runnable() {

				public void run() {

					if (thumb.getIcon() == null) {
						thumb.setIcon(new ImageIcon(preview));
					} else {
						thumb.repaint();
					}
				}
			};

			@Override
			public void run() {

				JJMediaReader mr = null;
				JJMediaReader.JJReaderVideo vr = null;
				BufferedImage preview_tmp;

				try {
					info.setText(null);
					thumb.setText(null);
					Graphics2D gg = preview.createGraphics();
					gg.setColor(Color.black);
					gg.fillRect(0, 0, preview.getWidth(), preview.getHeight());

					mr = new JJMediaReader(image.getPath());
					vr = mr.openFirstVideoStream();

					if (vr == null) {
						thumb.setText("<empty>");
						return;
					}

					int height = vr.getHeight();
					int width = vr.getWidth();
					PixelFormat fmt = vr.getPixelFormat();

					info.setText(String
							.format("<html><b>Type:</b> %s<br><b>Size:</b> %dx%d<br><b>Format:</b> %s\n",
									vr.getCodec().getName(), width, height, fmt));

					int delay = (int) (1000 * vr.getContext().getTimeBase()
							.q2d());

					if (delay < 10) {
						System.out.printf(
								"suspect time delay = %d, resetting to 20ms\n",
								delay);
						delay = 40;
					}

					final int w, h;
					if (width > height) {
						h = 256 * height / width;
						w = 256;
					} else {
						w = 256 * width / height;
						h = 256;
					}

					vr.setOutputFormat(PixelFormat.PIX_FMT_BGR24, w, h);
					preview_tmp = vr.createImage();

					while (!cancelled
							&& (vr = (JJReaderVideo) mr.readFrame()) != null) {
						vr.getOutputFrame(preview_tmp);

						// Use java to centre it
						gg.setColor(Color.black);
						gg.fillRect(0, 0, preview.getWidth(),
								preview.getHeight());
						int l = (preview.getWidth() - w) / 2;
						int t = (preview.getHeight() - h) / 2;
						gg.drawImage(preview_tmp, l, t, l + w, t + h, 0, 0, w,
								h, null);

						SwingUtilities.invokeAndWait(setIcon);

						Thread.sleep(delay);
					}
				} catch (InterruptedException x) {
				} catch (Exception x) {
					System.out.println("ex reading image" + x);
					info.setText("Unable to read video");
					x.printStackTrace();
				} finally {
					if (mr != null) {
						// System.out.println("closing video file");
						mr.dispose();
					}
				}
			}
		}
	}

	public JJFileChooser() {

		super();

		setAccessory(new VideoInfo(this));

		// HACK: change mode to detail mode to make it nicer to use
		try {
			JPanel rootPanel = (JPanel) getComponent(0);
			JPanel buttonPanel = (JPanel) rootPanel.getComponent(0);
			int len = buttonPanel.getComponentCount();
			int seen = 0;

			for (int i = 0; i < len; i++) {
				Component c = buttonPanel.getComponent(i);

				if (c instanceof JToggleButton) {
					seen++;
					if (seen == 2) {
						// ((JToggleButton)c).setSelected(true);
						((JToggleButton) c).doClick();
					}
				}
			}
		} catch (Exception x) {
			System.out.println("i made a mistake: " + x.getMessage());
		}
		setPreferredSize(new Dimension(500 + 256, 600));

		AVFormatContext.registerAll();
	}

	public static void main(String[] args) {

		if (true) {
			try {
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (Exception ex) {
			}
		}

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				JJFileChooser vfc = new JJFileChooser();

				vfc.showOpenDialog(null);
			}
		});
	}
}
