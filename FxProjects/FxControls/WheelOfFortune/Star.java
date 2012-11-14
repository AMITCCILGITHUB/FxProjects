package WheelOfFortune;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class Star extends Region {

	private double rin;
	private double rout;
	private double points;

	private double startAngle;
	private Color fill;

	public Star() {

	}

	public double getRin() {

		return rin;
	}

	public void setRin(double rin) {

		this.rin = rin;
	}

	public double getRout() {

		return rout;
	}

	public void setRout(double rout) {

		this.rout = rout;
	}

	public double getPoints() {

		return points;
	}

	public void setPoints(double points) {

		this.points = points;
	}

	public double getStartAngle() {

		return startAngle;
	}

	public void setStartAngle(double startAngle) {

		this.startAngle = startAngle;
	}

	public Color getFill() {

		return fill;
	}

	public void setFill(Color fill) {

		this.fill = fill;
	}
}
