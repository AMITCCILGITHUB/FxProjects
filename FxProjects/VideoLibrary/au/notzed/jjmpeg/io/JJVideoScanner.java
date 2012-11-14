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
package au.notzed.jjmpeg.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import au.notzed.jjmpeg.AVCodec;
import au.notzed.jjmpeg.AVCodecContext;
import au.notzed.jjmpeg.AVFormatContext;
import au.notzed.jjmpeg.AVFrame;
import au.notzed.jjmpeg.AVPacket;
import au.notzed.jjmpeg.AVPlane;
import au.notzed.jjmpeg.AVRational;
import au.notzed.jjmpeg.AVStream;
import au.notzed.jjmpeg.PixelFormat;
import au.notzed.jjmpeg.SwsContext;
import au.notzed.jjmpeg.exception.AVDecodingError;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.exception.AVInvalidCodecException;
import au.notzed.jjmpeg.exception.AVInvalidStreamException;

/**
 * High level interface for scanning video frames.
 * 
 * This will deprecated in the future once JJMediaReader has the seeking stuff
 * in it.
 * 
 * @author notzed
 */
@Deprecated
public class JJVideoScanner {

	AVFormatContext format;
	AVCodecContext codecContext = null;
	int videoStream = -1;
	AVCodec codec;
	int swidth;
	int sheight;
	SwsContext scale;
	int height;
	int width;
	PixelFormat fmt;
	// timebase
	int tb_Num;
	int tb_Den;
	// start pts
	long startpts;
	// start ms
	long startms;
	// duration (estimated?)
	long duration;
	long durationms;
	//
	long seekid = -1;
	//
	AVFrame iframe;
	AVFrame oframe;
	AVPacket packet;

	/**
	 * Use JJMediaReader instead.
	 * 
	 * @param name
	 * @throws AVInvalidStreamException
	 * @throws AVIOException
	 * @throws AVInvalidCodecException
	 * @deprecated
	 */
	@Deprecated
	public JJVideoScanner(String name) throws AVInvalidStreamException,
			AVIOException, AVInvalidCodecException {

		// AVFormatContext.registerAll();
		format = AVFormatContext.openInputFile(name);

		if (format.findStreamInfo() < 0) {
			throw new AVInvalidStreamException("No streams found");
		}

		// find first video stream
		AVStream stream = null;
		int nstreams = format.getNBStreams();
		for (int i = 0; i < nstreams; i++) {
			AVStream s = format.getStreamAt(i);
			codecContext = s.getCodec();
			if (codecContext.getCodecType() == AVCodecContext.AVMEDIA_TYPE_VIDEO) {
				videoStream = i;
				stream = s;
				break;
			}
		}

		if (stream == null) {
			throw new AVInvalidStreamException("No video stream");
		}

		System.out.printf("codec size %dx%d\n", codecContext.getWidth(),
				codecContext.getHeight());
		System.out.println("codec id = " + codecContext.getCodecID());

		// find decoder for the video stream
		codec = AVCodec.findDecoder(codecContext.getCodecID());

		if (codec == null) {
			throw new AVInvalidCodecException("No video stream");
		}

		System.out.println("opening codec");
		codecContext.open(codec);

		System.out.println("pixel format: " + codecContext.getPixFmt());

		iframe = AVFrame.create();
		packet = AVPacket.create();

		height = codecContext.getHeight();
		width = codecContext.getWidth();
		fmt = codecContext.getPixFmt();

		swidth = width;
		sheight = height;

		oframe = AVFrame.create(PixelFormat.PIX_FMT_BGR24, swidth, sheight);
		scale = SwsContext.create(width, height, fmt, swidth, sheight,
				PixelFormat.PIX_FMT_BGR24, SwsContext.SWS_BILINEAR);

		AVRational tb = stream.getTimeBase();
		tb_Num = tb.getNum();
		tb_Den = tb.getDen();

		startpts = stream.getStartTime();
		startms = AVRational.starSlash(startpts * 1000, tb_Num, tb_Den);
		duration = stream.getDuration();
		durationms = AVRational.starSlash(duration * 1000, tb_Num, tb_Den);

		System.out.println("startpts   = " + startpts);
		System.out.println("startms    = " + startms);
		System.out.println("duration   = " + duration);
		System.out.println("durationms = " + durationms);
	}

	public void dispose() {

		scale.dispose();
		oframe.dispose();
		iframe.dispose();
		packet.dispose();
		codec.dispose();
		codecContext.dispose();
		format.dispose();
	}

