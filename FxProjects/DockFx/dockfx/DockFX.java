/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dockfx;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.JFileChooser;

/**
 * 
 * @author paul
 */
public class DockFX extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage stage) {

		stage.setTitle("Hello World!");

		Group root = new Group();

		final ImageView dock = new ImageView(new Image("images/dock1.png"));
		dock.setTranslateX(100);
		dock.setTranslateY(200);
		dock.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				if (arg0.getButton() == MouseButton.PRIMARY) {
					dock.setImage(new Image("images/dock1.png"));
				} else {
					dock.setImage(new Image("images/dock2.png"));
				}
			}
		});
		root.getChildren().add(dock);

		final String[] images = { "Browser-32.png", "Internet-32.png",
				"Search-32.png", "Mail-32.png", "User-32.png" };

		HBox box = new HBox(20);
		for (int i = 0; i < images.length; i++) {
			BouncingIcon icon = new BouncingIcon(
					new Image("icons/" + images[i]));
			icon.setEffect(new Reflection());
			box.getChildren().add(icon);
			final String action = images[i].split("-")[0].toLowerCase();
			icon.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent t) {

					mouseClicked(action);
				}
			});
		}

		box.setTranslateX(150);
		box.setTranslateY(220);
		root.getChildren().add(box);

		stage.setScene(new Scene(root, 550, 300));
		stage.show();
	}

	public void mouseClicked(String action) {

		switch (action) {
			case "user":
				try {
					// open user home
					// System.out.println(System.getenv().keySet().toString());
					System.out.println("HOME = " + System.getenv("HOME"));
					Desktop.getDesktop().open(new File(System.getenv("HOME")));
				} catch (IOException ex) {
					// No implementation for your OS
					// fallback
					JFileChooser chooser = new JFileChooser(new File(
							System.getenv("HOME")));
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File selectedFile = chooser.getSelectedFile();
						System.out.println(selectedFile.getAbsolutePath());
						// do something with selectedFile
					}
				}
				break;
			case "internet":
				try {
					Desktop.getDesktop().browse(
							new URI("http://www.google.com"));
				} catch (Exception ex) {
				}
				break;
		}
	}
}