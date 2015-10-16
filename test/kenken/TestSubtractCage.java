package kenken;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestSubtractCage extends TestCageGeneric {
	public static Grid testGrid;
	
	@BeforeClass
	public static void setUpGrid() {
		testGrid = new Grid(5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullSquareException() {
		Grid.SubtractCage tester = testGrid.new SubtractCage(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArrayException() {
		Grid.SubtractCage tester = 
				testGrid.new SubtractCage(new Grid.Square[0], 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSingleValueException() {
		Grid.SubtractCage tester = 
				testGrid.new SubtractCage(new Grid.Square[1], 1);
	}
	
	@Test
	public void testOptionsTwoValueDiffOne() {
		Grid.Square[] v = new Grid.Square[2];
        Grid.SubtractCage tester = testGrid.new SubtractCage(v, 1);
        
        int[][] result = new int[8][2];
        result[0] = new int[]{1,2};
        result[1] = new int[]{2,1};
        result[2] = new int[]{2,3};
        result[3] = new int[]{3,2};
        result[4] = new int[]{3,4};
        result[5] = new int[]{4,3};
        result[6] = new int[]{4,5};
        result[7] = new int[]{5,4};
        
        int[][] combResult = tester.getOptions().toArray(new int[0][0]);
        
        assertTrue(testOptionEquality(result, combResult));
	}
	
	@Test
	public void testOptionsTwoValueDiffFour() {
		Grid.Square[] v = new Grid.Square[2];
        Grid.SubtractCage tester = testGrid.new SubtractCage(v, 4);
        
        int[][] result = new int[2][2];
        result[0] = new int[]{1,5};
        result[1] = new int[]{5,1};
        
        int[][] combResult = tester.getOptions().toArray(new int[0][0]);
        
        assertTrue(testOptionEquality(result, combResult));
	}
}
