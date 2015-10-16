package kenken;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestEqualCage {

	private static Grid testGrid;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testGrid = new Grid(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullSquareException() {
		Grid.EqualCage tester = testGrid.new EqualCage(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptySquareException() {
		Grid.EqualCage tester = testGrid.new EqualCage(new Grid.Square[0], 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValueTooHighException() {
		Grid.EqualCage tester = 
				testGrid.new EqualCage(new Grid.Square[1], testGrid.size()+1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValueTooLowException() {
		Grid.EqualCage tester = 
				testGrid.new EqualCage(new Grid.Square[1], 0);
	}
	
	@Test
	public void testSuccessfulCreation() {
		Grid.EqualCage tester =
				testGrid.new EqualCage(new Grid.Square[]{testGrid.new Square()},
						                       testGrid.size());
		assertTrue(tester.getValues()[0].value == testGrid.size());
	}

}
