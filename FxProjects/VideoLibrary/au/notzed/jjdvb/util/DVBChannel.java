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
package au.notzed.jjdvb.util;

import au.notzed.jjdvb.DVBFrontendParameters;
import au.notzed.jjdvb.FEBandwidth;
import au.notzed.jjdvb.FECodeRate;
import au.notzed.jjdvb.FEGuardInterval;
import au.notzed.jjdvb.FEHierarchy;
import au.notzed.jjdvb.FEModulation;
import au.notzed.jjdvb.FESpectralInversion;
import au.notzed.jjdvb.FETransmitMode;
import java.io.IOException;

/**
 * Represents a single channel
 *
 * TODO: decide whether the C object is wrapped for each instance
 * or just set when required.
 * 
 * @author notzed
 */
public class DVBChannel {
	public String name;
	public DVBFrontendParameters params;
	public int vpid;
	public int apid;
	public int sid;

	/**
	 * Initialise a channel from a tzap format channel list line
	 *
	 * e.g. for my abc:
	 * ABC News 24:226500000:INVERSION_AUTO:BANDWIDTH_7_MHZ:FEC_3_4:FEC_1_2:QAM_64:TRANSMISSION_MODE_8K:GUARD_INTERVAL_1_16:HIERARCHY_NONE:2314:0:592

	 * @param chlist
	 */
	public DVBChannel(String chlist) throws IOException {
		String[] s = chlist.split(":");

		if (s.length < 12)
			throw new IOException("Invalid channel format");

		name = s[0];
		params = DVBFrontendParameters.create();
		params.setFrequency((int)Long.parseLong(s[1]));
		params.setInversion(FESpectralInversion.valueOf(s[2]));
		params.setofdmBandwidth(FEBandwidth.valueOf(s[3]));
		params.setofdmCodeRateHP(FECodeRate.valueOf(s[4]));
		params.setofdmCodeRateLP(FECodeRate.valueOf(s[5]));
		params.setofdmConstellation(FEModulation.valueOf(s[6]));
		params.setofdmTransmissionMode(FETransmitMode.valueOf(s[7]));
		params.setofdmGuardInterval(FEGuardInterval.valueOf(s[8]));
		params.setofdmHierarchyInformation(FEHierarchy.valueOf(s[9]));
		vpid = Integer.parseInt(s[10]);
		apid = Integer.parseInt(s[11]);
		sid = Integer.parseInt(s[12]);
	}
}
