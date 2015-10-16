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
		ArrayList x = new ArrayList();
		java.util.Random r = new java.util.Random();
		for (int i=0; i < 10; i++ ) {
			int add = r.nextInt(100);
			x.add(add);
		}
		
		/* Set int[] and total for function */
		int[] nums = new int[x.size()];
		int total = 0;
		for(int i=0; i<x.size(); i++) {
			nums[i] = (int) x.get(i);
			total += nums[i];
		}
		
		assertTrue(CageUtils.sumEquals(nums, total));
	}

}
