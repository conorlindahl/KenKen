package kenken;

import java.util.List;
import java.util.Scanner;

abstract public class Solver {
	protected Grid kenken;
	protected boolean solved;
	
	public Solver(String description) throws InvalidInitializationException {
		init(description);
	}
	
	
	abstract public void solve(); // Called to cause Solver to solve
	
	public boolean isSolved() {
		return solved;
	}
	
	public int[][] getKenKen() {
		return kenken.getRepresentation();
	}
	
	private void init(String kenkenString) 
			throws InvalidInitializationException {
		
		if(kenkenString == null) {
			return;
		}
		
		Scanner sc = new Scanner(kenkenString);
		
		try {
			int kenkenSize = Integer.parseInt(sc.nextLine());
			kenken = new Grid(kenkenSize);
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
		} catch (NumberFormatException ex) {
			throw new InvalidInitializationException("Error in format of "
					+ "kenken String.");
		} finally {
			sc.close();
		}
	}
}
