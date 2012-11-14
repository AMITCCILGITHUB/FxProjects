/*
 * Based on Libavformat API example: Output a media file in any supported
 * libavformat format. The default codecs are used.
 *
 * Copyright (c) 2003 Fabrice Bellard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/*
 * See: http://cekirdek.pardus.org.tr/~ismail/ffmpeg-docs/output-example_8c-source.html
 */
package au.notzed.jjmpeg.io;

import au.notzed.jjmpeg.AVCodec;
import au.notzed.jjmpeg.AVCodecContext;
import au.notzed.jjmpeg.AVFormatContext;
import au.notzed.jjmpeg.AVFrame;
import au.notzed.jjmpeg.AVIOContext;
import au.notzed.jjmpeg.AVMediaType;
import au.notzed.jjmpeg.AVOutputFormat;
import au.notzed.jjmpeg.AVPacket;
import au.notzed.jjmpeg.AVPlane;
import au.notzed.jjmpeg.AVRational;
import au.notzed.jjmpeg.AVSamples;
import au.notzed.jjmpeg.AVStream;
import au.notzed.jjmpeg.CodecID;
import au.notzed.jjmpeg.PixelFormat;
import au.notzed.jjmpeg.SampleFormat;
import au.notzed.jjmpeg.SwsContext;
import au.notzed.jjmpeg.exception.AVEncodingError;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.exception.AVInvalidCodecException;
import au.notzed.jjmpeg.exception.AVInvalidFormatException;
import au.notzed.jjmpeg.exception.AVInvalidStreamException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

/**
 * High level av file writer interface
 * @author notzed
 */
public class JJMediaWriter {

	LinkedList<JJWriterStream> streams = new LinkedList<JJWriterStream>();
	String filename;
	AVOutputFormat format;
	AVFormatContext oc;
	AVIOContext output;

	/**
	 * Create a new stream for writing to a file.
	 *
	 * Once created, call addVideoStream, (addAudioStream) and so on, before calling open().
	 * Frames are then added using the various addFrame() methods until finished,
	 * and then call close().
	 *
	 * @param filename
	 * @throws AVInvalidFormatException
	 */
	public JJMediaWriter(String filename) throws AVInvalidFormatException {
		this.filename = filename;

		/* create packet for re-use later */

		/* auto detect the output format from the name. default is mpeg. */
		format = AVOutputFormat.guessFormat(null, filename, null);
		if (format == null) {
			format = AVOutputFormat.guessFormat("mpeg", null, null);
		}
		if (format == null) {
			throw new AVInvalidFormatException(filename);
		}

		/* allocate the output media context */
		oc = AVFormatContext.allocContext();
		oc.setOutputFormat(format);

		// Caller now calls addVideoStream, etc.
	}

	/**
	 * Get the output format being used.
	 * @return
	 */
	public AVOutputFormat getFormat() {
		return format;
	}

	/**
	 * The stream must be opened after adding streams and before writing to them.
	 * @throws AVInvalidFormatException
	 * @throws AVInvalidCodecException
	 * @throws AVIOException
	 */
	public void open() throws AVInvalidFormatException, AVInvalidCodecException, AVIOException {
		/* set the output parameters (must be done even if no parameters). */
		if (oc.setParameters(null) < 0) {
			throw new AVInvalidFormatException("Unable to set parameters");
		}

		/* now that all the parameters are set, we can open the audio and
		video codecs and allocate the necessary encode buffers */
		for (JJWriterStream sd : streams) {
			sd.open();
		}

		/* open the output file, if needed */
		//if (!(format->flags & AVFMT_NOFILE)) {
		output = AVIOContext.open(filename, AVIOContext.URL_WRONLY);
		oc.setIOContext(output);

		/* write the stream header, if any */
		oc.writeHeader();
	}

	/**
	 * Add a stream using the default video codec for this stream.
	 * The streamid is the index of the stream in the streams list.
	 * @param width
	 * @param height
	 * @param frame_rate
	 * @param bit_rate
	 * @return
	 * @throws AVInvalidStreamException
	 */
	public JJWriterVideo addVideoStream(int width, int height, int frame_rate, int bit_rate) throws AVInvalidStreamException {
		return addVideoStream(format.getVideoCodec(), streams.size(), width, height, frame_rate, bit_rate);
	}

