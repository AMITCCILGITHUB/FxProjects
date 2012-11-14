/*
 * RadialCheckMenuItem
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

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class RadialCheckMenuItem extends RadialMenuItem {

    protected boolean selected = false;

    protected Paint selectedColor;

    protected Paint selectedMouseOnColor;

    public RadialCheckMenuItem(final double menuSize, final Node graphic) {
	super(menuSize, graphic);
	this.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(final MouseEvent arg0) {
		RadialCheckMenuItem.this.selected = !RadialCheckMenuItem.this.selected;
		RadialCheckMenuItem.this.drawMenuItem();
	    }
	});
    }

    public RadialCheckMenuItem(final double menuSize, final Node graphic,
	    final boolean selected) {
	this(menuSize, graphic);
	this.selected = selected;
    }

    public RadialCheckMenuItem(final double menuSize, final Node graphic,
	    final boolean selected, final Paint selectedColor) {
	this(menuSize, graphic, selected);
	this.selectedColor = selectedColor;
    }

    public RadialCheckMenuItem(final double menuSize, final Node graphic,
	    final boolean selected, final Paint selectedColor,
	    final Paint selectedMouseOnColor) {
	this(menuSize, graphic, selected);
	this.selectedColor = selectedColor;
	this.selectedMouseOnColor = selectedMouseOnColor;
    }

    @Override
    protected void drawMenuItem() {
	super.drawMenuItem();

	Paint color = null;
	if (this.backgroundVisible) {
	    if (this.selected && this.selectedColor != null) {
		if (this.mouseOn && this.selectedMouseOnColor != null) {
		    color = this.selectedMouseOnColor;
		} else {
		    color = this.selectedColor;
		}
	    } else {
		if (this.mouseOn && this.backgroundMouseOnColor != null) {
		    color = this.backgroundMouseOnColor;
		} else {
		    color = this.backgroundColor;
		}
	    }
	}

	this.path.setFill(color);
    }

}
