/* I am automatically generated.  Editing me would be pointless,
   but I wont stop you if you so desire. */

package au.notzed.jjmpeg;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.IntBuffer;
import java.nio.DoubleBuffer;

abstract class AVCodecContextNativeAbstract extends AVNative {
	protected AVCodecContextNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native int getBitRate(ByteBuffer p);
	static native int setBitRate(ByteBuffer p, int val);
	static native int getFlags(ByteBuffer p);
	static native int setFlags(ByteBuffer p, int val);
	static native int getWidth(ByteBuffer p);
	static native int setWidth(ByteBuffer p, int val);
	static native int getHeight(ByteBuffer p);
	static native int setHeight(ByteBuffer p, int val);
	static native int getPixFmt(ByteBuffer p);
	static native int setPixFmt(ByteBuffer p, int val);
	static native int getSampleRate(ByteBuffer p);
	static native int setSampleRate(ByteBuffer p, int val);
	static native int getChannels(ByteBuffer p);
	static native int setChannels(ByteBuffer p, int val);
	static native int getChannelLayout(ByteBuffer p);
	static native int setChannelLayout(ByteBuffer p, int val);
	static native int getSampleFmt(ByteBuffer p);
	static native int setSampleFmt(ByteBuffer p, int val);
	static native int getFrameSize(ByteBuffer p);
	static native int setFrameSize(ByteBuffer p, int val);
	static native int getFrameNumber(ByteBuffer p);
	static native int getCodecType(ByteBuffer p);
	static native int setCodecType(ByteBuffer p, int val);
	static native int getCodecID(ByteBuffer p);
	static native int setCodecID(ByteBuffer p, int val);
	static native int getGOPSize(ByteBuffer p);
	static native int setGOPSize(ByteBuffer p, int val);
	static native int getMaxBFrames(ByteBuffer p);
	static native int setMaxBFrames(ByteBuffer p, int val);
	static native ByteBuffer getTimeBase(ByteBuffer p);
	static native int getTimeBaseNum(ByteBuffer p);
	static native int setTimeBaseNum(ByteBuffer p, int val);
	static native int getTimeBaseDen(ByteBuffer p);
	static native int setTimeBaseDen(ByteBuffer p, int val);
	static native int getStrictStdCompliance(ByteBuffer p);
	static native int setStrictStdCompliance(ByteBuffer p, int val);
	static native int getErrorRecognition(ByteBuffer p);
	static native int setErrorRecognition(ByteBuffer p, int val);
	static native int getIdctAlgo(ByteBuffer p);
	static native int setIdctAlgo(ByteBuffer p, int val);
	static native int getErrorConcealment(ByteBuffer p);
	static native int setErrorConcealment(ByteBuffer p, int val);
	static native int getGlobalQuality(ByteBuffer p);
	static native int setGlobalQuality(ByteBuffer p, int val);
	static native int getMbDecision(ByteBuffer p);
	static native int setMbDecision(ByteBuffer p, int val);
	static native ByteBuffer getCodedFrame(ByteBuffer p);
	// Native Methods
	static native int open(ByteBuffer p, ByteBuffer codec);
	static native int close(ByteBuffer p);
	static native int decode_video2(ByteBuffer p, ByteBuffer picture, IntBuffer got_picture_ptr, ByteBuffer avpkt);
	static native int encode_video(ByteBuffer p, ByteBuffer buf, int buf_size, ByteBuffer pict);
	static native int decode_audio3(ByteBuffer p, ShortBuffer samples, IntBuffer frame_size_ptr, ByteBuffer avpkt);
	static native int encode_audio(ByteBuffer p, ByteBuffer buf, int buf_size, ShortBuffer samples);
	static native void flush_buffers(ByteBuffer p);
	static native ByteBuffer alloc_context();
	static native void init();
}

