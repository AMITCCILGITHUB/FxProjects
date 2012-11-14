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

import java.nio.ByteBuffer;

/**
 *
 * @author notzed
 */
public class DVBFrontendParameters extends DVBFrontendParametersAbstract {

	protected DVBFrontendParameters(ByteBuffer p) {
		super(p);
	}

	public static DVBFrontendParameters create() {
		return new DVBFrontendParameters(alloc());
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		_free(p);
	}

	static native ByteBuffer alloc();
}
