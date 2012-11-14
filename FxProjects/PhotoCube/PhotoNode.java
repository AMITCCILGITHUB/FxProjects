import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.scene.transform.TranslateBuilder;
import javafx.util.Duration;
import javafxpert.cube.FaceType;

public class PhotoNode extends Parent {

	double dragPressedAngleX;
	double dragPressedAngleY;
	double dragStartOffsetX;
	double dragStartOffsetY;

	PhotoFace rearFace;
	PhotoFace bottomFace;
	PhotoFace leftFace;
	PhotoFace rightFace;
	PhotoFace topFace;
	PhotoFace frontFace;

	SimpleDoubleProperty angleX = new SimpleDoubleProperty(0);
	SimpleDoubleProperty angleY = new SimpleDoubleProperty(0);

	public PhotoNode() {

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

		rearFace = new PhotoFace(FaceType.REAR);
		rearFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(PhotoFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(180.0).axis(Rotate.Y_AXIS)
						.pivotX(PhotoFace.edgeLength / 2).build());

		bottomFace = new PhotoFace(FaceType.BOTTOM);
		bottomFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(PhotoFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(90.0).axis(Rotate.X_AXIS)
						.pivotY(PhotoFace.edgeLength).build());

		leftFace = new PhotoFace(FaceType.LEFT);
		leftFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(PhotoFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(90.0).axis(Rotate.Y_AXIS)
						.pivotX(0).build());

		rightFace = new PhotoFace(FaceType.RIGHT);
		rightFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(PhotoFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(-90.0).axis(Rotate.Y_AXIS)
						.pivotX(PhotoFace.edgeLength).build());

		topFace = new PhotoFace(FaceType.TOP);
		topFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(PhotoFace.edgeLength)
						.build(),
				RotateBuilder.create().angle(-90.0).axis(Rotate.X_AXIS)
						.pivotX(0).build());

		frontFace = new PhotoFace(FaceType.FRONT);
		frontFace.getTransforms().setAll(
				TranslateBuilder.create().x(0).y(0).z(0).build());

		getChildren().addAll(rearFace, topFace, leftFace, rightFace,
				bottomFace, frontFace);

		Rotate xRotate;
		Rotate yRotate;
		getTransforms().setAll(
				xRotate = RotateBuilder.create().axis(Rotate.X_AXIS)
						.pivotX(PhotoFace.edgeLength * 0.5)
						.pivotY(PhotoFace.edgeLength * 0.5)
						.pivotZ(PhotoFace.edgeLength * 0.5).build(),
				yRotate = RotateBuilder.create().axis(Rotate.Y_AXIS)
						.pivotX(PhotoFace.edgeLength * 0.5)
						.pivotY(PhotoFace.edgeLength * 0.5)
						.pivotZ(PhotoFace.edgeLength * 0.5).build());
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);

		this.setOnMousePressed(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				dragPressedAngleX = angleX.getValue();
				dragPressedAngleY = angleY.getValue();

				dragStartOffsetX = me.getScreenX()
						- getScene().getWindow().getX();
				dragStartOffsetY = me.getScreenY()
						- getScene().getWindow().getY();
			}
		});

		this.setOnMouseDragged(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				angleY.setValue((((me.getScreenX() - getScene().getWindow()
						.getX()) - dragStartOffsetX))
						/ 3
						* -1
						+ dragPressedAngleY);
				angleX.setValue((((me.getScreenY() - getScene().getWindow()
						.getY()) - dragStartOffsetY)) / 3 + dragPressedAngleX);
			}
		});

		goHomePosition();
	}

	final void arrangeFacesZOrder() {

		rearFace.zPos.setValue(PhotoFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 0)));
		bottomFace.zPos.setValue(PhotoFace.radius
				* Math.cos(Math.toRadians(angleX.getValue() + 270)));
		leftFace.zPos.setValue(PhotoFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 270)));
		rightFace.zPos.setValue(PhotoFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 90)));
		topFace.zPos.setValue(PhotoFace.radius
				* Math.cos(Math.toRadians(angleX.getValue() + 90)));
		frontFace.zPos.setValue(PhotoFace.radius
				* Math.cos(Math.toRadians(angleY.getValue() + 180)));
	}

	public void goHomePosition() {

		Timeline homeTimeline = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(new Duration(1000.0), new KeyValue(angleX,
								30, Interpolator.EASE_BOTH), new KeyValue(
								angleY, -40, Interpolator.EASE_BOTH))).build();
		homeTimeline.play();
	}
}