abstract class AVCodecContextAbstract extends AVObject {
	// Fields
	public  int getBitRate() {
		return AVCodecContextNativeAbstract.getBitRate(n.p);
	}
	public  void setBitRate(int val) {
		AVCodecContextNativeAbstract.setBitRate(n.p, val);
	}
	public  int getFlags() {
		return AVCodecContextNativeAbstract.getFlags(n.p);
	}
	public  void setFlags(int val) {
		AVCodecContextNativeAbstract.setFlags(n.p, val);
	}
	public  int getWidth() {
		return AVCodecContextNativeAbstract.getWidth(n.p);
	}
	public  void setWidth(int val) {
		AVCodecContextNativeAbstract.setWidth(n.p, val);
	}
	public  int getHeight() {
		return AVCodecContextNativeAbstract.getHeight(n.p);
	}
	public  void setHeight(int val) {
		AVCodecContextNativeAbstract.setHeight(n.p, val);
	}
	public  PixelFormat getPixFmt() {
		return PixelFormat.values()[AVCodecContextNativeAbstract.getPixFmt(n.p)+1];
	}
	public  void setPixFmt(PixelFormat val) {
		AVCodecContextNativeAbstract.setPixFmt(n.p, val.toC());
	}
	public  int getSampleRate() {
		return AVCodecContextNativeAbstract.getSampleRate(n.p);
	}
	public  void setSampleRate(int val) {
		AVCodecContextNativeAbstract.setSampleRate(n.p, val);
	}
	public  int getChannels() {
		return AVCodecContextNativeAbstract.getChannels(n.p);
	}
	public  void setChannels(int val) {
		AVCodecContextNativeAbstract.setChannels(n.p, val);
	}
	public  int getChannelLayout() {
		return AVCodecContextNativeAbstract.getChannelLayout(n.p);
	}
	public  void setChannelLayout(int val) {
		AVCodecContextNativeAbstract.setChannelLayout(n.p, val);
	}
	public  SampleFormat getSampleFmt() {
		return SampleFormat.values()[AVCodecContextNativeAbstract.getSampleFmt(n.p)+1];
	}
	public  void setSampleFmt(SampleFormat val) {
		AVCodecContextNativeAbstract.setSampleFmt(n.p, val.toC());
	}
	public  int getFrameSize() {
		return AVCodecContextNativeAbstract.getFrameSize(n.p);
	}
	public  void setFrameSize(int val) {
		AVCodecContextNativeAbstract.setFrameSize(n.p, val);
	}
	public  int getFrameNumber() {
		return AVCodecContextNativeAbstract.getFrameNumber(n.p);
	}
	public  int getCodecType() {
		return AVCodecContextNativeAbstract.getCodecType(n.p);
	}
	public  void setCodecType(int val) {
		AVCodecContextNativeAbstract.setCodecType(n.p, val);
	}
	public  int getCodecID() {
		return AVCodecContextNativeAbstract.getCodecID(n.p);
	}
	public  void setCodecID(int val) {
		AVCodecContextNativeAbstract.setCodecID(n.p, val);
	}
	public  int getGOPSize() {
		return AVCodecContextNativeAbstract.getGOPSize(n.p);
	}
	public  void setGOPSize(int val) {
		AVCodecContextNativeAbstract.setGOPSize(n.p, val);
	}
	public  int getMaxBFrames() {
		return AVCodecContextNativeAbstract.getMaxBFrames(n.p);
	}
	public  void setMaxBFrames(int val) {
		AVCodecContextNativeAbstract.setMaxBFrames(n.p, val);
	}
	public  AVRational getTimeBase() {
		return AVRational.create(AVCodecContextNativeAbstract.getTimeBase(n.p));
	}
	public  int getTimeBaseNum() {
		return AVCodecContextNativeAbstract.getTimeBaseNum(n.p);
	}
	public  void setTimeBaseNum(int val) {
		AVCodecContextNativeAbstract.setTimeBaseNum(n.p, val);
	}
	public  int getTimeBaseDen() {
		return AVCodecContextNativeAbstract.getTimeBaseDen(n.p);
	}
	public  void setTimeBaseDen(int val) {
		AVCodecContextNativeAbstract.setTimeBaseDen(n.p, val);
	}
	public  int getStrictStdCompliance() {
		return AVCodecContextNativeAbstract.getStrictStdCompliance(n.p);
	}
	public  void setStrictStdCompliance(int val) {
		AVCodecContextNativeAbstract.setStrictStdCompliance(n.p, val);
	}
	public  int getErrorRecognition() {
		return AVCodecContextNativeAbstract.getErrorRecognition(n.p);
	}
	public  void setErrorRecognition(int val) {
		AVCodecContextNativeAbstract.setErrorRecognition(n.p, val);
	}
	public  int getIdctAlgo() {
		return AVCodecContextNativeAbstract.getIdctAlgo(n.p);
	}
	public  void setIdctAlgo(int val) {
		AVCodecContextNativeAbstract.setIdctAlgo(n.p, val);
	}
	public  int getErrorConcealment() {
		return AVCodecContextNativeAbstract.getErrorConcealment(n.p);
	}
	public  void setErrorConcealment(int val) {
		AVCodecContextNativeAbstract.setErrorConcealment(n.p, val);
	}
	public  int getGlobalQuality() {
		return AVCodecContextNativeAbstract.getGlobalQuality(n.p);
	}
	public  void setGlobalQuality(int val) {
		AVCodecContextNativeAbstract.setGlobalQuality(n.p, val);
	}
	public  int getMbDecision() {
		return AVCodecContextNativeAbstract.getMbDecision(n.p);
	}
	public  void setMbDecision(int val) {
		AVCodecContextNativeAbstract.setMbDecision(n.p, val);
	}
	public  AVFrame getCodedFrame() {
		return AVFrame.create(AVCodecContextNativeAbstract.getCodedFrame(n.p));
	}
	// Public Methods
	public int close() {
		return AVCodecContextNativeAbstract.close(n.p);
	}
	 int decodeVideo2(AVFrame picture, IntBuffer got_picture_ptr, AVPacket avpkt) {
		return AVCodecContextNativeAbstract.decode_video2(n.p, picture != null ? picture.n.p : null, got_picture_ptr, avpkt != null ? avpkt.n.p : null);
	}
	public int encodeVideo(ByteBuffer buf, int buf_size, AVFrame pict) {
		return AVCodecContextNativeAbstract.encode_video(n.p, buf, buf_size, pict != null ? pict.n.p : null);
	}
	 int decodeAudio3(ShortBuffer samples, IntBuffer frame_size_ptr, AVPacket avpkt) {
		return AVCodecContextNativeAbstract.decode_audio3(n.p, samples, frame_size_ptr, avpkt != null ? avpkt.n.p : null);
	}
	public int encodeAudio(ByteBuffer buf, int buf_size, ShortBuffer samples) {
		return AVCodecContextNativeAbstract.encode_audio(n.p, buf, buf_size, samples);
	}
	public void flushBuffers() {
		AVCodecContextNativeAbstract.flush_buffers(n.p);
	}
	static protected AVCodecContext allocContext() {
		return AVCodecContext.create(AVCodecContextNativeAbstract.alloc_context());
	}
	static public void init() {
		AVCodecContextNativeAbstract.init();
	}
}
abstract class AVCodecNativeAbstract extends AVNative {
	protected AVCodecNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native String getName(ByteBuffer p);
	// Native Methods
	static native ByteBuffer find_encoder(int id);
	static native ByteBuffer find_decoder(int id);
	static native ByteBuffer find_encoder_by_name(String name);
}

