/*
 * Copyright (c) 2011 Michael Zucchi
 *
 * This file is part of jjdvb, a java binding to linux dvb.
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
package au.notzed.jjdvb;

import au.notzed.jjmpeg.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author notzed
 */
abstract public class DVBNative {

	final ByteBuffer p;

	protected DVBNative(ByteBuffer p) {
		this.p = p;
		p.order(ByteOrder.nativeOrder());
	}
	static final boolean is64;

	static {
		int bits;

		System.loadLibrary("jjdvb");
		bits = getPointerBits();

		if (bits == 0) {
			throw new UnsatisfiedLinkError("Unable to open jjdvb");
		}
		is64 = bits == 64;

		// may as well do these here i guess?
		AVCodecContext.init();
		AVFormatContext.registerAll();
	}

	static native ByteBuffer getPointer(ByteBuffer base, int offset, int size);

	static native ByteBuffer getPointerIndex(ByteBuffer base, int offset, int size, int index);

	static native int getPointerBits();

	static native ByteBuffer _malloc(int size);

	static native void _free(ByteBuffer mem);
}
