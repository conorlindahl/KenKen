package kenken;

public interface Solver {
	public void solve(); // Called to cause Solver to solve
	public boolean isSolved();
	public int[][] getKenKen(); // Returns int[][] representation of KenKen
}