abstract class AVCodecAbstract extends AVObject {
	// Fields
	public  String getName() {
		return AVCodecNativeAbstract.getName(n.p);
	}
	// Public Methods
	static public AVCodec findEncoder(int id) {
		return AVCodec.create(AVCodecNativeAbstract.find_encoder(id));
	}
	static public AVCodec findDecoder(int id) {
		return AVCodec.create(AVCodecNativeAbstract.find_decoder(id));
	}
	static public AVCodec findEncoderByName(String name) {
		return AVCodec.create(AVCodecNativeAbstract.find_encoder_by_name(name));
	}
}
abstract class AVFormatContextNativeAbstract extends AVNative {
	protected AVFormatContextNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native ByteBuffer getInputFormat(ByteBuffer p);
	static native ByteBuffer setInputFormat(ByteBuffer p, ByteBuffer val);
	static native ByteBuffer getOutputFormat(ByteBuffer p);
	static native ByteBuffer setOutputFormat(ByteBuffer p, ByteBuffer val);
	static native ByteBuffer getIOContext(ByteBuffer p);
	static native ByteBuffer setIOContext(ByteBuffer p, ByteBuffer val);
	static native int getNBStreams(ByteBuffer p);
	static native ByteBuffer getStreamAt(ByteBuffer p, int index);
	static native long getStartTime(ByteBuffer p);
	static native long getDuration(ByteBuffer p);
	static native long getFileSize(ByteBuffer p);
	static native int getBitRate(ByteBuffer p);
	static native int getFlags(ByteBuffer p);
	static native int setFlags(ByteBuffer p, int val);
	// Native Methods
	static native void close_input_file(ByteBuffer p);
	static native void close_input_stream(ByteBuffer p);
	static native int seek_frame(ByteBuffer p, int stream_index, long timestamp, int flags);
	static native int read_frame(ByteBuffer p, ByteBuffer pkt);
	static native int find_stream_info(ByteBuffer p);
	static native ByteBuffer new_stream(ByteBuffer p, int id);
	static native int set_parameters(ByteBuffer p, ByteBuffer ap);
	static native int write_header(ByteBuffer p);
	static native int write_frame(ByteBuffer p, ByteBuffer pkt);
	static native int interleaved_write_frame(ByteBuffer p, ByteBuffer pkt);
	static native int write_trailer(ByteBuffer p);
	static native void register_all();
	static native int seek_file(ByteBuffer p, int stream_index, long min_ts, long ts, long max_ts, int flags);
	static native ByteBuffer alloc_context();
	static native void free_context(ByteBuffer p);
}

