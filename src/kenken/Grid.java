package kenken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
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
		init();
	}
	
	public int size() {
		return size;
	}
	
	/**
	 * Constructs a Cage for the kenken grid based on the description
	 * given in the String.<br>
	 * The description is expected to be of the following form:
	 * <idenifier> <total> <# of Squares> <space separated list of points><br>
	 * <b>Identifiers</b><br>
	 * = for Single square cages where the value for the square is given<br>
	 * + for a cage who's total is made by addition<br>
	 * - for a cage who's total is made by subtraction<br>
	 * x for a cage who's total is made by multiplication<br>
	 * / for a cage who's total is made by division<br>
	 * 
	 * @param description
	 */
	public void addCage(String description) {
		Scanner sc = new Scanner(description);
		String type = sc.next();
		int total = sc.nextInt();
		int numSquares = sc.nextInt();
		Square[] cageSquares = new Square[numSquares];
		
		try {
			for(int i=0; i<numSquares; i++) {
				String pointString = sc.next();
				String[] point = pointString.split(",");
				int x = Integer.parseInt(point[0]);
				int y = Integer.parseInt(point[1]);
				cageSquares[i] = grid[x][y];
			}
		} catch(NoSuchElementException ex) {
			throw new NoSuchElementException("Not enough points for creation of"
					+ " cage");
		} finally {
			sc.close(); // No longer need the scanner
		}
		
		if("=".equals(type)) {
			cages.add(new EqualCage(cageSquares, total));
		} else if("+".equals(type)) {
			cages.add(new AddCage(cageSquares, total));
		} else if("-".equals(type)) {
			cages.add(new SubtractCage(cageSquares, total));
		} else if("x".equals(type)) {
			cages.add(new MultiplyCage(cageSquares, total));
		} else if("/".equals(type)) {
			cages.add(new DivideCage(cageSquares, total));
		}
	}
	
	public List<Cage> getCages() {
		return Collections.unmodifiableList(cages);
	}
	
	private void init() {
		for(int x=0; x<size; x++) {
			for(int y=0; y<size; y++) {
				grid[x][y] = new Square();
			}
		}
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
		boolean rowsValid = checkRows();
		boolean colsValid = checkCols();
		
		return rowsValid && colsValid;
	}
	
	private boolean checkRows() {
		for(int i=0; i<size; i++) {
			if (checkRow(i) == false) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkCols() {
		for(int i=0; i<size; i++) {
			if (checkCol(i) == false ) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkRow(int row) {
		boolean[] nums = new boolean[size]; // Representing all needed numbers
		
		for(int i=0; i<nums.length; i++) { // Initialization
			nums[i] = false;
		}

		for(int i=0; i<size; i++) { 
			int val = grid[row][i].value;
			if(val == 0) {
				continue;
			}
			nums[val-1] = true; // If value is filled, set it's index to true
		}
		
		for(boolean i : nums) { // If any aren't set, then not a valid row
			if(!i) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkCol(int col) {
		boolean[] nums = new boolean[size]; // Representing all needed numbers
		
		for(int i=0; i<nums.length; i++) { // Initialization
			nums[i] = false;
		}

		for(int i=0; i<size; i++) {
			int val = grid[i][col].value;
			if(val == 0) {
				continue;
			}
			nums[val-1] = true; // If value is filled, set it's index to true
		}
		
		for(boolean i : nums) { // If any aren't set, then not a valid row
			if(!i) {
				return false; 
			}
		}
		return true;
	}
	
	public boolean isValid() {
		boolean validRows = areRowsValid();
		boolean validColumns = areColsValid();
		
		return validRows && validColumns;
	}

	private boolean areRowsValid() {
		for(int i=0; i<size; i++) {
			if(!isValidRow(i)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean areColsValid() {
		for(int i=0; i<size; i++) {
			if(!isValidCol(i)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isValidRow(int row) {
		Square[] validate = grid[row];
		boolean[] nums = new boolean[size]; // Represent used numbers
		
		for(int i=0; i<nums.length; i++) { // None used yet
			nums[i] = false;
		}
		
		for(int i=0; i<size; i++) {
			int number = validate[i].value; // Number in square
			if(number == 0) { // Square not used
				continue;
			}
			if(nums[number-1]) { // Number already exists in row
				return false;
			}
			nums[number-1] = true; // Set number to used
		}
		return true; // If we make it out, is successful
	}
	
	private boolean isValidCol(int col) {
		boolean[] nums = new boolean[size]; // Represents used numbers
		
		for(int i=0; i<nums.length; i++) { // None used yet
			nums[i] = false;
		}
		
		for(int i=0; i<size; i++) {
			int number = grid[i][col].value; // Get the value at that spot
			if(number == 0) { // Square not used yet
				continue;
			}
			if( nums[number-1] ) { // See if number already used
				return false;
			}
			nums[number-1] = true; // Set number to used
		}
		return true; // True if we make it out
	}
	
	public void print() {
		for(int i=0; i<size; i++) {
			for(int n=0; n<size; n++) {
				System.out.print(grid[i][n].value + " ");
			}
			System.out.println();
		}
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
			validateOptions();
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
			validateOptions();
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
			validateOptions();
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
			validateOptions();
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
