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
public class FE extends FEAbstract {

	protected FE(ByteBuffer p) {
		super(p);
	}

	public static FE create(String frontend) throws IOException {
		ByteBuffer x = fe_open(frontend);
		if (x == null) {
			throw new IOException("Unable to open frontend");
		}

		return new FE(x);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		_free(p);
	}
	static final int FE_READ_STATUS = 69;
	static final int FE_SET_FRONTEND = 76;

	static native ByteBuffer fe_open(String path);

	native void fe_close();

	native int fe_ioctl(int id);

	native int fe_ioctl(int id, ByteBuffer bb);

	public void close() {
		fe_close();
	}

	public int setFrontend(DVBFrontendParameters channel) {
		return fe_ioctl(FE_SET_FRONTEND, channel.p);
	}

	public int readStatus(FEStatus status) {
		return fe_ioctl(FE_READ_STATUS, status.p);
	}
}
