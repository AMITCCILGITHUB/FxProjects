/*
 * PresenterImpl
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
package mrlonee.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.paint.StopBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.util.Duration;
import mrlonee.presenter.Presenter;

public class PresenterImpl implements Presenter {

	protected List<Node> toPresentList = new ArrayList<Node>();

	protected List<Node> thumbList = new ArrayList<Node>();

	protected Group root = new Group();

	protected Group thumb = new Group();

	protected Node thumbSelection;

	protected int interationCounter = -1;

	protected Node currentPresentedNode = null;

	protected double screenX = 0;

	protected double screenY = 0;

	protected double screenWidth = 0.0d;

	protected double screenHeight = 0.0d;

	protected double speedInPixelPerSecond = 100;

	protected Circle target = CircleBuilder.create().radius(5)
			.fill(Color.BLACK).build();

	public PresenterImpl() {
		this.thumbSelection = CircleBuilder.create().radius(11)
				.fill(Color.TRANSPARENT).strokeWidth(2).stroke(Color.BLUE)
				.build();
		this.thumbSelection.setVisible(false);
		this.thumb.getChildren().add(this.thumbSelection);

	}

	@Override
	public void addElementToPresent(final Node node) {

		// Manage double click
		node.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					presentElement(node);
				}
			}
		});

		this.toPresentList.add(node);
		this.root.getChildren().add(node);
		this.thumb.getChildren().add(this.createThumb(node));
	}

	@Override
	public Group getRootNode() {
		return this.root;
	}

	@Override
	public void presentNextElement() {
		int nextIndex = interationCounter + 1;
		if (nextIndex == this.toPresentList.size()) {
			nextIndex = 0;
		}

		this.presentElement(nextIndex);
	}

	@Override
	public void presentPreviousElement() {
		int previousIndex = interationCounter - 1;
		if (previousIndex < 0) {
			previousIndex = this.toPresentList.size() - 1;
		}
		this.presentElement(previousIndex);

	}

	@Override
	public void presentElement(final Node node) {
		int index = this.toPresentList.indexOf(node);
		this.presentElement(index);
	}

	@Override
	public void setScreenSize(double width, double height) {
		this.screenWidth = width;
		this.screenHeight = height;

		this.centerScreenOnCurrentBound();
	}

	// @Override
	// public void setScreenLocation(double x, double y) {
	// this.screenX = x;
	// this.screenY= y;
	//
	// this.centerScreenOnCurrentBound();
	// }

	@Override
	public Group getThumbNode() {
		return this.thumb;
	}

	protected void presentElement(int elementIndex) {
		if (interationCounter != elementIndex) {
			interationCounter = elementIndex;
			Node node = this.toPresentList.get(interationCounter);
			this.playTransitionToNode(node);
			this.currentPresentedNode = node;
		}
	}

	protected void playTransitionToNode(final Node node) {
		double toRotate = -node.getRotate();
		if (toRotate == 360)
			toRotate = 0;

		// calculate the scale we will need to see the node in full screen
		double toScale = this.screenWidth
				/ Math.max(node.getBoundsInParent().getWidth() + 30, node
						.getBoundsInParent().getHeight() + 30);

		double prevScale = this.root.getScaleX();
		double prevRot = this.root.getRotate();

		this.root.setRotate(toRotate);
		this.root.setScaleX(toScale);
		this.root.setScaleY(toScale);

		Bounds nodeBoundsInScene = this.root.localToScene(node
				.getBoundsInParent());

		double toX = this.root.getTranslateX()
				+ this.screenWidth
				/ 2.0
				- (nodeBoundsInScene.getMinX() + (nodeBoundsInScene.getWidth() / 2.0));
		double toY = this.root.getTranslateY()
				+ this.screenHeight
				/ 2.0
				- (nodeBoundsInScene.getMinY() + (nodeBoundsInScene.getHeight() / 2.0));

		this.root.setScaleX(prevScale);
		this.root.setScaleY(prevScale);
		this.root.setRotate(prevRot);

		Timeline animationTransition = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.valueOf("600"), new KeyValue(
								this.root.translateXProperty(), toX),
								new KeyValue(this.root.translateYProperty(),
										toY), new KeyValue(this.root
										.scaleXProperty(), toScale),
								new KeyValue(this.root.scaleYProperty(),
										toScale), new KeyValue(this.root
										.rotateProperty(), toRotate))).build();

		animationTransition.play();

		selectThumb();

	}

	protected double computeFinalScale(final Node node) {
		double scale = 1.0;

		System.err.println(scale);

		return scale;
	}

	protected void centerScreenOnCurrentBound() {
		if (this.currentPresentedNode != null) {
			double x = this.screenX
					+ this.screenWidth
					/ 2.0
					- (currentPresentedNode.getBoundsInParent().getMinX() + currentPresentedNode
							.getBoundsInParent().getWidth() / 2.0);

			double y = this.screenY
					+ this.screenHeight
					/ 2.0
					- (currentPresentedNode.getBoundsInParent().getMinY() + currentPresentedNode
							.getBoundsInParent().getHeight() / 2.0);

			this.root.setTranslateX(x);
			this.root.setTranslateY(y);
		}
	}

	protected Node createThumb(final Node node) {

		List<Stop> thumbStop = new ArrayList<Stop>();
		thumbStop.add(StopBuilder.create().offset(0.2).color(Color.CYAN)
				.build());
		thumbStop.add(StopBuilder.create().offset(0.8)
				.color(Color.CYAN.darker()).build());
		thumbStop.add(StopBuilder.create().offset(1.0).color(Color.CYAN)
				.build());

		Circle thumb = CircleBuilder
				.create()
				.radius(10)
				.fill(RadialGradientBuilder.create().centerX(0.3).centerY(0.3)
						.stops(thumbStop).build())
				.translateX(30 * (this.toPresentList.size() - 1)).translateY(1)
				.build();

		final int index = this.thumbList.size();

		thumb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent paramT) {
				presentElement(index);
			}
		});

		this.thumbList.add(thumb);

		return thumb;
	}

	protected void selectThumb() {
		thumbSelection.setVisible(true);
		Node thumb = this.thumbList.get(interationCounter);
		this.thumbSelection.translateXProperty().bind(
				thumb.translateXProperty());
		this.thumbSelection.translateYProperty().bind(
				thumb.translateYProperty());
	}

}