abstract class AVFormatContextAbstract extends AVObject {
	// Fields
	public  AVInputFormat getInputFormat() {
		return AVInputFormat.create(AVFormatContextNativeAbstract.getInputFormat(n.p));
	}
	public  void setInputFormat(AVInputFormat val) {
		AVFormatContextNativeAbstract.setInputFormat(n.p, val != null ? val.n.p : null);
	}
	public  AVOutputFormat getOutputFormat() {
		return AVOutputFormat.create(AVFormatContextNativeAbstract.getOutputFormat(n.p));
	}
	public  void setOutputFormat(AVOutputFormat val) {
		AVFormatContextNativeAbstract.setOutputFormat(n.p, val != null ? val.n.p : null);
	}
	public  AVIOContext getIOContext() {
		return AVIOContext.create(AVFormatContextNativeAbstract.getIOContext(n.p));
	}
	public  void setIOContext(AVIOContext val) {
		AVFormatContextNativeAbstract.setIOContext(n.p, val != null ? val.n.p : null);
	}
	public  int getNBStreams() {
		return AVFormatContextNativeAbstract.getNBStreams(n.p);
	}
	public  AVStream getStreamAt(int index) {
		return AVStream.create(AVFormatContextNativeAbstract.getStreamAt(n.p, index));
	}
	public  long getStartTime() {
		return AVFormatContextNativeAbstract.getStartTime(n.p);
	}
	public  long getDuration() {
		return AVFormatContextNativeAbstract.getDuration(n.p);
	}
	public  long getFileSize() {
		return AVFormatContextNativeAbstract.getFileSize(n.p);
	}
	public  int getBitRate() {
		return AVFormatContextNativeAbstract.getBitRate(n.p);
	}
	public  int getFlags() {
		return AVFormatContextNativeAbstract.getFlags(n.p);
	}
	public  void setFlags(int val) {
		AVFormatContextNativeAbstract.setFlags(n.p, val);
	}
	// Public Methods
	public void closeInputFile() {
		AVFormatContextNativeAbstract.close_input_file(n.p);
	}
	public void closeInputStream() {
		AVFormatContextNativeAbstract.close_input_stream(n.p);
	}
	public int seekFrame(int stream_index, long timestamp, int flags) {
		return AVFormatContextNativeAbstract.seek_frame(n.p, stream_index, timestamp, flags);
	}
	public int readFrame(AVPacket pkt) {
		return AVFormatContextNativeAbstract.read_frame(n.p, pkt != null ? pkt.n.p : null);
	}
	public int findStreamInfo() {
		return AVFormatContextNativeAbstract.find_stream_info(n.p);
	}
	public AVStream newStream(int id) {
		return AVStream.create(AVFormatContextNativeAbstract.new_stream(n.p, id));
	}
	public int setParameters(AVFormatParameters ap) {
		return AVFormatContextNativeAbstract.set_parameters(n.p, ap != null ? ap.n.p : null);
	}
	public int writeHeader() {
		return AVFormatContextNativeAbstract.write_header(n.p);
	}
	public int writeFrame(AVPacket pkt) {
		return AVFormatContextNativeAbstract.write_frame(n.p, pkt != null ? pkt.n.p : null);
	}
	public int interleavedWriteFrame(AVPacket pkt) {
		return AVFormatContextNativeAbstract.interleaved_write_frame(n.p, pkt != null ? pkt.n.p : null);
	}
	public int writeTrailer() {
		return AVFormatContextNativeAbstract.write_trailer(n.p);
	}
	static public void registerAll() {
		AVFormatContextNativeAbstract.register_all();
	}
	public int seekFile(int stream_index, long min_ts, long ts, long max_ts, int flags) {
		return AVFormatContextNativeAbstract.seek_file(n.p, stream_index, min_ts, ts, max_ts, flags);
	}
	static public AVFormatContext allocContext() {
		return AVFormatContext.create(AVFormatContextNativeAbstract.alloc_context());
	}
	protected void freeContext() {
		AVFormatContextNativeAbstract.free_context(n.p);
	}
}
abstract class AVInputFormatNativeAbstract extends AVNative {
	protected AVInputFormatNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native String getName(ByteBuffer p);
	static native String getLongName(ByteBuffer p);
	// Native Methods
	static native ByteBuffer find_input_format(String short_name);
}

