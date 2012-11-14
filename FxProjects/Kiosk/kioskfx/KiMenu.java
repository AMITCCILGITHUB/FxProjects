package kioskfx;

import java.util.Vector;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//~--- JDK imports ------------------------------------------------------------

public class KiMenu {

	private SimpleIntegerProperty currentSection;
	private SimpleDoubleProperty currentShift;
	private SimpleObjectProperty<Color> fogColor;
	private SimpleDoubleProperty height;
	private SimpleDoubleProperty iconHeight;
	private SimpleDoubleProperty iconWidth;
	// private ImageView imageView;
	private Text label;
	private SimpleDoubleProperty leftMargin;
	private Node node;
	private Vector<KiSection> sections;
	private Group sectionsGroup;
	private Group actionsGroup;
	private KiBackground background;
	private SimpleDoubleProperty topMargin;
	private SimpleDoubleProperty width;
	private Image defaultImage;
	private SimpleObjectProperty<Color> itemColor;
	private SimpleObjectProperty<Color> titleColor;
	// Polygon polygon;
	Rectangle fog;
	private SimpleDoubleProperty itemHeight;
	private int titleSize;
	private int itemSize;

	public KiMenu() {

		width = new SimpleDoubleProperty(900);
		height = new SimpleDoubleProperty(600);
		fogColor = new SimpleObjectProperty<Color>(Color.web("#000000"));
		iconWidth = new SimpleDoubleProperty(150);
		iconHeight = new SimpleDoubleProperty(120);
		leftMargin = new SimpleDoubleProperty(250);
		topMargin = new SimpleDoubleProperty(100);
		currentSection = new SimpleIntegerProperty(-1);
		currentShift = new SimpleDoubleProperty(0.0);
		itemColor = new SimpleObjectProperty<Color>(Color.web("#ffffff"));
		titleColor = new SimpleObjectProperty<Color>(Color.web("#ffffff"));
		Group root = new Group();
		titleSize = 99;
		itemSize = 64;
		itemHeight = new SimpleDoubleProperty(70);
		/*
		 * Rectangle t = new Rectangle(); t.setFill(Color.web("#330000"));
		 * t.widthProperty().bind(width); t.heightProperty().bind(height);
		 * root.getChildren().add(t);
		 */
		sections = new Vector<KiSection>();

		// Image backgroundImage = new
		// Image(this.getClass().getResourceAsStream("background.jpg"));
		// KiBackground ff=new
		// KiBackground().height(height).width(width).image(backgroundImage);
		// imageView = new ImageView();
		// imageView.setImage(backgroundImage);
		// defaultImageView.fitWidthProperty().bind(width);
		// imageView.setPreserveRatio(true);
		defaultImage = new Image(this.getClass().getResourceAsStream(
				"background.jpg"));
		background = new KiBackground().height(height).width(width)
				.image(defaultImage);
		root.getChildren().add(background.node());

		fog = new Rectangle();

		fog.widthProperty().bind(width);
		fog.heightProperty().bind(height.multiply(2));
		fog.translateYProperty().bind(height.divide(-2.0));
		fog.setFill(getFogFill());
		fog.setMouseTransparent(true);
		root.getChildren().add(fog);
		label = new Text();
		// Font font =
		// Font.loadFont(this.getClass().getResourceAsStream("font.ttf"), 99);
		label.setFont(Font.loadFont(
				this.getClass().getResourceAsStream("font.ttf"), titleSize));
		// new Font("Verdana", 90));

		// label.setTranslateX(300);
		label.setTranslateY(0);
		label.setTextOrigin(VPos.TOP);

		// label.setText("Text label");
		// label.setFill(Color.web("#ffffff66"));
		label.fillProperty().bind(titleColor);
		label.translateXProperty().bind(leftMargin);
		root.getChildren().add(label);
		/*
		 * polygon = new Polygon(); Stop[] st = new Stop[]{new Stop(0,
		 * Color.web("#ffffff33")), new Stop(1, Color.web("#ffffff00"))};
		 * LinearGradient gr = new LinearGradient(0, 0, 0, 0.5, true,
		 * CycleMethod.NO_CYCLE, st); polygon.setFill(gr);
		 */
		// root.getChildren().add(polygon);

		sectionsGroup = new Group();
		sectionsGroup.translateXProperty().bind(
				iconWidth.add(16).multiply(currentShift).negate()
						.add(leftMargin));
		actionsGroup = new Group();
		actionsGroup.translateXProperty().bind(leftMargin);
		actionsGroup.translateYProperty().bind(
				topMargin.add(iconHeight).add(16));
		// currentSection.multiply(sectionWidth).
		// leftMargin);
		sectionsGroup.translateYProperty().bind(topMargin);
		root.getChildren().add(sectionsGroup);
		root.getChildren().add(actionsGroup);
		addWatchers();

		// currentSection.set(0);

		Rectangle clip = new Rectangle();

		clip.widthProperty().bind(width);
		clip.heightProperty().bind(height);
		root.setClip(clip);
		node = root;
	}

