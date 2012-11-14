import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;

public class MenuItemView extends Group {

	public MenuItemView(String menuImage, double centerX, double centerY,
			double radius) {

		Circle menuItem = CircleBuilder
				.create()
				.centerX(centerX)
				.centerY(centerY)
				.translateX(centerX - radius)
				.translateY(centerY - radius)
				.radius(radius)
				.fill(Color.TRANSPARENT)
				.style("-fx-fill: linear-gradient(#d6d6d6, #ffffff); -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 20, 0, 0, 0)")
				.build();

		ImageView menuImageView = ImageViewBuilder.create()
				.image(new Image(menuImage)).fitWidth(radius).fitHeight(radius)
				.smooth(true).preserveRatio(true).translateX(centerX - radius)
				.translateY(centerY - radius).build();

		getChildren().addAll(
				StackPaneBuilder.create().children(menuItem, menuImageView)
						.build());

	}
}
