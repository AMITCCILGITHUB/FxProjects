/*
 * Launcher
 * Copyright 2011 (C) Mr LoNee - (Laurent NICOLAS) - www.mrlonee.com 
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
package mrlonee.presenter;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.effect.Light;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.LightingBuilder;
import javafx.scene.effect.ReflectionBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.paint.StopBuilder;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import mrlonee.presenter.impl.PresenterImpl;

public class Launcher extends Application {

	public static void main(String[] args) {
		Launcher.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Presenter presenterServices = new PresenterImpl();

		final Group rootContainer = new Group();
		final Group formRoot = presenterServices.getRootNode();
		final Group thumb = presenterServices.getThumbNode();

		rootContainer.getChildren().add(formRoot);
		rootContainer.getChildren().add(thumb);

		buildPresentation(formRoot, presenterServices);
		buildGUI(rootContainer, formRoot, presenterServices);

		final Scene scene = new Scene(rootContainer, 600, 600, Color.WHITE);

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> paramObservableValue,
					Number paramT1, Number paramT2) {
				presenterServices.setScreenSize(paramObservableValue.getValue()
						.doubleValue(), scene.getHeight());
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> paramObservableValue,
					Number paramT1, Number paramT2) {
				presenterServices.setScreenSize(scene.getWidth(),
						paramObservableValue.getValue().doubleValue());
			}
		});

		final Group root = presenterServices.getRootNode();

		EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {

			protected double eventPressX;
			protected double eventPressY;
			protected double rootPressX;
			protected double rootPressY;

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
					scene.setCursor(Cursor.MOVE);
					eventPressX = event.getSceneX();
					eventPressY = event.getSceneY();

					rootPressX = root.getTranslateX();
					rootPressY = root.getTranslateY();
				} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					root.setTranslateX(rootPressX
							+ (event.getSceneX() - eventPressX));
					root.setTranslateY(rootPressY
							+ (event.getSceneY() - eventPressY));
				} else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
					scene.setCursor(Cursor.DEFAULT);
				}
			}
		};

		scene.setOnMousePressed(dragHandler);
		scene.setOnMouseReleased(dragHandler);
		scene.setOnMouseDragged(dragHandler);

		// Initial Scale
		formRoot.scaleXProperty().set(0.2);
		formRoot.scaleYProperty().set(0.2);
		formRoot.translateYProperty().set(0);

		thumb.translateXProperty().set(
				600 / 2.0 - thumb.getLayoutBounds().getWidth() / 2.0);
		thumb.translateYProperty().set(550);

		presenterServices.setScreenSize(600, 600);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.setTitle("MrLoNee Presenter Beta Demo");
		primaryStage.show();
	}

	protected static void buildPresentation(final Group formRoot,
			final Presenter presenterServices) {

		/*
		 * Hello slide
		 */
		Distant lightEffectDistant = new Light.Distant();
		lightEffectDistant.azimuthProperty().set(-135);

		Text text = TextBuilder
				.create()
				.text("Hello")
				.x(0)
				.y(0)
				.fill(Color.GREEN)
				.font(new Font(50))
				.scaleX(3)
				.scaleY(3)
				.effect(LightingBuilder.create().light(lightEffectDistant)
						.surfaceScale(5.0).build()).build();

		presenterServices.addElementToPresent(text);

		/*
		 * Slide 1
		 */
		List<Stop> slide1Stops = new ArrayList<Stop>();
		slide1Stops.add(StopBuilder.create().offset(0.0).color(Color.BLUE)
				.build());
		slide1Stops.add(StopBuilder.create().offset(1.0).color(Color.DARKBLUE)
				.build());

		Group slide1 = GroupBuilder
				.create()
				.children(
						RectangleBuilder
								.create()
								.x(0)
								.y(0)
								.width(200)
								.height(150)
								.fill(LinearGradientBuilder.create()
										.startX(0.5).startY(0.0).endX(1.0)
										.endY(0.0).stops(slide1Stops)
										.proportional(true)
										.cycleMethod(CycleMethod.REPEAT)
										.build()).build(),

						TextBuilder
								.create()
								.x(60)
								.y(70)
								.font(new Font(30))
								.textAlignment(TextAlignment.CENTER)
								.text("JavaFx")
								.smooth(true)
								.fill(Color.WHITE)
								.effect(DropShadowBuilder.create().offsetX(3.0)
										.offsetY(3.0).build()).build(),

						TextBuilder.create().x(75).y(90).font(new Font(20))
								.textAlignment(TextAlignment.CENTER)
								.text("Rocks").smooth(true).fill(Color.WHITE)
								.build()).translateX(150).translateY(200)
				.rotate(90).build();

		presenterServices.addElementToPresent(slide1);

		/*
		 * Slide 2
		 */
		List<Stop> slide2Stops = new ArrayList<Stop>();
		slide2Stops.add(StopBuilder.create().offset(0.2).color(Color.RED)
				.build());
		slide2Stops.add(StopBuilder.create().offset(0.8)
				.color(Color.RED.darker()).build());
		slide2Stops.add(StopBuilder.create().offset(1.0).color(Color.RED)
				.build());

		Group slide2 = GroupBuilder
				.create()
				.children(
						CircleBuilder
								.create()
								.centerX(200)
								.centerY(200)
								.radius(200)
								.fill(RadialGradientBuilder.create()
										.centerX(0.3).centerY(0.3)
										.stops(slide2Stops).build()).build(),

						TextBuilder
								.create()
								.x(115)
								.y(180)
								.font(new Font(30))
								.textAlignment(TextAlignment.CENTER)
								.text("MrLoNee Too !")
								.effect(ReflectionBuilder.create()
										.fraction(0.8).build()).smooth(true)
								.fill(Color.WHITE).build()).translateX(400)
				.translateY(400).rotate(140).build();

		presenterServices.addElementToPresent(slide2);

		Group visit = GroupBuilder
				.create()
				.children(
						RectangleBuilder.create().x(0).y(-100).width(800)
								.height(500).scaleX(10).scaleY(10)
								.fill(Color.TRANSPARENT)
								.strokeType(StrokeType.CENTERED).strokeWidth(2)
								.stroke(Color.LIGHTGRAY).mouseTransparent(true)
								.build(),

						TextBuilder.create().x(0).y(-1300).scaleX(8).scaleY(8)
								.font(new Font(80))
								.textAlignment(TextAlignment.CENTER)
								.text("visit www.mrlonee.com !").smooth(true)
								.fill(Color.BLACK).build()).build();

		presenterServices.addElementToPresent(visit);
	}

	public static void buildGUI(final Group rootContainer,
			final Group formRoot, final Presenter presenterServices) {

		Path nextSymbol = PathBuilder
				.create()
				.elements(new MoveTo(0.0, 0.0), new LineTo(0.0f, 30.0f),
						new LineTo(20, 15), new LineTo(0.0f, 0.0f))
				.fill(Color.BLACK).build();

		Path previousSymbol = PathBuilder
				.create()
				.elements(new MoveTo(20.0, 0.0), new LineTo(20.0f, 30.0f),
						new LineTo(0, 15), new LineTo(20.0, 0.0f))
				.fill(Color.BLACK).build();

		Button nextButton = ButtonBuilder.create().graphic(nextSymbol)
				.onMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						presenterServices.presentNextElement();
					}
				}).translateX(558).translateY(280).opacity(0.3).build();

		Button previousButton = ButtonBuilder.create().graphic(previousSymbol)
				.onMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						presenterServices.presentPreviousElement();
					}
				}).translateX(0).translateY(280).opacity(0.3).build();

		rootContainer.getChildren().add(nextButton);
		rootContainer.getChildren().add(previousButton);

		// Path zoomIn = PathBuilder.create().elements(
		// new MoveTo(0.0, 10.0),
		// new LineTo(20.0f, 10.0f),
		// new MoveTo(10, 0),
		// new LineTo(10f, 20.0f)).strokeWidth(4).fill(Color.BLACK).build();
		// Button zoomInButton =
		// ButtonBuilder.create().graphic(zoomIn).onMouseClicked(
		// new EventHandler<MouseEvent>() {
		//
		// @Override
		// public void handle(MouseEvent arg0) {
		// double currentScale =
		// presenterServices.getRootNode().scaleXProperty().get();
		// ScaleTransition scaleTransition = ScaleTransitionBuilder.create()
		// .node(presenterServices.getRootNode())
		// .toX(currentScale*2.0)
		// .toY(currentScale*2.0)
		// .duration(Duration.valueOf(300))
		// .build();
		// scaleTransition.play();
		//
		// }
		// }).translateX(230).translateY(60).opacity(0.3).build();
		// rootContainer.getChildren().add(zoomInButton);
		//
		// Path zoomOut = PathBuilder.create().elements(
		// new MoveTo(0.0, 10.0),
		// new LineTo(20.0f, 10.0f)).strokeWidth(4).fill(Color.BLACK).build();
		// Button zoomOutButton =
		// ButtonBuilder.create().graphic(zoomOut).onMouseClicked(
		// new EventHandler<MouseEvent>() {
		//
		// @Override
		// public void handle(MouseEvent arg0) {
		// double currentScale =
		// presenterServices.getRootNode().scaleXProperty().get();
		// ScaleTransition scaleTransition = ScaleTransitionBuilder.create()
		// .node(presenterServices.getRootNode())
		// .toX(currentScale/2.0)
		// .toY(currentScale/2.0)
		// .duration(Duration.valueOf(300))
		// .build();
		// scaleTransition.play();
		//
		// }
		// }).translateX(320).translateY(60).opacity(0.3).build();
		// rootContainer.getChildren().add(zoomOutButton);
	}

}
