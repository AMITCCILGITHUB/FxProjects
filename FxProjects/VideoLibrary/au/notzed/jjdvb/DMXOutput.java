/*
 * Copyright (C) 2000 Marcus Metzler <marcus@convergence.de>
 *                  & Ralph  Metzler <ralph@convergence.de>
 *                    for convergence integrated media GmbH
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

/**
 *
 * @author notzed
 */
public enum DMXOutput {

	/** Streaming directly to decoder. */
	DMX_OUT_DECODER,
	/** Output going to a memory buffer
	 * (to be retrieved via the read command).*/
	DMX_OUT_TAP,
	/** Output multiplexed into a new TS
	 * (to be retrieved by reading from the
	 * logical DVR device).                 */
	DMX_OUT_TS_TAP,
	/** Like TS_TAP but retrieved from the DMX device */
	DMX_OUT_TSDEMUX_TAP;

	public int toC() {
		return ordinal();
	}

	public static DMXOutput fromC(int v) {
		return values()[v];
	}
}
