import java.util.ArrayList;


public class Board3D {
	
	private int dim; // Dimension of the board
	private int numQueens; // Number of queens specified for this board
	private Square3D[][][] squares; // Squares that make up the board
	private ArrayList<Square3D> squaresList;	// Squares in ArrayList form
	
	// Constructor stuff
	public Board3D() {
		dim = 0;
		numQueens = 0;
		squares = new Square3D[dim][dim][dim];
		squaresList = new ArrayList<Square3D>();
		newSquares();
	}
	
	public Board3D(int dimension) {
		dim = dimension;
		numQueens = 0;
		squares = new Square3D[dim][dim][dim];
		squaresList = new ArrayList<Square3D>();
		newSquares();
	}
	
	public Board3D(int dimension, int queens) {
		dim = dimension;
		numQueens = queens;
		squares = new Square3D[dim][dim][dim];
		squaresList = new ArrayList<Square3D>();
		newSquares();
	}
	
	private void newSquares() {
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				for (int k = 0; k < dim; k++) {
					squares[i][j][k] = new Square3D(k, j, i);
					squaresList.add(squares[i][j][k]);
				}
			}
		}
	}
	
	// Placing and removing a queen
	public void placeQueen(Square3D s) {
		s.placeQueen();
	}
	
	public void removeQueen(Square3D s) {
		s.removeQueen();
	}
	
	// Safe squares
	public boolean isSafe(Square3D s) {
		boolean safe = true;
		for (int i = 0; i < listOfQueens().size(); i++) {
			Square3D square = listOfQueens().get(i);
			if (square.isParallel(s) || square.isDiagonal(s)) safe = false;
		}
		
		return safe;
	}
	
	// Retrieving queens
	public ArrayList<Square3D> listOfQueens() {
		ArrayList<Square3D> queens = new ArrayList<Square3D>();
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				for (int k = 0; k < dim; k++) {
					Square3D s = squares[i][j][k];
					if (s.hasQueen()) queens.add(s);
				}
			}
		}
		
		return queens;
	}
	
	public int getQueensOnBoard() {
		return listOfQueens().size();
	}
	
	public int numQueensOnBoard() {
		return numQueens;
	}
	
	public Square3D[][][] getSquares() {
		return squares;
	}
	
	public ArrayList<Square3D> getSquaresList() {
		return squaresList;
	}
	
	public int getDimension() {
		return dim;
	}
	
	// Solution finder
//	public void findSolution(int index) {
//		
//		if (finalSolution.size() > 0) {
//			return;
//		}
//		
//		if (getQueensOnBoard() == numQueens) {
//			finalSolution.addAll(listOfQueens());
//			return;
//		} else if (index == dim * dim * dim) {
//			return;
//		}
//		
//		for (int i = index; i < dim * dim * dim; i++) {
//			Square3D s = squaresList.get(i);
//			if (isSafe(s)) {
//				placeQueen(s);
//				findSolution(index + 1);
//				removeQueen(s);
//			}
//		}
//	}
	
	public static void main(String[] args) {
//		Board3D b = new Board3D(8, 32);
//		b.findSolution(0);
//		System.out.println(b.getSolution().size());
//		for (int i = 0; i < b.getSolution().size(); i++) {
//			System.out.println(b.getSolution().get(i));
//		}
	}
	
}
