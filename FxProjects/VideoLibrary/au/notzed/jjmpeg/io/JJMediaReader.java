/*
 * Copyright (c) 2012 Michael Zucchi
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
package au.notzed.jjmpeg.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import au.notzed.jjmpeg.AVAudioPacket;
import au.notzed.jjmpeg.AVCodec;
import au.notzed.jjmpeg.AVCodecContext;
import au.notzed.jjmpeg.AVFormatContext;
import au.notzed.jjmpeg.AVFrame;
import au.notzed.jjmpeg.AVInputFormat;
import au.notzed.jjmpeg.AVPacket;
import au.notzed.jjmpeg.AVPlane;
import au.notzed.jjmpeg.AVRational;
import au.notzed.jjmpeg.AVSamples;
import au.notzed.jjmpeg.AVStream;
import au.notzed.jjmpeg.PixelFormat;
import au.notzed.jjmpeg.SampleFormat;
import au.notzed.jjmpeg.SwsContext;
import au.notzed.jjmpeg.exception.AVDecodingError;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.exception.AVInvalidCodecException;
import au.notzed.jjmpeg.exception.AVInvalidStreamException;

/**
 * High level interface for scanning audio and video frames.
 * 
 * TODO: handle all frames.
 * 
 * @author notzed
 */
public class JJMediaReader {

	static final boolean dump = true;
	LinkedList<JJReaderStream> streams = new LinkedList<JJReaderStream>();
	HashMap<Integer, JJReaderStream> streamsByID = new HashMap<Integer, JJReaderStream>();
	AVFormatContext format;
	private AVPacket packet;
	private boolean freePacket = false;
	private boolean autoScan = false;
	HashMap<Integer, JJReaderStream> discovered = new HashMap<Integer, JJReaderStream>();
	//
	long seekid = -1;
	long seekms = -1;

	//

	/**
	 * Create a new media reader, will scan the file for available streams.
	 * 
	 * @param name
	 * @throws AVInvalidStreamException
	 * @throws AVIOException
	 * @throws AVInvalidCodecException
	 */
	public JJMediaReader(String name) throws AVInvalidStreamException,
			AVIOException, AVInvalidCodecException {

		this(name, null, true);
	}

	/**
	 * 
	 * @param name
	 * @param scanStreams
	 *            if true, scan for streams on open, if false, then streams are
	 *            discovered as they are read. This is broken, and probably will
	 *            never work.
	 * @throws AVInvalidStreamException
	 * @throws AVIOException
	 * @throws AVInvalidCodecException
	 * @deprecated This is experimental, and I might not keep it
	 */
	@Deprecated
	public JJMediaReader(String name, AVInputFormat fmt, boolean scanStreams)
			throws AVInvalidStreamException, AVIOException,
			AVInvalidCodecException {

		format = AVFormatContext.openInputFile(name, fmt, 0, null);

		if (scanStreams) {
			if (format.findStreamInfo() < 0) {
				throw new AVInvalidStreamException("No streams found");
			}

			// find all streams
			AVStream vstream = null;
			AVStream astream = null;
			int nstreams = format.getNBStreams();
			for (int i = 0; i < nstreams && (vstream == null | astream == null); i++) {
				AVStream s = format.getStreamAt(i);
				AVCodecContext cc = s.getCodec();
				switch (cc.getCodecType()) {
					case AVCodecContext.AVMEDIA_TYPE_VIDEO:
						JJReaderVideo vs = new JJReaderVideo(s);
						streams.add(vs);
						streamsByID.put(s.getIndex(), vs);
						break;
					case AVCodecContext.AVMEDIA_TYPE_AUDIO:
						JJReaderAudio as = new JJReaderAudio(s);
						streams.add(as);
						streamsByID.put(s.getIndex(), as);
						break;
					default:
						s.dispose();
						break;
				}
				cc.dispose();
			}

			if (streams.isEmpty()) {
				throw new AVInvalidStreamException(
						"No audio or video streams found");
			}
		} else {
			autoScan = true;
		}
		packet = AVPacket.create();
	}

