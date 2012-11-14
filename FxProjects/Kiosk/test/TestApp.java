package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kioskfx.KiAction;
import kioskfx.KiJob;
import kioskfx.KiMenu;
import kioskfx.KiSection;

public class TestApp extends Application {

	KiMenu menu;

	public static void main(String[] args) {

		Application.launch(TestApp.class, args);
	}

	void alert(String msg) {

		javax.swing.JOptionPane.showMessageDialog(null, msg, "KiskFX",
				javax.swing.JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Kiosk component demo");
		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.web("#333333"));
		primaryStage.setWidth(1000);
		primaryStage.setHeight(700);
		menu = new KiMenu()
				.width(primaryStage.widthProperty())
				.height(primaryStage.heightProperty())
				.section(
						new KiSection()
								.title("Information")
								.image(new Image(this.getClass()
										.getResourceAsStream("info.png")))
								.action(new KiAction().title("About component")
										.onSelect(new KiJob() {

											@Override
											public void start() {

												alert("Component version is "
														+ menu.version());
											}
										}))
								.action(new KiAction().title("About author")
										.onSelect(new KiJob() {

											@Override
											public void start() {

												alert("Sergey Surikov\nsee http://www.javafx.me");
											}
										})))
				.section(
						new KiSection()
								.title("Background")
								.image(new Image(this.getClass()
										.getResourceAsStream("image.png")))
								.action(new KiAction()
										.title("Default")
										.image(new Image(this.getClass()
												.getResourceAsStream(
														"refresh.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.image(null);
											}
										}))
								.action(new KiAction().title("City").onSelect(
										new KiJob() {

											@Override
											public void start() {

												menu.image(new Image(this
														.getClass()
														.getResourceAsStream(
																"night.jpg")));
											}
										}))
								.action(new KiAction().title("Ancient")
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.image(new Image(this
														.getClass()
														.getResourceAsStream(
																"egypt.jpg")));
											}
										}))
								.action(new KiAction().title("Nature")
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.image(new Image(this
														.getClass()
														.getResourceAsStream(
																"tree.jpg")));
											}
										})))
				.section(
						new KiSection()
								.title("Kiosk color")
								.image(new Image(this.getClass()
										.getResourceAsStream("color.png")))
								.action(new KiAction()
										.title("Default")
										.image(new Image(this.getClass()
												.getResourceAsStream(
														"refresh.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.fog(null);
											}
										}))
								.action(new KiAction().title("Blue").onSelect(
										new KiJob() {

											@Override
											public void start() {

												menu.fog(Color.web("#000033"));
											}
										}))
								.action(new KiAction().title("Green").onSelect(
										new KiJob() {

											@Override
											public void start() {

												menu.fog(Color.web("#003300"));
											}
										})))
				.section(
						new KiSection()
								.title("Position")
								.image(new Image(this.getClass()
										.getResourceAsStream("other.png")))
								.action(new KiAction()
										.title("Decrease left margin")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.leftMargin(menu
														.leftMargin() - 10);
											}
										}))
								.action(new KiAction()
										.title("Increase left margin")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.leftMargin(menu
														.leftMargin() + 10);
											}
										}))
								.action(new KiAction()
										.title("Decrease top margin")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.topMargin(menu.topMargin() - 10);
											}
										}))
								.action(new KiAction()
										.title("Increase top margin")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.topMargin(menu.topMargin() + 10);
											}
										})))
				.section(
						new KiSection()
								.title("Icon size")
								.image(new Image(this.getClass()
										.getResourceAsStream("size.png")))
								.action(new KiAction()
										.title("Decrease width")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.iconWidth(menu.iconWidth() - 10);
											}
										}))
								.action(new KiAction()
										.title("Increase width")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.iconWidth(menu.iconWidth() + 10);
											}
										}))
								.action(new KiAction()
										.title("Decrease height")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.iconHeight(menu
														.iconHeight() - 10);
											}
										}))
								.action(new KiAction()
										.title("Increase height")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.iconHeight(menu
														.iconHeight() + 10);
											}
										})))
				.section(
						new KiSection()
								.title("Item color")
								.image(new Image(this.getClass()
										.getResourceAsStream("color.png")))
								.action(new KiAction()
										.title("Default")
										.image(new Image(this.getClass()
												.getResourceAsStream(
														"refresh.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.itemColor(Color
														.web("#ffffff"));
											}
										}))
								.action(new KiAction().title("Yellow")
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.itemColor(Color
														.web("#ffff99"));
											}
										}))
								.action(new KiAction().title("Cyan").onSelect(
										new KiJob() {

											@Override
											public void start() {

												menu.itemColor(Color
														.web("#99ffff"));
											}
										})))
				.section(
						new KiSection()
								.title("Items size")
								.image(new Image(this.getClass()
										.getResourceAsStream("size.png")))
								.action(new KiAction()
										.title("Decrease height")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.itemHeight(menu
														.itemHeight() - 10);
											}
										}))
								.action(new KiAction()
										.title("Increase height")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.itemHeight(menu
														.itemHeight() + 10);
											}
										})))
				.section(
						new KiSection()
								.title("Title color")
								.image(new Image(this.getClass()
										.getResourceAsStream("color.png")))
								.action(new KiAction()
										.title("Default")
										.image(new Image(this.getClass()
												.getResourceAsStream(
														"refresh.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.titleColor(Color
														.web("#ffffff"));
											}
										}))
								.action(new KiAction().title("Yellow")
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.titleColor(Color
														.web("#ffff99"));
											}
										}))
								.action(new KiAction().title("Cyan").onSelect(
										new KiJob() {

											@Override
											public void start() {

												menu.titleColor(Color
														.web("#99ffff"));
											}
										})))
				.section(
						new KiSection()
								.title("Font size")
								.image(new Image(this.getClass()
										.getResourceAsStream("font.png")))
								.action(new KiAction()
										.title("Decrease title size")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.titleSize(menu.titleSize() - 3);
											}
										}))
								.action(new KiAction()
										.title("Increase title size")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.titleSize(menu.titleSize() + 3);
											}
										}))
								.action(new KiAction()
										.title("Decrease item size")
										.image(new Image(
												this.getClass()
														.getResourceAsStream(
																"down.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.itemSize(menu.itemSize() - 3);
											}
										}))
								.action(new KiAction()
										.title("Increase item size")
										.image(new Image(this.getClass()
												.getResourceAsStream("up.png")))
										.onSelect(new KiJob() {

											@Override
											public void start() {

												menu.itemSize(menu.itemSize() + 3);
											}
										})));
		root.getChildren().add(menu.node());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