	/**
	 * Add a video stream.
	 * @param codec_id
	 * @param width
	 * @param height
	 * @param frame_rate
	 * @param bit_rate
	 * @return
	 * @throws AVInvalidStreamException
	 */
	public JJWriterVideo addVideoStream(int codec_id, int streamid, int width, int height, int frame_rate, int bit_rate) throws AVInvalidStreamException {
		AVCodecContext c;
		AVStream st;

		System.out.printf("adding video %s [%d %dx%d@%d]\n", codec_id, streamid, width, height, frame_rate);

		st = oc.newStream(streamid);
		if (st == null) {
			throw new AVInvalidStreamException("Unable to create stream");
		}

		c = st.getCodec();
		c.setCodecID(codec_id);
		c.setCodecType(AVMediaType.AVMEDIA_TYPE_VIDEO.toC());
		/* put sample parameters */
		c.setBitRate(bit_rate);

		/* resolution must be a multiple of two */
		c.setWidth(width);
		c.setHeight(height);
		/* time base: this is the fundamental unit of time (in seconds) in terms
		of which frame timestamps are represented. for fixed-fps content,
		timebase should be 1/framerate and timestamp increments should be
		identically 1. */
		c.setTimeBaseDen(frame_rate);
		c.setTimeBaseNum(1);
		c.setGOPSize(12); /* emit one intra frame every twelve frames at most */
		c.setPixFmt(PixelFormat.PIX_FMT_YUV420P);
		if (codec_id == CodecID.CODEC_ID_MPEG2VIDEO) {
			/* just for testing, we also add B frames */
			c.setMaxBFrames(2);
		}
		if (codec_id == CodecID.CODEC_ID_MPEG1VIDEO) {
			/* Needed to avoid using macroblocks in which some coeffs overflow.
			This does not happen with normal video, it just happens here as
			the motion of the chroma plane does not match the luma plane. */
			c.setMbDecision(2);
		}
		// FIXME: some formats want stream headers to be separate
		if ((oc.getOutputFormat().getFlags() & AVOutputFormat.AVFMT_GLOBALHEADER) != 0) {
			c.setFlags(c.getFlags() | AVCodecContext.CODEC_FLAG_GLOBAL_HEADER);
		}
		JJWriterVideo sd = new JJWriterVideo(st);
		streams.add(sd);

		return sd;
	}

	/**
	 * Add a new audio stream.
	 * @param codec_id
	 * @param streamid
	 * @param fmt
	 * @param sample_rate
	 * @param bit_rate
	 * @return
	 * @throws AVInvalidStreamException
	 */
	public JJWriterAudio addAudioStream(int codec_id, int streamid, SampleFormat fmt, int sample_rate, int channels, int bit_rate) throws AVInvalidStreamException {
		AVCodecContext c;
		AVStream st;

		st = oc.newStream(streamid);
		if (st == null) {
			throw new AVInvalidStreamException("Unable to create stream");
		}

		{
			AVCodec codec = AVCodec.findDecoder(codec_id);
			System.out.printf("Adding audio %s [%d %s %dHz %db]\n", codec.getName(), streamid, fmt, sample_rate, bit_rate);
		}

		c = st.getCodec();
		c.setCodecID(codec_id);
		c.setCodecType(AVMediaType.AVMEDIA_TYPE_AUDIO.toC());

		// sample parameters
		c.setSampleFmt(fmt);
		c.setBitRate(bit_rate);
		c.setSampleRate(sample_rate);

		c.setChannels(channels);
		c.setChannelLayout(4);

		if ((oc.getOutputFormat().getFlags() & AVOutputFormat.AVFMT_GLOBALHEADER) != 0) {
			c.setFlags(c.getFlags() | AVCodecContext.CODEC_FLAG_GLOBAL_HEADER);
		}

		JJWriterAudio as = new JJWriterAudio(st);

		streams.add(as);

		return as;
	}

