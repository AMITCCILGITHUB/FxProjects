package samples;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.jonathangiles.hacking.tableview.cellSpan.CellSpan;
import net.jonathangiles.hacking.tableview.cellSpan.CellSpanTableView;
import net.jonathangiles.hacking.tableview.cellSpan.SpanModel;
import samples.misc.Person;

/**
 * 
 * @author Jonathan Giles
 */
public class CellSpanTableViewSample extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("CellSpanTableView!");

		// we need a TabPane to see the samples
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		Tab standardTableViewTab = new Tab("Standard");
		buildStandardTableViewTab(standardTableViewTab);
		tabPane.getTabs().add(standardTableViewTab);

		Tab rowSpanTableViewTab = new Tab("Row Span");
		buildRowSpanTableViewTab(rowSpanTableViewTab);
		tabPane.getTabs().add(rowSpanTableViewTab);

		Tab columnSpanTableViewTab = new Tab("Column Span");
		buildColumnSpanTableViewTab(columnSpanTableViewTab);
		tabPane.getTabs().add(columnSpanTableViewTab);

		Tab rowAndColumnSpanTableViewTab = new Tab("Row/Column Span");
		buildRowAndColumnSpanTableViewTab(rowAndColumnSpanTableViewTab);
		tabPane.getTabs().add(rowAndColumnSpanTableViewTab);

		// show on screen
		StackPane root = new StackPane();
		root.getChildren().add(tabPane);
		Scene scene = new Scene(root, 520, 600);
		// scene.getStylesheets().add(CellSpanTableView.class.getResource("cell-span.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private TableView buildBasicTableView(boolean enableCellSpan) {

		// define columns
		TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>(
				"First Name");
		firstNameCol.setPrefWidth(120);
		firstNameCol
				.setCellValueFactory(new PropertyValueFactory<Person, String>(
						"firstName"));

		TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>(
				"Last Name");
		lastNameCol.setPrefWidth(120);
		lastNameCol
				.setCellValueFactory(new PropertyValueFactory<Person, String>(
						"lastName"));

		TableColumn<Person, String> balanceCol = new TableColumn<Person, String>(
				"Balance");
		balanceCol.setPrefWidth(120);
		balanceCol
				.setCellValueFactory(new PropertyValueFactory<Person, String>(
						"balance"));

		// create a tableview
		TableView<Person> tableView = enableCellSpan ? new CellSpanTableView<Person>()
				: new TableView<Person>();
		tableView.getColumns().addAll(firstNameCol, lastNameCol, balanceCol);

		// insert the test data
		tableView.setItems(Person.getTestList());
		// FXCollections.observableArrayList(
		// new Person("Jonathan", "Giles", true, 38.23),
		// new Person("Richard", "Bair", true, 12.32),
		// new Person("Jasper", "Potts", true, 43.23)
		// ));

		// for exploration, we enable single cell selection mode, and also print
		// out
		// some debug output to show the currently selected table position
		final TableView.TableViewSelectionModel<Person> sm = tableView
				.getSelectionModel();
		sm.getSelectedCells().addListener(
				new ListChangeListener<TablePosition>() {

					@Override
					public void onChanged(Change<? extends TablePosition> change) {

						if (sm.getSelectedCells().isEmpty()) {
							// System.out.println("No selection");
						} else {
							TablePosition tp = sm.getSelectedCells().get(0);
							if (tp == null)
								return;
							System.out.println("Selection: [ row: "
									+ tp.getRow() + ", column: "
									+ tp.getColumn() + ", column name: "
									+ tp.getTableColumn().getText() + " ] ");
						}
					}
				});

		return tableView;
	}

	private BorderPane buildBorderPane(Tab tab) {

		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		tab.setContent(pane);
		return pane;
	}

	private void buildStandardTableViewTab(Tab tab) {

		BorderPane pane = buildBorderPane(tab);
		TableView tableView = buildBasicTableView(false);
		pane.setCenter(tableView);

		pane.setRight(buildCommonControlGrid(tableView));
	}

	private void buildRowSpanTableViewTab(Tab tab) {

		BorderPane pane = buildBorderPane(tab);
		CellSpanTableView tableView = (CellSpanTableView) buildBasicTableView(true);
		pane.setCenter(tableView);

		// install the span model
		tableView.setSpanModel(new SpanModel() {

			private final CellSpan spanTwoRows = new CellSpan(2, 1);

			@Override
			public CellSpan getCellSpanAt(int rowIndex, int columnIndex) {

				return rowIndex % 3 == 0 && columnIndex == 1 ? spanTwoRows
						: null;
			}

			@Override
			public boolean isCellSpanEnabled() {

				return true;
			}
		});

		pane.setRight(buildCommonControlGrid(tableView));
	}

	private void buildColumnSpanTableViewTab(Tab tab) {

		BorderPane pane = buildBorderPane(tab);
		CellSpanTableView tableView = (CellSpanTableView) buildBasicTableView(true);
		pane.setCenter(tableView);

		// install the span model
		tableView.setSpanModel(new SpanModel() {

			private final CellSpan spanTwoColumns = new CellSpan(1, 2);

			@Override
			public CellSpan getCellSpanAt(int rowIndex, int columnIndex) {

				return rowIndex % 2 == 0 && columnIndex == 0 ? spanTwoColumns
						: null;
			}

			@Override
			public boolean isCellSpanEnabled() {

				return true;
			}
		});

		pane.setRight(buildCommonControlGrid(tableView));
	}

	private void buildRowAndColumnSpanTableViewTab(Tab tab) {

		BorderPane pane = buildBorderPane(tab);
		CellSpanTableView tableView = (CellSpanTableView) buildBasicTableView(true);
		pane.setCenter(tableView);

		// install the span model
		tableView.setSpanModel(new SpanModel() {

			private final CellSpan spanTwoRowsAndTwoColumns = new CellSpan(2, 2);

			@Override
			public CellSpan getCellSpanAt(int rowIndex, int columnIndex) {

				return rowIndex % 3 == 0 && columnIndex == 0 ? spanTwoRowsAndTwoColumns
						: null;
			}

			@Override
			public boolean isCellSpanEnabled() {

				return true;
			}
		});

		pane.setRight(buildCommonControlGrid(tableView));
	}

	private GridPane buildCommonControlGrid(final TableView tableView) {

		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(5, 5, 5, 5));

		// allow user to change between cell selection and row selection modes
		ChoiceBox<String> rowOrCellSelectionBox = new ChoiceBox(
				FXCollections.observableArrayList("Row-based", "Cell-based"));
		rowOrCellSelectionBox.getSelectionModel().select(0);
		rowOrCellSelectionBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue ov, String t, String t1) {

						if ("Row-based".equals(t1)) {
							tableView.getSelectionModel()
									.setCellSelectionEnabled(false);
						} else if ("Cell-based".equals(t1)) {
							tableView.getSelectionModel()
									.setCellSelectionEnabled(true);
						}
					}
				});
		ChoiceBox<SelectionMode> selectionModeBox = new ChoiceBox(
				FXCollections.observableArrayList(SelectionMode.values()));
		selectionModeBox.getSelectionModel().select(SelectionMode.SINGLE);
		selectionModeBox.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<SelectionMode>() {

					@Override
					public void changed(ObservableValue ov, SelectionMode t,
							SelectionMode t1) {

						tableView.getSelectionModel().setSelectionMode(t1);
					}
				});
		grid.add(new Label("Selection Mode:"), 1, 1);
		grid.add(rowOrCellSelectionBox, 1, 2);
		grid.add(selectionModeBox, 1, 3);

		return grid;
	}
}
