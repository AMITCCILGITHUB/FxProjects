/*
 * Copyright (C) 2001-2003 Michael Niedermayer <michaelni@gmx.at>
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
package au.notzed.jjmpeg;

import java.nio.ByteBuffer;

/**
 * Used to convert between formats and optionally scale at the same time.
 * @author notzed
 */
public class ReSampleContext extends ReSampleContextAbstract {

	protected ReSampleContext(ByteBuffer p) {
		setNative(new ReSampleContextNative(this, p));
	}

	static ReSampleContext create(ByteBuffer p) {
		return new ReSampleContext(p);
	}

	static public ReSampleContext create(int output_channels, int input_channels, int output_rate, int input_rate, SampleFormat sample_fmt_out, SampleFormat sample_fmt_in, int filter_length, int log2_phase_count, int linear, double cutoff) {
		return ReSampleContext.resampleInit(output_channels, input_channels, output_rate, input_rate, sample_fmt_out, sample_fmt_in, filter_length, log2_phase_count, linear, cutoff);
	}

	public void close() {
		dispose();
	}
}

class ReSampleContextNative extends ReSampleContextNativeAbstract {

	ReSampleContextNative(AVObject o, ByteBuffer p) {
		super(o, p);
	}

	@Override
	public void dispose() {
		if (p != null) {
			resample_close(p);
			super.dispose();
		}
	}
}
