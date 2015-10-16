package kenken;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestProductEquals {

	@Test
	public void testNullArrayTrue() {
		assertTrue(CageUtils.productEquals(null, 0));
	}
	
	@Test
	public void testNullArrayFalse() {
		assertFalse(CageUtils.productEquals(null, 1));
	}
	
	@Test
	public void testEmptyArrayTrue() {
		assertTrue(CageUtils.productEquals(new int[0], 0));
	}
	
	@Test
	public void testEmptyArrayFalse() {
		assertFalse(CageUtils.productEquals(new int[0], 1));
	}
	
	@Test
	public void testSingleValueTrue() {
		assertTrue(CageUtils.productEquals(new int[]{20}, 20));
	}
	
	@Test
	public void testSingleValueFalse() {
		assertFalse(CageUtils.productEquals(new int[]{20}, 21));
	}
	
	@Test
	public void testManyValuesTrue() {
		int[] nums = new int[10];
		java.util.Random r = new java.util.Random();
		
		nums[0] = r.nextInt(100);
		int product = nums[0];
		for(int i=1; i<nums.length; i++){
			nums[i] = r.nextInt(100);
			product *= nums[i];
		}
		
		assertTrue(CageUtils.productEquals(nums, product));
	}

}
