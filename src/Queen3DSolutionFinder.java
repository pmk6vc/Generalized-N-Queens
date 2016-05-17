import java.util.ArrayList;


public class Queen3DSolutionFinder {

	private Board3D b;
	private ArrayList<Square3D> finalSolution = new ArrayList<Square3D>();
	
	public Queen3DSolutionFinder(Board3D b) {
		this.b = b;
	}
	
	// Solution finder
	public void findSolution(int index) {
		
		if (finalSolution.size() > 0) {
			return;
		}
		
		if (b.getQueensOnBoard() == b.numQueensOnBoard()) {
			finalSolution.addAll(b.listOfQueens());
			return;
		} else if (index == b.getDimension() * b.getDimension() * b.getDimension()) {
			return;
		}
		
		for (int i = index; i < b.getDimension() * b.getDimension() * b.getDimension(); i++) {
			Square3D s = b.getSquaresList().get(i);
			if (b.isSafe(s)) {
				b.placeQueen(s);
				findSolution(index + 1);
				b.removeQueen(s);
			}
		}
	}
	
	// Retrieving solution
	public ArrayList<Square3D> getSolution() {
		return finalSolution;
	}
	
	public static void main(String[] args) {
		Board3D b = new Board3D(4, 6);
		Queen3DSolutionFinder q = new Queen3DSolutionFinder(b);
		q.findSolution(0);
		System.out.println(q.getSolution().size());
		for (int i = 0; i < q.getSolution().size(); i++) {
			System.out.println(q.getSolution().get(i));
		}
	}

}
