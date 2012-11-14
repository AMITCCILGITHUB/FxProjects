import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafxpert.cube.FaceType;

public class PhotoFace extends Parent {

	public static double edgeLength = 256.0;
	public static double radius = (edgeLength * 0.5);

	SimpleDoubleProperty zPos = new SimpleDoubleProperty(0);

	public PhotoFace(FaceType type) {

		StackPane stackPane = StackPaneBuilder.create().prefWidth(edgeLength)
				.prefWidth(edgeLength).build();

		Rectangle2D viewPort = null;
		switch (type) {
			case FRONT:
				viewPort = new Rectangle2D(0, 0, edgeLength, edgeLength);
				break;
			case REAR:
				viewPort = new Rectangle2D(0, 0, edgeLength, edgeLength);
				break;
			case TOP:
				viewPort = new Rectangle2D(0, 0, edgeLength, edgeLength);
				break;
			case BOTTOM:
				viewPort = new Rectangle2D(0, 0, edgeLength, edgeLength);
				break;
			case LEFT:
				viewPort = new Rectangle2D(0, 0, edgeLength, edgeLength);
				break;
			case RIGHT:
				viewPort = new Rectangle2D(0, 0, edgeLength, edgeLength);
				break;
			default:
				break;

		}

		ImageView photoFace = ImageViewBuilder.create().viewport(viewPort)
				.image(new Image("image.jpg")).build();

		stackPane.getChildren().add(photoFace);
		this.getChildren().add(stackPane);

		zPos.addListener(new ChangeListener<Object>() {

			public void changed(ObservableValue<?> ov, Object oldValue,
					Object newValue) {

				// TODO
			}
		});
	}
}