	/**
	 * Opens the first video and audio stream found
	 * 
	 * TODO: could open the first one we know how to decode
	 */
	public void openDefaultStreams() throws AVInvalidCodecException,
			AVIOException {

		boolean aopen = false;
		boolean vopen = false;

		for (JJReaderStream m : streams) {
			switch (m.getType()) {
				case AVCodecContext.AVMEDIA_TYPE_VIDEO:
					if (!vopen) {
						m.open();
						vopen = true;
					}
					break;
				case AVCodecContext.AVMEDIA_TYPE_AUDIO:
					if (!aopen) {
						m.open();
						aopen = true;
					}
					break;
			}
		}
	}

	/**
	 * Find and open the first video stream.
	 */
	public JJReaderVideo openFirstVideoStream() throws AVInvalidCodecException,
			AVIOException {

		for (JJReaderStream m : streams) {
			if (m.getType() == AVCodecContext.AVMEDIA_TYPE_VIDEO) {
				m.open();
				return (JJReaderVideo) m;
			}
		}
		return null;
	}

	public List<JJReaderStream> getStreams() {

		return streams;
	}

	public JJReaderStream getStreamByID(int id) {

		return streamsByID.get(id);
	}

	public void dispose() {

		for (JJReaderStream m : streams) {
			m.dispose();
		}
		format.dispose();
		packet.dispose();
	}

	/**
	 * Get source AVFormatContext.
	 * 
	 * @return
	 */
	public AVFormatContext getFormat() {

		return format;
	}

	long pts;

	/**
	 * Retrieve (calculated) pts of the last frame decoded.
	 * 
	 * Well be -1 at EOF
	 * 
	 * @return
	 */
	public long getPTS() {

		return pts;
	}

	/**
	 * call flushBuffers() on all opened streams codecs.
	 * 
	 * e.g. after a seek.
	 */
	public void flushCodec() {

		for (JJReaderStream rs : streams) {
			rs.flushCodec();
		}
	}

	/**
	 * Attempt to seek to the nearest millisecond.
	 * 
	 * The next frame will have pts (in milliseconds) >= stamp.
	 * 
	 * @param stamp
	 * @throws AVIOException
	 */
	public void seekMS(long stamp) throws AVIOException {

		int res;

		res = format.seekFile(-1, 0, stamp * 1000, stamp * 1000, 0);
		if (res < 0) {
			throw new AVIOException(res, "Cannot seek");
		}

		seekms = stamp;

		flushCodec();
	}

	/**
	 * Seek in stream units
	 * 
	 * The next frame will have pts >= stamp
	 * 
	 * @param stamp
	 * @throws AVIOException
	 */
	public void seek(long stamp) throws AVIOException {

		int res;

		res = format.seekFile(-1, 0, stamp, stamp, 0);
		if (res < 0) {
			throw new AVIOException(res, "Cannot seek");
		}

		seekid = stamp;

		flushCodec();
	}

	/**
	 * This is invoked by readFrame when a new stream is discovered, if and only
	 * if JJMediaReader was created with scanStreams == false.
	 * 
	 * If the reader wishes to process the stream in question, it should create
	 * and return a new valid and opened JJReaderStream, or return null if it is
	 * not interested.
	 * 
	 * By default all audio/video streams are added and opened for processing.
	 * 
	 * Experimental auto-scan stuff
	 * 
	 * @param stream
	 * @param cc
	 * @return
	 */
	protected JJReaderStream addStream(AVStream stream) {

		AVCodecContext cc = stream.getCodec();
		JJReaderStream rs = null;

		if (cc != null) {
			try {
				switch (cc.getCodecType()) {
					case AVCodecContext.AVMEDIA_TYPE_AUDIO:
						System.out.println("opening discovered audio stream\n");
						rs = new JJReaderAudio(stream);
						rs.open();
						break;
					case AVCodecContext.AVMEDIA_TYPE_VIDEO:
						System.out.println("opening discovered video stream\n");
						rs = new JJReaderVideo(stream);
						rs.open();
						break;
				}
			} catch (Exception x) {
				if (rs != null) {
					rs.dispose();
				}
				rs = null;
			}
		}
		return rs;
	}

