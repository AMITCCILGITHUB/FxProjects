import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CircularMenu extends Application {

	private String[] menuImages = { "car.png", "hotel.png", "workoffice.png",
			"university.png", "carrepair.png", "cycling.png", "airport.png",
			"doctor.png" };

	private List<MenuItem> menuItems = new ArrayList<>();

	private boolean open = false;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Radial Menu");
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500, Color.WHEAT);

		int index = 0;
		for (String menuImage : menuImages) {
			MenuItem menu = new LeafMenuItem(MenuUtils.MenuType.LEAF,
					menuImage, 250, 250, 24, index * 360 / menuImages.length);
			menuItems.add(menu);
			index++;
		}

		MenuItem centerMenu = new CenterMenuItem(MenuUtils.MenuType.CENTER,
				"home.png", 250, 250, 24, 0);
		menuItems.add(centerMenu);

		centerMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (open) {
					for (MenuItem menu : menuItems) {
						menu.startReverseAnimation();
						open = false;
					}
				} else {
					for (MenuItem menu : menuItems) {
						menu.startAnimation();
						open = true;
					}
				}
			}
		});

		root.getChildren().addAll(menuItems);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}

}
