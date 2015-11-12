package gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KenKenApp extends Application {

	private List<VisualSquare[]> lockedCages;
	
	{
		lockedCages = new ArrayList<VisualSquare[]>();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KenKen Solver");
		
		Text title = new Text("KenKen Solver");
		title.setFont(Font.font("Times New Roman", 32));
		
		GridPane grid = setUpKenKenVisual();
		
		VBox controlPanel = getControlPanel();
		
		BorderPane layout = new BorderPane();
		layout.setTop(title);
		BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
		layout.setLeft(grid);
		layout.setRight(controlPanel);
		BorderPane.setAlignment(controlPanel, Pos.BOTTOM_LEFT);
		BorderPane.setMargin(controlPanel, new Insets(50, 100, 0, 0));

		Scene r = new Scene(layout, 550, 400);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(r);
		primaryStage.show();
	}
	
	private GridPane setUpKenKenVisual() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setPadding(new Insets(50));
		
		for(int x=0; x<5; x+=1) {
			for(int y=0; y<5; y+=1) {
				VisualSquare v = new VisualSquare(50, 50);
				v.row = y;
				v.col = x;
				v.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if(v.locked) { return; }
						
						GraphicsContext gc = v.getGraphicsContext2D();
						if (!v.selected) {
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(4.0);
							gc.strokeRect(0, 0, 50, 50);
							VisualSquare.selectedSquares.remove(v);
						} else {
							gc.setStroke(Color.WHITE);
							gc.setLineWidth(5.0);
							gc.strokeRect(0, 0, 50, 50);
							gc.setStroke(Color.DARKGREY);
							gc.setLineWidth(3.0);
							gc.strokeRect(0, 0, 50, 50);
							VisualSquare.selectedSquares.add(v);
						}
						v.selected = !v.selected;
					}
					
				});
				GraphicsContext gc = v.getGraphicsContext2D();
				gc.setStroke(Color.DARKGREY);
				gc.setLineWidth(3.0);
				gc.strokeRect(0, 0, 50, 50);
				grid.add(v, x, y);
			}
		}
		
		return grid;
	}
	
	private VBox getControlPanel() {
		VBox container = new VBox();
		container.setPadding(new Insets(5,0,0,5));
		container.setSpacing(5);
		
		/*
		 * The drop down box for choosing what operation a cage should be
		 */
		ChoiceBox<String> operationSelection = new ChoiceBox<String>();
		operationSelection.setMaxWidth(150);
		String mult = "Multiply";
		String add = "Add";
		String sub = "Subtract";
		String div = "Divide";
		String eq = "Equal";
		operationSelection.getItems().addAll(add, sub, mult, div, eq);
		operationSelection.setValue(add);
		
		container.getChildren().add(operationSelection);

		/*
		 * The resource to determine the value for a specific cage
		 */
		TextField totalInput = new TextField();
		
		HBox totalBox = new HBox();
		Label totalLabel = new Label("Cage Total: ");
		totalLabel.setPadding(new Insets(5, 0, 0, 0));
		totalInput.setMaxWidth(50);
		totalBox.getChildren().addAll(totalLabel, totalInput);
		
		/*
		 * The resource that activates the creation of the Cages
		 */
		
		container.getChildren().add(totalBox);
		return container;
	}

	private static class VisualCage {
		List<VisualSquare> containedSquares;
		String operation;
		int total;
		
		public VisualCage(String op, int t) {
			containedSquares = new ArrayList<VisualSquare>();
		}
		
		public void addSquares(List<VisualSquare> toAdd) {
			for(VisualSquare i : toAdd) {
				containedSquares.add(i);
			}
		}
		
		/**
		 * Makes String of the form:
		 * <operation> <total> <# of Squares> <coordinate points of Squares>
		 */
		public String toString() {
			String rep = operation;
			rep += " " + total + " " + containedSquares.size();
			for(VisualSquare s : containedSquares) {
				rep += " (" + s.row + "," + s.col + ")";
			}
			
			return rep;
		}
	}
	
	private static class VisualSquare extends Canvas {
		int row;
		int col;
		boolean selected;
		boolean locked;
		//You're my boolean and I have you locked ;)
		
		public VisualSquare() {
			genericConstruction();
		}
		
		public VisualSquare(int width, int height) {
			super(width, height);
			genericConstruction();
		}
		
		private void genericConstruction() {
			row = 0;
			col = 0;
			selected = false;
			locked = false;
		}
		
		static Set<VisualSquare> selectedSquares;
		
		static {
			selectedSquares = new HashSet<VisualSquare>();
		}
	}

}