	/**
	 * Reads and decodes packets until data is ready in one of the opened
	 * streams.
	 * 
	 * @return
	 */
	public JJReaderStream readFrame() {

		if (freePacket) {
			packet.freePacket();
		}
		freePacket = false;

		while (format.readFrame(packet) >= 0) {
			// System.out.println("read packet");
			try {
				int index = packet.getStreamIndex();
				JJReaderStream ms = streamsByID.get(index);

				// Experimental auto-scan stuff
				if (autoScan && ms == null) {
					ms = discovered.get(index);
					if (ms == null) {
						AVStream stream = format.getStreamAt(index);
						ms = addStream(stream);
						if (ms != null) {
							streams.add(ms);
							streamsByID.put(index, ms);
							discovered.put(index, ms);
						} else {
							discovered.put(index, new JJReaderUnknown(stream));
						}
					}
				}
				if (ms != null) {
					if (ms.decode(packet)) {
						pts = packet.getDTS();

						// If seeking, attempt to get to the exact frame
						if (seekid != -1 && pts < seekid) {
							continue;
						} else if (seekms != -1 && ms.convertPTS(pts) < seekms) {
							continue;
						}
						seekid = -1;
						seekms = -1;
						freePacket = true;
						return ms;
					}
				}
			} catch (AVDecodingError x) {
				System.err.println("Decoding error: " + x);
			} finally {
				if (!freePacket) {
					packet.freePacket();
				}
			}
		}

		return null;
	}

	public abstract class JJReaderStream {

		AVStream stream;
		AVCodecContext c;
		int streamID = -1;
		protected AVCodec codec;
		protected boolean opened = false;
		// timebase
		int tb_Num;
		int tb_Den;
		// start pts
		long startpts;
		// start ms
		long startms;
		//
		long duration;
		long durationms;

		public JJReaderStream(AVStream stream) {

			this.stream = stream;

			c = stream.getCodec();

			AVRational tb = stream.getTimeBase();
			tb_Num = tb.getNum();
			tb_Den = tb.getDen();
			tb.dispose();

			startpts = stream.getStartTime();
			startms = AVRational.starSlash(startpts * 1000, tb_Num, tb_Den);
			duration = stream.getDuration();
			durationms = AVRational.starSlash(duration * 1000, tb_Num, tb_Den);
		}

		public void open() throws AVInvalidCodecException, AVIOException {

		}

		public void dispose() {

			// (I cant remember why i removed this. I think it gets closed
			// anyway and was causing a crash)
			// if (opened)
			// c.close();
			if (codec != null) {
				codec.dispose();
			}
			c.dispose();
			stream.dispose();
		}

		public AVCodecContext getContext() {

			return c;
		}

		public AVStream getStream() {

			return stream;
		}

		public AVCodec getCodec() {

			return codec;
		}

		/**
		 * Retrieve duration of sequence, in milliseconds.
		 * 
		 * @return
		 */
		public long getDurationMS() {

			return durationms;
		}

		/**
		 * Get duration in timebase units (i.e. frames?)
		 * 
		 * @return
		 */
		public long getDuration() {

			return duration;
		}

		public boolean isOpened() {

			return opened;
		}

		/**
		 * Convert the 'pts' provided to milliseconds relative to the start of
		 * the stream.
		 * 
		 * @param pts
		 * @return
		 */
		public long convertPTS(long pts) {

			return AVRational.starSlash(pts * 1000, tb_Num, tb_Den) - startms;
		}

		/**
		 * Decode a packet. Returns true if data is now ready.
		 * 
		 * It is ok to call this on an unopened stream: return false.
		 * 
		 * @param packet
		 * @return
		 */
		abstract public boolean decode(AVPacket packet) throws AVDecodingError;