abstract class AVInputFormatAbstract extends AVObject {
	// Fields
	public  String getName() {
		return AVInputFormatNativeAbstract.getName(n.p);
	}
	public  String getLongName() {
		return AVInputFormatNativeAbstract.getLongName(n.p);
	}
	// Public Methods
	static public AVInputFormat findInputFormat(String short_name) {
		return AVInputFormat.create(AVInputFormatNativeAbstract.find_input_format(short_name));
	}
}
abstract class AVOutputFormatNativeAbstract extends AVNative {
	protected AVOutputFormatNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native String getName(ByteBuffer p);
	static native String getLongName(ByteBuffer p);
	static native String getMimeType(ByteBuffer p);
	static native String getExtensions(ByteBuffer p);
	static native int getVideoCodec(ByteBuffer p);
	static native int getAudioCodec(ByteBuffer p);
	static native int getSubtitleCodec(ByteBuffer p);
	static native int getFlags(ByteBuffer p);
	static native int setFlags(ByteBuffer p, int val);
	// Native Methods
	static native ByteBuffer guess_format(String short_name, String filename, String mime_type);
}

abstract class AVOutputFormatAbstract extends AVObject {
	// Fields
	public  String getName() {
		return AVOutputFormatNativeAbstract.getName(n.p);
	}
	public  String getLongName() {
		return AVOutputFormatNativeAbstract.getLongName(n.p);
	}
	public  String getMimeType() {
		return AVOutputFormatNativeAbstract.getMimeType(n.p);
	}
	public  String getExtensions() {
		return AVOutputFormatNativeAbstract.getExtensions(n.p);
	}
	public  int getVideoCodec() {
		return AVOutputFormatNativeAbstract.getVideoCodec(n.p);
	}
	public  int getAudioCodec() {
		return AVOutputFormatNativeAbstract.getAudioCodec(n.p);
	}
	public  int getSubtitleCodec() {
		return AVOutputFormatNativeAbstract.getSubtitleCodec(n.p);
	}
	public  int getFlags() {
		return AVOutputFormatNativeAbstract.getFlags(n.p);
	}
	public  void setFlags(int val) {
		AVOutputFormatNativeAbstract.setFlags(n.p, val);
	}
	// Public Methods
	static public AVOutputFormat guessFormat(String short_name, String filename, String mime_type) {
		return AVOutputFormat.create(AVOutputFormatNativeAbstract.guess_format(short_name, filename, mime_type));
	}
}
abstract class AVFormatParametersNativeAbstract extends AVNative {
	protected AVFormatParametersNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	// Native Methods
}

