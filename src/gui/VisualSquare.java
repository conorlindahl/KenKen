package gui;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class VisualSquare extends Canvas {
	int row;
	int col;
	boolean selected;
	boolean locked;
	//You're my boolean and I have you locked ;)
	
	public VisualSquare(int r, int c, int width, int height) {
		super(width, height);
		row = r;
		col = c;
		selected = false;
		locked = false;
		setOnMouseClicked(new VisualSquareEvent());
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
	
	private void select() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setStroke(Params.selectedSquareColor);
		gc.setLineWidth(Params.selectedStrokeSize);
		gc.strokeRect(0, 0, 
				Params.squareSize, Params.squareSize);
		VisualSquare.selectedSquares.add(this);
	}
	
	private void unselect() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setStroke(Params.backgroundColor);
		gc.setLineWidth(Params.selectedStrokeSize+1);
		gc.strokeRect(0, 0, 
				Params.squareSize, Params.squareSize);
		gc.setStroke(Params.unselectedSquareColor);
		gc.setLineWidth(Params.unselectedStrokeSize);
		gc.strokeRect(0, 0, 
				Params.squareSize, Params.squareSize);
		VisualSquare.selectedSquares.remove(this);
	}
	
	/*
	 * Made the behavior of the VisualSquare inside of the VisualSquare class
	 * because the behavior is uniform across all squares. They all should 
	 * take the same color when selected, and likewise when unselected. 
	 */
	private class VisualSquareEvent implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			if(locked) { return; }
			
			if (!selected) {
				VisualSquare.this.select();
			} else {
				VisualSquare.this.unselect();
			}
			VisualSquare.this.selected = !VisualSquare.this.selected;
		}
	}
	
	static Set<VisualSquare> selectedSquares;
	
	static {
		selectedSquares = new HashSet<VisualSquare>();
	}
	
	public static void clearSelected() {
		selectedSquares = new HashSet<VisualSquare>();
	}
}
