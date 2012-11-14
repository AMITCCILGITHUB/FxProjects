/*
 * RadialMenuItem
 * Copyright 2012 (C) Mr LoNee - (Laurent NICOLAS) - www.mrlonee.com 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.mrlonee.radialmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class RadialMenuItem extends Group {

	double getStartAngle() {

		return this.startAngle;
	}

	void setStartAngle(final double startAngle) {

		this.startAngle = startAngle;
		this.drawMenuItem();
	}

	double getInnerRadius() {

		return this.innerRadius;
	}

	void setInnerRadius(final double innerRadius) {

		this.innerRadius = innerRadius;
		this.drawMenuItem();
	}

	double getRadius() {

		return this.radius;
	}

	void setRadius(final double radius) {

		this.radius = radius;
		this.drawMenuItem();
	}

	double getOffset() {

		return this.offset;
	}

	void setOffset(final double offset) {

		this.offset = offset;
		this.drawMenuItem();
	}

	Paint getBackgroundMouseOnColor() {

		return this.backgroundMouseOnColor;
	}

	void setBackgroundMouseOnColor(final Paint color) {

		this.backgroundMouseOnColor = color;
		this.drawMenuItem();
	}

	Paint getBackgroundColor() {

		return this.backgroundColor;
	}

	void setBackgroundColor(final Paint backgroundColor) {

		this.backgroundColor = backgroundColor;
		this.drawMenuItem();
	}

	boolean isClockwise() {

		return this.clockWise;
	}

	void setClockwise(final boolean clockWise) {

		this.clockWise = clockWise;
		this.drawMenuItem();
	}

	Paint getStrokeColor() {

		return this.strokeColor;
	}

	void setStrokeColor(final Paint color) {

		this.strokeColor = color;
		this.drawMenuItem();
	}

	void setBackgroundVisible(final boolean visible) {

		this.backgroundVisible = visible;
		this.drawMenuItem();
	}

	boolean isBackgroundVisible() {

		return this.backgroundVisible;
	}

	void setStrokeVisible(final boolean visible) {

		this.strokeVisible = visible;
		this.drawMenuItem();
	}

	boolean isStrokeVisible() {

		return this.strokeVisible;
	}

	public Node getGraphic() {

		return this.graphic;
	}

	public void setGraphic(final Node graphic) {

		if (this.graphic != null) {
			this.getChildren().remove(graphic);
		}
		this.graphic = graphic;
		if (this.graphic != null) {
			this.getChildren().add(graphic);
		}
		this.drawMenuItem();
	}

	public void setText(final String text) {

		this.text = text;
		this.drawMenuItem();
	}

	public String getText() {

		return this.text;
	}

	protected double startAngle;

	protected double menuSize;

	protected double innerRadius;

	protected double radius;

	protected double offset;

	protected Paint computedBackgroundFill;

	protected Paint backgroundMouseOnColor;

	protected Paint backgroundColor;

	protected boolean backgroundVisible = true;

	protected boolean strokeVisible = true;

	protected boolean clockWise;

	protected boolean northBased;

	protected Paint strokeColor;

	protected MoveTo moveTo;

	protected ArcTo arcToInner;

	protected ArcTo arcTo;

	protected LineTo lineTo;

	protected LineTo lineTo2;

	protected double innerStartX;

	protected double innerStartY;

	protected double innerEndX;

	protected double innerEndY;

	protected boolean innerSweep;

	protected double startX;

	protected double startY;

	protected double endX;

	protected double endY;

	protected boolean sweep;

	protected double graphicX;

	protected double graphicY;

	protected double translateX;

	protected double translateY;

	protected boolean mouseOn = false;

	protected Path path;

	protected Node graphic;

	protected String text;

	public RadialMenuItem() {

		this.path = new Path();
		this.moveTo = new MoveTo();
		this.arcToInner = new ArcTo();
		this.arcTo = new ArcTo();
		this.lineTo = new LineTo();
		this.lineTo2 = new LineTo();

		this.path.getElements().add(this.moveTo);
		this.path.getElements().add(this.arcToInner);
		this.path.getElements().add(this.lineTo);
		this.path.getElements().add(this.arcTo);
		this.path.getElements().add(this.lineTo2);

		this.getChildren().add(this.path);

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent arg0) {

				RadialMenuItem.this.mouseOn = true;
				RadialMenuItem.this.drawMenuItem();
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent arg0) {

				RadialMenuItem.this.mouseOn = false;
				RadialMenuItem.this.drawMenuItem();
			}
		});

	}

	public RadialMenuItem(final double menuSize, final Node graphic) {

		this();

		this.menuSize = menuSize;
		this.graphic = graphic;
		if (this.graphic != null)
			this.getChildren().add(this.graphic);

		this.drawMenuItem();
	}

	public RadialMenuItem(final double menuSize, final Node graphic,
			final EventHandler<ActionEvent> actionHandler) {

		this(menuSize, graphic);
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent paramT) {

				actionHandler.handle(new ActionEvent(paramT.getSource(), paramT
						.getTarget()));
			}
		});
		this.drawMenuItem();
	}

	public RadialMenuItem(final double menuSize, final String text,
			final Node graphic, final EventHandler<ActionEvent> actionHandler) {

		this(menuSize, graphic, actionHandler);

		this.text = text;
		this.drawMenuItem();
	}

	protected void drawMenuItem() {

		this.path
				.setFill(this.backgroundVisible ? (this.mouseOn
						&& this.backgroundMouseOnColor != null ? this.backgroundMouseOnColor
						: this.backgroundColor)
						: null);
		this.path.setStroke(this.strokeVisible ? this.strokeColor : null);

		this.path.setFillRule(FillRule.EVEN_ODD);

		this.computeCoordinates();

		this.update();

	}

	protected void update() {

		this.moveTo.setX(this.innerStartX);
		this.moveTo.setY(this.innerStartY);

		this.arcToInner.setX(this.innerEndX);
		this.arcToInner.setY(this.innerEndY);
		this.arcToInner.setSweepFlag(this.innerSweep);
		this.arcToInner.setRadiusX(this.innerRadius);
		this.arcToInner.setRadiusY(this.innerRadius);

		this.lineTo.setX(this.startX);
		this.lineTo.setY(this.startY);

		this.arcTo.setX(this.endX);
		this.arcTo.setY(this.endY);
		this.arcTo.setSweepFlag(this.sweep);
		this.arcTo.setRadiusX(this.radius);
		this.arcTo.setRadiusY(this.radius);

		this.lineTo2.setX(this.innerStartX);
		this.lineTo2.setY(this.innerStartY);

		if (this.graphic != null) {
			this.graphic.setTranslateX(this.graphicX);
			this.graphic.setTranslateY(this.graphicY);
		}

		this.translateXProperty().set(this.translateX);
		this.translateYProperty().set(this.translateY);
	}

	protected void computeCoordinates() {

		final double graphicAngle = (this.startAngle) + (this.menuSize / 2.0);
		final double graphicRadius = this.innerRadius
				+ (this.radius - this.innerRadius) / 2.0;

		if (!this.clockWise) {
			this.innerStartX = this.innerRadius
					* Math.cos(Math.toRadians(this.startAngle));
			this.innerStartY = -this.innerRadius
					* Math.sin(Math.toRadians(this.startAngle));
			this.innerEndX = this.innerRadius
					* Math.cos(Math.toRadians(this.startAngle + this.menuSize));
			this.innerEndY = -this.innerRadius
					* Math.sin(Math.toRadians(this.startAngle + this.menuSize));

			this.innerSweep = false;

			this.startX = this.radius
					* Math.cos(Math.toRadians(this.startAngle + this.menuSize));
			this.startY = -this.radius
					* Math.sin(Math.toRadians(this.startAngle + this.menuSize));
			this.endX = this.radius * Math.cos(Math.toRadians(this.startAngle));
			this.endY = -this.radius
					* Math.sin(Math.toRadians(this.startAngle));

			this.sweep = true;

			this.graphicX = graphicRadius
					* Math.cos(Math.toRadians(graphicAngle))
					- this.graphic.getBoundsInParent().getWidth() / 2;
			this.graphicY = -graphicRadius
					* Math.sin(Math.toRadians(graphicAngle))
					- this.graphic.getBoundsInParent().getHeight() / 2.0;

			this.translateX = this.offset
					* Math.cos(Math.toRadians(this.startAngle
							+ (this.menuSize / 2.0)));
			this.translateY = -this.offset
					* Math.sin(Math.toRadians(this.startAngle
							+ (this.menuSize / 2.0)));

		} else if (this.clockWise) {
			this.innerStartX = this.innerRadius
					* Math.cos(Math.toRadians(this.startAngle));
			this.innerStartY = this.innerRadius
					* Math.sin(Math.toRadians(this.startAngle));
			this.innerEndX = this.innerRadius
					* Math.cos(Math.toRadians(this.startAngle + this.menuSize));
			this.innerEndY = this.innerRadius
					* Math.sin(Math.toRadians(this.startAngle + this.menuSize));

			this.innerSweep = true;

			this.startX = this.radius
					* Math.cos(Math.toRadians(this.startAngle + this.menuSize));
			this.startY = this.radius
					* Math.sin(Math.toRadians(this.startAngle + this.menuSize));
			this.endX = this.radius * Math.cos(Math.toRadians(this.startAngle));
			this.endY = this.radius * Math.sin(Math.toRadians(this.startAngle));

			this.sweep = false;

			this.graphicX = graphicRadius
					* Math.cos(Math.toRadians(graphicAngle));
			this.graphicY = graphicRadius
					* Math.sin(Math.toRadians(graphicAngle));

			this.translateX = this.offset
					* Math.cos(Math.toRadians(this.startAngle
							+ (this.menuSize / 2.0)));
			this.translateY = this.offset
					* Math.sin(Math.toRadians(this.startAngle + this.startAngle
							+ (this.menuSize / 2.0)));

		}
	}

	public double getMenuSize() {

		return this.menuSize;
	}
}