abstract class AVFormatParametersAbstract extends AVObject {
	// Fields
	// Public Methods
}
abstract class AVPacketNativeAbstract extends AVNative {
	protected AVPacketNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native long getPTS(ByteBuffer p);
	static native long setPTS(ByteBuffer p, long val);
	static native long getDTS(ByteBuffer p);
	static native long setDTS(ByteBuffer p, long val);
	static native int getSize(ByteBuffer p);
	static native int getStreamIndex(ByteBuffer p);
	static native int setStreamIndex(ByteBuffer p, int val);
	static native long getPos(ByteBuffer p);
	static native int getFlags(ByteBuffer p);
	static native int setFlags(ByteBuffer p, int val);
	// Native Methods
	static native void free_packet(ByteBuffer p);
	static native void init_packet(ByteBuffer p);
}

abstract class AVPacketAbstract extends AVObject {
	// Fields
	public  long getPTS() {
		return AVPacketNativeAbstract.getPTS(n.p);
	}
	public  void setPTS(long val) {
		AVPacketNativeAbstract.setPTS(n.p, val);
	}
	public  long getDTS() {
		return AVPacketNativeAbstract.getDTS(n.p);
	}
	public  void setDTS(long val) {
		AVPacketNativeAbstract.setDTS(n.p, val);
	}
	public  int getSize() {
		return AVPacketNativeAbstract.getSize(n.p);
	}
	public  int getStreamIndex() {
		return AVPacketNativeAbstract.getStreamIndex(n.p);
	}
	public  void setStreamIndex(int val) {
		AVPacketNativeAbstract.setStreamIndex(n.p, val);
	}
	public  long getPos() {
		return AVPacketNativeAbstract.getPos(n.p);
	}
	public  int getFlags() {
		return AVPacketNativeAbstract.getFlags(n.p);
	}
	public  void setFlags(int val) {
		AVPacketNativeAbstract.setFlags(n.p, val);
	}
	// Public Methods
	public void freePacket() {
		AVPacketNativeAbstract.free_packet(n.p);
	}
	public void initPacket() {
		AVPacketNativeAbstract.init_packet(n.p);
	}
}
abstract class AVFrameNativeAbstract extends AVNative {
	protected AVFrameNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native int getLineSizeAt(ByteBuffer p, int index);
	static native int getKeyFrame(ByteBuffer p);
	static native long getPTS(ByteBuffer p);
	static native long setPTS(ByteBuffer p, long val);
	static native int getDisplayPictureNumber(ByteBuffer p);
	static native int getCodedPictureNumber(ByteBuffer p);
	// Native Methods
	static native ByteBuffer alloc_frame();
	static native int alloc(ByteBuffer p, int pix_fmt, int width, int height);
	static native void free(ByteBuffer p);
}

