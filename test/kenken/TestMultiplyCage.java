package kenken;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestMultiplyCage extends TestCageGeneric {

	private static Grid testGrid;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		testGrid = new Grid(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullArrayException() {
		Grid.MultiplyCage tester = testGrid.new MultiplyCage(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArrayException() {
		Grid.MultiplyCage tester = 
				testGrid.new MultiplyCage(new Grid.Square[0], 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSingleValueException() {
		Grid.MultiplyCage tester = 
				testGrid.new MultiplyCage(new Grid.Square[1], 0);
	}
	
	@Test
	public void testTwoValueProductNone() {
		Grid.MultiplyCage tester = testGrid.new MultiplyCage(new Grid.Square[2], 0);
		Set<int[]> result = new HashSet<int[]>();
		Set<int[]> options = new HashSet<int[]>(tester.getOptions());
		assertTrue(result.equals(options));
	}
	
	@Test
	public void testTwoValueProductFour() {
		int product = 4;
		Grid.MultiplyCage tester = 
				testGrid.new MultiplyCage(new Grid.Square[2], product);
		int[][] options = tester.getOptions().toArray(new int[0][0]);
		int[][] result = new int[3][2];
		
		result[0] = new int[]{1,4};
		result[1] = new int[]{4,1};
		result[2] = new int[]{2,2};
		
		assertTrue(testOptionEquality(result, options) 
				&& (options.length == result.length));
	}
	
	@Test
	public void testThreeValueProductFifteen() {
		int product = 15;
		Grid.MultiplyCage tester = 
				testGrid.new MultiplyCage(new Grid.Square[3], product);
		int[][] options = tester.getOptions().toArray(new int[0][0]);
		int[][] result = new int[6][3]; 
		
		result[0] = new int[]{1,3,5};
		result[1] = new int[]{1,5,3};
		result[2] = new int[]{3,1,5};
		result[3] = new int[]{3,5,1};
		result[4] = new int[]{5,1,3};
		result[5] = new int[]{5,3,1};
		
		assertTrue(testOptionEquality(result, options) 
				&& (result.length == options.length));
	}
}