		/**
		 * Retreive the AVMEDIA_TYPE_* for this stream.
		 * 
		 * @return
		 */
		abstract public int getType();

		void flushCodec() {

			if (opened) {
				c.flushBuffers();
			}
		}

		/**
		 * Attempt to seek relative to this stream, to the nearest millisecond.
		 * 
		 * The next frame will have pts (in milliseconds) >= stamp.
		 * 
		 * This doesn't seem to work very well, use JJMediaReader.seekMS()
		 * 
		 * @param stamp
		 * @throws AVIOException
		 */
		public void seekMS(long stamp) throws AVIOException {

			int res;

			res = format.seekFile(stream.getIndex(), 0, stamp * 1000,
					stamp * 1000, 0);
			if (res < 0) {
				throw new AVIOException(res, "Cannot seek");
			}

			seekms = stamp;

			c.flushBuffers();
		}

		/**
		 * Seek relative to this stream, in stream units
		 * 
		 * The next frame will have pts >= stamp
		 * 
		 * This doesn't seem to work very well, use JJMediaReader.seek()
		 * 
		 * @param stamp
		 * @throws AVIOException
		 */
		public void seek(long stamp) throws AVIOException {

			int res;

			res = format.seekFile(stream.getIndex(), 0, stamp, stamp, 0);
			if (res < 0) {
				throw new AVIOException(res, "Cannot seek");
			}

			seekid = stamp;
			c.flushBuffers();
		}
	}

	public class JJReaderUnknown extends JJReaderStream {

		public JJReaderUnknown(AVStream stream) {

			super(stream);
		}

		@Override
		public boolean decode(AVPacket packet) throws AVDecodingError {

			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public int getType() {

			return AVCodecContext.AVMEDIA_TYPE_UNKNOWN;
		}

		@Override
		void flushCodec() {

		}
	}

	public class JJReaderVideo extends JJReaderStream {

		// scaled output
		int owidth;
		int oheight;
		SwsContext oscale;
		PixelFormat ofmt;
		int oframeCount = 1;
		int oframeIndex = -1;
		AVFrame oframe[];
		// icon scaled output
		int cwidth;
		int cheight;
		SwsContext cscale;
		PixelFormat cfmt;
		AVFrame cframe;
		// format info
		int height;
		int width;
		PixelFormat fmt;
		AVFrame iframe;
		//
		/**
		 * Is the scaled/converted frame stale
		 */
		boolean stale;

		public JJReaderVideo(AVStream stream) throws AVInvalidCodecException,
				AVIOException {

			super(stream);
		}

		@Override
		public void open() throws AVInvalidCodecException, AVIOException {

			if (dump) {
				System.out.println("Open video reader");
				System.out.printf(" video size %dx%d\n", c.getWidth(),
						c.getHeight());
				System.out.println(" video codec id = " + c.getCodecID());
				System.out.println(" pixel format: " + c.getPixFmt());
			}

			if (c.getPixFmt() == PixelFormat.PIX_FMT_NONE) {
				throw new AVInvalidCodecException("No decodable video present");
			}

			// find decoder for the video stream
			codec = AVCodec.findDecoder(c.getCodecID());

			if (codec == null) {
				throw new AVInvalidCodecException(
						"Unable to decode video stream");
			}

			if (dump) {
				System.out.println(" video codec: " + codec.getName());
			}

			c.open(codec);

			opened = true;

			iframe = AVFrame.create();

			height = c.getHeight();
			width = c.getWidth();
			fmt = c.getPixFmt();

			owidth = width;
			oheight = height;
		}

		@Override
		public void dispose() {

			super.dispose();

			if (iframe != null)
				iframe.dispose();

			if (oscale != null) {
				oscale.dispose();
			}

			clearOutputFrames();

			if (cscale != null) {
				cscale.dispose();
				cframe.dispose();
			}
		}

