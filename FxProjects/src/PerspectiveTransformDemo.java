import javafx.scene.Group;
import javafx.scene.effect.PerspectiveTransformBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PerspectiveTransformDemo extends ExtendedApplicationX {

	@Override
	protected void setup() {

		VBox vBox = new VBox();
		vBox.setSpacing(15);

		Group g1 = getNode();
		g1.setEffect(PerspectiveTransformBuilder.create().ulx(10.0).uly(10.0)
				.urx(310.0).ury(40.0).lrx(310.0).lry(60.0).llx(10.0).lly(90.0)
				.build());// Left to right

		Group g2 = getNode();
		g2.setEffect(PerspectiveTransformBuilder.create().ulx(30.0).uly(60.0)
				.urx(290.0).ury(60.0).lrx(310.0).lry(90.0).llx(10.0).lly(90.0)
				.build());// Front to Back

		Group g3 = getNode();
		g3.setEffect(PerspectiveTransformBuilder.create().ulx(310.0).uly(40.0)
				.urx(10.0).ury(10.0).lrx(10.0).lry(90.0).llx(310.0).lly(60.0)
				.build());// Reverse Right to Left

		Group g4 = getNode();
		g4.setEffect(PerspectiveTransformBuilder.create().ulx(10.0).uly(40.0)
				.urx(310.0).ury(10.0).lrx(310.0).lry(90.0).llx(10.0).lly(60.0)
				.build());// Right to Left

		vBox.getChildren().addAll(g1, g2, g3, g4);
		root.setContent(vBox);
	}

	private Group getNode() {

		Group g = new Group();
		g.setCache(true);

		Rectangle r = new Rectangle();
		r.setX(10.0);
		r.setY(10.0);
		r.setWidth(280.0);
		r.setHeight(80.0);
		r.setFill(Color.BLUE);

		Text t = new Text();
		t.setX(20.0);
		t.setY(65.0);
		t.setText("Perspective");
		t.setFill(Color.YELLOW);
		t.setFont(Font.font(null, FontWeight.BOLD, 36));

		g.getChildren().add(r);
		g.getChildren().add(t);
		return g;
	}

	public static void main(String[] args) {

		ExtendedApplicationX.launch(args);
	}

}