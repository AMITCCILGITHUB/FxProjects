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

public class KiAction {
    
    private String title;
    private Node node;
    Text label;
    private SimpleDoubleProperty order;
    private ImageView imageView;
    private SimpleDoubleProperty height;
    private SimpleDoubleProperty width;
    private int size;
    //private double margin = 0;
    private KiJob onSelect;
    private SimpleDoubleProperty opacity;
    private SimpleObjectProperty<Color> itemColor;
    
    public KiAction() {
	title = "123";
	itemColor = new SimpleObjectProperty<Color>(Color.web("#ffffff"));
	imageView = new ImageView();
	order = new SimpleDoubleProperty(0);
	height = new SimpleDoubleProperty(70);
	width = new SimpleDoubleProperty(270);
        size = 67;
	opacity = new SimpleDoubleProperty(0.5);
	//margin = height.get() + 8;
	
	Group root = new Group();
	label = new Text();
	label.setText(title);
	//label.setFill(Color.web("#ffffff"));
	label.fillProperty().bind(itemColor);
	//label.setTextOrigin(VPos.TOP);
	//Font font = Font.loadFont(this.getClass().getResourceAsStream("font2.ttf"), 70);
	label.setFont(Font.loadFont(this.getClass().getResourceAsStream("font2.ttf"), size));
	label.translateYProperty().bind(height.add(4).multiply(order).add(height));
	//label.setTranslateX(margin);
	label.translateXProperty().bind(height.add(16));
	
	imageView.translateYProperty().bind(height.add(4).multiply(order));
	imageView.setPreserveRatio(true);
	imageView.setFitWidth(0);
	imageView.fitHeightProperty().bind(height);
	
	label.opacityProperty().bind(opacity);
	imageView.opacityProperty().bind(opacity);

	//margin=4+imageView.get.boundsInLocalProperty()..getBoundsInLocal().getWidth();
	//.setFitHeight(height.get());
	image(new Image(this.getClass().getResourceAsStream("item.png")));
	
	Rectangle r = new Rectangle();
	r.setFill(Color.web("#ffffff01"));
	r.setWidth(200);
	r.heightProperty().bind(height);
	r.translateYProperty().bind(height.add(4).multiply(order));
	r.widthProperty().bind(width);
	
	root.getChildren().add(r);
	root.getChildren().add(imageView);
	root.getChildren().add(label);
	
	root.setOnMousePressed(new EventHandler<MouseEvent>() {
	    
	    @Override
	    public void handle(MouseEvent event) {
		if (onSelect != null) {
		    //opacity.set(0.1);
		    new KiTimeline().job(new KiJob() {

			@Override
			public void start() {
			    opacity.set(0.1);
			}
		    }).job(new KiJob() {

			@Override
			public void start() {
			    setHover();
			    onSelect.start();			    
			}
		    }).duration(100).start();
		}
	    }
	});
	onSelect = new KiJob();
	root.hoverProperty().addListener(new ChangeListener<Boolean>() {
	    
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		//adjust();
		setHover();
	    }
	});
	
	node = root;
	
    }
    
    private void setHover() {
	if (node.isHover()) {
	    opacity.set(1.0);
	} else {
	    opacity.set(0.5);
	}
    }
    
    public String title() {
	return title;
    }
    
    public KiAction onSelect(KiJob it) {
	onSelect = it;
	return this;
    }

    public KiAction height(SimpleDoubleProperty it) {
	height.bind(it);
	return this;
    }

    public KiAction itemColor(SimpleObjectProperty<Color> it) {
	itemColor.bind(it);
	return this;
    }
    
    
    public KiAction itemSize(int it) {
	size=it;
        label.setFont(Font.loadFont(this.getClass().getResourceAsStream("font2.ttf"), size));
	return this;
    }
    
    public KiAction image(Image it) {
	//order.set(it);

	
	imageView.setImage(it);
	//glass.content(imageView);
	return this;
    }
    
    public KiAction title(String it) {
	this.title = it;
	label.setText(title);
	return this;
    }
    
    public KiAction order(int it) {
	this.order.set(it);
	return this;
    }
    
    public Node node() {
	return node;
    }
}
