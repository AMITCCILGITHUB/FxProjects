/*
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
package au.notzed.jjmpeg.io;

import au.notzed.jjmpeg.AVIOContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Read a file through Java.
 *
 * @author notzed
 */
public class JJFileInputStream extends AVIOContext {

	FileInputStream fis;

	protected JJFileInputStream(FileInputStream is) {
		super(4096, 0);
		this.fis = is;
	}

	public static JJFileInputStream create(FileInputStream is) {
		JJFileInputStream jjfis = new JJFileInputStream(is);
		
		return jjfis;
	}

	public int readPacket(ByteBuffer dst) {
		//System.out.println("jjfilestream readpacket");
		try {
			int ret = fis.getChannel().read(dst);
			
			//System.out.println("jjfilestream.readpacket read " + ret);
			
			return ret;
		} catch (IOException ex) {
			//System.out.println("jjfilestream readpacket ioexception");
			return -1;
		}
	}

	public int writePacket(ByteBuffer src) {
		//System.out.println("jjfilestream writepacket");
		try {
			return fis.getChannel().write(src);
		} catch (IOException ex) {
			return -1;
		}
	}

	public long seek(long offset, int whence) {
		long res = -1;

		//System.out.println("jjfilestream seek " + offset + ", " + whence);
		
		try {
			switch (whence) {
				case AVSEEK_SIZE:
					return (int) fis.getChannel().size();
				case SEEK_SET:
					fis.getChannel().position(offset);
					res = fis.getChannel().position();
					break;
				case SEEK_CUR:
					fis.getChannel().position(fis.getChannel().position() + whence);
					res = fis.getChannel().position();
					break;
				case SEEK_END:
					fis.getChannel().position(fis.getChannel().size() - whence);
					res = fis.getChannel().position();
					break;
			}
		} catch (IOException ex) {
		}
		return res;
	}
}
