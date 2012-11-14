/*
 * CubeNode.java
 *
 * A cube-shaped UI component upon whose faces other nodes may placed.
 *
 * Developed by James L. Weaver (jim.weaver#javafxpert.com) to demonstrate the
 * use of 3D features in the JavaFX 2.0 API
 */
package javafxpert.cube;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.scene.transform.TranslateBuilder;
import javafx.util.Duration;
import javafxpert.model.CubeModel;

/**
 * 
 * @author Jim Weaver
 */
public class CubeNode extends Parent {

	static double HOME_ANGLE_X = 30.0;
	static double HOME_ANGLE_Y = -40.0;
	static double MIN_ANGLE_X = -70.0;
	static double MAX_ANGLE_X = 70.0;
	static double MIN_ANGLE_Y = -720.0;
	static double MAX_ANGLE_Y = 720.0;
	static double MIN_TRANSLATE_Z = 0.0;
	static double MAX_TRANSLATE_Z = 10000.0;

	CubeModel cubeModel = CubeModel.instance;

	CubeFace rearFace;
	CubeFace bottomFace;
	CubeFace leftFace;
	CubeFace rightFace;
	CubeFace topFace;
	CubeFace frontFace;

	SimpleDoubleProperty angleX = new SimpleDoubleProperty(0);

	SimpleDoubleProperty angleY = new SimpleDoubleProperty(0);

