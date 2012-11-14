/*
 * RadialMenu
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

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class RadialMenu extends Group {

    protected List<RadialMenuItem> items = new ArrayList<RadialMenuItem>();

    protected double innerRadius;
    protected double radius;
    protected DoubleProperty offset;
    protected DoubleProperty initialAngle;

    public Paint getBackgroundFill() {
	return this.backgroundFill;
    }

    public void setBackgroundFill(final Paint backgroundFill) {
	this.backgroundFill = backgroundFill;
    }

    public Paint getBackgroundMouseOnFill() {
	return this.backgroundMouseOnFill;
    }

    public void setBackgroundMouseOnFill(final Paint backgroundMouseOnFill) {
	this.backgroundMouseOnFill = backgroundMouseOnFill;
    }

    public Paint getStrokeFill() {
	return this.strokeFill;
    }

    public void setStrokeFill(final Paint strokeFill) {
	this.strokeFill = strokeFill;
    }

    public Node getGraphic() {
	return this.graphic;
    }

    public void setGraphic(final Node graphic) {
	this.graphic = graphic;
    }

    public double getInitialAngle() {
	return this.initialAngle.get();
    }

    public DoubleProperty getInitialAngleProperty() {
	return this.initialAngle;
    }

    public double getInnerRadius() {
	return this.innerRadius;
    }

    public double getRadius() {
	return this.radius;
    }

    public double getOffset() {
	return this.offset.get();
    }

    public DoubleProperty getOffsetProperty() {
	return this.offset;
    }

    public boolean isClockwise() {
	return this.clockwise;
    }

    public boolean isBackgroundVisible() {
	return this.backgroundVisible;
    }

    public boolean isStrokeVisible() {
	return this.strokeVisible;
    }

    protected Paint backgroundFill;
    protected Paint backgroundMouseOnFill;
    protected Paint strokeFill;
    protected boolean clockwise;
    protected boolean backgroundVisible = true;;
    protected boolean strokeVisible = true;
    protected Node graphic;

    public RadialMenu() {

    }

    public RadialMenu(final double initialAngle, final double innerRadius,
	    final double radius, final double offset, final Paint bgFill,
	    final Paint bgMouseOnFill, final Paint strokeFill,
	    final boolean clockwise) {

	this.initialAngle = new SimpleDoubleProperty(initialAngle);
	this.initialAngle.addListener(new ChangeListener<Number>() {

	    @Override
	    public void changed(
		    final ObservableValue<? extends Number> paramObservableValue,
		    final Number paramT1, final Number paramT2) {
		RadialMenu.this.setInitialAngle(paramObservableValue.getValue()
			.doubleValue());
	    }
	});
	this.innerRadius = innerRadius;
	this.radius = radius;
	this.offset = new SimpleDoubleProperty(offset);
	this.offset.addListener(new ChangeListener<Number>() {

	    @Override
	    public void changed(
		    final ObservableValue<? extends Number> paramObservableValue,
		    final Number paramT1, final Number paramT2) {
		RadialMenu.this.setOffset(paramObservableValue.getValue()
			.doubleValue());
	    }
	});
	this.backgroundFill = bgFill;
	this.backgroundMouseOnFill = bgMouseOnFill;
	this.strokeFill = strokeFill;
	this.clockwise = clockwise;

    }

    public void setOnMenuItemMouseClicked(
	    final EventHandler<? super MouseEvent> paramEventHandler) {
	for (final RadialMenuItem item : this.items) {
	    item.setOnMouseClicked(paramEventHandler);
	}
    }

    public void setInitialAngle(final double angle) {
	this.initialAngle.set(angle);

	double angleOffset = this.initialAngle.get();
	for (final RadialMenuItem item : this.items) {
	    item.setStartAngle(angleOffset);
	    angleOffset = angleOffset + item.getMenuSize();
	}
    }

    public void setInnerRadius(final double radius) {
	this.innerRadius = radius;

	for (final RadialMenuItem item : this.items) {
	    item.setInnerRadius(radius);
	}
    }

    public void setRadius(final double radius) {
	this.radius = radius;

	for (final RadialMenuItem item : this.items) {
	    item.setRadius(radius);
	}
    }

    public void setOffset(final double offset) {
	this.offset.set(offset);

	for (final RadialMenuItem item : this.items) {
	    item.setOffset(offset);
	}
    }

    public void setBackgroundVisible(final boolean visible) {
	this.backgroundVisible = visible;

	for (final RadialMenuItem item : this.items) {
	    item.setBackgroundVisible(this.backgroundVisible);
	}
    }

    public void setStrokeVisible(final boolean visible) {
	this.strokeVisible = visible;

	for (final RadialMenuItem item : this.items) {
	    item.setStrokeVisible(this.strokeVisible);
	}
    }

    public void setBackgroundColor(final Paint color) {
	this.backgroundFill = color;

	for (final RadialMenuItem item : this.items) {
	    item.setBackgroundColor(color);
	}
    }

    public void setBackgroundMouseOnColor(final Paint color) {
	this.backgroundMouseOnFill = color;

	for (final RadialMenuItem item : this.items) {
	    item.setBackgroundMouseOnColor(color);
	}
    }

    public void setStrokeColor(final Paint color) {
	this.strokeFill = color;

	for (final RadialMenuItem item : this.items) {
	    item.setStrokeColor(color);
	}
    }

    public void setClockwise(final boolean clockwise) {
	this.clockwise = clockwise;

	for (final RadialMenuItem item : this.items) {
	    item.setClockwise(clockwise);
	}
    }

    public void addMenuItem(final RadialMenuItem item) {
	item.setBackgroundColor(this.backgroundFill);
	item.setBackgroundMouseOnColor(this.backgroundMouseOnFill);
	item.setInnerRadius(this.innerRadius);
	item.setRadius(this.radius);
	item.setOffset(this.offset.get());
	item.setStrokeColor(this.strokeFill);
	item.setClockwise(this.clockwise);
	this.items.add(item);
	this.getChildren().add(item);
	double angleOffset = this.initialAngle.get();
	for (final RadialMenuItem it : this.items) {
	    it.setStartAngle(angleOffset);
	    angleOffset = angleOffset + item.getMenuSize();
	}
    }

    public List<RadialMenuItem> getMenuItems() {
	return this.items;
    }

    public void removeMenuItem(final RadialMenuItem item) {
	this.items.remove(item);
	this.getChildren().remove(item);
    }

    public void removeMenuItem(final int itemIndex) {
	final RadialMenuItem item = this.items.remove(itemIndex);
	this.getChildren().remove(item);
    }

}
