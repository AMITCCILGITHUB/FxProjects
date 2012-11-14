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

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author notzed
 */
public class DMX extends DMXAbstract {

	protected DMX(ByteBuffer p) {
		super(p);
	}

	public static DMX create(String dmxname) throws IOException {
		ByteBuffer x = dmx_open(dmxname);

		if (x == null) {
			throw new IOException("Unable to open demux " + dmxname);
		}

		return new DMX(x);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		_free(p);
	}
	// TODO: use the real numbers
	static final int DMX_START = 0;
	static final int DMX_STOP = 1;
	static final int DMX_SET_FILTER = 2;
	static final int DMX_SET_PES_FILTER = 3;
	static final int DMX_SET_BUFFER_SIZE = 4;
	//static final int DMX_GET_PES_PIDS = 5;//         _IOR('o', 47, __u16[5])
	//static final int DMX_GET_CAPS = 6;//             _IOR('o', 48, dmx_caps_t)
	//static final int DMX_SET_SOURCE = 7;//           _IOW('o', 49, dmx_source_t)
	//static final int DMX_GET_STC = 8;//              _IOWR('o', 50, struct dmx_stc)
	static final int DMX_ADD_PID = 9;//             _IOW('o', 51, __u16)
	static final int DMX_REMOVE_PID = 10;//           _IOW('o', 52, __u16)

	static native ByteBuffer dmx_open(String path);

	native void dmx_close();

	native int dmx_ioctl(int id);

	native int dmx_ioctl(int id, long v);

	native int dmx_ioctl(int id, ByteBuffer Sbb);

	public void close() {
		dmx_close();
	}

	public void start() {
		dmx_ioctl(DMX_START);
	}

	public void stop() {
		dmx_ioctl(DMX_STOP);
	}

	public void setPESFilter(DMXPESFilterParams filter) {
		if (dmx_ioctl(DMX_SET_PES_FILTER, filter.p) != 0) {
			System.err.println("setPESFilter failed");
		}
	}

	public void addPID(short pid) {
		dmx_ioctl(DMX_ADD_PID, pid);
	}

	public void setBufferSize(long size) {
		int res;

		stop();
		res = dmx_ioctl(DMX_SET_BUFFER_SIZE, size);
		start();

		if ((res) != 0) {
			System.err.println("setBufferSize failed " + res);
		}
	}
}
