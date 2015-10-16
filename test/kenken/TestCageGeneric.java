package kenken;

import java.util.Arrays;


public class TestCageGeneric {

	protected boolean testOptionEquality(int[][] first, int[][] second) {
		boolean trial = true;
		for(int[] x : first) {
			trial = false;
			for(int[] y : second) {
				if(Arrays.equals(x, y)) {
					trial = true;
				}
			}
			if ( trial == false ) {
				break;
			}
		}
		
		return trial;
	}
}
