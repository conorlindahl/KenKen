package kenken;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestValidGrid {

	@Test
	public void testSingleSquareNotValid() {
		TestGrid testGrid = new TestGrid(1);
		assertTrue(testGrid.isValid());
	}
	
	@Test
	public void testSingleSquareValid() {
		TestGrid testGrid = new TestGrid(1);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		assertTrue(testGrid.isValid());
	}
	
	@Test
	public void testGridSizeTwoValid() {
		TestGrid testGrid = new TestGrid(2);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		tester[0][1].value = 2;
		tester[1][0].value = 2;
		tester[1][1].value = 1;
		
		assertTrue(testGrid.isValid());
	}
	
	@Test
	public void testGridSizeTwoNotValid() {
		TestGrid testGrid = new TestGrid(2);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		tester[0][1].value = 1;
		tester[1][0].value = 1;
		tester[1][1].value = 1;
		
		assertFalse(testGrid.isValid());
	}
	
	@Test
	public void testGridSizeThreeValid() {
		TestGrid testGrid = new TestGrid(3);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		tester[0][1].value = 2;
		tester[0][2].value = 3;
		tester[1][0].value = 2;
		tester[1][1].value = 3;
		tester[1][2].value = 1;
		tester[2][0].value = 3;
		tester[2][1].value = 1;
		tester[2][2].value = 2;
		
		assertTrue(testGrid.isValid());
	}
	
	@Test
	public void testGridSizeThreeNotValidNotFull() {
		TestGrid testGrid = new TestGrid(3);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		tester[0][1].value = 2;
		tester[0][2].value = 3;
		tester[1][0].value = 2;
		tester[1][1].value = 3;
		tester[1][2].value = 1;
		tester[2][0].value = 3;
		tester[2][1].value = 1;
		tester[2][2].value = 0;
		
		assertTrue(testGrid.isValid());
	}
	
	@Test
	public void testGridSizeThreeNotValidColumnRepeat() {
		TestGrid testGrid = new TestGrid(3);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		tester[0][1].value = 2;
		tester[0][2].value = 3;
		tester[1][0].value = 1;
		tester[1][1].value = 2;
		tester[1][2].value = 3;
		tester[2][0].value = 1;
		tester[2][1].value = 2;
		tester[2][2].value = 3;
		
		assertFalse(testGrid.isValid());
	}
	
	@Test
	public void testGridSizeThreeNotValidRowRepeat() {
		TestGrid testGrid = new TestGrid(3);
		Grid.Square[][] tester = testGrid.getGrid();
		
		tester[0][0].value = 1;
		tester[0][1].value = 1;
		tester[0][2].value = 1;
		tester[1][0].value = 2;
		tester[1][1].value = 2;
		tester[1][2].value = 2;
		tester[2][0].value = 3;
		tester[2][1].value = 3;
		tester[2][2].value = 3;
		
		assertFalse(testGrid.isValid());
	}
}

class TestGrid extends Grid {
	
	public TestGrid(int size) {
		super(size);
		testInit();
	}
	
	public Square[][] getGrid() {
		return grid;
	}
	
	private void testInit() {
		for(int x=0; x<size; x+=1){
			for(int y=0; y<size; y+=1) {
				grid[x][y] = new Square();
				grid[x][y].value = 0;
			}
		}
	}
}
