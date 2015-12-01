package gui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

public class Params {
	public static final double sceneWidth = 550.0;
	public static final double sceneHeight = 400.0;

	public static int kenkenDimension = 5;
	public static final Map<String, String> operations;
	static {
		operations = new HashMap<String, String>();
		operations.put("Add", "+");
		operations.put("Subtract", "-");
		operations.put("Multiply", "x");
		operations.put("Divide", "\u00F7");
		operations.put("Equal", "");
	}
	
	public static final double kenkenGridSize = 250.0;
	public static final Color backgroundColor = Color.WHITE;
	public static int squareSize = (int) kenkenGridSize/kenkenDimension;

	public static final Color selectedSquareColor = Color.BLACK; 
	public static final double selectedStrokeSize = 3.0;
	public static final Color unselectedSquareColor = Color.DARKGRAY;
	public static final double unselectedStrokeSize = 2.0;
	public static final double cageOutlineSize = 6.0;
	public static final double inCageLineSize = 2.0;
	
	public static final String solverType = "RecursiveSolver";
}