package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputFromFile implements Input {

	private String fileName;
	private Scanner reader = null;
	
	public InputFromFile(String fn) {
		fileName = fn;
		
		try {
			reader = new Scanner(new File(fileName));
		} catch (FileNotFoundException ex) {
			System.err.println("The file " + fileName + " was not found.");
			System.err.println("InputFromFile not initialized");
		}
	}
	
	@Override
	public String getKenKenString() {
		String kenkenString = "";
		while(reader.hasNextLine()) {
			kenkenString = kenkenString + reader.nextLine() + "\n";
		}
				
		reader.close(); // Done with sc
		
		return kenkenString;
	}
}
