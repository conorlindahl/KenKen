package kenken;

import java.util.Scanner;

import input.Input;
import input.InputFromFile;

public class Main {

	public static void main(String[] args)  {
		Input i;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a file name: ");
		String fileName = sc.nextLine();
		i = new InputFromFile(fileName);
		
		Solver s = null;
		try {
			s = new RecursiveSolver(i.getKenKenString());
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