abstract class AVFrameAbstract extends AVObject {
	// Fields
	public  int getLineSizeAt(int index) {
		return AVFrameNativeAbstract.getLineSizeAt(n.p, index);
	}
	public  int getKeyFrame() {
		return AVFrameNativeAbstract.getKeyFrame(n.p);
	}
	public  long getPTS() {
		return AVFrameNativeAbstract.getPTS(n.p);
	}
	public  void setPTS(long val) {
		AVFrameNativeAbstract.setPTS(n.p, val);
	}
	public  int getDisplayPictureNumber() {
		return AVFrameNativeAbstract.getDisplayPictureNumber(n.p);
	}
	public  int getCodedPictureNumber() {
		return AVFrameNativeAbstract.getCodedPictureNumber(n.p);
	}
	// Public Methods
	static public AVFrame allocFrame() {
		return AVFrame.create(AVFrameNativeAbstract.alloc_frame());
	}
	public int alloc(int pix_fmt, int width, int height) {
		return AVFrameNativeAbstract.alloc(n.p, pix_fmt, width, height);
	}
	public void free() {
		AVFrameNativeAbstract.free(n.p);
	}
}
abstract class AVStreamNativeAbstract extends AVNative {
	protected AVStreamNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native int getIndex(ByteBuffer p);
	static native ByteBuffer getCodec(ByteBuffer p);
	static native long getNBFrames(ByteBuffer p);
	static native float getQuality(ByteBuffer p);
	static native float setQuality(ByteBuffer p, float val);
	static native long getStartTime(ByteBuffer p);
	static native long setStartTime(ByteBuffer p, long val);
	static native long getDuration(ByteBuffer p);
	static native long setDuration(ByteBuffer p, long val);
	static native ByteBuffer getRFrameRate(ByteBuffer p);
	static native ByteBuffer getTimeBase(ByteBuffer p);
	static native ByteBuffer getSampleAspectRatio(ByteBuffer p);
	static native int getSampleAspectRatioNum(ByteBuffer p);
	static native int setSampleAspectRatioNum(ByteBuffer p, int val);
	static native int getSampleAspectRatioDen(ByteBuffer p);
	static native int setSampleAspectRatioDen(ByteBuffer p, int val);
	// Native Methods
}

abstract class AVStreamAbstract extends AVObject {
	// Fields
	public  int getIndex() {
		return AVStreamNativeAbstract.getIndex(n.p);
	}
	public  AVCodecContext getCodec() {
		return AVCodecContext.create(AVStreamNativeAbstract.getCodec(n.p));
	}
	public  long getNBFrames() {
		return AVStreamNativeAbstract.getNBFrames(n.p);
	}
	public  float getQuality() {
		return AVStreamNativeAbstract.getQuality(n.p);
	}
	public  void setQuality(float val) {
		AVStreamNativeAbstract.setQuality(n.p, val);
	}
	public  long getStartTime() {
		return AVStreamNativeAbstract.getStartTime(n.p);
	}
	public  void setStartTime(long val) {
		AVStreamNativeAbstract.setStartTime(n.p, val);
	}
	public  long getDuration() {
		return AVStreamNativeAbstract.getDuration(n.p);
	}
	public  void setDuration(long val) {
		AVStreamNativeAbstract.setDuration(n.p, val);
	}
	public  AVRational getRFrameRate() {
		return AVRational.create(AVStreamNativeAbstract.getRFrameRate(n.p));
	}
	public  AVRational getTimeBase() {
		return AVRational.create(AVStreamNativeAbstract.getTimeBase(n.p));
	}
	public  AVRational getSampleAspectRatio() {
		return AVRational.create(AVStreamNativeAbstract.getSampleAspectRatio(n.p));
	}
	public  int getSampleAspectRatioNum() {
		return AVStreamNativeAbstract.getSampleAspectRatioNum(n.p);
	}
	public  void setSampleAspectRatioNum(int val) {
		AVStreamNativeAbstract.setSampleAspectRatioNum(n.p, val);
	}
	public  int getSampleAspectRatioDen() {
		return AVStreamNativeAbstract.getSampleAspectRatioDen(n.p);
	}
	public  void setSampleAspectRatioDen(int val) {
		AVStreamNativeAbstract.setSampleAspectRatioDen(n.p, val);
	}
	// Public Methods
}
abstract class AVRationalNativeAbstract extends AVNative {
	protected AVRationalNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	static native int getNum(ByteBuffer p);
	static native int setNum(ByteBuffer p, int val);
	static native int getDen(ByteBuffer p);
	static native int setDen(ByteBuffer p, int val);
	// Native Methods
}

