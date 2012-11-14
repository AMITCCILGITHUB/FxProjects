package ui.layouts.ribbonBar.tabs.home;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
 * Font. This class represents the Font Ribbon Bar Component.
 */
public class Font {

	private ToggleButton btnBold, btnItalics, btnUnderline, btnSuper, btnSub;
	private Button btnEraser, btnIncreaseFontSize, btnDecreaseFontSize;

	private ChoiceBox<String> cbxFontFamily;
	private ChoiceBox<Integer> cbxFontSize;
	private VBox root;

	/**
	 * Default Constructor.
	 */
	public Font() {

		this.btnBold = new ToggleButton();
		this.btnItalics = new ToggleButton();
		this.btnUnderline = new ToggleButton();
		this.btnSuper = new ToggleButton();
		this.btnSub = new ToggleButton();
		this.btnEraser = new Button();
		this.btnIncreaseFontSize = new Button();
		this.btnDecreaseFontSize = new Button();
		this.cbxFontFamily = new ChoiceBox<>();
		this.cbxFontSize = new ChoiceBox<>();
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

		// Set vertical spacing b/n ChoiceBox and ToggleButtons
		layout.setVgap(2);

		// Build UI Controls
		buildFontFamilyChoiceBox();
		buildFontSizeChoiceBox();
		buildBoldButton();
		buildItalicsButton();
		buildUnderlineButton();
		buildSuperScriptButton();
		buildSubScriptButton();
		buildEraserButton();
		buildIncreaseFontSizeButton();
		buildDecreaseFontSizeButton();

		// Group the Superscript and Subscript buttons into a ToggleGroup.
		ToggleGroup group = new ToggleGroup();
		group.getToggles().addAll(this.btnSuper, this.btnSub);

		// layout3 GridPane is for increase/decrease font size buttons.
		GridPane layout3 = new GridPane();
		layout3.add(this.btnIncreaseFontSize, 3, 0);
		layout3.add(this.btnDecreaseFontSize, 4, 0);

		// layout2 GridPane is for top row of choiceBoxes and layout3.
		GridPane layout2 = new GridPane();
		layout2.setHgap(5);
		layout2.add(this.cbxFontFamily, 0, 0);
		layout2.add(this.cbxFontSize, 1, 0);
		layout2.add(this.btnEraser, 2, 0);
		layout2.add(layout3, 3, 0);

		// Add All Componets to the GridPane.
		layout.add(layout2, 0, 0, 6, 1);
		layout.add(this.btnBold, 1, 1);
		layout.add(this.btnItalics, 2, 1);
		layout.add(this.btnUnderline, 3, 1);
		layout.add(this.btnSuper, 4, 1);
		layout.add(this.btnSub, 5, 1);

		// Build the Toolbar Container Label.
		Label label = new Label("Font");
		label.getStyleClass().add("ribbonLabel");
		label.setTooltip(new Tooltip("Specify font styles."));

		// TODO: find a better way to center a label.
		VBox vbox = new VBox();
		vbox.getChildren().add(label);
		VBox.setVgrow(label, Priority.ALWAYS);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setStyle("-fx-padding: 5 0 0 0");
		layout.add(vbox, 0, 2, 6, 1);

		// Center alignment in the VBox, add GridPane, set VBox CSS Selector.
		this.root.setAlignment(Pos.CENTER);
		this.root.getChildren().add(layout);
		this.root.getStyleClass().add("toolbarContainer");
	}

