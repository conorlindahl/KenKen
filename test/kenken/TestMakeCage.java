package kenken;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.Test;

public class TestMakeCage {
	
	@Test
	public void testAddMultiplyCage() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("Multiply 24 3 0,2 1,2 1,3");
		List<Grid.Cage> cage = tester.getCages();
		
		Grid.Cage testCage = cage.get(0);
		
		boolean rightType = testCage instanceof Grid.MultiplyCage;
		boolean rightTotal = testCage.total == 24;
		boolean rightNumSquares = testCage.getValues().length == 3;
		
		assertTrue(rightType && rightTotal && rightNumSquares);
	}
	
	@Test
	public void testAddAddCage() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("Add 6 2 0,3 0,4");
		List<Grid.Cage> cage = tester.getCages();
		
		Grid.Cage testCage = cage.get(0);
		
		boolean rightType = testCage instanceof Grid.AddCage;
		boolean rightTotal = testCage.total == 6;
		boolean rightNumSquares = testCage.getValues().length == 2;
		
		assertTrue(rightType && rightTotal && rightNumSquares);
	}
	
	@Test
	public void testAddSubtractCage() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("Subtract 3 2 1,4 2,4");
		List<Grid.Cage> cage = tester.getCages();
		
		Grid.Cage testCage = cage.get(0);
		
		boolean rightType = testCage instanceof Grid.SubtractCage;
		boolean rightTotal = testCage.total == 3;
		boolean rightNumSquares = testCage.getValues().length == 2;
		
		assertTrue(rightType && rightTotal && rightNumSquares);
	}
	
	@Test
	public void testAddDivideCage() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("Divide 2 2 2,3 3,3");
		List<Grid.Cage> cage = tester.getCages();
		
		Grid.Cage testCage = cage.get(0);
		
		boolean rightType = testCage instanceof Grid.DivideCage;
		boolean rightTotal = testCage.total == 2;
		boolean rightNumSquares = testCage.getValues().length == 2;
		
		assertTrue(rightType && rightTotal && rightNumSquares);
	}
	
	@Test
	public void testAddEqualCage() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("Equal 4 1 2,3");
		List<Grid.Cage> cage = tester.getCages();
		
		Grid.Cage testCage = cage.get(0);
		
		boolean rightType = testCage instanceof Grid.EqualCage;
		boolean rightTotal = testCage.total == 4;
		boolean rightNumSquares = testCage.getValues().length == 1;
		
		assertTrue(rightType && rightTotal && rightNumSquares);
	}
	
	@Test(expected=InvalidInitializationException.class)
	public void testAddInvalidCage() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("HAHACOOL 4 1 2,3");
		List<Grid.Cage> cage = tester.getCages();
		
		boolean noCages = cage.size() == 0;
		
		assertTrue(noCages);
	}
	
	@Test(expected = InvalidInitializationException.class)
	public void testAddMissingPoints() throws InvalidInitializationException {
		Grid tester = new Grid(5);
		tester.addCage("Multiply 4 2 2,3");
	}
	
	@Test
	public void loadEntireGrid() throws InvalidInitializationException {
		Scanner sc = null;
		try {
			sc = new Scanner(new File("testInput.txt"));
		} catch (FileNotFoundException ex) {
			fail("Did not successfully make grid");
		}
		
		int gridSize = Integer.parseInt(sc.nextLine());
		int numCages = Integer.parseInt(sc.nextLine());
		
		Grid testGrid = new Grid(gridSize);
		while( sc.hasNextLine() ) {
			testGrid.addCage(sc.nextLine());
		}
		
		boolean rightNumCages = testGrid.getCages().size() == numCages;
		boolean allSquaresInit = testGrid.isInitialized();
		
		assertTrue(rightNumCages && allSquaresInit);
	}

}
