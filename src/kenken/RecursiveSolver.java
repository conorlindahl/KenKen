package kenken;

import java.util.List;
import java.util.Set;

public class RecursiveSolver extends Solver {
	
	public RecursiveSolver(String kenkenString) 
			throws InvalidInitializationException {
		super(kenkenString);
		solved = false;
	}
	
	public void solve() {
		solved = recurseSolve(0, kenken.getCages(), kenken);
	}
	
	private static boolean recurseSolve(int cageNumber, List<Grid.Cage> cages, Grid grid) {
		if( cageNumber == cages.size() ) {
			// Verify grid is valid
			return grid.isFilled();
		}
		
		Grid.Cage current = cages.get(cageNumber);
		
		/* Step 1: get the options for cages */
		Set<int[]> options = current.getOptions();
		
		/* Step 2: choose next option to test 
		 *          if out of steps -> step 4*/
		for(int[] i : options) {
			current.fillValues(i);
			/* Step 3: if test works -> return true
			 *         else -> Step 2
			 */
			if ( !grid.isValid() ) { // If grid breaks, don't want this value
				continue;
			}
			
			if (recurseSolve(cageNumber+1, cages, grid) ) {
				return true;
			}
		}
		
		/* Step 4: reset squares & return false
		 */
		current.reset();
		return false;
	}
}
