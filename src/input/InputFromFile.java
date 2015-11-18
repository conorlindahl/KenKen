package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputFromFile implements Input {

	private String fileName;
	private Scanner reader;
	
	public InputFromFile(String fn) {
		fileName = fn;
		
		try {
			reader = new Scanner(new File(fileName));
		} catch (FileNotFoundException ex) {
			System.out.println("File " + fileName + " not found.");
			reader = null;
		}
	}
	
	@Override
	public boolean isReady() {
		return reader != null;
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
