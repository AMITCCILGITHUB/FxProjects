/**
 * Copyright (c) 2011, JFXtras
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Tom Eugelink
 * 
 */
abstract public class ApplicationFx extends javafx.application.Application {

	// logger logger
	final static Logger logger = Logger
			.getLogger(ApplicationFx.class.getName());

	/**
	 * Do some prelaunch things like
	 * 
	 * @param args
	 */
	public static void prelaunch() {

		prelaunchUnpackOSFilesToBinDirectory();
	}

	/**
	 * Unpack files from the jar to ../lib so JavaFX's loader can find them. An
	 * alternative is to unpack the JAR to a temp folder and have
	 * java.library.path point to it. JavaFX first tries ../bin and if not foud
	 * then System.loadLibrary.
	 * 
	 * @param args
	 */
	public static void prelaunchUnpackOSFilesToBinDirectory() {

		try {
			// find the JavaFX windows binaries jar
			File lJavaFXBinFile = findResourceFile("jfxmedia.dll");
			if (lJavaFXBinFile == null)
				findResourceFile("jfxmedia.so");
			if (lJavaFXBinFile == null)
				return;
			if (logger.isLoggable(Level.FINE))
				logger.fine("Using JavaFX OS libraries from "
						+ lJavaFXBinFile.getAbsolutePath());

			// create an temp unpack directory
			// first create a temp file (because Java only supports temp files)
			File lJFXtrasTempdir = File.createTempFile("jfxtras_", "");
			// delete the temp file
			if (!lJFXtrasTempdir.delete())
				throw new IOException(
						"Could not delete temp file in order to recreate it as a dir: "
								+ lJFXtrasTempdir.getAbsolutePath());
			// create a fixed temp dir in the parent directory
			// Using a fixed temp directory prevents every run of a JavaFX /
			// JFXtras application to create, unpack and leave behind a new
			// directory
			// And further more it will improve startup time, because it if the
			// directory already exist, unpacking is skipped (small risc
			// involved here when unpacking went wrong the previous time)
			lJFXtrasTempdir = new File(lJFXtrasTempdir.getParent(), "jfxtras");
			if (lJFXtrasTempdir.exists() == false && !lJFXtrasTempdir.mkdirs())
				throw new IOException("Could not create dir: "
						+ lJFXtrasTempdir.getAbsolutePath());

			// create the unpack directory for the bin jar inside the tempdir
			// There may be multiple versions of the bin, so each version is
			// unpacked to a separate directory.
			File lUnpackToDir = new File(lJFXtrasTempdir,
					lJavaFXBinFile.getName() + ".unpacked");
			if (logger.isLoggable(Level.FINE))
				logger.fine("Unpacking to " + lUnpackToDir.getAbsolutePath());

			// create it
			if (lUnpackToDir.exists()) {
				lUnpackToDir.mkdirs();
			}

			// unpack the jar to the bin directory
			@SuppressWarnings("resource")
			JarFile lJarFile = new JarFile(lJavaFXBinFile);
			for (Enumeration<JarEntry> lEnum = lJarFile.entries(); lEnum
					.hasMoreElements();) {
				// each element
				JarEntry lJarEntry = lEnum.nextElement();
				File lLibFile = new File(lUnpackToDir, lJarEntry.getName());

				// if directory, create it
				if (lJarEntry.isDirectory()) {
					if (lLibFile.exists() == false)
						lLibFile.mkdirs();
					continue;
				}

				// copy entry contents
				if (lJarEntry.getTime() == -1 // if the jar does not know how
												// old it is
						|| lLibFile.exists() == false // if the destination does
														// not exist
						|| lJarEntry.getTime() > lLibFile.lastModified() // if
																			// the
																			// jar
																			// file
																			// is
																			// newer
																			// that
																			// the
																			// destination
				) {
					if (logger.isLoggable(Level.FINE))
						logger.fine("Unpacking " + lLibFile.getAbsolutePath());
					try {
						InputStream lInputStream = lJarFile
								.getInputStream(lJarEntry);
						OutputStream lOutputStream = new BufferedOutputStream(
								new FileOutputStream(lLibFile));
						try {
							// copy
							copy(lInputStream, lOutputStream);

							// if the jar knows how old it is, copy that
							// information so we know if we can skip it next
							// time
							if (lJarEntry.getTime() != -1)
								lLibFile.setLastModified(lJarEntry.getTime());
						} finally {
							lInputStream.close();
							lOutputStream.close();
						}
					} catch (Throwable e) {
						if (logger.isLoggable(Level.INFO))
							logger.info(e.getMessage());
					}
				} else if (logger.isLoggable(Level.FINE))
					logger.fine("File is already up to date: "
							+ lLibFile.getAbsolutePath());
			}

			// add the bin directory to java.libary.path
			String lJavaLibraryPathId = "java.library.path";
			String lJavaLibraryPath = System.getProperty(lJavaLibraryPathId);
			lJavaLibraryPath = lUnpackToDir.getAbsolutePath()
					+ File.pathSeparator
					+ (lJavaLibraryPath == null ? "" : lJavaLibraryPath);
			System.setProperty(lJavaLibraryPathId, lJavaLibraryPath);
			// Changing the system property after Java is started doesn't have
			// any effect, since the property is evaluated very early and
			// cached.
			// But the guys over at JDIC discovered a way how to work around it.
			// A bit dirty, but it'll do the job.
			// http://blog.cedarsoft.com/2010/11/setting-java-library-path-programmatically/
			Field lFieldSysPath = ClassLoader.class
					.getDeclaredField("sys_paths");
			lFieldSysPath.setAccessible(true);
			lFieldSysPath.set(null, null);
		} catch (Throwable e) {
			logger.warning(e.getMessage());
		}
	}

	/**
	 * find a resource and and then the file it is in
	 * 
	 * @param resource
	 * @return
	 */
	static private File findResourceFile(String resource) {

		try {
			// find the JavaFX runtime JAR
			URL lURL = null;
			for (Enumeration<URL> lEnum = ApplicationFx.class.getClassLoader()
					.getResources(resource); lEnum.hasMoreElements();) {
				lURL = lEnum.nextElement();
				break;
			}
			if (lURL == null)
				return null; // not found
			if (lURL.toString().startsWith("jar:") == false)
				return null; // not in a jar
			if (lURL.toString().startsWith("jar:file:") == false)
				return null; // not in a jar as a file

			// extract file
			String lFilename = lURL.getFile();
			if (lFilename.contains(".jar!/")) {
				lFilename = lFilename.substring(0,
						lFilename.indexOf(".jar!/") + 4); // strip the in-jar
															// part
			}
			if (lFilename.startsWith("file:/") == false)
				return null; // not a file
			if (lFilename.startsWith("file:///") == true)
				lFilename = lFilename.substring(8); // strip file:///
			if (lFilename.startsWith("file:/") == true)
				lFilename = lFilename.substring(6); // strip file:/
			lFilename = URLDecoder.decode(lFilename, "UTF-8");

			// create file
			File lFile = new File(lFilename);

			// done
			return lFile;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * copy all bytes from one stream to another (by now this should be a
	 * standard util method)
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	static public void copy(InputStream in, OutputStream out)
			throws IOException {

		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
}