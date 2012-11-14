import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

class GlassPane extends Group {

	void setShape(Shape shape, boolean showInfo) {

		while (!getChildren().isEmpty()) {
			getChildren().remove(0);
		}
		if (shape != null) {
			double BLUE = 0.95;
			Stop[] stops = new Stop[] {
					new Stop(0.00, Color.color(BLUE, BLUE, 1, 0.3)),
					new Stop(0.2, Color.color(BLUE, BLUE, 1, 0.7)),
					new Stop(0.25, Color.color(BLUE, BLUE, 1, 0.5)),
					new Stop(0.4, Color.color(BLUE, BLUE, 1, 0.3)),
					new Stop(0.70, Color.color(BLUE, BLUE, 1, 0.7)),
					new Stop(1.0, Color.color(BLUE, BLUE, 1, 0.3)), };
			LinearGradient lg = new LinearGradient(0, 0, 1, 1, true,
					CycleMethod.NO_CYCLE, stops);
			shape.setFill(lg);
			shape.setMouseTransparent(true);
			shape.setStroke(null);
			getChildren().add(shape);

			if (showInfo) {
				Text info = new Text("'ESC' to leave the Snipper\n"
						+ "'A' to capture the whole screen\n"
						+ "Drag the mouse to capture a rectangle");
				info.setFill(Color.WHITE);
				info.setTranslateX(200);
				info.setTranslateY(60);
				info.setScaleX(2);
				info.setScaleY(2);
				info.setEffect(new DropShadow());
				info.setMouseTransparent(true);
				getChildren().add(info);
			}
		}
	}
}