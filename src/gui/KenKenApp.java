package gui;

import java.util.ArrayList;
import java.util.Collection;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KenKenApp extends Application {
	
	/* Unsure if this is needed
	private List<VisualSquare[]> lockedCages;
	
	{
		lockedCages = new ArrayList<VisualSquare[]>();
	}
	*/
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

		double sceneWidth = Params.sceneWidth;
		double sceneHeight = Params.sceneHeight;
		Scene r = new Scene(layout, sceneWidth, sceneHeight);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(r);
		primaryStage.show();
	}
	
	private GridPane setUpKenKenVisual() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setPadding(new Insets(50));
		
		for(int x=0; x<Params.kenkenDimension; x+=1) {
			for(int y=0; y<Params.kenkenDimension; y+=1) {
				VisualSquare v =  
						new VisualSquare(Params.squareSize, Params.squareSize);
				
				v.row = y;
				v.col = x;
				v.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if(v.locked) { return; }
						
						GraphicsContext gc = v.getGraphicsContext2D();
						if (!v.selected) {
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(Params.selectedSquareStrokeSize);
							gc.strokeRect(0, 0, 
									Params.squareSize, Params.squareSize);
							VisualSquare.selectedSquares.add(v);
						} else {
							gc.setStroke(Color.WHITE);
							gc.setLineWidth(Params.selectedSquareStrokeSize+1);
							gc.strokeRect(0, 0, 
									Params.squareSize, Params.squareSize);
							gc.setStroke(Color.DARKGREY);
							gc.setLineWidth(Params.unselectedSquareStrokeSize);
							gc.strokeRect(0, 0, 
									Params.squareSize, Params.squareSize);
							VisualSquare.selectedSquares.remove(v);
						}
						v.selected = !v.selected;
					}
					
				});
				GraphicsContext gc = v.getGraphicsContext2D();
				gc.setStroke(Color.DARKGREY);
				gc.setLineWidth(Params.unselectedSquareStrokeSize);
				gc.strokeRect(0, 0, Params.squareSize, Params.squareSize);
				grid.add(v, x, y);
			}
		}
		
		return grid;
	}
	
	private VBox getControlPanel() {
		VBox container = new VBox();
		container.setAlignment(Pos.TOP_RIGHT);
		container.setPadding(new Insets(5,0,0,5));
		container.setSpacing(5);
		
		/*
		 * The drop down box for choosing what operation a cage should be
		 */
		ChoiceBox<String> operationSelection = new ChoiceBox<String>();
		operationSelection.setMaxWidth(175);
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
		totalInput.setMaxWidth(75);
		totalBox.getChildren().addAll(totalLabel, totalInput);
		
		container.getChildren().add(totalBox);
		
		/*
		 * The resource that activates the creation of the Cages
		 */
		Button createCageButton = new Button("Create Cage");
		createCageButton.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				
				String operation = operationSelection.getValue();
				
				String totalString = totalInput.getText();
				if(!totalString.matches("[0-9]+")) {
					return;
				}
				int total = Integer.parseInt(totalString);
				
				Set<VisualSquare> selectedSquares = VisualSquare.selectedSquares;
				System.out.println("Number of selected Squares: " + selectedSquares.size());
				
				for(VisualSquare currentSquare : selectedSquares) {
					GraphicsContext gc = currentSquare.getGraphicsContext2D();
					gc.setStroke(Color.BLACK);
					gc.setLineWidth(Params.cageOutlineSize);
					gc.setLineCap(StrokeLineCap.BUTT);
					gc.strokeRect(0, 0, Params.squareSize, Params.squareSize);
					
					double frontAdjust = Params.cageOutlineSize/2;
					double backAdjust = Params.squareSize-Params.cageOutlineSize/2;
					
					for(VisualSquare compareSquare : selectedSquares) {
						compareSquare.locked = true;
						if(currentSquare.isAdjacentAbove(compareSquare)) {
							gc.setStroke(Color.WHITE);
							gc.setLineWidth(Params.cageOutlineSize+1);
							gc.strokeLine(frontAdjust, 0, backAdjust, 0);
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(Params.inCageLineSize);
							gc.strokeLine(frontAdjust, 0, backAdjust, 0);
						} else if(currentSquare.isAdjacentRight(compareSquare)) {
							gc.setStroke(Color.WHITE);
							gc.setLineWidth(Params.cageOutlineSize+1);
							gc.strokeLine(Params.squareSize, frontAdjust, Params.squareSize, backAdjust);
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(Params.inCageLineSize);
							gc.strokeLine(Params.squareSize, frontAdjust, Params.squareSize, backAdjust);
							
						} else if(currentSquare.isAdjacentBelow(compareSquare)) {
							gc.setStroke(Color.WHITE);
							gc.setLineWidth(Params.cageOutlineSize+1);
							gc.strokeLine(frontAdjust, Params.squareSize, backAdjust, Params.squareSize);
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(Params.inCageLineSize);
							gc.strokeLine(frontAdjust, Params.squareSize, backAdjust, Params.squareSize);
						} else if(currentSquare.isAdjacentLeft(compareSquare)) {
							gc.setStroke(Color.WHITE);
							gc.setLineWidth(Params.cageOutlineSize+1);
							gc.strokeLine(0, frontAdjust, 0, backAdjust);
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(Params.inCageLineSize);
							gc.strokeLine(0, frontAdjust, 0, backAdjust);
						}
					}
				}
				
				VisualCage createdCage = new VisualCage(operation, total);
				createdCage.addSquares(selectedSquares);
				
				selectedSquares.removeAll(selectedSquares);
			}
		});
		
		container.getChildren().add(createCageButton);
		
		/*
		 * The resource that allows the KenKen to be solved
		 */
		
		Button solveKenKenButton = new Button("Solve");
		
		container.getChildren().add(solveKenKenButton);
		
		return container;
	}

	private static class VisualCage {
		List<VisualSquare> containedSquares;
		String operation;
		int total;
		
		public VisualCage(String op, int t) {
			containedSquares = new ArrayList<VisualSquare>();
			allCages.add(this);
		}
		
		public void addSquares(Collection<VisualSquare> toAdd) {
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
				rep += " (" + s.col + "," + s.row + ")";
			}
			
			return rep;
		}
		
		private static List<VisualCage> allCages;
		
		static {
			allCages = new ArrayList<VisualCage>();
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
		
		public boolean isAdjacentAbove(VisualSquare vs) {
			int aboveRow = this.row - 1;
			int sameCol = this.col;
			
			return (vs.row == aboveRow) && (vs.col == sameCol);
		}
		
		public boolean isAdjacentRight(VisualSquare vs) {
			int sameRow = this.row;
			int rightCol = this.col+1;
			
			return (vs.row == sameRow) && (vs.col == rightCol);
		}
		
		public boolean isAdjacentBelow(VisualSquare vs) {
			int belowRow = this.row + 1;
			int sameCol = this.col;
			
			return (vs.row == belowRow) && (vs.col == sameCol);
		}
		
		public boolean isAdjacentLeft(VisualSquare vs) {
			int sameRow = this.row;
			int leftCol = this.col - 1;
			
			return (vs.row == sameRow) && (vs.col == leftCol);
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
		
		public static void clearSelected() {
			selectedSquares = new HashSet<VisualSquare>();
		}
	}
	
	private static class Params {
		public static double sceneWidth = 550.0;
		public static double sceneHeight = 400.0;

		public static int kenkenDimension = 5;
		
		public static double kenkenGridSize = 250.0;
		public static int squareSize = (int) kenkenGridSize/kenkenDimension;
		public static double unselectedSquareStrokeSize = 2.0;
		public static double selectedSquareStrokeSize = 3.0;
		public static double cageOutlineSize = 6.0;
		public static double inCageLineSize = 2.0;
	}

}
