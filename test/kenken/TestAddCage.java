package kenken;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestAddCage extends TestCageGeneric {

	public static Grid testGrid;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		testGrid = new Grid(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArrayException() {
		Grid.AddCage tester = testGrid.new AddCage(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArrayException() {
		Grid.AddCage tester = testGrid.new AddCage(new Grid.Square[0], 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSingleValueException() {
		Grid.AddCage tester = testGrid.new AddCage(new Grid.Square[1], 0);
	}
	
	@Test
	public void testAddTwoValueSumOne() {
		Grid.AddCage tester = testGrid.new AddCage(new Grid.Square[2], 1);
		int[][] result = new int[0][0];
		int[][] sumOption = tester.getOptions().toArray(new int[0][0]);
		assertTrue(testOptionEquality(result, sumOption) 
				&& result.length == sumOption.length);
	}
	
	@Test
	public void testAddTwoValuesSumGridSize() {
		int goalSum = testGrid.size();
		Grid.Square[] v = new Grid.Square[2];
        Grid.AddCage tester = testGrid.new AddCage(v, goalSum);
        
        int[][] result = new int[4][2];
        result[0] = new int[]{1,4};
        result[1] = new int[]{4,1};
        result[2] = new int[]{2,3};
        result[3] = new int[]{3,2};
        
        int[][] combResult = tester.getOptions().toArray(new int[0][0]);
        
        assertTrue(testOptionEquality(result, combResult)
        		&& result.length == combResult.length);
	}
	
	@Test
	public void testAddTwoValuesSumTwiceGridSize() {
		int goalSum = 2*testGrid.size();
		Grid.Square[] v = new Grid.Square[2];
        Grid.AddCage tester = testGrid.new AddCage(v, goalSum);
        
        int[][] result = new int[1][2];
        result[0] = new int[]{testGrid.size(), testGrid.size()};
        
        int[][] combResult = tester.getOptions().toArray(new int[0][0]);
        
        assertTrue(testOptionEquality(result, combResult)
        		&& result.length == combResult.length);
	}
}
