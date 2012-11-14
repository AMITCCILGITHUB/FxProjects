package ui.layouts.ribbonBar.tabs.commerce;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Tables. This class represents the Payment Method Ribbon Bar Component.
 */
public class Payment {

	private ToggleButton btnV, btnMC, btnA, btnD, btnP;
	private VBox root;

	/**
	 * Default Constructor.
	 */
	public Payment() {

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

		// Build UI Controls
		this.buildVisaButton();
		this.buildMasterCardButton();
		this.buildAmericanExpressButton();
		this.buildDiscoverButton();
		this.buildPayPalButton();

		// Group Toggle Buttons together so only one maybe selected.
		ToggleGroup group = new ToggleGroup();
		this.btnA.setToggleGroup(group);
		this.btnD.setToggleGroup(group);
		this.btnMC.setToggleGroup(group);
		this.btnV.setToggleGroup(group);
		this.btnP.setToggleGroup(group);

		// Add All Componets to the GridPane.
		layout.add(this.btnV, 0, 0);
		layout.add(this.btnMC, 1, 0);
		layout.add(this.btnA, 2, 0);
		layout.add(this.btnD, 3, 0);
		layout.add(this.btnP, 4, 0);

		// Build the Toolbar Container Label.
		Label label = new Label("Payment Method");
		label.getStyleClass().add("ribbonLabel");
		label.setTooltip(new Tooltip("How to pay..."));

		// TODO: find a better way to center a label.
		VBox vbox = new VBox();
		vbox.getChildren().add(label);
		VBox.setVgrow(label, Priority.ALWAYS);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setStyle("-fx-padding: 5 0 0 0");
		layout.add(vbox, 0, 2, 5, 1);

		// Center alignment in the VBox, add GridPane, set VBox CSS Selector.
		this.root.setAlignment(Pos.CENTER);
		this.root.getChildren().add(layout);
		this.root.getStyleClass().add("toolbarContainer");
	}

	/**
	 * buildVisaButton. Helper method to build a Button.
	 */
	private void buildVisaButton() {

		// Create button with text.
		this.btnV = new ToggleButton("Visa");

		// Set the Image above the text.
		this.btnV.setContentDisplay(ContentDisplay.TOP);

		// Add image.
		String imgPath = "/ui/common/images/visa.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);
		this.btnV.setGraphic(imageView);

		// Set CSS Styles.
		this.btnV.getStyleClass().add("ribbonToggleButton");
		this.btnV.getStyleClass().add("leftToggleButton");

		// Set Tooltip
		this.btnV.setTooltip(new Tooltip("Visa"));

		// Set simple Click Event Handler.
		this.btnV.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Visa Toggle Button clicked.");

			}

		});
	}

	/**
	 * buildMasterCardButton. Helper method to build a Button.
	 */
	private void buildMasterCardButton() {

		// Create button with text.
		this.btnMC = new ToggleButton("MasterCard");

		// Set the Image above the text.
		this.btnMC.setContentDisplay(ContentDisplay.TOP);

		// Add image.
		String imgPath = "/ui/common/images/mastercard.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);
		this.btnMC.setGraphic(imageView);

		// Set CSS Styles.
		this.btnMC.getStyleClass().add("ribbonToggleButton");
		this.btnMC.getStyleClass().add("middleToggleButton");

		// Set Tooltip
		this.btnMC.setTooltip(new Tooltip("MasterCard"));

		// Set simple Click Event Handler.
		this.btnMC.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("MasterCard Toggle Button clicked.");

			}

		});
	}

	/**
	 * buildAmericanExpressButton. Helper method to build a Button.
	 */
	private void buildAmericanExpressButton() {

		// Create button with text.
		this.btnA = new ToggleButton("Amex");

		// Set the Image above the text.
		this.btnA.setContentDisplay(ContentDisplay.TOP);

		// Add image.
		String imgPath = "/ui/common/images/amex.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);
		this.btnA.setGraphic(imageView);

		// Set CSS Styles.
		this.btnA.getStyleClass().add("ribbonToggleButton");
		this.btnA.getStyleClass().add("middleToggleButton");

		// Set Tooltip
		this.btnA.setTooltip(new Tooltip("American Express"));

		// Set simple Click Event Handler.
		this.btnA.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("American Express Toggle Button clicked.");

			}

		});
	}

	/**
	 * buildDiscoverButton. Helper method to build a Button.
	 */
	private void buildDiscoverButton() {

		// Create button with text.
		this.btnD = new ToggleButton("Discover");

		// Set the Image above the text.
		this.btnD.setContentDisplay(ContentDisplay.TOP);

		// Add image.
		String imgPath = "/ui/common/images/discover.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);
		this.btnD.setGraphic(imageView);

		// Set CSS Styles.
		this.btnD.getStyleClass().add("ribbonToggleButton");
		this.btnD.getStyleClass().add("middleToggleButton");

		// Set Tooltip
		this.btnD.setTooltip(new Tooltip("Discover Card"));

		// Set simple Click Event Handler.
		this.btnD.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Discover Card Toggle Button clicked.");

			}

		});
	}

	/**
	 * buildPayPalButton. Helper method to build a Button.
	 */
	private void buildPayPalButton() {

		// Create button with text.
		this.btnP = new ToggleButton("PayPal");

		// Set the Image above the text.
		this.btnP.setContentDisplay(ContentDisplay.TOP);

		// Add image.
		String imgPath = "/ui/common/images/paypal.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				24.0, 24.0, true, true);
		ImageView imageView = new ImageView(image);
		this.btnP.setGraphic(imageView);

		// Set CSS Styles.
		this.btnP.getStyleClass().add("ribbonToggleButton");
		this.btnP.getStyleClass().add("rightToggleButton");

		// Set Tooltip
		this.btnP.setTooltip(new Tooltip("PayPal"));

		// Set simple Click Event Handler.
		this.btnP.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("PayPal Toggle Button clicked.");

			}

		});
	}

}
