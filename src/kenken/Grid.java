package kenken;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Grid {
	
	protected Square[][] grid;
	protected List<Cage> cages;
	protected int size;
	
	public Grid(int size) {
		grid = new Square[size][size];
		this.size = size;
		cages = new ArrayList<Cage>();
	}
	
	public int size() {
		return size;
	}
	
	/**
	 * Constructs a Cage for the kenken grid based on the description
	 * given in the String.<br>
	 * The description is expected to be of the following form:
	 * <operation> <total> <# of Squares> <space separated list of points><br>
	 * <br>
	 * <b>Operations:</b><br>
	 * <b><i>Subtract<br>
	 * Add<br>
	 * Multiply<br>
	 * Divide<br>
	 * Equal</b> (For when a Cage consists of one square)</i><br>
	 * 
	 * 
	 * @param description
	 * @throws NoSuchMethodException 
	 */
	public void addCage(String description) throws InvalidInitializationException {
		Scanner sc = new Scanner(description);
		String operation = sc.next();
		int total = sc.nextInt();
		int numSquares = sc.nextInt();
		Square[] cageSquares = new Square[numSquares];
		
		try {
			for(int i=0; i<numSquares; i++) {
				String pointString = sc.next();
				String[] point = pointString.split(",");
				int x = Integer.parseInt(point[0]);
				int y = Integer.parseInt(point[1]);
				grid[x][y] = new Square();
				cageSquares[i] = grid[x][y];
			}
		} catch(Exception ex) {
			String message = "Error parsing line: " + description + "\n" +
					"Did not construct cage.";
			throw new InvalidInitializationException(message);
		} finally {
			sc.close(); // No longer need the scanner
		}
		
		try {
			Class<?> cageType = Class.forName("kenken.Grid$" + operation + "Cage");
			Constructor<?> cageConstructor = cageType.getConstructor(Grid.class, Square[].class, int.class);
			Cage newCage = (Cage) cageConstructor.newInstance(Grid.this, cageSquares, total);
			cages.add(newCage);
		} catch (Exception ex) {
			throw new InvalidInitializationException("Unable to construct " +
					operation + "Cage");
		}
	}
	
	public List<Cage> getCages() {
		return Collections.unmodifiableList(cages);
	}
	
	public boolean isInitialized() {
		boolean isInitialized = true;
		for(Square[] i : grid) {
			for(Square a : i) {
				if (a == null) {
					isInitialized = false;
				}
			}
		}
		return isInitialized;
	}
	
	public boolean isFilled() {
		boolean rowsValid = isEachRowFilled();
		boolean colsValid = isEachColFilled();
		
		return rowsValid && colsValid;
	}
	
	private boolean isEachRowFilled() {
		int currentRow=0;
		while(currentRow<size && isRowFilled(currentRow)) {
			currentRow+=1;
		}
		return currentRow == size;
	}
	
	private boolean isEachColFilled() {
		int currentCol=0;
		while(currentCol<size && isColFilled(currentCol)) {
			currentCol+=1;
		}
		return currentCol == size;
	}
	
	private boolean isRowFilled(int row) {
		boolean isValid = true;
		int i=0;
		while(isValid && i<size) {
			int currentNum = grid[row][i].value;
			if(currentNum <= 0 || currentNum > size) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	private boolean isColFilled(int col) {
		boolean isValid = true;
		int i=0;
		while(isValid && i<size) {
			int currentNum = grid[i][col].value;
			if(currentNum <= 0 || currentNum > size) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	public boolean isValid() {
		boolean validRows = isEachRowValid();
		boolean validColumns = isEachColValid();
		
		return validRows && validColumns;
	}

	private boolean isEachRowValid() {
		for(int i=0; i<size; i++) {
			if(!isRowValid(i)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isEachColValid() {
		for(int i=0; i<size; i++) {
			if(!isColValid(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A valid row is a row that doesn't have any repeating numbers. That is,
	 * The row [ 1, 2, 3, 4, 5 ] is valid,
	 * as is the row [ 1, 0, 3, 4, 0 ],
	 * as is the row [ 0, 0, 0, 0, 0 ].
	 * 
	 * @param int row
	 */
	private boolean isRowValid(int row) {
		boolean[] used = new boolean[size]; // Represent used numbers
		
		for(int i=0; i<used.length; i++) { // None used yet
			used[i] = false;
		}
		
		int i=0;
		boolean valid = true;
		while(valid && i<size) {
			int currentNum = grid[row][i].value;
			if(currentNum == 0) {
				// Unconcerned with squares whose value is zero
				i+=1;
				continue;
			}
			
			valid = used[currentNum-1] ? false : true;
			used[currentNum-1] = true;
			i+=1;
		}
		
		return valid;
	}
	
	private boolean isColValid(int col) {
		boolean[] used = new boolean[size]; // Represents used numbers
		
		for(int i=0; i<used.length; i++) { // None used yet
			used[i] = false;
		}
		
		int i=0;
		boolean valid = true;
		while(valid && i<size) {
			int currentNum = grid[i][col].value;
			if(currentNum == 0) {
				// Unconcerned with squares whose value is zero
				i+=1;
				continue;
			}
			
			valid = used[currentNum-1] ? false : true;
			used[currentNum-1] = true;
			i+=1;
		}
		
		return valid;
	}
	
	public int[][] getRepresentation() {
		int[][] rep = new int[size][size];
		for(int i=0; i<size; i++) {
			for(int n=0; n<size; n++) {
				rep[i][n] = grid[i][n].value;
			}
		}
		return rep;
	}
	
	public class Square {
		public int value=0;
	}

	public abstract class Cage {
		protected Square[] values;
		protected Set<int[]> options;
		protected int total;
		
		public boolean isFilled() {
			for(Square v : values) {
				if (v.value == 0) {
					return false;
				}
			}
			return true;
		}
		
		public final Set<int[]> getOptions() {
			return Collections.unmodifiableSet(options);
		}
		
		public final Square[] getValues() {
			return values;
		}
		
		public void fillValues(int[] v) {
			/* Ensuring correct number of elements passed */
			if(v.length != values.length) {
				throw new IllegalArgumentException("This cage has " + values.length  
			                                        + " values");
			}
			
			/* Ensuring that passed values are allowed for this Cage */
			boolean validOption = false;
			for(int[] o : options) {
				if ( Arrays.equals(o, v) ) {
					validOption = true;
				}
			}
			if(!validOption) {
				throw new IllegalArgumentException("The passed array is not an "
				          + "option for this Cage.");
			}
			
			/* Filling values into squares */
			for(int i=0; i<values.length; i++) {
				values[i].value = v[i];
			}
		}
		
		public void reset() {
			for(Square i : values) {
				i.value = 0;
			}
		}
		
		abstract protected void calcPossibilities();
		
		/**
		 * Modifies a to represent next Option in list. 
		 * Option is expected to be an integer array that
		 * begins with all values set to one, with the last
		 * option being all values set to the max value for
		 * the grid.<br>
		 * <br>
		 * Example:<br>
		 * Consider a 5x5 Grid. For a 3 square Cage, the options
		 * would begin {1,1,1}. After many nextOption(int[]) calls
		 * the options may look like {5,3,2}. For the subsequent call
		 * the options would look like {1,4,2}. All options are
		 * used once the option array looks like {5,5,5}.
		 */
		protected void nextOption(int[] a) {
			a[0]+=1;
			if(a[0] > Grid.this.size) {
				rollover(a);
			}
		}
		/**
		 * Utility function to help calcPossibilities
		 * 
		 * Takes an array a that represent options for this Cage and
		 * rolls over a value<br>
		 * <br>
		 * Examples:<br>
		 * 1) {5,1,1} -> {1,2,1}<br>
		 * 2) {5,5,2} -> {1,1,3}<br>
		 * 3) {5,5,5} -> ArrayIndexOutOfBoundException
		 */
		protected void rollover(int[] a) {
			int i=0;
			while(a[i] >= Grid.this.size){
				a[i] = 1; // Resets value in array
				i++;
			}
			a[i] += 1; // First value that is < maxValue catches the rollover
		}
		
		protected boolean moreOptionsAvailable(int[] a) {
			boolean moreOptions = false;
			for(int i : a) {
				if ( i != Grid.this.size) {
					moreOptions = true;
					break;
				}
			}
			return moreOptions;
		}
		
		protected void validateOptions() {
			Set<int[]> removeThese = new HashSet<int[]>();
			
			for(int[] i : options) {
				fillValues(i);
				if(!Grid.this.isValid()) {
					removeThese.add(i);
				}
				reset();
			}
			
			options.removeAll(removeThese);
		}
	}
	
	public class SubtractCage extends Cage {
		
		public SubtractCage(Square[] squares, int total) {
			if ( squares == null || squares.length < 2) {
				throw new IllegalArgumentException("A subtraction cage must have"
						+ " at least two squares");
			}
			this.values = squares;
			this.total = total;
			calcPossibilities();
//			validateOptions();
		}
		
		protected void calcPossibilities() {
			Set<int[]> oList = new HashSet<int[]>();
			
			/* Number of squares == number of values per option */
			int numSquares = this.values.length;
			int[] opt = new int[numSquares];
			
			/* Initialize options */
			for(int i=0; i<opt.length; i++) {
				opt[i] = 1; // starting value
			}
			opt[0] = 0;
			
			/* calculate */
			while (moreOptionsAvailable(opt)) {
				nextOption(opt);
				if(CageUtils.combinationDiff(opt, total)) {
					oList.add(Arrays.copyOf(opt, opt.length));
				}
			}
			
			this.options = oList;
		}
	}

	public class AddCage extends Cage {
		
		public AddCage(Square[] squares, int total) {
			if(squares == null || squares.length <= 1) {
				throw new IllegalArgumentException("An addition cage must"
						+ " have at least two values");
			}
			this.values = squares;
			this.total = total;
			calcPossibilities();
//			validateOptions();
		}
		
		protected void calcPossibilities() {
			Set<int[]> oList = new HashSet<int[]>();
			
			/* Number of squares == number of values per option */
			int numSquares = this.values.length;
			int[] opt = new int[numSquares];
			
			/* Initialize options */
			for(int i=0; i<opt.length; i++) {
				opt[i] = 1; // starting value
			}
			opt[0] = 0;
			
			/* calculate */
			while (moreOptionsAvailable(opt)) {
				nextOption(opt);
				if(CageUtils.sumEquals(opt, total)) {
					oList.add(Arrays.copyOf(opt, opt.length));
				}
			}
			
			this.options = oList;
		}
	}
	
	public class MultiplyCage extends Cage {
		
		public MultiplyCage(Square[] squares, int total) {
			if(squares == null || squares.length < 2) {
				throw new IllegalArgumentException("A multiply cage must have"
						+ " at least two squares");
			}
			this.values = squares;
			this.total = total;
			calcPossibilities();
//			validateOptions();
		}
		
		protected void calcPossibilities() {
			Set<int[]> oList = new HashSet<int[]>();
			
			/* Number of squares == number of values per option */
			int numSquares = this.values.length;
			int[] opt = new int[numSquares];
			
			/* Initialize options */
			for(int i=0; i<opt.length; i++) {
				opt[i] = 1; // starting value
			}
			opt[0] = 0;
			
			/* calculate */
			while (moreOptionsAvailable(opt)) {
				nextOption(opt);
				if(CageUtils.productEquals(opt, total)) {
					oList.add(Arrays.copyOf(opt, opt.length));
				}
			}
			
			this.options = oList;
		}
	}
	
	public class DivideCage extends Cage {
		
		public DivideCage(Square[] squares, int total) {
			if(squares == null || squares.length < 2) {
				throw new IllegalArgumentException("A divide cage needs at "
						+ "least two squares");
			}
			this.values = squares;
			this.total = total;
			calcPossibilities();
//			validateOptions();
		}
		
		protected void calcPossibilities() {
			Set<int[]> oList = new HashSet<int[]>();
			
			/* Number of squares == number of values per option */
			int numSquares = this.values.length;
			int[] opt = new int[numSquares];
			
			/* Initialize options */
			for(int i=0; i<opt.length; i++) {
				opt[i] = 1; // starting value
			}
			opt[0] = 0;
			
			/* calculate */
			while (moreOptionsAvailable(opt)) {
				nextOption(opt);
				if(CageUtils.combinationDiv(opt, total)) {
					oList.add(Arrays.copyOf(opt, opt.length));
				}
			}
			
			this.options = oList;
		}
	}
	
	public class EqualCage extends Cage {
		public EqualCage(Square[] squares, int total) {
			if ( squares == null || squares.length != 1 ) {
				throw new IllegalArgumentException("An equal cage can only "
						+ "have one square");
			}
			if ( total > Grid.this.size || total <= 0 ) {
				throw new IllegalArgumentException(total + " must be between "
						+ Grid.this.size + " and 1");
			}
			
			this.values = squares;
			values[0].value = total;
			this.total = total;
			calcPossibilities();
		}
		
		protected void calcPossibilities() {
			Set<int[]> options = new HashSet<int[]>();
			options.add(new int[]{total});
			this.options = options;
		}
	}
}

class InvalidInitializationException extends Exception {
	public InvalidInitializationException(String a) {
		super(a);
	}
}