/*
 *  Copyright (c) 2012 Michael Zucchi
 *
 *  This programme is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This programme is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this programme.  If not, see <http://www.gnu.org/licenses/>.
 */


import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.util.Duration;

/**
 *
 * @author notzed
 */
public class BlurTransition extends Transition {

	Node node;
	double fromRadius = 32;
	double toRadius = 1;
	GaussianBlur blur;

	public BlurTransition(Duration dur, Node node) {
		this.node = node;

		setCycleDuration(dur);

		node.setEffect(blur = new GaussianBlur(fromRadius));
	}

	@Override
	protected void interpolate(double d) {
		double rad = fromRadius + (toRadius - fromRadius) * d;

		blur.setRadius(rad);
	}
}