	public CubeNode(CubeModel model) {

		angleX.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable ov) {

				arrangeFacesZOrder();

			}
		});

		angleY.addListener(new ChangeListener<Object>() {

			public void changed(ObservableValue<?> ov, Object oldValue,
					Object newValue) {

				arrangeFacesZOrder();
			}
		});

		rearFace = new CubeFace(cubeModel, CubeFace.REAR_FACE);
		rearFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(CubeFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(180.0).axis(Rotate.Y_AXIS)
						.pivotX(CubeFace.edgeLength / 2).build());

		bottomFace = new CubeFace(cubeModel, CubeFace.BOTTOM_FACE);
		bottomFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(CubeFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(90.0).axis(Rotate.X_AXIS)
						.pivotY(CubeFace.edgeLength).build());

		leftFace = new CubeFace(cubeModel, CubeFace.LEFT_FACE);
		leftFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(CubeFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(90.0).axis(Rotate.Y_AXIS)
						.pivotX(0).build());

		rightFace = new CubeFace(cubeModel, CubeFace.RIGHT_FACE);
		rightFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(CubeFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(-90.0).axis(Rotate.Y_AXIS)
						.pivotX(CubeFace.edgeLength).build());

		topFace = new CubeFace(cubeModel, CubeFace.TOP_FACE);
		topFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(CubeFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(-90.0).axis(Rotate.X_AXIS)
						.pivotX(0).build());

		frontFace = new CubeFace(cubeModel, CubeFace.FRONT_FACE);
		frontFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(0).build());

		getChildren().addAll(rearFace, topFace, leftFace, rightFace,
				bottomFace, frontFace);

		Rotate xRotate;
		Rotate yRotate;
		getTransforms().setAll(
				xRotate = RotateBuilder.create().axis(Rotate.X_AXIS)
						.pivotX(CubeFace.edgeLength * 0.5)
						.pivotY(CubeFace.edgeLength * 0.5)
						.pivotZ(CubeFace.edgeLength * 0.5).build(),
				yRotate = RotateBuilder.create().axis(Rotate.Y_AXIS)
						.pivotX(CubeFace.edgeLength * 0.5)
						.pivotY(CubeFace.edgeLength * 0.5)
						.pivotZ(CubeFace.edgeLength * 0.5).build());
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);

		setOnMousePressed(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				dragPressedAngleX = angleX.getValue();
				dragPressedAngleY = angleY.getValue();

				dragStartOffsetX = me.getScreenX()
						- getScene().getWindow().getX();
				dragStartOffsetY = me.getScreenY()
						- getScene().getWindow().getY();
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				if (me.isControlDown()) {
					getScene().getWindow().setX(
							me.getScreenX() - dragStartOffsetX);
					getScene().getWindow().setY(
							me.getScreenY() - dragStartOffsetY);
				} else if (me.isAltDown()) {
					double curTranslateZ = getTranslateZ();
					double proposedTranslateZ = curTranslateZ
							+ ((me.getScreenY() - getScene().getWindow().getY()) - dragStartOffsetY)
							* 10;
					if (proposedTranslateZ > MAX_TRANSLATE_Z) {
						setTranslateZ(MAX_TRANSLATE_Z);
					} else if (proposedTranslateZ < MIN_TRANSLATE_Z) {
						setTranslateZ(MIN_TRANSLATE_Z);
					} else {
						setTranslateZ(proposedTranslateZ);
					}
				} else {
					angleY.setValue((((me.getScreenX() - getScene().getWindow()
							.getX()) - dragStartOffsetX))
							/ 3
							* -1
							+ dragPressedAngleY);
					angleX.setValue((((me.getScreenY() - getScene().getWindow()
							.getY()) - dragStartOffsetY))
							/ 3
							+ dragPressedAngleX);
				}
			}
		});

		setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent ke) {

				if (ke.getCode() == KeyCode.SPACE) {
					if (cubeModel.mapOpacity.getValue() == 0.0) {
						showMapTimeline.playFromStart();
					} else {
						hideMapTimeline.playFromStart();
					}
				} else if (ke.getCode() == KeyCode.ESCAPE) {
					Platform.exit();
				}
			}
		});
	}

	final void arrangeFacesZOrder() {

		rearFace.zPos.setValue(CubeFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 0)));
		bottomFace.zPos.setValue(CubeFace.radius
				* Math.cos(Math.toRadians(angleX.getValue() + 270)));
		leftFace.zPos.setValue(CubeFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 270)));
		rightFace.zPos.setValue(CubeFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 90)));
		topFace.zPos.setValue(CubeFace.radius
				* Math.cos(Math.toRadians(angleX.getValue() + 90)));
		frontFace.zPos.setValue(CubeFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 180)));

		FXCollections.sort(getChildren(), new CubeFaceComparator());
	}

	public void goHomePosition() {

		Timeline homeTimeline = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(new Duration(1000.0), new KeyValue(angleX,
								HOME_ANGLE_X, Interpolator.EASE_BOTH),
								new KeyValue(angleY, HOME_ANGLE_Y,
										Interpolator.EASE_BOTH))).build();
		homeTimeline.play();
	}

	public Timeline showMapTimeline = TimelineBuilder
			.create()
			.keyFrames(
					new KeyFrame(new Duration(0.0),
							new EventHandler<ActionEvent>() {

								public void handle(javafx.event.ActionEvent t) {

									goHomePosition();
								}
							}, new KeyValue(cubeModel.mapOpacity, 0.0,
									Interpolator.LINEAR)),
					new KeyFrame(new Duration(1000.0), new KeyValue(
							cubeModel.mapOpacity, 0.7, Interpolator.EASE_BOTH)))
			.build();

	public Timeline hideMapTimeline = TimelineBuilder
			.create()
			.keyFrames(
					new KeyFrame(new Duration(0.0),
							new EventHandler<ActionEvent>() {

								public void handle(javafx.event.ActionEvent t) {

									// goHomePosition();
								}
							}, new KeyValue(cubeModel.mapOpacity, 0.7,
									Interpolator.LINEAR)),
					new KeyFrame(new Duration(1000.0), new KeyValue(
							cubeModel.mapOpacity, 0.0, Interpolator.EASE_BOTH)))
			.build();

	public Node frontNode;
	public Node rearNode;
	public Node leftNode;
	public Node rightNode;
	public Node topNode;
	public Node bottomNode;

	double dragPressedAngleX;
	double dragPressedAngleY;

	double dragStartOffsetX;
	double dragStartOffsetY;

}
