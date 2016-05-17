import java.util.ArrayList;


public class UniqueQueenSolutionFinder {

	private Board b;
	private ArrayList<Board> qSolutions = new ArrayList<Board>();	// Keep track of all solutions with just queens
	private ArrayList<Board> qUniqueSolutions = new ArrayList<Board>();	// Keep track of all unique solutions

	public UniqueQueenSolutionFinder(Board b) {
		this.b = b;
	}
	
	// Code to find all non-isomorphic solutions for the n x n queens problem
	public void findSolutionsQueen(int row) {		
		// Base case #1
		if (b.numSpecifiedQueens() == 0) {
			// Add blank board to solution set and move on
			qSolutions.add(b);
			qUniqueSolutions.add(b);
			return;
		}
		
		// Base case #2
		if (b.numSpecifiedQueens() == b.numQueensOnBoard()) {	// All required queens have been placed
			qSolutions.add(b.copy());
			return;
		} else if (row == b.getDimension()) {	// Board has run out
			return;
		}
		
		for (int col = 0; col < b.getDimension(); col++) {
			if (b.notAttacked(row, col)) {
				b.placeQueen(row, col);
				findSolutionsQueen(row + 1);
				b.removeQueen(row, col);
			} 
		}
	}
	
	public void findUniqueSolutionsQueen() {
		qUniqueSolutions.clear();
		boolean unique = true;
		// Find unique solutions
		for (int i = 0; i < qSolutions.size(); i++) {
			Board solution = qSolutions.get(i);
			for (int j = 0; j < qUniqueSolutions.size(); j++) {
				Board uniqueSolution = qUniqueSolutions.get(j);
				if (uniqueSolution.equals(solution)) {
					unique = false;
					break;
				}
			}
			
			if (unique) qUniqueSolutions.add(solution);
			unique = true;
		}
		
	}
	
	// Getters
	public ArrayList<Board> getQSolutions() {
		return this.qSolutions;
	}
	
	public ArrayList<Board> getUQSolutions() {
		return this.qUniqueSolutions;
	}
	
	public static void main(String[] args) {
		Board b = new Board(5, 7, 0, 0, 0, 0);
		UniqueQueenSolutionFinder q = new UniqueQueenSolutionFinder(b);
		q.findSolutionsQueen(0);
		q.findUniqueSolutionsQueen();
		
		System.out.println(q.getQSolutions().size());
		System.out.println(q.getUQSolutions().size());
		try {
			q.getUQSolutions().get(0).draw();
		} catch (Exception e) {
			System.out.println("No solution!");
		}
	}

}
