import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressBarBuilder;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.stage.Stage;

public class WorkerAndTaskExample extends Application {

	private Model model;
	private View view;

	public static void main(String[] args) {

		Application.launch(args);
	}

	public WorkerAndTaskExample() {

		model = new Model();
	}

	@Override
	public void start(Stage stage) throws Exception {

		view = new View(model);
		hookupEvents();
		stage.setTitle("Worker and Task Example");
		stage.setScene(view.scene);
		stage.show();
	}

	private void hookupEvents() {

		view.startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent actionEvent) {

				new Thread((Runnable) model.worker).start();
			}
		});
		view.cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent actionEvent) {

				model.worker.cancel();
			}
		});
		view.exceptionButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent actionEvent) {

				model.shouldThrow.getAndSet(true);
			}
		});
	}

	private static class Model {

		public Worker<String> worker;
		public AtomicBoolean shouldThrow = new AtomicBoolean(false);

		private Model() {

			worker = new Task<String>() {

				@Override
				protected String call() throws Exception {

					updateTitle("Example Task");
					updateMessage("Starting...");
					final int total = 250;
					updateProgress(0, total);
					for (int i = 1; i <= total; i++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							return "Cancelled at " + System.currentTimeMillis();
						}
						if (shouldThrow.get()) {
							throw new RuntimeException("Exception thrown at "
									+ System.currentTimeMillis());
						}
						updateTitle("Example Task (" + i + ")");
						updateMessage("Processed " + i + " of " + total
								+ " items.");
						updateProgress(i, total);
					}
					return "Completed at " + System.currentTimeMillis();
				}
			};
		}
	}

	private static class View {

		public ProgressBar progressBar;
		public Label title;
		public Label message;
		public Label running;
		public Label state;
		public Label totalWork;
		public Label workDone;
		public Label progress;
		public Label value;
		public Label exception;
		public Button startButton;
		public Button cancelButton;
		public Button exceptionButton;
		public Scene scene;

		private View(final Model model) {

			progressBar = ProgressBarBuilder.create().minWidth(250).build();
			title = new Label();
			message = new Label();
			running = new Label();
			state = new Label();
			totalWork = new Label();
			workDone = new Label();
			progress = new Label();
			value = new Label();
			exception = new Label();
			startButton = new Button("Start");
			cancelButton = new Button("Cancel");
			exceptionButton = new Button("Exception");
			final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker
					.stateProperty();
			progressBar.progressProperty()
					.bind(model.worker.progressProperty());
			title.textProperty().bind(model.worker.titleProperty());
			message.textProperty().bind(model.worker.messageProperty());
			running.textProperty().bind(
					Bindings.format("%s", model.worker.runningProperty()));
			state.textProperty().bind(Bindings.format("%s", stateProperty));
			totalWork.textProperty().bind(
					model.worker.totalWorkProperty().asString());
			workDone.textProperty().bind(
					model.worker.workDoneProperty().asString());
			progress.textProperty().bind(
					Bindings.format("%5.2f%%", model.worker.progressProperty()
							.multiply(100)));
			value.textProperty().bind(model.worker.valueProperty());
			exception.textProperty().bind(new StringBinding() {

				{
					super.bind(model.worker.exceptionProperty());
				}

				@Override
				protected String computeValue() {

					final Throwable exception = model.worker.getException();
					if (exception == null)
						return "";
					return exception.getMessage();
				}
			});
			startButton.disableProperty().bind(
					stateProperty.isNotEqualTo(Worker.State.READY));
			cancelButton.disableProperty().bind(
					stateProperty.isNotEqualTo(Worker.State.RUNNING));
			exceptionButton.disableProperty().bind(
					stateProperty.isNotEqualTo(Worker.State.RUNNING));
			final HBox topPane = HBoxBuilder.create()
					.padding(new Insets(10, 10, 10, 10)).spacing(10)
					.alignment(Pos.CENTER).children(progressBar).build();
			final GridPane centerPane = GridPaneBuilder
					.create()
					.hgap(10)
					.vgap(10)
					.padding(new Insets(10, 10, 10, 10))
					.columnConstraints(
							ColumnConstraintsBuilder.create()
									.halignment(HPos.RIGHT).minWidth(65)
									.build(),
							ColumnConstraintsBuilder.create()
									.halignment(HPos.LEFT).minWidth(200)
									.build()).build();
			centerPane.add(new Label("Title:"), 0, 0);
			centerPane.add(new Label("Message:"), 0, 1);
			centerPane.add(new Label("Running:"), 0, 2);
			centerPane.add(new Label("State:"), 0, 3);
			centerPane.add(new Label("Total Work:"), 0, 4);
			centerPane.add(new Label("Work Done:"), 0, 5);
			centerPane.add(new Label("Progress:"), 0, 6);
			centerPane.add(new Label("Value:"), 0, 7);
			centerPane.add(new Label("Exception:"), 0, 8);
			centerPane.add(title, 1, 0);
			centerPane.add(message, 1, 1);
			centerPane.add(running, 1, 2);
			centerPane.add(state, 1, 3);
			centerPane.add(totalWork, 1, 4);
			centerPane.add(workDone, 1, 5);
			centerPane.add(progress, 1, 6);
			centerPane.add(value, 1, 7);
			centerPane.add(exception, 1, 8);
			final HBox buttonPane = HBoxBuilder.create()
					.padding(new Insets(10, 10, 10, 10)).spacing(10)
					.alignment(Pos.CENTER)
					.children(startButton, cancelButton, exceptionButton)
					.build();
			scene = SceneBuilder
					.create()
					.root(BorderPaneBuilder.create().top(topPane)
							.center(centerPane).bottom(buttonPane).build())
					.build();
		}
	}
}