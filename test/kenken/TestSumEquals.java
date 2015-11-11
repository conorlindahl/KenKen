package kenken;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestSumEquals {

	@Test
	public void testNullArrayTrue() {
		assertTrue(CageUtils.sumEquals(null, 0));
	}
	
	@Test
	public void testNullArrayFalse() {
		assertFalse(CageUtils.sumEquals(null, 1));
	}
	
	@Test
	public void testEmptyArrayTrue() {
		assertTrue(CageUtils.sumEquals(new int[0], 0));
	}
	
	@Test
	public void testEmptyArrayFalse() {
		assertFalse(CageUtils.sumEquals(new int[0], 1));
	}
	
	@Test
	public void testSingleValueSumTrue() {
		assertTrue(CageUtils.sumEquals(new int[]{5}, 5));
	}
	
	@Test
	public void testSingleValueSumFalse() {
		assertFalse(CageUtils.sumEquals(new int[]{5}, 4));
	}
	
	@Test
	public void testMultipleValueTrue() {
		/* Add random values to list */
		java.util.Random r = new java.util.Random();
		int[] nums = new int[10];
		int total = 0;
		for (int i=0; i < 10; i++ ) {
			nums[i] = r.nextInt(100);
			total += nums[i];
		}
		
		assertTrue(CageUtils.sumEquals(nums, total));
	}

}
