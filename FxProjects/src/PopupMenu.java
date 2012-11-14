import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.TilePaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.util.Duration;

public class PopupMenu {

	private Group group;
	private Scene scene;
	private Node content;
	private Pane pane;
	private StackPane stackpane;
	private TilePane tile;
	private Rectangle outRectangle, inRectangle, borderRectangle;
	private Button closeBtn;
	private final Double MIN_HEIGHT = 60.0, MIN_WIDTH = 80.0;
	private Double height, width;
	private final SimpleDoubleProperty sdpHeightInRect = new SimpleDoubleProperty(
			0);
	private final SimpleDoubleProperty sdpWidthInRect = new SimpleDoubleProperty(
			0);
	private final SimpleDoubleProperty sdpHeightBorderRect = new SimpleDoubleProperty(
			0);
	private final SimpleDoubleProperty sdpWidthBorderRect = new SimpleDoubleProperty(
			0);
	private final SimpleDoubleProperty tileX = new SimpleDoubleProperty(0);
	private final SimpleDoubleProperty tileY = new SimpleDoubleProperty(0);
	private final SimpleDoubleProperty closeBtnX = new SimpleDoubleProperty(0);
	private final SimpleDoubleProperty closeBtnY = new SimpleDoubleProperty(0);
	private final int BORDER_SIZE = 3;
	private final int CONTENT_PADDING = 20;

	public PopupMenu(final Scene scene, final Node content) {

		this.content = content;
		this.content.setVisible(true);
		this.content.layoutBoundsProperty().addListener(
				new InvalidationListener() {

					@Override
					public void invalidated(Observable observable) {

						calculateWidthAndHeight();
					}
				});

		this.group = (Group) scene.getRoot();
		this.scene = scene;

		outRectangle = RectangleBuilder.create().width(scene.getWidth() - 2)
				.height(scene.getHeight() - 2).opacity(0.2).fill(Color.BLACK)
				.onMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {

						event.consume();
						closeMenu();
					}
				}).build();

		inRectangle = RectangleBuilder.create().width(sdpHeightInRect.get())
				.height(sdpWidthInRect.get()).fill(Color.WHITESMOKE)
				.arcHeight(10).arcWidth(10).opacity(0.9).build();
		inRectangle.heightProperty().bindBidirectional(sdpHeightInRect);
		inRectangle.widthProperty().bindBidirectional(sdpWidthInRect);

		borderRectangle = RectangleBuilder.create()
				.width(sdpWidthBorderRect.get())
				.height(sdpHeightBorderRect.get()).arcHeight(10).arcWidth(10)
				.opacity(0.9).build();
		borderRectangle.heightProperty().bindBidirectional(sdpHeightBorderRect);
		borderRectangle.widthProperty().bindBidirectional(sdpWidthBorderRect);

		tile = TilePaneBuilder.create().orientation(Orientation.HORIZONTAL)
				.tileAlignment(Pos.CENTER).managed(false)
				.children(this.content).build();
		tile.layoutXProperty().bindBidirectional(tileX);
		tile.layoutYProperty().bindBidirectional(tileY);

		stackpane = StackPaneBuilder.create().alignment(Pos.CENTER)
				.children(outRectangle, borderRectangle, inRectangle, tile)
				.build();

		closeBtn = ButtonBuilder.create().text("[X]").cancelButton(true)
				.visible(true).onAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						closeMenu();
					}
				}).build();

		closeBtn.layoutXProperty().bindBidirectional(closeBtnX);
		closeBtn.layoutYProperty().bindBidirectional(closeBtnY);

		pane = PaneBuilder.create().children(stackpane, closeBtn).build();

		// This is needed to fire to set everything first time around. Otherwise
		// the display is not centered
		// when the popup is shown on subsequent ocasions without the content
		// being altered
		calculateWidthAndHeight();
	}

	public void openMenu() {

		group.getChildren().addAll(pane);

		FadeTransition fadein = new FadeTransition(Duration.millis(800), pane);
		fadein.setFromValue(0.0);
		fadein.setToValue(1.0);
		fadein.play();
	}

	public void closeMenu() {

		FadeTransition fadeout = new FadeTransition(Duration.millis(700), pane);
		fadeout.setFromValue(1.0);
		fadeout.setToValue(0.0);
		fadeout.play();
		fadeout.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				group.getChildren().removeAll(pane);
			}
		});
	}

	private void calculateWidthAndHeight() {

		Bounds boundsInLocal = this.content.getBoundsInLocal();
		height = (boundsInLocal.getHeight() < MIN_HEIGHT) ? MIN_HEIGHT
				: boundsInLocal.getHeight();
		width = (boundsInLocal.getWidth() < MIN_WIDTH) ? MIN_WIDTH
				: boundsInLocal.getWidth();

		sdpHeightInRect.set(height + CONTENT_PADDING);
		sdpWidthInRect.set(width + CONTENT_PADDING);

		sdpHeightBorderRect.set(sdpHeightInRect.get() + BORDER_SIZE);
		sdpWidthBorderRect.set(sdpWidthInRect.get() + BORDER_SIZE);

		tileX.set(scene.getWidth() / 2 - width / 2);
		tileY.set(scene.getHeight() / 2 - height / 2);

		closeBtnX.set((scene.getWidth() / 2) - 18);
		closeBtnY
				.set((scene.getHeight() - (scene.getHeight() - borderRectangle
						.getHeight()) / 2)
						- (((borderRectangle.getHeight() - inRectangle
								.getHeight()) * 2)) - 5);
		System.err.println("H:" + height + ", W:" + width);
	}
}