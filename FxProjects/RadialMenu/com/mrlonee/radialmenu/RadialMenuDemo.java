/*
 * RadialMenuDemo
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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ParallelTransitionBuilder;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ColorPickerBuilder;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class RadialMenuDemo extends Application {

    protected ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
	    1);

    protected Group container = new Group();

    protected int counter;
    protected RadialMenu radialMenu;
    protected boolean show;
    protected double lastOffsetValue;
    protected double lastInitialAngleValue;

    public static void main(final String[] args) {
	RadialMenuDemo.launch(args);
    }

    private void hideRadialMenu() {
	this.lastInitialAngleValue = this.radialMenu.getInitialAngle();
	this.lastOffsetValue = this.radialMenu.getOffset();

	final FadeTransition fade = FadeTransitionBuilder.create()
		.node(this.radialMenu).fromValue(1).toValue(0)
		.duration(Duration.millis(300))
		.onFinished(new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(final ActionEvent arg0) {
			RadialMenuDemo.this.radialMenu.setVisible(false);
		    }
		}).build();

	// final Animation offset = new Timeline(new KeyFrame(Duration.ZERO,
	// new KeyValue(this.radialMenu.getOffsetProperty(),
	// this.lastOffsetValue)), new KeyFrame(
	// Duration.millis(400), new KeyValue(
	// this.radialMenu.getOffsetProperty(), 0)));
	//
	// final Animation angle = new Timeline(new KeyFrame(Duration.ZERO,
	// new KeyValue(this.radialMenu.getInitialAngleProperty(),
	// this.lastInitialAngleValue)), new KeyFrame(
	// Duration.millis(400), new KeyValue(
	// this.radialMenu.getInitialAngleProperty(),
	// this.lastInitialAngleValue + 20)));

	final ParallelTransition transition = ParallelTransitionBuilder
		.create().children(fade).build();

	transition.play();
    }

    private void showRadialMenu(final double x, final double y) {
	if (this.radialMenu.isVisible()) {
	    this.lastInitialAngleValue = this.radialMenu.getInitialAngle();
	    this.lastOffsetValue = this.radialMenu.getOffset();
	}
	this.radialMenu.setLayoutX(x);
	this.radialMenu.setLayoutY(y);
	this.radialMenu.setVisible(true);

	final FadeTransition fade = FadeTransitionBuilder.create()
		.node(this.radialMenu).duration(Duration.millis(400))
		.fromValue(0).toValue(1.0).build();

	final Animation offset = new Timeline(new KeyFrame(Duration.ZERO,
		new KeyValue(this.radialMenu.getOffsetProperty(), 0)),
		new KeyFrame(Duration.millis(300), new KeyValue(this.radialMenu
			.getOffsetProperty(), this.lastOffsetValue)));

	final Animation angle = new Timeline(new KeyFrame(Duration.ZERO,
		new KeyValue(this.radialMenu.getInitialAngleProperty(),
			this.lastInitialAngleValue + 20)), new KeyFrame(
		Duration.millis(300), new KeyValue(
			this.radialMenu.getInitialAngleProperty(),
			this.lastInitialAngleValue)));

	final ParallelTransition transition = ParallelTransitionBuilder
		.create().children(fade, offset, angle).build();

	transition.play();
    }

    public Node[] createShapes() {
	this.counter++;
	this.radialMenu = new RadialMenu(-23, 30, 100, 10, Color.LIGHTBLUE,
		Color.LIGHTBLUE.darker(), Color.DARKBLUE, false);

	final ImageView play = ImageViewBuilder.create()
		.image(new Image("file:images/play.png")).build();

	final ImageView stop = ImageViewBuilder.create()
		.image(new Image("file:images/stop.png")).build();

	final ImageView pause = ImageViewBuilder.create()
		.image(new Image("file:images/pause.png")).build();

	final ImageView forward = ImageViewBuilder.create()
		.image(new Image("file:images/forward.png")).build();

	final ImageView backward = ImageViewBuilder.create()
		.image(new Image("file:images/backward.png")).build();

	final EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(final ActionEvent paramT) {
		final RadialMenuItem item = (RadialMenuItem) paramT.getSource();
		System.out.println(item.getText());
	    }

	};

	this.radialMenu.addMenuItem(new RadialMenuItem(45, "forward", forward,
		handler));

	this.radialMenu.addMenuItem(new RadialMenuItem(45, "pause", pause,
		handler));

	this.radialMenu.addMenuItem(new RadialMenuItem(45, "play", play,
		handler));

	this.radialMenu.addMenuItem(new RadialMenuItem(45, "stop", stop,
		handler));

	this.radialMenu.addMenuItem(new RadialMenuItem(45, "backward",
		backward, handler));

	final GridPane controls = new GridPane();
	controls.setVgap(15);

	final Slider initialAngleSlider = SliderBuilder.create().min(-360)
		.max(360).value(-23).minWidth(200).majorTickUnit(30)
		.showTickLabels(true).build();

	initialAngleSlider.valueProperty().addListener(
		new ChangeListener<Number>() {
		    @Override
		    public void changed(
			    final ObservableValue<? extends Number> ov,
			    final Number old_val, final Number val) {
			RadialMenuDemo.this.lastInitialAngleValue = val
				.doubleValue();
			RadialMenuDemo.this.radialMenu.setInitialAngle(val
				.intValue());
			System.out.println("Angle" + val);
		    }
		});

	controls.add(LabelBuilder.create().text("initial angle ").build(), 0, 1);
	controls.add(initialAngleSlider, 1, 1);

	final Slider innerRadiusSlider = SliderBuilder.create().min(0).max(300)
		.value(30).minWidth(200).majorTickUnit(30).showTickLabels(true)
		.build();

	innerRadiusSlider.valueProperty().addListener(
		new ChangeListener<Number>() {
		    @Override
		    public void changed(
			    final ObservableValue<? extends Number> ov,
			    final Number old_val, final Number new_val) {
			RadialMenuDemo.this.radialMenu.setInnerRadius(new_val
				.intValue());
		    }
		});

	controls.add(LabelBuilder.create().text("inner radius ").build(), 0, 3);
	controls.add(innerRadiusSlider, 1, 3);

	final Slider radiusSlider = SliderBuilder.create().min(100).max(500)
		.value(100).minWidth(200).majorTickUnit(30)
		.showTickLabels(true).build();

	radiusSlider.valueProperty().addListener(new ChangeListener<Number>() {
	    @Override
	    public void changed(final ObservableValue<? extends Number> ov,
		    final Number old_val, final Number new_val) {
		RadialMenuDemo.this.radialMenu.setRadius(new_val.intValue());
	    }
	});

	controls.add(LabelBuilder.create().text("radius ").build(), 0, 4);
	controls.add(radiusSlider, 1, 4);

	final Slider offsetSlider = SliderBuilder.create().min(0).max(100)
		.value(10).minWidth(200).majorTickUnit(30).showTickLabels(true)
		.build();

	offsetSlider.valueProperty().addListener(new ChangeListener<Number>() {
	    @Override
	    public void changed(final ObservableValue<? extends Number> ov,
		    final Number old_val, final Number val) {
		RadialMenuDemo.this.lastOffsetValue = val.doubleValue();
		RadialMenuDemo.this.radialMenu.setOffset(val.intValue());
	    }
	});

	controls.add(LabelBuilder.create().text("offset ").build(), 0, 5);
	controls.add(offsetSlider, 1, 5);

	final ColorPicker colorPicker = ColorPickerBuilder.create()
		.value(Color.LIGHTBLUE).build();

	colorPicker.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(final ActionEvent t) {
		RadialMenuDemo.this.radialMenu.setBackgroundColor(colorPicker
			.getValue());
	    }
	});

	controls.add(LabelBuilder.create().text("backgroundColor ").build(), 0,
		6);
	controls.add(colorPicker, 1, 6);

	final ColorPicker backgroundMouseOnPicker = ColorPickerBuilder.create()
		.value(Color.LIGHTBLUE.darker()).build();

	backgroundMouseOnPicker.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(final ActionEvent t) {
		RadialMenuDemo.this.radialMenu
			.setBackgroundMouseOnColor(backgroundMouseOnPicker
				.getValue());
	    }
	});

	controls.add(LabelBuilder.create().text("background mouse on Color ")
		.build(), 0, 7);
	controls.add(backgroundMouseOnPicker, 1, 7);

	final ColorPicker strokePicker = ColorPickerBuilder.create()
		.value(Color.DARKBLUE).build();

	strokePicker.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(final ActionEvent t) {
		RadialMenuDemo.this.radialMenu.setStrokeColor(strokePicker
			.getValue());
	    }
	});

	controls.add(LabelBuilder.create().text("stroke Color ").build(), 0, 8);
	controls.add(strokePicker, 1, 8);

	final CheckBox clockwiseCheckBox = CheckBoxBuilder.create()
		.selected(false).build();

	clockwiseCheckBox.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(final ActionEvent t) {
		RadialMenuDemo.this.radialMenu.setClockwise(clockwiseCheckBox
			.isSelected());
	    }
	});

	controls.add(LabelBuilder.create().text("clockwise ").build(), 0, 9);
	controls.add(clockwiseCheckBox, 1, 9);

	controls.translateXProperty().set(200);
	controls.translateYProperty().set(-200);

	return new Node[] { this.radialMenu, controls };
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
	this.container = new Group();
	this.container.translateXProperty().set(150);
	this.container.translateYProperty().set(250);
	this.container.setCursor(Cursor.MOVE);

	final Group root = new Group(this.container);

	final Scene scene = new Scene(root);
	scene.setFill(Color.WHITE);

	primaryStage.setScene(scene);
	primaryStage.setWidth(750);
	primaryStage.setHeight(500);
	primaryStage.setX(0);
	primaryStage.setY(0);
	primaryStage.setTitle("Radial Menu Demo");
	primaryStage.show();

	final Slider scaleSlider = SliderBuilder.create().min(0).max(20)
		.value(1.0).minWidth(600).majorTickUnit(2).showTickLabels(true)
		.build();
	final GridPane zoom = new GridPane();
	zoom.translateYProperty().set(20);
	zoom.add(LabelBuilder.create().text("Scale ").build(), 0, 0);
	zoom.add(scaleSlider, 1, 0);
	this.container.scaleXProperty().bind(scaleSlider.valueProperty());
	this.container.scaleYProperty().bind(scaleSlider.valueProperty());
	root.getChildren().add(zoom);

	// final Path axis = PathBuilder
	// .create()
	// .elements(new MoveTo(0, 0), new LineTo(0, 200),
	// new LineTo(-10, 190), new MoveTo(0, 200),
	// new LineTo(10, 190), new MoveTo(0, 200),
	// new LineTo(0, 3000),
	//
	// new MoveTo(0, 0), new LineTo(200, 0),
	// new LineTo(190, -10), new MoveTo(200, 0),
	// new LineTo(190, 10), new MoveTo(200, 0),
	// new LineTo(3000, 0)).stroke(Color.BLACK).build();
	//
	final Group lineGroup = new Group();
	final int offset = 50;
	final int nbLine = 100;
	final Color lineColor = new Color(0.05, 0.05, 0.05, 0.5);

	final Rectangle backgroundRectangle = RectangleBuilder.create()
		.fill(Color.WHITE).x(-offset * nbLine).y(-offset * nbLine)
		.width(offset * nbLine * 2).height(offset * nbLine * 2).build();
	lineGroup.getChildren().add(backgroundRectangle);

	for (int x = -nbLine; x < nbLine; x++) {
	    final Line line = LineBuilder.create().startX(x * offset)
		    .startY(-nbLine * offset).endX(x * offset)
		    .endY(nbLine * offset).stroke(lineColor).build();
	    lineGroup.getChildren().addAll(line);
	}

	for (int y = -nbLine; y < nbLine; y++) {
	    final Line line = LineBuilder.create().startX(-nbLine * offset)
		    .startY(y * offset).endX(nbLine * offset).endY(y * offset)
		    .stroke(lineColor).build();

	    lineGroup.getChildren().addAll(line);
	}

	// final Text origin = TextBuilder.create().text("(0,0)")
	// .fill(Color.BLACK).x(-10).y(-5).build();
	//
	// final Text x = TextBuilder.create().text("X Axis").fill(Color.BLACK)
	// .x(50).y(-5).build();
	//
	// final Text y = TextBuilder.create().text("Y Axis").fill(Color.BLACK)
	// .x(-25).y(70).rotate(-90).build();

	final AtomicBoolean dragging = new AtomicBoolean(false);

	final EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
	    protected double pressX = 0.0d;
	    protected double pressY = 0.0d;

	    @Override
	    public void handle(final MouseEvent mouseEvent) {
		if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
		    this.pressX = mouseEvent.getX();
		    this.pressY = mouseEvent.getY();
		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
		    dragging.set(true);
		    final double newX = RadialMenuDemo.this.container
			    .getTranslateX() + mouseEvent.getX() - this.pressX;
		    final double newY = RadialMenuDemo.this.container
			    .getTranslateY() + mouseEvent.getY() - this.pressY;

		    RadialMenuDemo.this.container.translateXProperty()
			    .set(newX);
		    RadialMenuDemo.this.container.translateYProperty()
			    .set(newY);

		} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
		    dragging.set(false);
		}
	    }
	};
	this.container.setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(final MouseEvent event) {
		if (!dragging.get()) {
		    if (event.getButton() == MouseButton.SECONDARY) {
			RadialMenuDemo.this.showRadialMenu(event.getX(),
				event.getY());
		    } else {
			RadialMenuDemo.this.hideRadialMenu();
		    }
		}

	    }

	});
	this.container.setOnMousePressed(handler);
	this.container.setOnMouseReleased(handler);
	this.container.setOnMouseDragged(handler);

	RadialMenuDemo.this.container.getChildren().clear();

	final Node[] dut = RadialMenuDemo.this.createShapes();

	RadialMenuDemo.this.container.getChildren().add(lineGroup);
	RadialMenuDemo.this.container.getChildren().addAll(dut);
	// RadialMenuDemo.this.container.getChildren().addAll(axis, origin, x,
	// y);

	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

	    @Override
	    public void handle(final WindowEvent paramT) {
		RadialMenuDemo.this.executor.shutdownNow();
	    }
	});
    }
}