	/**
	 * Set output rendered size.
	 * 
	 * If this is changed, must re-call createImage(), getOutputFrame(), etc.
	 * 
	 * @param swidth
	 * @param sheight
	 */
	public void setSize(int swidth, int sheight) {

		oframe.dispose();
		scale.dispose();
		this.swidth = swidth;
		this.sheight = sheight;

		oframe = AVFrame.create(PixelFormat.PIX_FMT_BGR24, swidth, sheight);
		scale = SwsContext.create(width, height, fmt, swidth, sheight,
				PixelFormat.PIX_FMT_BGR24, SwsContext.SWS_BILINEAR);
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

	/**
	 * Convert the 'pts' provided to milliseconds relative to the start of the
	 * video stream.
	 * 
	 * @param pts
	 * @return
	 */
	public long convertPTS(long pts) {

		return AVRational.starSlash(pts * 1000, tb_Num, tb_Den) - startms;
	}

	/**
	 * Allocate an image suitable for readFrame()
	 * 
	 * @return
	 */
	public BufferedImage createImage() {

		return new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}

	/**
	 * Frame used for scaled/format converted output.
	 * 
	 * Can be used directly if using readFrame(target)
	 * 
	 * @return
	 */
	public AVFrame getOutputFrame() {

		return oframe;
	}

	/**
	 * Get raw format for images.
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
	 * Internal read the next decoded video frame.
	 * 
	 * @return
	 * @throws AVDecodingError
	 */
	AVFrame readAVFrame() throws AVDecodingError {

		pts = -1;
		while (format.readFrame(packet) >= 0) {
			try {
				// is this from the video stream?
				if (packet.getStreamIndex() == videoStream) {
					// decode video frame
					boolean frameFinished = codecContext.decodeVideo(iframe,
							packet);

					if (frameFinished) {
						pts = packet.getDTS();

						// If seeking, attempt to get to the exact frame
						if (seekid != -1 && pts < seekid) {
							continue;
						}
						seekid = -1;
						return iframe;
					}
				}
			} finally {
				packet.freePacket();
			}
		}

		return null;
	}

	/**
	 * Read a raw frame into a scaled/format converted target frame.
	 * 
	 * Target should be this.getFrame() or a copy of it.
	 * 
	 * @param target
	 * @return the internal pts. Use convertPTS() to convert to milliseconds.
	 * @throws AVDecodingError
	 */
	public long readFrame(AVFrame target) throws AVDecodingError {

		// read packets until a whole frame is decoded
		AVFrame frame = readAVFrame();

		if (frame != null) {
			scale.scale(frame, 0, height, target);
			return pts;
		}

		return -1;
	}

	/**
	 * Read a frame into the target image.
	 * 
	 * Image should be allocated using createFrame()
	 * 
	 * @param dst
	 * @return the internal pts. Use convertPTS() to convert to milliseconds.
	 * @throws AVDecodingError
	 */
	public long readFrame(BufferedImage dst) throws AVDecodingError {

		long pt = readFrame(oframe);

		if (pt != -1) {
			AVPlane splane = oframe.getPlaneAt(0, PixelFormat.PIX_FMT_BGR24,
					swidth, sheight);
			byte[] data = ((DataBufferByte) dst.getRaster().getDataBuffer())
					.getData();

			splane.data.get(data, 0,
					Math.min(data.length, splane.data.capacity()));
			splane.data.rewind();
		}
		return pt;
	}

	/**
	 * Read the raw byte-byffer for the frame.
	 * 
	 * Data will be in the input format.
	 * 
	 * @return
	 * @throws AVDecodingError
	 */
	public ByteBuffer readFrame() throws AVDecodingError {

		AVFrame af = readAVFrame();
		if (af != null) {
			AVPlane splane = af.getPlaneAt(0, fmt, width, height);

			return splane.data;
		} else {
			return null;
		}
	}

	/**
	 * Attempt to seek to the nearest millisecond.
	 * 
	 * The next frame read should match the stamp.
	 * 
	 * This only seeks to key frames
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

		codecContext.flushBuffers();
	}

	/**
	 * Seek in stream units
	 * 
	 * @param stamp
	 * @throws AVIOException
	 */
	public void seek(long stamp) throws AVIOException {

		int res;

		res = format.seekFile(videoStream, 0, stamp, stamp, 0);
		if (res < 0) {
			throw new AVIOException(res, "Cannot seek");
		}

		seekid = stamp;
		codecContext.flushBuffers();
	}
}
