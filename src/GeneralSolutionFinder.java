import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class GeneralSolutionFinder {

	private Board b;
	private ArrayList<Board> finalSolution = new ArrayList<Board>();	// Keep track of a solution with several pieces
	
	public GeneralSolutionFinder(Board b) {
		this.b = b;
	}
	
	// Code to find a solution with several pieces	
	public void findSolution(int row) {
		findSolutionQueen(row);
	}
	
	public void findSolutionQueen(int row) {
		if (finalSolution.size() > 0) return;
		
		
		if (b.numSpecifiedQueens() == 0) { 
			findSolutionRook(0);
			return;
		} else if (b.numSpecifiedQueens() + b.numSpecifiedRooks() > b.getDimension()) { 
			return;
		} else if (b.numSpecifiedBishops() + b.numSpecifiedQueens() + b.numSpecifiedRooks() > b.getDimension() * b.getDimension()) {
			return;
		}
		
		if (b.numSpecifiedQueens() == b.numQueensOnBoard()) {	// All required queens have been placed
//			System.out.println("Searching for rooks...");
			findSolutionRook(0);
			return;	
		} else if (row == b.getDimension()) {	// Board has run out
			return;
		}
		
		for (int col = 0; col < b.getDimension(); col++) {
			if (b.notAttacked(row, col)) {
				b.placeQueen(row, col);
				findSolutionQueen(row + 1);
				b.removeQueen(row, col);
			} 
		}
	}
	
	public void findSolutionRook(int row) {
		if (finalSolution.size() > 0) return;
		
		if (b.numSpecifiedRooks() == b.numRooksOnBoard()) {
			findSolutionBishop(0);
			return;
		} else if (row == b.getDimension()) {
//			System.out.println("Search is over");
			return;	// Search is over
		}
		
		for (int col = 0; col < b.getDimension(); col++) {
//			System.out.println("Scanning (" + row + ", " + col + ")");			
			if (b.notAttacked(row, col)) {
				b.placeRook(row, col);
				findSolutionRook(row + 1);
				b.removeRook(row, col);
			} else if (col == b.getDimension() - 1 && row < b.getDimension()) {
				findSolutionRook(row + 1);
				b.removeRook(row, col);
			}
		}
	}
	
	public void findSolutionBishop(int index) {
		if (finalSolution.size() > 0) return;
		
		if (b.numSpecifiedBishops() == b.numBishopsOnBoard()) {
			findSolutionKnight(0);
			return;
		} else if (index == b.getDimension() * b.getDimension()) {
			return;
		}
		
		for (int i = index; i < b.getDimension() * b.getDimension(); i++) {
			Square s = b.getSquareList().get(i);
			if (b.notAttacked(s) && b.bNotAttackingR(s)) {
				b.placeBishop(s.getRow(), s.getCol());
				findSolutionBishop(index + 1);
				b.removeBishop(s.getRow(), s.getCol());
			}
		}
	}
	
	public void findSolutionKnight(int index) {
		if (finalSolution.size() > 0) return;
		
		if (b.numSpecifiedKnights() == b.numKnightsOnBoard()) {
			findSolutionKing(0);
			return;
		} else if (index == b.getDimension() * b.getDimension()) {
			return;
		}
		
		for (int i = index; i < b.getDimension() * b.getDimension(); i++) {
			Square s = b.getSquareList().get(i);
			if (b.notAttacked(s) && b.nNotAttackingP(s)) {
				b.placeKnight(s.getRow(), s.getCol());
				findSolutionKnight(index + 1);
				b.removeKnight(s.getRow(), s.getCol());
			}
		}
	}
	
	public void findSolutionKing(int index) {
		if (finalSolution.size() > 0) return;
		
		if (b.numSpecifiedKings() == b.numKingsOnBoard()) {
			finalSolution.add(b.copy());
			return;
		} else if (index == b.getDimension() * b.getDimension()) {
			return;
		}
		
		for (int i = index; i < b.getDimension() * b.getDimension(); i++) {
			Square s = b.getSquareList().get(i);
			if (b.notAttacked(s) && b.kNotAttackingP(s)) {
				b.placeKing(s.getRow(), s.getCol());
				findSolutionKing(index + 1);
				b.removeKing(s.getRow(), s.getCol());
			}
		}
	}
	
	public ArrayList<Board> getSolution() {
		return finalSolution;
	}
	
	public static void main (String[] args) {
		ArrayList<Board> b = new ArrayList<Board>();
		Board board = new Board(12, 13, 2, 4, 1, 2);
		GeneralSolutionFinder q = new GeneralSolutionFinder(board);
		q.findSolution(0);
		System.out.println(q.finalSolution.size());
		if (q.finalSolution.size() > 0) {
			q.finalSolution.get(0).draw();
		}
	}

}
