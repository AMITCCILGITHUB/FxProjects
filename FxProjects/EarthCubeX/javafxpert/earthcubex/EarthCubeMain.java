/*
 * EarthCubeMain.java
 *
 * Main class for EarthCubeFX -- a program that uses the CubeNode component
 * and superimposes map tiles from Google Maps web services.
 *
 * Developed by James L. Weaver (jim.weaver#javafxpert.com) to demonstrate the
 * use of 3D features in the JavaFX 2.0 API
 */
package javafxpert.earthcubex;

import javafx.application.Application;
import javafx.scene.GroupBuilder;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafxpert.cube.CubeNode;
import javafxpert.model.CubeModel;

/**
 * 
 * @author Jim Weaver
 */
public class EarthCubeMain extends Application {

	/**
	 * A reference to the model
	 */
	CubeModel cubeModel;

	/**
	 * A reference to the program's Scene
	 */
	Scene scene;

	/**
	 * A reference to the cube
	 */
	CubeNode cube;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		Application.launch(EarthCubeMain.class, args);
	}

	@Override
	public void start(Stage primaryStage) {

		cubeModel = new CubeModel();
		CubeModel.instance = cubeModel;
		cube = new CubeNode(cubeModel);
		cube.setFocusTraversable(true);

		scene = SceneBuilder
				.create()
				.fill(Color.TRANSPARENT)
				.width(800)
				.height(800)
				.camera(PerspectiveCameraBuilder.create().fieldOfView(30)
						.build())
				.root(GroupBuilder.create().layoutX(150).layoutY(150)
						.children(cube).build()).build();

		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();

		cube.showMapTimeline.playFromStart();
	}
}
