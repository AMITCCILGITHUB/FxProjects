package kioskfx;

import javafx.application.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.animation.*;
import javafx.util.*;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.geometry.*;
import javafx.scene.transform.*;
import javafx.scene.effect.*;
import javafx.scene.text.*;

public class KiBackground {

    private SimpleDoubleProperty height;
    private SimpleDoubleProperty width;
    //private SimpleDoubleProperty x;
    //private SimpleDoubleProperty y;
    private ImageView imageView;
    private Group root;
    //private double wave;

    public KiBackground() {
        //wave = 32.0;
        width = new SimpleDoubleProperty(300);
        height = new SimpleDoubleProperty(200);
        //x = new SimpleDoubleProperty(0);
        //y = new SimpleDoubleProperty(100);
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        //imageView.translateXProperty().bind(height);
        root = new Group();
        root.getChildren().add(imageView);
        Rectangle clip = new Rectangle();

        clip.widthProperty().bind(width);//.subtract(wave));
        clip.heightProperty().bind(height);//.subtract(wave));
        root.setClip(clip);
        addWatchers();
        /*root.setOnMousePressed(new EventHandler<MouseEvent>() {
        
        @Override
        public void handle(MouseEvent event) {
        System.out.println(                        event);
        }
        });*/
        /*imageView.setOnMouseMoved(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet.");
                System.out.println(event.getSceneX());
                //double x=event.getX();
                //double y=event.getY();
                //System.out.println(  x+" : "+y);
                imageView.setTranslateX(-wave * event.getX() / width.get());
                imageView.setTranslateY(-wave * event.getY() / height.get());
            }
        });*/
    }

    private void addWatchers() {
        width.addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                adjust();
            }
        });
        height.addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                adjust();
            }
        });
    }

    private void adjust() {
        if (width.get() / imageView.getBoundsInLocal().getWidth() > height.get() / imageView.getBoundsInLocal().getHeight()) {
            imageView.setFitHeight(0);
            imageView.setFitWidth(width.get());
        } else {
            imageView.setFitWidth(0);
            imageView.setFitHeight(height.get());
        }
    }

    public KiBackground width(DoubleProperty nn) {
        width.bind(nn);//.add(wave));
        return this;
    }

    public KiBackground height(DoubleProperty nn) {
        height.bind(nn);//.add(wave));
        return this;
    }

    public KiBackground image(Image it) {
        imageView.setImage(it);
        return this;
    }

    public Node node() {
        return root;
    }
}
