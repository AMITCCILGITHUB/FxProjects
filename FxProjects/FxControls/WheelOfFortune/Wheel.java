package WheelOfFortune;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.RotateBuilder;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.scene.transform.TranslateBuilder;

public class Wheel extends Region {

	private int n;
	private int radius;
	private int cx;
	private int cy;
	private Color[] colors;

	private SimpleDoubleProperty angle = new SimpleDoubleProperty();
	private SimpleDoubleProperty rotAngle = new SimpleDoubleProperty();

	public Node composeNode() {

		n = 32;
		radius = 100;
		cx = radius;
		cy = radius;

		angle.set(360 / n);

		double extraTurns = 2;

		colors = new Color[] { Color.RED, Color.LIME, Color.BLUE, Color.YELLOW,
				Color.ORANGE, Color.CYAN };

		Rectangle selector = RectangleBuilder.create().x(radius * 2 - 10)
				.y(radius).width(30).height(10).fill(Color.BLACK).build();

		List<Arc> disc = new ArrayList<>();
		for (int j = 1; j <= n; j++) {
			disc.add(ArcBuilder.create().radiusX(radius * 2)
					.radiusY(radius * 2).startAngle(j * angle.get())
					.length(angle.get()).fill(colors[j % colors.length])
					.transforms(Translate.translate(-radius, -radius))
					.type(ArcType.CHORD).build());
		}

		Circle border = CircleBuilder.create().radius(radius)
				.stroke(Color.GRAY).strokeWidth(2).build();

		Circle glass = CircleBuilder.create().radius(radius - 25)
				.fill(Color.WHITE).opacity(0.3).build();

		Star star = new Star();
		star.setRin(radius / 4);
		star.setRout(radius / 2);
		star.setPoints(5);
		star.setStartAngle(18);
		star.setFill(Color.YELLOW);

		List<Text> numbers = new ArrayList<>();

		for (int i = 1; i <= n; i++) {
			numbers.add(TextBuilder
					.create()
					.transforms(Transform.rotate(i * angle.get(), 0, 0),
							Translate.translate(radius - 20, 0)).text(i + "")
					.build());
		}
		Rotate rot = RotateBuilder.create().pivotX(cx).pivotY(cy).build();
		rot.angleProperty().bind(rotAngle);

		Translate trans = TranslateBuilder.create().x(cx).y(cy).build();

		Group base = GroupBuilder.create().transforms(rot, trans)
				.children(numbers).children(disc).children(border, glass, star)
				.build();

		return GroupBuilder.create().children(selector, base).build();
	}

	public void run() {

		Random fortune = new Random(System.currentTimeMillis());
		int chosen = fortune.nextInt(n);

		// rotAngle = [1.. extraTurns*360+ chosen*angle] dur 200 * n;
	}
	/*
	 * var wheel = Wheel{};
	 * 
	 * Frame{ visible: true content: Canvas { content: [ wheel, View {
	 * content:Button { text: "Go" action: operation() { wheel.run(); } } } ] }
	 * }
	 */
}