	/**
	 * Close the file, completing the stream and freeing resources.
	 */
	public void close() {
		/* write the trailer, if any.  the trailer must be written
		 * before you close the CodecContexts open when you wrote the
		 * header; otherwise write_trailer may try to use memory that
		 * was freed on av_codec_close() */
		oc.writeTrailer();

		/* close each codec */
		for (JJWriterStream sd : streams) {
			sd.close();
		}

		/* close the output file */
		output.close();

		/* free the stream */
		oc.dispose();
	}

	public abstract class JJWriterStream {

		AVStream stream;
		ByteBuffer outputBuffer;
		AVCodecContext c;
		AVPacket packet;

		public JJWriterStream(AVStream stream) {
			this.stream = stream;
			c = stream.getCodec();
			packet = AVPacket.create();
		}

		/**
		 * Retrieve the av stream.
		 * @return
		 */
		public AVStream getStream() {
			return stream;
		}

		/**
		 * Retrieve the codec context for this stream.
		 * @return
		 */
		public AVCodecContext getContext() {
			return c;
		}

		abstract void open() throws AVInvalidCodecException, AVIOException;

		void close() {
			stream.getCodec().close();
			packet.dispose();
		}
	}

	/**
	 * Represents a video stream.
	 */
	public class JJWriterVideo extends JJWriterStream {

		AVFrame picture;
		// for rgb input
		private AVFrame rgbPicture;
		private AVPlane rgbPlane;
		private SwsContext rgbSWS;

		public JJWriterVideo(AVStream stream) {
			super(stream);
		}

		@Override
		void open() throws AVInvalidCodecException, AVIOException {
			AVCodec codec;

			/* find the video encoder */
			codec = AVCodec.findEncoder(c.getCodecID());
			if (codec == null) {
				throw new AVInvalidCodecException("codec not found");
			}

			/* open the codec */
			c.open(codec);

			//if (!(oc - > oformat - > flags & AVFMT_RAWPICTURE)) {
			/* allocate output buffer */
			/* XXX: API change will be done */
			/* buffers passed into lav* can be allocated any way you prefer,
			as long as they're aligned enough for the architecture, and
			they're freed appropriately (such as using av_free for buffers
			allocated with av_malloc) */
			//video_outbuf_size = 200000;
			//video_outbuf = av_malloc(video_outbuf_size);
			//}
			// Raw video will need a buffer as big as the image in yuv
			int size = c.getWidth() * c.getHeight() * 2;

			outputBuffer = ByteBuffer.allocateDirect(size);
			/* allocate the encoded raw picture */
			PixelFormat pixFmt = c.getPixFmt();
			picture = AVFrame.create(pixFmt, c.getWidth(), c.getHeight());

			/* if the output format is not YUV420P, then a temporary YUV420P
			picture is needed too. It is then converted to the required
			output format */
			//if (pixFmt != PixelFormat.PIX_FMT_YUV420P) {
			//	tmp = AVFrame.create(PixelFormat.PIX_FMT_YUV420P, c.getWidth(), c.getHeight());
			//}
		}

		@Override
		void close() {
			super.close();

			picture.dispose();
			//if (tmp != null) {
			//	tmp.dispose();
			//}
			if (rgbPicture != null) {
				rgbPicture.dispose();
				rgbSWS.dispose();
			}
		}

		/**
		 * Convert bufferedimage to matching output format
		 * @param bi
		 */
		private AVFrame loadImage(BufferedImage bi) {
			int height = c.getHeight();

			byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
			// Convert to YUV

			// TODO: only if output format not bgr24
			if (rgbPicture == null) {
				int width = c.getWidth();

				rgbPicture = AVFrame.create(PixelFormat.PIX_FMT_BGR24, width, height);
				rgbSWS = SwsContext.create(width, height, PixelFormat.PIX_FMT_BGR24, width, height, c.getPixFmt(), SwsContext.SWS_X);
				rgbPlane = rgbPicture.getPlaneAt(0, PixelFormat.PIX_FMT_BGR24, width, height);
			}

			rgbPlane.data.put(data);
			rgbPlane.data.rewind();
			rgbSWS.scale(rgbPicture, 0, height, picture);

			return picture;
		}

