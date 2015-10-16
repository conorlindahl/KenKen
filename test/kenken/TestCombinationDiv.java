package kenken;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCombinationDiv {

	@Test(expected = IllegalArgumentException.class)
	public void testNullArgumentException() {
		CageUtils.combinationDiv(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArrayException() {
		CageUtils.combinationDiv(null, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSingleValueException() {
		CageUtils.combinationDiv(new int[]{1}, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeTotal() {
		CageUtils.combinationDiv(new int[]{1}, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testZeroValueTotal() {
		CageUtils.combinationDiv(new int[]{4,2}, 0);
	}
	
	@Test
	public void testTwoValueTrue() {
		assertTrue(CageUtils.combinationDiv(new int[]{2,1}, 2));
	}
	
	
	
	@Test
	public void testMultipleValuesFirstTrue() {
		assertTrue(CageUtils.combinationDiv(new int[]{60,2,3,5}, 2));
	}
	
	@Test
	public void testMultipleValuesMidTrue() {
		assertTrue(CageUtils.combinationDiv(new int[]{2,5,900,2,3}, 15));
	}
	
	@Test
	public void testMultipleValuesLastTrue() {
		assertTrue(CageUtils.combinationDiv(new int[]{2,2,2,800}, 100));
	}
	
	@Test
	public void testMultipleValuesNoneTrue() {
		assertFalse(CageUtils.combinationDiv(new int[]{2,3,6,7}, 4));
	}
}
