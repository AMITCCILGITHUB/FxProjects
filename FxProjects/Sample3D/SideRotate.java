import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;

public class SideRotate extends Rotate {

	public SideRotate(int pivotX, int pivotY, int pivotZ, Point3D axis) {

		initComponents(pivotX, pivotY, pivotZ, axis);
	}

	private void initComponents(int pivotX, int pivotY, int pivotZ, Point3D axis) {

		this.setPivotX(pivotX);
		this.setPivotY(pivotY);
		this.setPivotZ(pivotZ);
		this.setAxis(axis);
	}
}
