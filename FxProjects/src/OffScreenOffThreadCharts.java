import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;

public class OffScreenOffThreadCharts extends Application {

	private static final String CHART_FILE_PREFIX = "chart_";
	private static final String WORKING_DIR = System.getProperty("user.dir");

	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"HH:mm:ss.SSS");
	private final Random random = new Random();

	private final int N_CHARTS = 300;
	private final int PREVIEW_SIZE = 600;
	private final int CHART_SIZE = 600;

	final ExecutorService saveChartsExecutor = createExecutor("SaveCharts");

	@Override
	public void start(Stage stage) throws IOException {

		stage.setTitle("Chart Export Sample");

		final SaveChartsTask saveChartsTask = new SaveChartsTask(N_CHARTS);

		final VBox layout = new VBox(10);
		layout.getChildren().addAll(createProgressPane(saveChartsTask),
				createChartImagePagination(saveChartsTask));
		layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 15;");

		stage.setOnCloseRequest(new EventHandler() {

			@Override
			public void handle(Event event) {

				saveChartsTask.cancel();
			}
		});

		stage.setScene(new Scene(layout));
		stage.show();

		saveChartsExecutor.execute(saveChartsTask);
	}

	@Override
	public void stop() throws Exception {

		saveChartsExecutor.shutdown();
		saveChartsExecutor.awaitTermination(5, TimeUnit.SECONDS);
	}

	private Pagination createChartImagePagination(
			final SaveChartsTask saveChartsTask) {

		final Pagination pagination = new Pagination(N_CHARTS);
		pagination.setMinSize(PREVIEW_SIZE + 100, PREVIEW_SIZE + 100);
		pagination.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(final Integer chartNumber) {

				final StackPane page = new StackPane();
				page.setStyle("-fx-background-color: antiquewhite;");

				if (chartNumber < saveChartsTask.getWorkDone()) {
					page.getChildren().setAll(
							createImageViewForChartFile(chartNumber));
				} else {
					ProgressIndicator progressIndicator = new ProgressIndicator();
					progressIndicator.setMaxSize(PREVIEW_SIZE * 1 / 4,
							PREVIEW_SIZE * 1 / 4);
					page.getChildren().setAll(progressIndicator);

					final ChangeListener<Number> WORK_DONE_LISTENER = new ChangeListener<Number>() {

						@Override
						public void changed(
								ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue) {

							if (chartNumber < saveChartsTask.getWorkDone()) {
								page.getChildren()
										.setAll(createImageViewForChartFile(chartNumber));
								saveChartsTask.workDoneProperty()
										.removeListener(this);
							}
						}
					};

					saveChartsTask.workDoneProperty().addListener(
							WORK_DONE_LISTENER);
				}

				return page;
			}
		});

		return pagination;
	}

	private ImageView createImageViewForChartFile(Integer chartNumber) {

		ImageView imageView = new ImageView(new Image("file:///"
				+ getChartFilePath(chartNumber)));
		imageView.setFitWidth(PREVIEW_SIZE);
		imageView.setPreserveRatio(true);
		return imageView;
	}

	private Pane createProgressPane(SaveChartsTask saveChartsTask) {

		GridPane progressPane = new GridPane();

		progressPane.setHgap(5);
		progressPane.setVgap(5);
		progressPane.addRow(0, new Label("Create:"),
				createBoundProgressBar(saveChartsTask
						.chartsCreationProgressProperty()));
		progressPane.addRow(1, new Label("Snapshot:"),
				createBoundProgressBar(saveChartsTask
						.chartsSnapshotProgressProperty()));
		progressPane.addRow(2, new Label("Save:"),
				createBoundProgressBar(saveChartsTask
						.imagesExportProgressProperty()));
		progressPane.addRow(
				3,
				new Label("Processing:"),
				createBoundProgressBar(Bindings
						.when(saveChartsTask.stateProperty().isEqualTo(
								Worker.State.SUCCEEDED))
						.then(new SimpleDoubleProperty(1))
						.otherwise(
								new SimpleDoubleProperty(
										ProgressBar.INDETERMINATE_PROGRESS))));

		return progressPane;
	}

	private ProgressBar createBoundProgressBar(NumberExpression progressProperty) {

		ProgressBar progressBar = new ProgressBar();
		progressBar.setMaxWidth(Double.MAX_VALUE);
		progressBar.progressProperty().bind(progressProperty);
		GridPane.setHgrow(progressBar, Priority.ALWAYS);
		return progressBar;
	}

	class ChartsCreationTask extends Task<Void> {

		private final int nCharts;
		private final BlockingQueue<Parent> charts;

		ChartsCreationTask(BlockingQueue<Parent> charts, final int nCharts) {

			this.charts = charts;
			this.nCharts = nCharts;
			updateProgress(0, nCharts);
		}

		@Override
		protected Void call() throws Exception {

			int i = nCharts;
			while (i > 0) {
				if (isCancelled()) {
					break;
				}
				charts.put(createChart());
				i--;
				updateProgress(nCharts - i, nCharts);
			}

			return null;
		}

		private Parent createChart() {

			final Pane chartContainer = new Pane();
			try {
				// create a chart.
				final PieChart chart = new PieChart();
				ObservableList<PieChart.Data> pieChartData = FXCollections
						.observableArrayList(new PieChart.Data("Grapefruit",
								random.nextInt(30)), new PieChart.Data(
								"Oranges", random.nextInt(30)),
								new PieChart.Data("Plums", random.nextInt(30)),
								new PieChart.Data("Pears", random.nextInt(30)),
								new PieChart.Data("Apples", random.nextInt(30)));
				chart.setData(pieChartData);
				chart.setTitle("Imported Fruits - "
						+ dateFormat.format(new Date()));

				// Place the chart in a container pane.
				chartContainer.getChildren().add(chart);
				chart.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				chart.setPrefSize(CHART_SIZE, CHART_SIZE);
				chart.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
				chart.setStyle("-fx-font-size: 16px;");

				TextField txt = new TextField("Test");
				Tooltip tip = new Tooltip("Sample");
				txt.setTooltip(tip);
				chartContainer.getChildren().add(txt);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return chartContainer;
		}
	}

	class ChartsSnapshotTask extends Task<Void> {

		private final int nCharts;
		private final BlockingQueue<Parent> charts;
		private final BlockingQueue<BufferedImage> images;

		ChartsSnapshotTask(BlockingQueue<Parent> charts,
				BlockingQueue<BufferedImage> images, final int nCharts) {

			this.charts = charts;
			this.images = images;
			this.nCharts = nCharts;
			updateProgress(0, nCharts);
		}

		@Override
		protected Void call() throws Exception {

			int i = nCharts;
			while (i > 0) {
				if (isCancelled()) {
					break;
				}
				images.put(snapshotChart(charts.take()));
				i--;
				updateProgress(nCharts - i, nCharts);
			}

			return null;
		}

		private BufferedImage snapshotChart(final Parent chartContainer)
				throws InterruptedException {

			final CountDownLatch latch = new CountDownLatch(1);
			// render the chart in an offscreen scene (scene is used to allow
			// css processing) and snapshot it to an image.
			// the snapshot is done in runlater as it must occur on the javafx
			// application thread.
			final SimpleObjectProperty<BufferedImage> imageProperty = new SimpleObjectProperty();
			Platform.runLater(new Runnable() {

				@Override
				public void run() {

					Scene snapshotScene = new Scene(chartContainer);
					final SnapshotParameters params = new SnapshotParameters();
					params.setFill(Color.ALICEBLUE);
					chartContainer.snapshot(
							new Callback<SnapshotResult, Void>() {

								@Override
								public Void call(SnapshotResult result) {

									imageProperty.set(SwingFXUtils.fromFXImage(
											result.getImage(), null));
									latch.countDown();
									return null;
								}
							}, params, null);
				}
			});

			latch.await();

			return imageProperty.get();
		}
	}

	class PngsExportTask extends Task<Void> {

		private final int nImages;
		private final BlockingQueue<BufferedImage> images;

		PngsExportTask(BlockingQueue<BufferedImage> images, final int nImages) {

			this.images = images;
			this.nImages = nImages;
			updateProgress(0, nImages);
		}

		@Override
		protected Void call() throws Exception {

			int i = nImages;
			while (i > 0) {
				if (isCancelled()) {
					break;
				}
				exportPng(images.take(), getChartFilePath(nImages - i));
				i--;
				updateProgress(nImages - i, nImages);
			}

			return null;
		}

		private void exportPng(BufferedImage image, String filename) {

			try {
				ImageIO.write(image, "png", new File(filename));
			} catch (IOException ex) {
				Logger.getLogger(OffScreenOffThreadCharts.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	class SaveChartsTask<Void> extends Task {

		private final BlockingQueue<Parent> charts = new ArrayBlockingQueue(10);
		private final BlockingQueue<BufferedImage> bufferedImages = new ArrayBlockingQueue(
				10);
		private final ExecutorService chartsCreationExecutor = createExecutor("CreateCharts");
		private final ExecutorService chartsSnapshotExecutor = createExecutor("TakeSnapshots");
		private final ExecutorService imagesExportExecutor = createExecutor("ExportImages");
		private final ChartsCreationTask chartsCreationTask;
		private final ChartsSnapshotTask chartsSnapshotTask;
		private final PngsExportTask imagesExportTask;

		SaveChartsTask(final int nCharts) {

			chartsCreationTask = new ChartsCreationTask(charts, nCharts);
			chartsSnapshotTask = new ChartsSnapshotTask(charts, bufferedImages,
					nCharts);
			imagesExportTask = new PngsExportTask(bufferedImages, nCharts);

			setOnCancelled(new EventHandler() {

				@Override
				public void handle(Event event) {

					chartsCreationTask.cancel();
					chartsSnapshotTask.cancel();
					imagesExportTask.cancel();
				}
			});

			imagesExportTask.workDoneProperty().addListener(
					new ChangeListener<Number>() {

						@Override
						public void changed(
								ObservableValue<? extends Number> observable,
								Number oldValue, Number workDone) {

							updateProgress(workDone.intValue(), nCharts);
						}
					});
		}

		ReadOnlyDoubleProperty chartsCreationProgressProperty() {

			return chartsCreationTask.progressProperty();
		}

		ReadOnlyDoubleProperty chartsSnapshotProgressProperty() {

			return chartsSnapshotTask.progressProperty();
		}

		ReadOnlyDoubleProperty imagesExportProgressProperty() {

			return imagesExportTask.progressProperty();
		}

		@Override
		protected Void call() throws Exception {

			chartsCreationExecutor.execute(chartsCreationTask);
			chartsSnapshotExecutor.execute(chartsSnapshotTask);
			imagesExportExecutor.execute(imagesExportTask);

			chartsCreationExecutor.shutdown();
			chartsSnapshotExecutor.shutdown();
			imagesExportExecutor.shutdown();

			try {
				imagesExportExecutor.awaitTermination(1, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				/** no action required */
			}

			return null;
		}
	}

	private String getChartFilePath(int chartNumber) {

		return new File(WORKING_DIR, CHART_FILE_PREFIX + chartNumber + ".png")
				.getPath();
	}

	private ExecutorService createExecutor(final String name) {

		ThreadFactory factory = new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {

				Thread t = new Thread(r);
				t.setName(name);
				t.setDaemon(true);
				return t;
			}
		};

		return Executors.newSingleThreadExecutor(factory);
	}

	public static void main(String[] args) {

		launch(args);
	}
}