	/**
	 * buildFontFamilyChoiceBox. Helper method to add values to ChoiceBox and
	 * set ChangeListener.
	 */
	private void buildFontFamilyChoiceBox() {

		this.cbxFontFamily.getItems().addAll(
				FXCollections.observableArrayList("Arial", "Courier", "Tahoma",
						"Times New Roman", "Verdana", "Vivaldi", "Wide Latin"));

		// Select Verdana to show selection Model (Zero based seleted Index).
		this.cbxFontFamily.getSelectionModel().select(4);

		// Set ChoiceBox width to match the formatting buttons.
		this.cbxFontFamily.setMaxWidth(Double.MAX_VALUE);
		this.cbxFontFamily.setId("fontFamilyChoiceBox");

		// Proceed with caution here. The ChangeListener is new to me.
		this.cbxFontFamily.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Object>() {

					@Override
					public void changed(ObservableValue<?> value,
							final Object prevValue, final Object nextValue) {

						// Retrieve previous Selected Index (if necessary).
						final int oldValue = (int) prevValue;

						// Retrieve new Selected Index. ?
						final int currValue = (int) nextValue;

						final String oldFont = cbxFontFamily.getItems()
								.get(oldValue).toString();
						final String newFont = cbxFontFamily.getItems()
								.get(currValue).toString();
						System.out.printf(
								"The original font selection was: %s\n",
								oldFont);
						System.out.printf(
								"The current font selection is: %s\n", newFont);
					}

				});
	}

	/**
	 * buildFontSizeChoiceBox. Helper method to add values to ChoiceBox and set
	 * ChangeListener.
	 */
	private void buildFontSizeChoiceBox() {

		this.cbxFontSize.getItems().addAll(
				FXCollections.observableArrayList(8, 10, 12, 14, 16, 18, 24,
						36, 48, 72));

		// Select 12px to show selection Model (Zero based seleted Index).
		this.cbxFontSize.getSelectionModel().select(2);

		// Set CSS ID
		this.cbxFontSize.setId("fontSizeChoiceBox");

		// Proceed with caution here. The ChangeListener is new to me.
		this.cbxFontSize.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Object>() {

					@Override
					public void changed(ObservableValue<?> value,
							Object prevValue, Object nextValue) {

						// Retrieve previous Selected Index (if necessary).
						int oldValue = (int) prevValue;

						// Retrieve new Selected Index. ?
						int currValue = (int) nextValue;

						String oldFont = cbxFontSize.getItems().get(oldValue)
								.toString();
						String newFont = cbxFontSize.getItems().get(currValue)
								.toString();
						System.out.printf("The original font size was: %s\n",
								oldFont);
						System.out.printf("The current font size is: %s\n",
								newFont);
					}

				});
	}

	/**
	 * buildBoldButton. Helper method to build bold button.
	 */
	private void buildBoldButton() {

		String imgPath = "/ui/common/images/bold.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);
		this.btnBold.setGraphic(imageView);

		// Set CSS Styles. Since we added a CSS style selector, the coordinating
		// CSS styles are .ribbonToggleButton and .leftToggleButton.
		this.btnBold.getStyleClass().add("ribbonToggleButton");
		this.btnBold.getStyleClass().add("leftToggleButton");

		// Set Tooltip
		this.btnBold.setTooltip(new Tooltip("Bold"));

		// Set simple Click Event Handler.
		this.btnBold.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (btnBold.isSelected())
					System.out.println("Bold Style Selected.");
				else
					System.out.println("Bold Style Deselected.");

			}

		});
	}

	/**
	 * buildItalicsButton. Helper method to build italics button.
	 */
	private void buildItalicsButton() {

		String imgPath = "/ui/common/images/italics.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnItalics.setGraphic(imageView);
		this.btnItalics.getStyleClass().add("ribbonToggleButton");
		this.btnItalics.getStyleClass().add("middleToggleButton");
		this.btnItalics.setTooltip(new Tooltip("Italics"));
		this.btnItalics.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (btnItalics.isSelected())
					System.out.println("Italics Style Selected.");
				else
					System.out.println("Italics Style Deselected.");
			}

		});
	}

	/**
	 * buildUnderlineButton. Helper method to build underline button.
	 */
	private void buildUnderlineButton() {

		String imgPath = "/ui/common/images/underline.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnUnderline.setGraphic(imageView);
		this.btnUnderline.getStyleClass().add("ribbonToggleButton");
		this.btnUnderline.getStyleClass().add("middleToggleButton");
		this.btnUnderline.setTooltip(new Tooltip("Underline"));
		this.btnUnderline.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (btnUnderline.isSelected())
					System.out.println("Underline Style Selected.");
				else
					System.out.println("Underline Style Deselected.");
			}

		});
	}

	/**
	 * buildSuperScriptButton. Helper method to build superscript button.
	 */
	private void buildSuperScriptButton() {

		String imgPath = "/ui/common/images/superscript.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnSuper.setGraphic(imageView);
		this.btnSuper.getStyleClass().add("ribbonToggleButton");
		this.btnSuper.getStyleClass().add("middleToggleButton");
		this.btnSuper.setTooltip(new Tooltip("Superscript"));
		this.btnSuper.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (btnSuper.isSelected())
					System.out.println("SuperScript Style Selected.");
				else
					System.out.println("SuperScript Style Deselected.");
			}

		});
	}

	/**
	 * buildSubScriptButton. Helper method to build subscript button.
	 */
	private void buildSubScriptButton() {

		String imgPath = "/ui/common/images/subscript.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnSub.setGraphic(imageView);
		this.btnSub.getStyleClass().add("ribbonToggleButton");
		this.btnSub.getStyleClass().add("rightToggleButton");
		this.btnSub.setTooltip(new Tooltip("Subscript"));
		this.btnSub.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (btnSub.isSelected())
					System.out.println("SubScript Style Selected.");
				else
					System.out.println("SubScript Style Deselected.");
			}

		});
	}

	/**
	 * buildEraserButton. Helper method to build eraser button.
	 */
	private void buildEraserButton() {

		String imgPath = "/ui/common/images/eraser.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnEraser.setGraphic(imageView);
		this.btnEraser.getStyleClass().add("ribbonToggleButton");
		this.btnEraser.setTooltip(new Tooltip("Clear Font Styles"));
		this.btnEraser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Clear Font Styles Button Selected.");
			}

		});
	}

	/**
	 * buildIncreaseFontSizeButton. Helper method to build increase font size
	 * button.
	 */
	private void buildIncreaseFontSizeButton() {

		String imgPath = "/ui/common/images/increaseFont.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnIncreaseFontSize.setGraphic(imageView);
		this.btnIncreaseFontSize.getStyleClass().add("ribbonToggleButton");
		this.btnIncreaseFontSize.getStyleClass().add("leftToggleButton");
		this.btnIncreaseFontSize.setTooltip(new Tooltip("Increase Font Size"));
		this.btnIncreaseFontSize.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Increase Font Size Button Selected.");
			}

		});
	}

	/**
	 * buildDecreaseFontSizeButton. Helper method to build decrease font size
	 * button.
	 */
	private void buildDecreaseFontSizeButton() {

		String imgPath = "/ui/common/images/decreaseFont.png";
		Image image = new Image(this.getClass().getResourceAsStream(imgPath),
				16.0, 16.0, true, true);
		ImageView imageView = new ImageView(image);

		this.btnDecreaseFontSize.setGraphic(imageView);
		this.btnDecreaseFontSize.getStyleClass().add("ribbonToggleButton");
		this.btnDecreaseFontSize.getStyleClass().add("rightToggleButton");
		this.btnDecreaseFontSize.setTooltip(new Tooltip("Decrease Font Size"));
		this.btnDecreaseFontSize.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.out.println("Decrease Font Size Button Selected.");
			}

		});
	}

}
