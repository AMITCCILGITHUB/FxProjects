package ui.layouts.ribbonBar.tabs.home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Clipboard. This class represents the clipboard section.
 */
public class Clipboard {

	private SplitMenuButton pasteButton;
	private Button cutButton, copyButton;
	private GridPane layout;
	private VBox root; // Root Node for the Clipboard region.

	/**
	 * Default Constructor.
	 */
	public Clipboard() {

		this.layout = new GridPane();
		this.root = new VBox();
		this.pasteButton = new SplitMenuButton();
		this.cutButton = new Button();
		this.copyButton = new Button();

		build();
	}

	/**
	 * get. Returns the instance of the HBox which contains the layout of the
	 * clipboard region.
	 * 
	 * @return
	 */
	public VBox get() {

		return this.root;
	}

	/**
	 * build. Helper method to build the Clipboard layout.
	 */
	private void build() {

		this.layout.setId("clipboard");
		this.layout.setGridLinesVisible(false);
		this.layout.setVgap(5);
		this.layout.setHgap(5);

		// Build UI Controls
		buildPasteButton();
		buildCutButton();
		buildCopyButton();

		// Add the "Paste" SplitMenuButton, Cut and Copy Buttons.
		this.layout.add(this.pasteButton, 0, 0, 1, 2); // Spans two rows, 1 col.
		this.layout.add(this.cutButton, 1, 0); // Row 0, Col 1.
		this.layout.add(this.copyButton, 1, 1); // Row 1, COl 1.

		Label label = new Label("Clipboard");
		label.getStyleClass().add("ribbonLabel");
		label.setTooltip(new Tooltip("Cut, Copy, Paste"));

		// TODO: find a better way to center a label.
		VBox vbox = new VBox();
		vbox.getChildren().add(label);
		VBox.setVgrow(label, Priority.ALWAYS);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setStyle("-fx-padding: 5 0 0 0");
		this.layout.add(vbox, 0, 2, 2, 1);

		// Center child nodes in VBox.
		this.root.setAlignment(Pos.CENTER);
		this.root.getChildren().add(layout);
		this.root.getStyleClass().add("toolbarContainer");

	}

	/**
	 * pasteButton. Helper method to build the SplitMenuButton.
	 */
	private void buildPasteButton() {

		// Create button and set text.
		this.pasteButton = new SplitMenuButton();
		this.pasteButton.setText("Paste");

		// Set alignment of button to text.
		this.pasteButton.setContentDisplay(ContentDisplay.TOP);

		// Retrieve and set image of clipboard. I will set image size to 24x24,
		// preserve the ratio and enable smoothing in the Image constructor.
		String imgPath = "/ui/common/images/clipboard.png";
		Image clipboard = new Image(this.getClass()
				.getResourceAsStream(imgPath), 24.0, 24.0, true, true);

		// Create an ImageView for showing image.
		ImageView imageView = new ImageView(clipboard);

		// Set the gap b/n graphic and text. Assign the ImageView to the button.
		this.pasteButton.setGraphicTextGap(5.0);
		this.pasteButton.setGraphic(imageView);

		// Paste Menu Item
		MenuItem mnuPaste = new MenuItem("Paste");
		mnuPaste.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Paste clicked.");
			}

		});

		// Paste Special Menu Item
		MenuItem mnuPasteSpecial = new MenuItem("Paste Special...");
		mnuPasteSpecial.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Paste Special clicked.");
			}

		});

		// Paste As Hyperlink Menu Item
		MenuItem mnuPasteHyperlink = new MenuItem("Paste as Hyperlink");
		mnuPasteHyperlink.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Paste as Hyperlink clicked.");
			}

		});

		// Add all MenuItems to the MenuSplitButton's menu options.
		this.pasteButton.getItems().addAll(mnuPaste, mnuPasteSpecial,
				mnuPasteHyperlink);

		// Set the click event of the Button itself. Note that the JavaDocs
		// points out that MenuItem click events are not transferred to the
		// Buttons click event. So button doesnt reflect the last menu option
		// selected in the drop down portion of the SplitMenuButton.
		this.pasteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				System.out.println("Button Clicked.");
			}
		});

	}

	/**
	 * cutButton. Helper method to build cut button.
	 */
	private void buildCutButton() {

		String imgPath = "/ui/common/images/cut.png";
		Image cut = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(cut);

		this.cutButton.setTooltip(new Tooltip("Cut"));
		this.cutButton.setGraphic(imageView);
		this.cutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Cut Button Clicked.");
			}
		});
	}

	/**
	 * copyButton. Helper method to build copy button.
	 */
	private void buildCopyButton() {

		String imgPath = "/ui/common/images/copy.png";
		Image copy = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(copy);

		this.copyButton.setTooltip(new Tooltip("Copy"));
		this.copyButton.setGraphic(imageView);
		this.copyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Copy Button Clicked.");
			}
		});
	}

}
