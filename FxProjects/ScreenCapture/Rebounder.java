import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

class Rebounder {

	Rectangle lasso;
	double px;
	double py;

	Rebounder() {

		lasso = new Rectangle(0, 0);
		lasso.setFill(null);
		lasso.setSmooth(false);
		lasso.setStroke(Color.RED);
		lasso.setStrokeWidth(1);
		lasso.setStrokeType(StrokeType.OUTSIDE);
		lasso.setMouseTransparent(true);
		lasso.setVisible(false);
	}

	Rectangle start(double x, double y) {

		lasso.setX(x);
		lasso.setY(y);
		lasso.setWidth(0);
		lasso.setHeight(0);
		px = x;
		py = y;
		lasso.setVisible(true);
		return lasso;
	}

	Rectangle rebound(double x, double y) {

		lasso.setWidth(Math.abs(x - px));
		lasso.setHeight(Math.abs(y - py));
		lasso.setX(Math.min(x, px));
		lasso.setY(Math.min(y, py));
		return lasso;
	}

	Rectangle stop(double x, double y) {

		rebound(x, y);
		lasso.setVisible(false);
		return lasso;
	}

	boolean isStopped() {

		return !lasso.isVisible();
	}

	Node getLasso() {

		return lasso;
	}

	Shape shapeBuilder(Rectangle r) {

		return r != null ? Path.subtract(
				ScreenCapture.getScreenCapture().screenBounds, r)
				: new Rectangle(
						ScreenCapture.getScreenCapture().screenBounds
								.getWidth(),
						ScreenCapture.getScreenCapture().screenBounds
								.getHeight());
	}
}