	public static String version() {

		return "v1.2.1 for JavaFX v40";
	}

	public KiMenu itemHeight(double it) {

		itemHeight.set(it);
		return this;
	}

	public double itemHeight() {

		return itemHeight.get();

	}

	public KiMenu itemSize(int it) {

		itemSize = it;
		for (int i = 0; i < this.sections.size(); i++) {
			this.sections.get(i).itemSize(it);
		}
		return this;
	}

	public int itemSize() {

		return itemSize;

	}

	public KiMenu image(Image it) {

		if (it == null) {
			background.image(defaultImage);
		} else {
			background.image(it);
		}
		return this;
	}

	public KiMenu itemColor(Color it) {

		itemColor.set(it);
		return this;
	}

	public KiMenu titleSize(int it) {

		titleSize = it;
		label.setFont(Font.loadFont(
				this.getClass().getResourceAsStream("font.ttf"), titleSize));
		return this;
	}

	public int titleSize() {

		return titleSize;
	}

	public KiMenu titleColor(Color it) {

		titleColor.set(it);
		return this;
	}

	public KiMenu fog(Color it) {

		if (it == null) {
			fogColor.set(Color.web("#000000"));
		} else {
			fogColor.set(it);
		}
		fog.setFill(getFogFill());
		return this;
	}

	public KiMenu currentSection(IntegerProperty it) {

		// System.out.println(it);
		// this.currentSection.bindBidirectional(it);
		/*
		 * it.addListener(new ChangeListener () {
		 * 
		 * public void invalidated(javafx.beans.Observable observable) {
		 * System.out.println(observable); }
		 * 
		 * public void changed(ObservableValue observable, Object oldValue,
		 * Object newValue) { System.out.println(observable); } });
		 */
		return this;
	}

	private Paint getFogFill() {

		Color dark = new Color(fogColor.get().getRed(), fogColor.get()
				.getGreen(), fogColor.get().getBlue(), 1.0);
		Color light = new Color(fogColor.get().getRed(), fogColor.get()
				.getGreen(), fogColor.get().getBlue(), 0.5);
		Stop[] stops = new Stop[] { new Stop(0, light), new Stop(1, dark) };
		RadialGradient gr = new RadialGradient(0.0, 0.0, 0.5, 0.5, 0.5, true,
				CycleMethod.NO_CYCLE, stops);

		return gr;
	}

	private void setFar() {

		// System.out.println("far");
		double rtsz = 1.0 / ((0.0 + width.get() - leftMargin.get()) / iconWidth
				.get());
		double ltsz = 1.0 / ((0.0 + leftMargin.get() + iconWidth.get()) / iconWidth
				.get());
		for (int i = 0; i < sections.size(); i++) {
			if (i != this.currentSection.get()) {
				sections.get(i).active(false);
				int diff = currentSection.get() - i;
				if (diff < 0) { // right
					double rOpcty = 1.0 + diff * rtsz;
					if (rOpcty < 0) {
						rOpcty = 0;
					}
					if (rOpcty > 1) {
						rOpcty = 1;
					}
					sections.get(i).opacity(0.5 * rOpcty);
				} else {
					// if (diff > 0) { // left
					double lOpcty = 1.0 - diff * ltsz;
					if (lOpcty < 0) {
						lOpcty = 0;
					}
					if (lOpcty > 1) {
						lOpcty = 1;
					}
					sections.get(i).opacity(0.5 * lOpcty);
					// } else {
					// sections.get(i).opacity(1.0);
					// }
				}
			} else {
				sections.get(i).active(true);
			}
		}
	}

