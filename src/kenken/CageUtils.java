package kenken;

public class CageUtils {
    /** 
     * Attempts to find a value, at index n in nums, such that
     * nums[n] - (all other values in nums) == total. 
     * 
     * Precondition: All values in nums must be greater than zero or
     * results are undefined
     * 
     * @return True if some combination works, false otherwise
     * 
     * @param int[] nums - the integer values to attempt to combine
     * @param int total - the value to be computed
     * 
     */
	//TODO: Check for total < 0
	public static boolean combinationDiff(int[] nums, int total) {
		if(nums == null || nums.length == 0 ) {
			return total == 0 ? true : false;
		}
		
		for(int i=0; i<nums.length; i++) {
			int diff = nums[i];
			for(int n=0; n<nums.length; n++) {
				if(n == i) {
					continue;
				}
				diff -= nums[n];
				if(diff < total) {
					break;
				}
			}
			if(diff==total) {
				return true;
			}
		}
		return false;
    }
	
	/**
	 * Sums the contents of nums, and returns whether the sum
	 * is equal to total. 
	 *  
	 * @param nums
	 * @param total
	 * @return True if the sum of values in nums equals total,
	 * false otherwise
	 */
	public static boolean sumEquals(int[] nums, int total) {
		if(nums == null || nums.length == 0) {
			return total == 0 ? true : false;
		}
		
		int sum=0;
		for(int i : nums) {
			sum+=i;
		}
		return sum == total;
	}
	
	/**
	 * Determines if some combination of the values in nums satisfies
	 * the equation total == nums[i] / (all other values in num)
	 * 
	 * @param nums
	 * @param total
	 * @return True if some combination satisfies the equation, false
	 * otherwise
	 * 
	 * @throws IllegalArgumentException if nums.length is less than 2 or
	 * if total is less than or equal to zero.
	 */
	public static boolean combinationDiv(int[] nums, int total) {
		if(nums == null || nums.length <= 1) {
			throw new IllegalArgumentException("Need at least two numbers to " 
			              + "calculate a division");
		}
		if(total <= 0){
			throw new IllegalArgumentException("Total must be greater than 0");
		}
		
		int max = nums[0];
		int maxIndex = 0;
		for(int i=1; i<nums.length; i++) {
			if(nums[i] > nums[maxIndex]) {
				max = nums[i];
				maxIndex = i;
			}
		}
		
		int divisor=1;
		for(int n=0; n<nums.length; n++) {
			if(n==maxIndex) {
				continue;
			}
			divisor *= nums[n];
		}
		
		return (total == (max/divisor)) && (max%divisor == 0);
	}
	
	/**
	 * Determines if the product of all the values in nums is 
	 * equals to the value of total
	 * 
	 * @param nums
	 * @param total
	 * @return True if the product of the values in nums is equal
	 * to total, false otherwise. Returns true on an empty or null
	 * array only if total is zero.
	 */
	public static boolean productEquals(int[] nums, int total) {
		if (nums==null || nums.length == 0) {
			return total == 0 ? true : false;
		}
		
		int product = nums[0];
		for(int i=1; i<nums.length; i++) {
			product*=nums[i];
		}
		return product == total;
	}
	
}
