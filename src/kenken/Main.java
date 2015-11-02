package kenken;

import input.Input;
import input.InputFromFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

	private static String fileName = "easySizeFive.txt";
	
	public static void main(String[] args) throws IOException, InvalidInitializationException {

		String kenkenString = null; 
		while(kenkenString == null) {
			Input i = new InputFromFile();
			i.specifySource();
			kenkenString = i.getKenKenString();
		}
		
		Scanner sc = new Scanner(kenkenString);
		
		int kenkenSize = Integer.parseInt(sc.nextLine());
		Grid kenken = new Grid(kenkenSize);
		int numCages = Integer.parseInt(sc.nextLine());

		while(sc.hasNextLine()) {
			String cage = sc.nextLine();
			kenken.addCage(cage);
		}
		
		List<Grid.Cage> cages = kenken.getCages();
		
		boolean rightNumCages = cages.size() == numCages;
		boolean fullyInitialized = kenken.isInitialized();
		
		if(!rightNumCages || !fullyInitialized) {
			throw new InvalidInitializationException(
					"\nRight number of cages: " + rightNumCages + "\n"
					+ "Fully Initialized: " + fullyInitialized);
		}
		
		if ( recurseSolve(0, cages, kenken) ) {
			kenken.print();
		} else {
			System.out.println("No solution");
		}
		
		
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

class InvalidInitializationException extends Exception {
	public InvalidInitializationException(String a) {
		super(a);
	}
}