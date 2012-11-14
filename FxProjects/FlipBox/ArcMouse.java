import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class ArcMouse extends Application {

	@Override
	public void start(Stage primaryStage) {
		Pane pane = new Pane();

		Group designer = createDesigner();
		designer.setLayoutX(100);
		designer.setLayoutY(200);
		pane.getChildren().add(designer);

		Scene sc = new Scene(pane, 600, 600);
		primaryStage.setScene(sc);
		primaryStage.show();
	}

	public static final double RX = 100;
	public static final double RY = 50;
	public static final double S_ANGLE = 45;
	public static final double ARC_LENGTH = 90;

	private Arc arc;
	private Circle handle;
	private Line connection;
	double xMouse, yMouse;

	public Group createDesigner() {

		arc = new Arc();
		arc.setRadiusX(RX);
		arc.setRadiusY(RY);
		arc.setStartAngle(S_ANGLE);
		arc.setLength(ARC_LENGTH);
		arc.setFill(Color.LIGHTBLUE);
		arc.setType(ArcType.ROUND);

		handle = new Circle();
		handle.setRadius(5);
		handle.setStroke(Color.BLACK);
		handle.setFill(Color.TRANSPARENT);

		handle.setCenterX(RX * Math.cos(Math.toRadians(S_ANGLE)));
		handle.setCenterY(-RY * Math.cos(Math.toRadians(S_ANGLE)));

		connection = new Line();
		connection.startXProperty().bind(arc.centerXProperty());
		connection.startYProperty().bind(arc.centerYProperty());
		connection.endXProperty().bind(handle.centerXProperty());
		connection.endYProperty().bind(handle.centerYProperty());

		handle.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				xMouse = event.getX();
				yMouse = event.getY();

				handle.setCenterX(xMouse);
				handle.setCenterY(yMouse);

				double angleInRadians = Math.atan2(-yMouse, xMouse);

				arc.setStartAngle(Math.toDegrees(angleInRadians));

			}

		});

		return new Group(arc, connection, handle);

	}

	public static void main(String[] args) {
		launch(args);
	}
}