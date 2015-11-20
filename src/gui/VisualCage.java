package gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VisualCage {
	List<VisualSquare> containedSquares;
	String operation;
	int total;
	
	public VisualCage(String op, int t) {
		operation = op;
		total = t;
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
			rep += " " + s.row + "," + s.col;
		}
		
		return rep;
	}
	
	private static List<VisualCage> allCages;
	
	static {
		allCages = new ArrayList<VisualCage>();
	}
	
	public static List<VisualCage> getAllCagesUnmodifiable() {
		return Collections.unmodifiableList(allCages);
	}
}
