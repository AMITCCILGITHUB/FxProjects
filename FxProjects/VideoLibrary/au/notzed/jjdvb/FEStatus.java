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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author notzed
 */
public class FEStatus extends DVBNative {

	/** found something above the noise level */
	public final int FE_HAS_SIGNAL = 0x01;
	/** found a DVB signal  */
	public final int FE_HAS_CARRIER = 0x02;
	/** FEC is stable  */
	public final int FE_HAS_VITERBI = 0x04;
	/** found sync bytes  */
	public final int FE_HAS_SYNC = 0x08;
	/** everything's working... */
	public final int FE_HAS_LOCK = 0x10;
	/** no lock within the last ~2 seconds */
	public final int FE_TIMEDOUT = 0x20;
	/** frontend was reinitialized,
	 * application is recommended to reset
	 * DiSEqC, tone and parameters */
	public final int FE_REINIT = 0x40;

	public FEStatus() {
		super(ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()));
	}

	public int getStatus() {
		return p.asIntBuffer().get(0);
	}
}
