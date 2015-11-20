package gui;

import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import kenken.InvalidInitializationException;
import kenken.RecursiveSolver;
import kenken.Solver;

public class KenKenApp extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KenKen Solver");
		
		Text title = new Text("KenKen Solver");
		title.setFont(Font.font("Times New Roman", 32));
		
		GridPane grid = setUpKenKenVisual();
		
		ControlPanel controlPanel = new ControlPanel();
		
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
		
		for(int col=0; col<Params.kenkenDimension; col+=1) {
			for(int row=0; row<Params.kenkenDimension; row+=1) {
				VisualSquare v =  
						new VisualSquare(row, col, Params.squareSize, Params.squareSize);
				GraphicsContext gc = v.getGraphicsContext2D();
				gc.setStroke(Color.DARKGREY);
				gc.setLineWidth(Params.unselectedStrokeSize);
				gc.strokeRect(0, 0, Params.squareSize, Params.squareSize);
				grid.add(v, col, row);
			}
		}
		
		return grid;
	}
	
	private VBox getControlPanel() {
		VBox container = new VBox();

		
		/*
		 * The drop down box for choosing what operation a cage should be
		 */
		ChoiceBox<String> operationSelection = new ChoiceBox<String>();
		
		container.getChildren().add(operationSelection);

		/*
		 * The resource to determine the value for a specific cage
		 */
		TextField totalInput = new TextField();
		
		HBox totalBox = new HBox();
		Label totalLabel = new Label("Cage Total: ");

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
					//TODO: Decide if an error message should appear
					return;
				}
				int total = Integer.parseInt(totalString);
				
				Set<VisualSquare> selectedSquares = VisualSquare.selectedSquares;
				if(selectedSquares.size() == 0) {
					//TODO: Decide if an error message should appear
					return;
				}
				VisualSquare marker = null;
				
				
				for(VisualSquare currentSquare : selectedSquares) {
					if(marker == null || currentSquare.row < marker.row) {
						marker = currentSquare;
					} else if (currentSquare.row == marker.row) {
						if(currentSquare.col < marker.col) {
							marker = currentSquare;
						}
					}
					
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
				
				GraphicsContext gc = marker.getGraphicsContext2D();
				gc.setLineWidth(1.0);
				gc.strokeText(total + " " + Params.operations.get(operation), 5, 15);
				
				
				VisualCage createdCage = new VisualCage(operation, total);
				createdCage.addSquares(selectedSquares);
				
				VisualSquare.clearSelected();
			}
		});
		
		container.getChildren().add(createCageButton);
		
		/*
		 * The resource that allows the KenKen to be solved
		 */
		
		Button solveKenKenButton = new Button("Solve");
		solveKenKenButton.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				List<VisualCage> allCages = VisualCage.getAllCagesUnmodifiable();
				String description = Params.kenkenDimension + "\n" + allCages.size() + "\n";
				for(VisualCage i : allCages) {
					description += i.toString() + "\n";
				}
				
				Solver s = null;
				try {
					s = new RecursiveSolver(description);
				} catch (InvalidInitializationException ex) {
					System.err.println("KenKen Solver failed to initialize, check that everythign is set up");
					return;
				}
				
				s.solve();
				if(s.isSolved()) {
					int[][] solvedValues = s.getKenKen();
					
					for(VisualCage vc : VisualCage.getAllCagesUnmodifiable()) {
						for(VisualSquare vs : vc.containedSquares) {
							int squareValue = solvedValues[vs.row][vs.col];
							
							GraphicsContext gc = vs.getGraphicsContext2D();
							gc.setFont(new Font(24));
							gc.setLineWidth(3.0);
							gc.strokeText("" + squareValue, Params.cageOutlineSize, Params.squareSize - Params.cageOutlineSize);
						}
					}
				}
			}
			
		});
		
		container.getChildren().add(solveKenKenButton);
		
		return container;
	}

}
