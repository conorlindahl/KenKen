package kenken;

import input.Input;
import input.InputFromFile;

public class Main {

	public static void main(String[] args)  {
		Input i = new InputFromFile();
		String kenkenString = null;
		while(kenkenString == null) {
			i.specifySource();
			kenkenString = i.getKenKenString();
		}
		
		Solver s = null;
		try {
			s = new RecursiveSolver(kenkenString);
		} catch (InvalidInitializationException ex) {
			System.err.println("Something went wrong");
		}
		
		s.solve();
		if(s.isSolved()) {
			int[][] k = s.getKenKen();
			
			for(int[] x : k) {
				for(int y : x) {
					System.out.print(y + " ");
				}
				System.out.println();
			}
		}
	}
}