		final void clearOutputFrames() {

			if (oframe != null) {
				for (int i = 0; i < oframe.length; i++) {
					AVFrame of = oframe[i];
					if (of != null) {
						of.dispose();
					}
				}
				oframe = null;
			}
		}

		final void initOutputFrames() {

			clearOutputFrames();
			oframe = new AVFrame[oframeCount];
			for (int i = 0; i < oframeCount; i++) {
				oframe[i] = AVFrame.create(ofmt, owidth, oheight);
			}
			oframeIndex = -1;
		}

		@Override
		public boolean decode(AVPacket packet) throws AVDecodingError {

			if (iframe == null) {
				return false;
			}

			stale = true;

			boolean frameFinished = c.decodeVideo(iframe, packet);

			return frameFinished;
		}

		@Override
		public int getType() {

			return AVCodecContext.AVMEDIA_TYPE_VIDEO;
		}

		public int getWidth() {

			return width;
		}

		public int getHeight() {

			return height;
		}

		public PixelFormat getPixelFormat() {

			return fmt;
		}

		/**
		 * Set the number of buffers to use for buffering when using
		 * getOutputFrame()
		 * 
		 * @param count
		 */
		public void setOutputFrameCount(int count) {

			oframeCount = count;
		}

		public int getOutputFrameCount() {

			return oframeCount;
		}

		/**
		 * Current output frame buffer index.
		 * 
		 * @return
		 */
		public int getOutputFrameIndex() {

			return oframeIndex;
		}

		/**
		 * Retrieve the index of the next frame which will be decoded into.
		 * 
		 * @return
		 */
		public int getNextOutputFrameIndex() {

			int oi = oframeIndex + 1;
			if (oi >= oframeCount) {
				oi = 0;
			}
			return oi;
		}

		public AVFrame getOutputFrameAt(int index) {

			if (oframe == null) {
				initOutputFrames();
			}
			return oframe[index];
		}

		int nextOutputFrame() {

			oframeIndex++;
			if (oframeIndex >= oframeCount) {
				oframeIndex = 0;
			}
			return oframeIndex;
		}

		/**
		 * Set the output pixel format, the size will be the native source size.
		 * 
		 * @param ofmt
		 */
		public void setOutputFormat(PixelFormat ofmt) {

			setOutputFormat(ofmt, width, height);
		}

		/**
		 * Set the output format for use with getOutputFrame()
		 * 
		 * If using the BufferedImage version of getOutputFrame, ofmt must be
		 * one of the types listed below, but if one is using the raw version of
		 * getOutputFrame() it may be any format supported by libswscale.
		 * 
		 * The supported formats are mapped directly to the closest
		 * corresponding Java2D image types in createImage().
		 * 
		 * On little-endian architectures, these are:
		 * 
		 * PixelFormat.PIX_FMT_BGR24 === BufferedImage.TYPE_3BYTE_BGR
		 * PixelFormat.PIX_FMT_GRAY8 === BufferedImage.TYPE_BYTE_GRAY
		 * PixelFormat.PIX_FMT_RGBA === BufferedImage.TYPE_INT_BGR
		 * PixelFormat.PIX_FMT_BGRA === BufferedImage.TYPE_INT_ARGB
		 * 
		 * Invoking this will invalidate any images previously made from
		 * createImage() (insofar as it pertains to using them with
		 * getOutputFrame(image).)
		 * 
		 * @param ofmt
		 * @param owidth
		 * @param oheight
		 */
		public void setOutputFormat(PixelFormat ofmt, int owidth, int oheight) {

			if (oscale != null) {
				oscale.dispose();
				clearOutputFrames();
			}
			this.owidth = owidth;
			this.oheight = oheight;
			this.ofmt = ofmt;
			// oframe = AVFrame.create(ofmt, owidth, oheight);
			oscale = SwsContext.create(width, height, fmt, owidth, oheight,
					ofmt, SwsContext.SWS_BILINEAR);
		}

