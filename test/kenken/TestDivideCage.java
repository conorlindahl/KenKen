package kenken;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestDivideCage extends TestCageGeneric {

	private static Grid testGrid;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		testGrid = new Grid(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArrayException() {
		Grid.DivideCage tester = testGrid.new DivideCage(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArrayException() {
		Grid.DivideCage tester = 
				testGrid.new DivideCage(new Grid.Square[0], 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSingleValueException() {
		Grid.DivideCage tester = 
				testGrid.new DivideCage(new Grid.Square[1], 0);
	}
	
	@Test
	public void testTwoValueQuotientGridSize() {
		int quotient = testGrid.size();
		Grid.DivideCage tester = 
				testGrid.new DivideCage(new Grid.Square[2], quotient);
		int[][] options = tester.getOptions().toArray(new int[0][0]);
		
		int[][] result = new int[2][2];
		result[0] = new int[]{1,5};
		result[1] = new int[]{5,1};
		
		assertTrue(testOptionEquality(result, options)
				&& result.length == options.length);
	}
	
	@Test
	public void testTwoValueGridSize() {
		int quotient = testGrid.size();
		Grid.DivideCage tester = 
				testGrid.new DivideCage(new Grid.Square[2], quotient);
		int[][] options = tester.getOptions().toArray(new int[0][0]);
		
		int[][] result = new int[2][2];
		result[0] = new int[]{1,5};
		result[1] = new int[]{5,1};
		
		assertTrue(testOptionEquality(result, options)
				&& result.length == options.length);
	}
	
	@Test
	public void testThreeValueQuotientTwo() {
		int quotient = 2;
		Grid.DivideCage tester = 
				testGrid.new DivideCage(new Grid.Square[3], quotient);
		int[][] options = tester.getOptions().toArray(new int[0][0]);
		
		int[][] result = new int[9][3];
		result[0] = new int[]{1,2,4};
		result[1] = new int[]{1,4,2};
		result[2] = new int[]{2,1,4};
		result[3] = new int[]{2,4,1};
		result[4] = new int[]{4,1,2};
		result[5] = new int[]{4,2,1};
		result[6] = new int[]{2,1,1};
		result[7] = new int[]{1,2,1};
		result[8] = new int[]{1,1,2};
		
		assertTrue(testOptionEquality(result, options));
	}

}
