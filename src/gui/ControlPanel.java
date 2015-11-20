package gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import kenken.Solver;

public class ControlPanel extends VBox {
	private OperationSelection opChoices;
	private CageTotalInput userInput;
	private CreateCageButton create;
	private SolveButton solve;
	
	public ControlPanel() {
		init();
		opChoices = new OperationSelection();
		userInput = new CageTotalInput();
		create = new CreateCageButton("Create Cage");
		solve = new SolveButton("Solve");
		
		getChildren().addAll(opChoices, userInput, create, solve);
	}
	
	private void init() {
		setMaxWidth(175);
		setAlignment(Pos.TOP_RIGHT);
		setPadding(new Insets(5,0,0,5));
		setSpacing(5);
	}
	
	public String getSelectedOp() {
		return opChoices.getValue();
	}
	
	public boolean isSelectedOpValid() {
		return opChoices.isValid();
	}
	
	/*
	 * Use with isCageTotalValueValid or undefined behavior will occur
	 */
	public int getCageTotalValue() {
		return Integer.parseInt(userInput.getText());
	}
	
	public boolean isCageTotalValueValid() {
		return userInput.isValid();
	}
	
	private class OperationSelection extends ChoiceBox<String> {
		public OperationSelection() {
			//TODO: Paramatize this
			setMaxWidth(175);
			getItems().addAll(Params.operations.keySet());
			setValue("Equal");
		}
		
		public boolean isValid() {
			return getValue() != null;
		}
	}
	
	private class CageTotalInput extends HBox {
		private Label id;
		private TextField numIn;
		
		public CageTotalInput() {
			id = new Label("Cage Total: ");
			id.setPadding(new Insets(5, 0, 0, 0));
			
			numIn = new TextField();
			numIn.setMaxWidth(75);
			
			getChildren().addAll(id, numIn);
		}
		
		public boolean isValid() {
			TextField source = CageTotalInput.this.numIn;
			return source.getText().matches("[0-9]+");
		}
		
		public String getText() {
			return numIn.getText();
		}
	}
	
	private class CreateCageButton extends Button {
		public CreateCageButton(String msg) {
			super(msg);
			setOnMouseClicked(new CCBEventHandler());
		}
		
		private class CCBEventHandler implements EventHandler<MouseEvent> {

			@Override
			public void handle(MouseEvent event) {
				if(!ControlPanel.this.isSelectedOpValid()) {
					// TODO: Error message
					return;
				}
				if(!ControlPanel.this.isCageTotalValueValid()) {
					// TODO: Error message
					return;
				}
				String operation = ControlPanel.this.getSelectedOp();
				int total = ControlPanel.this.getCageTotalValue();
				
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
			
		}
	}
	
	private class SolveButton extends Button {
		public SolveButton(String msg) {
			super(msg);
			setOnMouseClicked(new SBEventHandler());
		}
		
		private class SBEventHandler implements EventHandler<MouseEvent> {
			@Override
			public void handle(MouseEvent event) {
				List<VisualCage> allCages = VisualCage.getAllCagesUnmodifiable();
				String description = Params.kenkenDimension + "\n" + allCages.size() + "\n";
				for(VisualCage i : allCages) {
					description += i.toString() + "\n";
				}
				
				Solver s = null;
				try {
					Class<?> solverClass = Class.forName("kenken." + Params.solverType);
					Constructor<?> solverConstructor = solverClass.getConstructor(String.class);
					s = (Solver) solverConstructor.newInstance(description);
				} catch (InvocationTargetException e) {
					System.err.println("KenKen Solver failed to initialize, check that everything is set up");
					return;
				} catch (Exception ex) {
					System.err.println("Something is wrong with your settings");
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
				} else {
					System.err.println("Unable to sovle KenKen");
				}
			}
		}
	}
	
}
