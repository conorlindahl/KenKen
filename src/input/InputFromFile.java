package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputFromFile implements Input {

	private String fileName;
	
	@Override
	public void specifySource() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter file name: ");
		fileName = sc.nextLine();
		sc.close();
	}
	
	@Override
	public String getKenKenString() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException ex) {
			System.err.println("The file " + fileName + " was not found.");
			return null;
		}

		String kenkenString = "";
		while(sc.hasNextLine()) {
			kenkenString = kenkenString + sc.nextLine() + "\n";
		}
				
		sc.close(); // Done with sc
		
		return kenkenString;
	}
}