		/**
		 * Create an image suitable for writing to this stream.
		 * i.e. will be TYPE_3BYTE_BGR
		 */
		public BufferedImage createImage() {
			return new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		}

		/**
		 * Write next video frame.
		 * @param sd
		 * @param frame format must match the codec pixel format
		 * @throws AVEncodingError
		 * @throws AVIOException
		 */
		public void addFrame(AVFrame frame) throws AVEncodingError, AVIOException {
			// TODO: raw video case
			/* encode the image */
			int out_size = c.encodeVideo(outputBuffer, frame);

			/* if zero size, it means the image was buffered */
			if (out_size > 0) {
				AVFrame cframe = c.getCodedFrame();

				packet.initPacket();
				if (cframe.getPTS() != AVCodecContext.AV_NOPTS_VALUE) {
					packet.setPTS(AVRational.rescaleQ(cframe.getPTS(), c.getTimeBase(), stream.getTimeBase()));
				}
				if (cframe.isKeyFrame()) {
					packet.setFlags(packet.getFlags() | AVPacket.AV_PKT_FLAG_KEY);
				}
				packet.setStreamIndex(stream.getIndex());
				packet.setData(outputBuffer, out_size);

				/* write the compressed frame in the media file */
				int ret = oc.interleavedWriteFrame(packet);
				//int ret = oc.writeFrame(pkt);

				if (ret < 0) {
					throw new AVIOException(ret);
				}
			}
		}

		/**
		 * Add a frame as an RGB buffered image.
		 * @param sd
		 * @param bi Should be created with createImage.
		 * @throws AVEncodingError
		 * @throws AVIOException
		 */
		public void addFrame(BufferedImage bi) throws AVEncodingError, AVIOException {
			// TODO: check image is the right size and format

			addFrame(loadImage(bi));
		}
	}

	public class JJWriterAudio extends JJWriterStream {

		AVFrame picture;
		long audio_input_frame_size;

		public JJWriterAudio(AVStream stream) {
			super(stream);
		}

		@Override
		void open() throws AVInvalidCodecException, AVIOException {
			AVCodec codec;

			/* find the audio encoder */
			codec = AVCodec.findEncoder(c.getCodecID());
			if (codec == null) {
				throw new AVInvalidCodecException("codec not found");
			}

			/* open the codec */
			c.open(codec);

			// TODO: pcm hack stuff
			if (c.getFrameSize() <= 1) {
			}

			System.out.println("audio codec framesize = " + c.getFrameSize());

			this.outputBuffer = ByteBuffer.allocateDirect(AVCodecContext.FF_MIN_BUFFER_SIZE).order(ByteOrder.nativeOrder());
		}

		@Override
		void close() {
			super.close();
		}

		/**
		 * Create a samples buffer suitable for storing raw samples
		 * @return
		 */
		public AVSamples createSamples() {
			return new AVSamples(c.getSampleFmt(), c.getChannels(), c.getFrameSize());
		}

		/**
		 * Write a new audio frame to the stream.
		 *
		 * It is up to the caller to interleave audio/video properly
		 * @param samples
		 * @throws AVEncodingError
		 * @throws AVIOException
		 */
		int n = 0;
		public void addFrame(AVSamples samples) throws AVEncodingError, AVIOException {
			int out_size = c.encodeAudio(this.outputBuffer, samples);

			AVFrame cframe = c.getCodedFrame();

			packet.initPacket();
			if (cframe != null && cframe.getPTS() != AVCodecContext.AV_NOPTS_VALUE) {
				packet.setPTS(AVRational.rescaleQ(cframe.getPTS(), c.getTimeBase(), stream.getTimeBase()));
			}

			packet.setStreamIndex(stream.getIndex());
			packet.setData(outputBuffer, out_size);
			int ret = oc.interleavedWriteFrame(packet);

			if (ret < 0) {
				throw new AVIOException(ret);
			}
		}
	}
}
