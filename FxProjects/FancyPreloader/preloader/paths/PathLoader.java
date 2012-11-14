/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package preloader.paths;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author msi
 */
public class PathLoader {

	public String getPath(String filename) throws IOException {

		StringBuffer buf = new StringBuffer();

		InputStream is = this.getClass().getResourceAsStream(filename);
		int read = -1;
		while ((read = is.read()) != -1) {
			buf.append((char) read);
		}

		return buf.toString();

	}

	public String getPath(int i) throws IOException {

		return getPath("path" + i);
	}

}
