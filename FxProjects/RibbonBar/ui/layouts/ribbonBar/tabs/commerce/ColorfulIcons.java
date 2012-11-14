package ui.layouts.ribbonBar.tabs.commerce;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * ColorfulIcons. Add some colorful icons for fun.
 */
public class ColorfulIcons {

	private Button btnGiftWrap, btnNotes, btnMagicWand, btnContactDetails;

	private VBox root;

	/**
	 * Default Constructor.
	 */
	public ColorfulIcons() {

		this.root = new VBox();
		build();
	}

	/**
	 * get. Returns the VBox to be placed on the Ribbon Bar.
	 * 
	 * @return
	 */
	public VBox get() {

		return this.root;
	}

	/**
	 * build. Helper method to build the layout.
	 */
	private void build() {

		// GridPane used to layout the components.
		GridPane layout = new GridPane();

		// Grid Lines to help layout buttons.
		layout.setGridLinesVisible(false);
		layout.setHgap(5);

		// Build UI Controls
		this.buildGiftWrapButton();
		this.buildMagicWandButton();
		this.buildNotesButton();
		this.buildCustomerDetailsButton();

		// Add All Componets to the GridPane.
		layout.add(this.btnGiftWrap, 0, 0);
		layout.add(this.btnMagicWand, 1, 0);
		layout.add(this.btnNotes, 2, 0);
		layout.add(this.btnContactDetails, 3, 0);

		// Build the Toolbar Container Label.
		Label label = new Label("Colorful Icons...");
		label.getStyleClass().add("ribbonLabel");
		label.setTooltip(new Tooltip("Here are a few icons for fun"));

		// TODO: find a better way to center a label.
		VBox vbox = new VBox();
		vbox.getChildren().add(label);
		VBox.setVgrow(label, Priority.ALWAYS);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setStyle("-fx-padding: 5 0 0 0");
		layout.add(vbox, 0, 2, 4, 1);

		// Center alignment in the VBox, add GridPane, set VBox CSS Selector.
		this.root.setAlignment(Pos.CENTER);
		this.root.getChildren().add(layout);
		this.root.getStyleClass().add("toolbarContainer");
	}

	/**
	 * buildGiftWrapButton. Helper method to build Gift Wrap Button.
	 */
	private void buildGiftWrapButton() {

		String imgPath = "/ui/common/images/giftWrap.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnGiftWrap = new Button("Gift Wrap");
		this.btnGiftWrap.setContentDisplay(ContentDisplay.TOP);
		this.btnGiftWrap.setGraphic(imageView);
		this.btnGiftWrap.getStyleClass().add("ribbonToggleButton");
		this.btnGiftWrap.setTooltip(new Tooltip("Giftwrap, who knew?"));

		this.btnGiftWrap.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Gift Wrap Button clicked.");

			}

		});
	}

	/**
	 * buildNotesButton. Helper method to build a Button.
	 */
	private void buildNotesButton() {

		String imgPath = "/ui/common/images/notepad.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnNotes = new Button("Notes");
		this.btnNotes.setContentDisplay(ContentDisplay.TOP);
		this.btnNotes.setGraphic(imageView);
		this.btnNotes.getStyleClass().add("ribbonToggleButton");
		this.btnNotes.setTooltip(new Tooltip("Add Note"));

		this.btnNotes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Notes Button clicked.");

			}

		});
	}

	/**
	 * buildCustomerDetailsButton. Helper method to build a Button.
	 */
	private void buildCustomerDetailsButton() {

		String imgPath = "/ui/common/images/customerDetails.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnContactDetails = new Button("Customer");
		this.btnContactDetails.setContentDisplay(ContentDisplay.TOP);
		this.btnContactDetails.setGraphic(imageView);
		this.btnContactDetails.getStyleClass().add("ribbonToggleButton");
		this.btnContactDetails.setTooltip(new Tooltip("Customer Details"));

		this.btnContactDetails.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Customer Details Button clicked.");

			}

		});
	}

	/**
	 * buildMagicWandButton. Helper method to build a Button.
	 */
	private void buildMagicWandButton() {

		String imgPath = "/ui/common/images/magicWand.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnMagicWand = new Button("Magic");
		this.btnMagicWand.setContentDisplay(ContentDisplay.TOP);
		this.btnMagicWand.setGraphic(imageView);
		this.btnMagicWand.getStyleClass().add("ribbonToggleButton");
		this.btnMagicWand.setTooltip(new Tooltip("How about a magic trick?"));

		this.btnMagicWand.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Magic Wand Button clicked.");

			}

		});
	}

}
