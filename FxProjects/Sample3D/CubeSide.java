import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class CubeSide extends Rectangle {

	private DoubleProperty sideAngle = new SimpleDoubleProperty();

	public CubeSide(Color color) {

		initComponents(color);
	}

	public CubeSide(Color color, Rotate rotate) {

		initComponents(color);
		this.getTransforms().add(rotate);
	}

	private void initComponents(Color color) {

		this.setX(-100);
		this.setY(-100);
		this.setWidth(200);
		this.setHeight(200);
		this.setFill(color);
	}

	public DoubleProperty sideAngleProperty() {

		return sideAngle;
	}
}
