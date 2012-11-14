import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Calculator extends Application {

	String inputString;
	Deque<Integer> stack;

	public Calculator() {

		inputString = "";
		stack = new ArrayDeque<>();
	}

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Simple Calculator");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// for debug
		// grid.setGridLinesVisible(true);

		Scene scene = new Scene(grid, 300, 280);
		primaryStage.setScene(scene);

		Text scenetitle = new Text("Simple Calculator");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 4, 1);

		Label result = new Label("Result:");
		grid.add(result, 0, 1, 2, 1);

		final TextField resultField = new TextField();
		resultField.setEditable(false);
		grid.add(resultField, 2, 1, 3, 1);

		initNumberButton(grid, resultField);
		initOperatorButton(grid, resultField);
		initEnterButton(grid, resultField);
		initClearButton(grid, resultField);

		primaryStage.show();
	}

	protected void initClearButton(GridPane grid, final TextField resultField) {

		Button clearBtn = new Button("Clear");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(clearBtn);
		grid.add(hbBtn, 4, 5);
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				inputString = "";
				stack = new ArrayDeque<>();
				resultField.setText("0");
			}
		});

	}

	protected void initEnterButton(GridPane grid, final TextField resultField) {

		Button enterBtn = new Button("Enter");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(enterBtn);
		grid.add(hbBtn, 4, 6);
		enterBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (0 < inputString.length()) {
					String str = resultField.getText();
					stack.push(Integer.valueOf(str));
					inputString = "";
					System.out.println("[stack] <= " + str);
				} else {
				}
			}
		});

	}

	protected void initOperatorButton(GridPane grid, final TextField resultField) {

		final Button divBtn = new Button("/");
		final Button mulBtn = new Button("*");
		final Button subBtn = new Button("-");
		final Button addBtn = new Button("+");
		grid.add(divBtn, 3, 2);
		grid.add(mulBtn, 3, 3);
		grid.add(subBtn, 3, 4);
		grid.add(addBtn, 3, 5);

		addBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (0 < stack.size()) {
					if (0 < inputString.length()) {
						stack.push(Integer.valueOf(inputString));
						System.out.println("[stack] <= "
								+ Integer.valueOf(inputString));
						inputString = "";
					}
					Token token = new Add();
					token.execute(stack);
					resultField.setText(String.valueOf(stack.peek()));
					System.out.println("[stack] <= " + stack.peek());
				}
			}
		});

		subBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (0 < stack.size()) {
					if (0 < inputString.length()) {
						stack.push(Integer.valueOf(inputString));
						System.out.println("[stack] <= "
								+ Integer.valueOf(inputString));
						inputString = "";
					}
					Token token = new Sub();
					token.execute(stack);
					resultField.setText(String.valueOf(stack.peek()));
					System.out.println("[stack] <= " + stack.peek());
				}
			}
		});

		mulBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (0 < stack.size()) {
					if (0 < inputString.length()) {
						stack.push(Integer.valueOf(inputString));
						System.out.println("[stack] <= "
								+ Integer.valueOf(inputString));
						inputString = "";
					}
					Token token = new Mul();
					token.execute(stack);
					resultField.setText(String.valueOf(stack.peek()));
					System.out.println("[stack] <= " + stack.peek());
				}
			}
		});

		divBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (0 < stack.size()) {
					if (0 < inputString.length()) {
						stack.push(Integer.valueOf(inputString));
						System.out.println("[stack] <= "
								+ Integer.valueOf(inputString));
						inputString = "";
					}
					Token token = new Div();
					token.execute(stack);
					resultField.setText(String.valueOf(stack.peek()));
					System.out.println("[stack] <= " + stack.peek());
				}
			}
		});
	}

	protected void initNumberButton(GridPane grid, final TextField resultField) {

		final Button btn0 = new Button("0");
		final Button btn1 = new Button("1");
		final Button btn2 = new Button("2");
		final Button btn3 = new Button("3");
		final Button btn4 = new Button("4");
		final Button btn5 = new Button("5");
		final Button btn6 = new Button("6");
		final Button btn7 = new Button("7");
		final Button btn8 = new Button("8");
		final Button btn9 = new Button("9");
		grid.add(btn0, 1, 5);
		grid.add(btn1, 0, 4);
		grid.add(btn2, 1, 4);
		grid.add(btn3, 2, 4);
		grid.add(btn4, 0, 3);
		grid.add(btn5, 1, 3);
		grid.add(btn6, 2, 3);
		grid.add(btn7, 0, 2);
		grid.add(btn8, 1, 2);
		grid.add(btn9, 2, 2);

		List<Button> numButtonList = new ArrayList<>();
		numButtonList.add(btn0);
		numButtonList.add(btn1);
		numButtonList.add(btn2);
		numButtonList.add(btn3);
		numButtonList.add(btn4);
		numButtonList.add(btn5);
		numButtonList.add(btn6);
		numButtonList.add(btn7);
		numButtonList.add(btn8);
		numButtonList.add(btn9);
		for (final Button button : numButtonList) {
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					String str = resultField.getText();

					if (0 < inputString.length() || str.equals("0")) {
						inputString = button.getText();
					} else {
						inputString += button.getText();
					}
					resultField.setText(inputString);
				}
			});
		}
	}
}

interface Token {

	Deque<Integer> execute(Deque<Integer> stack);
}

class Operand implements Token {

	Integer value;

	Operand(Integer val) {

		value = val;
	}

	@Override
	public Deque<Integer> execute(Deque<Integer> stack) {

		stack.push(value);
		return stack;
	}
}

interface Operator extends Token {
	// Deque<Integer> execute(Deque<Integer> stack);
}

class Add implements Operator {

	@Override
	public Deque<Integer> execute(Deque<Integer> stack) {

		if (stack != null) {
			Integer num1 = stack.pop();
			Integer num0 = stack.pop();
			stack.push(num0 + num1);
		}
		return stack;
	}
}

class Sub implements Operator {

	@Override
	public Deque<Integer> execute(Deque<Integer> stack) {

		if (stack != null) {
			Integer num1 = stack.pop();
			Integer num0 = stack.pop();
			stack.push(num0 - num1);
		}
		return stack;
	}
}

class Mul implements Operator {

	@Override
	public Deque<Integer> execute(Deque<Integer> stack) {

		if (stack != null) {
			Integer num1 = stack.pop();
			Integer num0 = stack.pop();
			stack.push(num0 * num1);
		}
		return stack;
	}
}

class Div implements Operator {

	@Override
	public Deque<Integer> execute(Deque<Integer> stack) {

		if (stack != null) {
			Integer num1 = stack.pop();
			Integer num0 = stack.pop();
			if (num1 != 0) {
				stack.push(num0 / num1);
			} else {
				stack.push(0);
			}
		}
		return stack;
	}
}