abstract class AVRationalAbstract extends AVObject {
	// Fields
	public  int getNum() {
		return AVRationalNativeAbstract.getNum(n.p);
	}
	public  void setNum(int val) {
		AVRationalNativeAbstract.setNum(n.p, val);
	}
	public  int getDen() {
		return AVRationalNativeAbstract.getDen(n.p);
	}
	public  void setDen(int val) {
		AVRationalNativeAbstract.setDen(n.p, val);
	}
	// Public Methods
}
abstract class AVIOContextNativeAbstract extends AVNative {
	protected AVIOContextNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	// Native Methods
	static native int close(ByteBuffer p);
}

abstract class AVIOContextAbstract extends AVObject {
	// Fields
	// Public Methods
	public int close() {
		return AVIOContextNativeAbstract.close(n.p);
	}
}
abstract class SwsContextNativeAbstract extends AVNative {
	protected SwsContextNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	// Native Methods
	static native ByteBuffer getContext(int srcW, int srcH, int srcFormat, int dstW, int dstH, int dstFormat, int flags, ByteBuffer srcFilter, ByteBuffer dstFilter, DoubleBuffer param);
	static native void freeContext(ByteBuffer p);
}

abstract class SwsContextAbstract extends AVObject {
	// Fields
	// Public Methods
	public void freeContext() {
		SwsContextNativeAbstract.freeContext(n.p);
	}
}
abstract class SwsFilterNativeAbstract extends AVNative {
	protected SwsFilterNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	// Native Methods
}

abstract class SwsFilterAbstract extends AVObject {
	// Fields
	// Public Methods
}
abstract class ReSampleContextNativeAbstract extends AVNative {
	protected ReSampleContextNativeAbstract(AVObject o, ByteBuffer p) {
		super(o, p);
	}
	// Fields
	// Native Methods
	static native ByteBuffer resample_init(int output_channels, int input_channels, int output_rate, int input_rate, int sample_fmt_out, int sample_fmt_in, int filter_length, int log2_phase_count, int linear, double cutoff);
	static native int resample(ByteBuffer p, ShortBuffer output, ShortBuffer input, int nb_samples);
	static native void resample_close(ByteBuffer p);
}

abstract class ReSampleContextAbstract extends AVObject {
	// Fields
	// Public Methods
	static public ReSampleContext resampleInit(int output_channels, int input_channels, int output_rate, int input_rate, SampleFormat sample_fmt_out, SampleFormat sample_fmt_in, int filter_length, int log2_phase_count, int linear, double cutoff) {
		return ReSampleContext.create(ReSampleContextNativeAbstract.resample_init(output_channels, input_channels, output_rate, input_rate, sample_fmt_out.toC(), sample_fmt_in.toC(), filter_length, log2_phase_count, linear, cutoff));
	}
	 int resample(ShortBuffer output, ShortBuffer input, int nb_samples) {
		return ReSampleContextNativeAbstract.resample(n.p, output, input, nb_samples);
	}
	public void resampleClose() {
		ReSampleContextNativeAbstract.resample_close(n.p);
	}
}