		/**
		 * Set the output size for generating an icon with getOutputIcon().
		 * 
		 * This will preserve the aspect ratio and generate an icon within the
		 * given bounds.
		 * 
		 * TODO: it only assumes square pixels at the moment.
		 * 
		 * @param maxwidth
		 * @param maxheight
		 */
		public void setIconSize(int maxwidth, int maxheight) {

			if (cscale != null) {
				cscale.dispose();
				cframe.dispose();
			}
			float wfactor = width / (float) maxwidth;
			float hfactor = height / (float) maxheight;

			if (wfactor < hfactor) {
				cwidth = (int) (maxheight * (long) width / height);
				cheight = maxheight;
			} else {
				cheight = (int) (maxwidth * (long) height / width);
				cwidth = maxwidth;
			}
			cfmt = PixelFormat.PIX_FMT_BGR24;
			cframe = AVFrame.create(cfmt, cwidth, cheight);
			cscale = SwsContext.create(width, height, fmt, cwidth, cheight,
					cfmt, SwsContext.SWS_BILINEAR);
		}

		/**
		 * Determine the BufferedImage type for a pixel format
		 * 
		 * @param fmt
		 * @return
		 */
		public int formatToType(PixelFormat fmt) {

			switch (fmt) {
				case PIX_FMT_BGR24:
					return BufferedImage.TYPE_3BYTE_BGR;
				case PIX_FMT_GRAY8:
					return BufferedImage.TYPE_BYTE_GRAY;
				case PIX_FMT_RGBA:
					return BufferedImage.TYPE_INT_BGR;
				case PIX_FMT_BGRA:
					return BufferedImage.TYPE_INT_ARGB;
				default:
					break;
			}
			throw new RuntimeException(
					"Unsupported Java image conversion format");
		}

		/**
		 * Determine PixelFormat for a BufferedImage type
		 * 
		 * @param type
		 * @return
		 */
		public PixelFormat typeToFormat(int type) {

			switch (type) {
				case BufferedImage.TYPE_3BYTE_BGR:
					return PixelFormat.PIX_FMT_BGR24;
				case BufferedImage.TYPE_BYTE_GRAY:
					return PixelFormat.PIX_FMT_GRAY8;
				case BufferedImage.TYPE_INT_BGR:
					return PixelFormat.PIX_FMT_RGBA;
				case BufferedImage.TYPE_INT_ARGB:
					return PixelFormat.PIX_FMT_BGRA;
			}
			throw new RuntimeException(
					"Unsupported Java image conversion format");
		}

		protected boolean canConvert(PixelFormat fmt) {

			return fmt == PixelFormat.PIX_FMT_BGR24
					| fmt == PixelFormat.PIX_FMT_GRAY8
					| fmt == PixelFormat.PIX_FMT_RGBA
					| fmt == PixelFormat.PIX_FMT_BGRA;
		}

		protected boolean canConvert(int type) {

			return type == BufferedImage.TYPE_3BYTE_BGR
					| type == BufferedImage.TYPE_BYTE_GRAY
					| type == BufferedImage.TYPE_INT_BGR
					| type == BufferedImage.TYPE_INT_ARGB;
		}

		/**
		 * Allocate an image suitable for getOutputFrame(BufferedImage)
		 * 
		 * @return
		 */
		public BufferedImage createImage() {

			if (ofmt == null) {
				setOutputFormat(PixelFormat.PIX_FMT_BGR24, width, height);
			}

			return new BufferedImage(owidth, oheight, formatToType(ofmt));
		}

		/**
		 * Retrieve the scaled/converted frame, or just the raw frame if no
		 * output format set
		 * 
		 * @return
		 */
		public AVFrame getOutputFrame() {

			if (ofmt != null) {
				if (oframe == null) {
					initOutputFrames();
				}

				if (stale) {
					oscale.scale(iframe, 0, height, oframe[nextOutputFrame()]);
					stale = false;
				}

				return oframe[getOutputFrameIndex()];
			}
			return iframe;
		}

