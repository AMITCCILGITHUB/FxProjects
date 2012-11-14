/*
 *  Copyright (c) 2012 Michael Zucchi
 *
 *  This programme is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This programme is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this programme.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * Scans a directory recursively, pre-loading images.
 * 
 * @author notzed
 */
public class DirectoryLoader extends Thread implements ImageLoader,
		FileVisitor<Path> {

	String root;
	double width = 1024;
	double height = 768;
	BlockingQueue<Image> images = new ArrayBlockingQueue<>(5);
	Image eof = new WritableImage(1, 1);
	boolean cancelled = false;

	public DirectoryLoader(String root) {

		this.root = root;
	}

	public void cancel() throws InterruptedException {

		cancelled = true;
		interrupt();
		join();
	}

	public Image getNextImage() {

		try {
			Image res = images.take();

			if (res != eof)
				return res;
		} catch (InterruptedException ex) {
			Logger.getLogger(DirectoryLoader.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return null;
	}

	@Override
	public void run() {

		System.out.println("scanning");
		try {
			Files.walkFileTree(Paths.get(root), this);
			System.out.println("complete");
		} catch (IOException ex) {
			Logger.getLogger(DirectoryLoader.class.getName()).log(Level.SEVERE,
					null, ex);
		} finally {
			if (!cancelled) {
				try {
					images.put(eof);
				} catch (InterruptedException ex) {
				}
			}
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path t, BasicFileAttributes bfa)
			throws IOException {

		if (cancelled)
			return FileVisitResult.TERMINATE;
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path t, BasicFileAttributes bfa)
			throws IOException {

		if (cancelled)
			return FileVisitResult.TERMINATE;
		try {
			System.out.println("Load: " + t.toUri().toString());
			Image image = new Image(t.toUri().toString(), width, height, true,
					true, false);

			if (!image.isError()) {
				System.out.println("load: " + t);
				images.put(image);
			}
		} catch (InterruptedException ex) {
			return FileVisitResult.TERMINATE;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path t, IOException ioe)
			throws IOException {

		if (cancelled)
			return FileVisitResult.TERMINATE;
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path t, IOException ioe)
			throws IOException {

		if (cancelled)
			return FileVisitResult.TERMINATE;
		return FileVisitResult.CONTINUE;
	}
}
