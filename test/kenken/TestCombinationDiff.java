package kenken;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCombinationDiff {

	@Test
	public void testNullCombinationTrue() {
		assertTrue(CageUtils.combinationDiff(null, 0));
	}

	@Test
	public void testNullCombinationFalse() {
		assertFalse(CageUtils.combinationDiff(null, 1));
	}
	
	@Test
	public void testEmptyCombinationTrue() {
		assertTrue(CageUtils.combinationDiff(new int[0], 0));
	}
	
	@Test
	public void testEmptyCombinationFalse() {
		assertFalse(CageUtils.combinationDiff(new int[0], 1));
	}
	
	@Test
	public void testSingleValueCombinationTrue() {
		assertTrue(CageUtils.combinationDiff(new int[]{1}, 1));
	}
	
	@Test
	public void testSingleValueCombinationFalse() {
		assertFalse(CageUtils.combinationDiff(new int[]{1}, 0));
	}
	
	@Test
	public void testFirstValueInCombinationTrue() {
		int[] tester = new int[]{15,1,3,5,2};
		// Total for 15 - all of tester is 4
		assertTrue(CageUtils.combinationDiff(tester, 4));
	}
	
	@Test
	public void testMiddleValueInCombinationTrue() {
		int[] tester = new int[]{9,3,4,54,3,4,6};
		// Total if select 54 is 25
		assertTrue(CageUtils.combinationDiff(tester, 25));
	}
	
	@Test
	public void testLastValueInCombinationTrue() {
		int[] tester = new int[]{1,2,3,4,5,30};
		// Total for last value is 15
		assertTrue(CageUtils.combinationDiff(tester, 15));
	}
	
	@Test
	public void testNoValueInCombination() {
		int[] tester = new int[]{1,2,3,4,5,6,7};
		// Make a total impossible to achieve
		assertFalse(CageUtils.combinationDiff(tester, 7));
	}
}