		/**
		 * Get the output frame into a buffered image.
		 * 
		 * dst must match setOutputFormat() type.
		 * 
		 * @param dst
		 * @return dst
		 */
		public BufferedImage getOutputFrame(BufferedImage dst) {

			if (ofmt == null) {
				setOutputFormat(typeToFormat(dst.getType()), width, height);
			}

			switch (ofmt) {
				case PIX_FMT_GRAY8: {
					// TODO: for 0.10 version, this isn't required. but for 0.7
					// it will add palette info
					// Scale to oframe, then copy across
					AVFrame frame = getOutputFrame();
					AVPlane splane = frame.getPlaneAt(0, ofmt, owidth, oheight);
					byte[] data = ((DataBufferByte) dst.getRaster()
							.getDataBuffer()).getData();

					splane.data.get(data, 0,
							Math.min(data.length, splane.data.capacity()));
					splane.data.rewind();
					break;
				}
				case PIX_FMT_BGR24: {
					// Scale directly to target image
					byte[] data = ((DataBufferByte) dst.getRaster()
							.getDataBuffer()).getData();
					oscale.scale(iframe, 0, height, data);
					break;
				}
				case PIX_FMT_BGRA:
				case PIX_FMT_RGBA: {
					// Scale directly to target (integer) image
					int[] data = ((DataBufferInt) dst.getRaster()
							.getDataBuffer()).getData();
					oscale.scale(iframe, 0, height, data);
					break;
				}
				default:
					break;
			}

			return dst;
		}

		/**
		 * Allocates an icon and scales the output frame to it.
		 * 
		 * Icon size must already have been set.
		 * 
		 * @return
		 */
		public BufferedImage getOutputIcon() throws IllegalStateException {

			if (cframe == null) {
				throw new IllegalStateException("Icon size not set");
			}

			BufferedImage icon = new BufferedImage(cwidth, cheight,
					BufferedImage.TYPE_3BYTE_BGR);
			byte[] data = ((DataBufferByte) icon.getRaster().getDataBuffer())
					.getData();
			cscale.scale(iframe, 0, height, data);

			return icon;
		}

		/**
		 * Retrieve the decoded frame.
		 * 
		 * @return
		 */
		public AVFrame getFrame() {

			return iframe;
		}
	}

	public class JJReaderAudio extends JJReaderStream {

		AVAudioPacket apacket;
		AVSamples samples;

		public JJReaderAudio(AVStream stream) throws AVInvalidCodecException,
				AVIOException {

			super(stream);
		}

		@Override
		public void open() throws AVInvalidCodecException, AVIOException {

			System.out.println("Open Audio Reader");
			System.out.println(" audio codec id = " + c.getCodecID());

			// find decoder for the video stream
			codec = AVCodec.findDecoder(c.getCodecID());

			if (codec == null) {
				throw new AVInvalidCodecException(
						"Unable to decode video stream");
			}

			c.open(codec);

			opened = true;

			System.out.println(" codec : " + codec.getName());
			System.out.println(" sampleformat : " + c.getSampleFmt());
			System.out.println(" samplerate : " + c.getSampleRate());

			apacket = AVAudioPacket.create();
			samples = new AVSamples(c.getSampleFmt());
		}

		public boolean decode(AVPacket packet) throws AVDecodingError {

			if (samples == null) {
				return false;
			}

			apacket.setSrc(packet);

			return apacket.getSize() > 0;
		}

		@Override
		public int getType() {

			return AVCodecContext.AVMEDIA_TYPE_AUDIO;
		}

		public SampleFormat getSampleFormat() {

			return c.getSampleFmt();
		}

		/**
		 * Retrieve the next block of decoded samples: this will return a new
		 * AVSamples until there are no more samples left.
		 */
		public AVSamples getSamples() throws AVDecodingError {

			while (apacket.getSize() > 0) {
				int len = c.decodeAudio(samples, apacket);
				if (len > 0) {
					return samples;
				}
			}
			return null;
		}
	}
}