	void addWatchers() {

		width.addListener(new InvalidationListener() {

			public void invalidated(javafx.beans.Observable observable) {

				adjust();
			}
		});
		height.addListener(new InvalidationListener() {

			public void invalidated(javafx.beans.Observable observable) {

				adjust();
			}
		});

		currentSection.addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {

				// System.out.println("changed "+oldValue+" to "+newValue);
				showCurrent();
			}
		});
	}

	public Node node() {

		adjust();

		return node;
	}

	void adjust() {

		// double imageWidth = imageView.getBoundsInLocal().getWidth();
		// double imageHeight = imageView.getBoundsInLocal().getHeight();
		// double scaleWidth = width.get() /
		// imageView.getBoundsInLocal().getWidth();
		// double scaleHeight = height.get() /
		// imageView.getBoundsInLocal().getHeight();

		/*
		 * if (width.get() / imageView.getBoundsInLocal().getWidth() >
		 * height.get() / imageView.getBoundsInLocal().getHeight()) {
		 * imageView.setFitHeight(0); imageView.setFitWidth(width.get()); } else
		 * { imageView.setFitWidth(0); imageView.setFitHeight(height.get()); }
		 */
		// System.out.println(scaleWidth + "/" + scaleHeight);
		// polygon.getPoints().clear();
		// polygon.getPoints().addAll(new Double[]{0.0, 0.0, width.get()/3, 0.0,
		// width.get()/5, height.get(),0.0, height.get()});
	}

	public KiMenu width(ReadOnlyDoubleProperty nn) {

		this.width.bind(nn);
		adjust();

		return this;
	}

	public KiMenu iconWidth(double nn) {

		this.iconWidth.set(nn);

		// adjust();
		return this;
	}

	public KiMenu leftMargin(double nn) {

		this.leftMargin.set(nn);

		// adjust();
		return this;
	}

	public double leftMargin() {

		return leftMargin.get();
	}

	public double topMargin() {

		return topMargin.get();
	}

	public KiMenu topMargin(double nn) {

		this.topMargin.set(nn);

		// adjust();
		return this;
	}

	public KiMenu iconHeight(double nn) {

		this.iconHeight.set(nn);

		// adjust();
		return this;
	}

	public double iconHeight() {

		return iconHeight.get();
	}

	public double iconWidth() {

		return iconWidth.get();
	}

	public KiMenu height(ReadOnlyDoubleProperty nn) {

		this.height.bind(nn);
		adjust();

		return this;
	}

	private void showCurrent() {

		// System.out.println("show "+currentSection.get());
		if (sections.size() > currentSection.get()) {
			label.setText(sections.get(currentSection.get()).title());
			this.setFar();
			moveToCurrent();

		}
	}

	private void moveToCurrent() {

		final double delta = (currentShift.get() - currentSection.get()) / 10.0;
		KiTimeline kt = new KiTimeline().duration(500);
		KiJob j = new KiJob() {

			@Override
			public void start() {

				currentShift.set(currentShift.get() - delta);
				actionsGroup.setOpacity(actionsGroup.getOpacity() - 0.1);
			}
		};
		for (int i = 0; i < 10; i++) {
			kt.job(j);
			// System.out.println(this.actionsGroup.getOpacity());

		}
		KiJob lj = new KiJob() {

			@Override
			public void start() {

				currentShift.set(currentSection.get());
				showActions();
			}
		};
		kt.job(lj);
		kt.start();
	}

	private void showActions() {

		this.actionsGroup.getChildren().clear();
		this.actionsGroup.setOpacity(1.0);
		KiSection s = this.sections.get(currentSection.get());
		for (int i = 0; i < s.actions().size(); i++) {

			KiAction a = s.actions().get(i);
			this.actionsGroup.getChildren().add(a.node());
			// System.out.println(a.title());
		}
	}

	public KiMenu section(KiSection it) {

		// this.width.bind(nn);
		// adjust();
		final int n = sections.size();
		KiJob job = new KiJob() {

			@Override
			public void start() {

				// System.out.println("set "+n+": "+currentSection.get());
				currentSection.set(n);
				// System.out.println("now "+currentSection.get());

			}
		};
		it.itemSize(itemSize);
		it.itemColor(itemColor);
		it.itemHeight(itemHeight);
		it.order(sections.size()).width(iconWidth).height(iconHeight)
				.onSelect(job);

		// it.image(new
		// Image(this.getClass().getResourceAsStream("section.png")));
		sections.add(it);
		sectionsGroup.getChildren().add(it.node());

		// move(0);
		currentSection.set(0);
		this.setFar();
		return this;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
