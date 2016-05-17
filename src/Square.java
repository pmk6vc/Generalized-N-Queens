
/**
 * 
 * @author pmk6vc
 * This class is the set of fields and methods that every chess square on a board should have
 * It is organized as follows:
 * Section 1: Fields
 * Section 2: Constructors
 * Section 3: Logic specific to squares
 * Section 4: Relevant getters, setters, equals(), toString()
 * Section 5: Methods for handling how squares appear on the screen
 */

public class Square implements Comparable {
	
	// SECTION 1: FIELDS
	private int rowCoord;		// Row of square
	private int colCoord;		// Column of square
	private boolean hasQueen;	// Keeps track of whether or not this square has a queen on it
	private boolean hasRook;	// Keeps track of whether or not this square has a rook on it
	private boolean hasBishop;	// Keeps track of whether or not this square has a bishop on it
	private boolean hasKnight;	// Keeps track of whether or not this square has a knight on it
	private boolean hasKing;	// Keeps track of whether or not this square has a king on it
	
	// SECTION 2: CONSTRUCTORS
	
	// Null constructor 
	// Sets both row and column coordinates to zero, no pieces by default
	public Square() {
		rowCoord = 0;
		colCoord = 0;
		hasQueen = false;
		hasRook = false;
		hasBishop = false;
		hasKnight = false;
		hasKing = false;
	}
	
	// Constructor that accepts two integers as row and column coordinates, respectively
	// No pieces by default
	public Square(int rowCoord, int colCoord) {
		this.rowCoord = rowCoord;
		this.colCoord = colCoord;
		hasQueen = false;
		hasRook = false;
		hasBishop = false;
		hasKnight = false;
		hasKing = false;
	}
	
	// SECTION 3: LOGIC
	
	// Placing and removing queen on this square
	public void placeQueen() {
		hasQueen = true;
	}
	
	public void removeQueen() {
		hasQueen = false;
	}
	
	// Placing and removing rook on this square
	public void placeRook() {
		hasRook = true;
	}
	
	public void removeRook() {
		hasRook = false;
	}
	
	// Placing and removing bishop on this square
	public void placeBishop() {
		hasBishop = true;
	}
	
	public void removeBishop() {
		hasBishop = false;
	}
	
	// Placing and removing knight on this square
	public void placeKnight() {
		hasKnight = true;
	}
	
	public void removeKnight() {
		hasKnight = false;
	}
	
	// Placing and removing king on this square
	public void placeKing() {
		hasKing = true;
	}
	
	public void removeKing() {
		hasKing = false;
	}
	
	// Location methods that return true if a square meets the behavior specified in the method, false otherwise
	// All methods return true if the passed square is the same as the calling square
	public boolean isVertical(Square c) {
		return (c.colCoord == this.colCoord); 
	}
	
	public boolean isHorizontal(Square c) {
		return (c.rowCoord == this.rowCoord);
	}
	
	public boolean isDiagonal(Square c) {
		int hDist = Math.abs(c.rowCoord - this.rowCoord);	// Horizontal distance
		int vDist = Math.abs(c.colCoord - this.colCoord);	// Vertical distance
		return (hDist == vDist);

	}
	
	public boolean isKnightHop(Square c) {
		int hDist = Math.abs(c.rowCoord - this.rowCoord);	// Horizontal distance
		int vDist = Math.abs(c.colCoord - this.colCoord);	// Vertical distance	
		return ((hDist == 2 && vDist == 1) || (hDist == 1 && vDist == 2));
	}
	
	public boolean isKingHop(Square c) {
		int hDist = Math.abs(c.rowCoord - this.rowCoord);	// Horizontal distance
		int vDist = Math.abs(c.colCoord - this.colCoord);	// Vertical distance
		return (hDist <= 1 && vDist <= 1);
	}
	
	public Square copy() {
		Square s = new Square (this.rowCoord, this.colCoord);
		s.hasQueen = this.hasQueen;
		s.hasRook = this.hasRook;
		s.hasBishop = this.hasBishop;
		s.hasKnight = this.hasKnight;
		s.hasKing = this.hasKing;
		return s;
	}
	
	// SECTION 4: NUTS AND BOLTS
	
	// equals() method
	// Returns true if two squares have the same row and column coordinates and pieces
	// Returns false otherwise
	@Override
	public boolean equals(Object o) {
		if (o instanceof Square) {
			Square c = (Square) o;
			if (c.rowCoord != this.rowCoord) return false;
			if (c.colCoord != this.colCoord) return false;
			if (c.hasQueen != this.hasQueen) return false;
			if (c.hasRook != this.hasRook) return false;
			if (c.hasBishop != this.hasBishop) return false;
			if (c.hasKnight != this.hasKnight) return false;
			if (c.hasKing != this.hasKing) return false;
			return true;
		}
		
		return false;
	}
	
	// toString() method
	// Prints row and column coordinates of the square
	@Override
	public String toString() {
		return "(" + rowCoord + ", " + colCoord + ")";
	}
	
	// Getters
	public int getRow() {
		return rowCoord;
	}
	
	public int getCol() {
		return colCoord;
	}
	
	public boolean hasPiece() {
		return (hasQueen || hasRook || hasBishop || hasKnight || hasKing);
	}
	
	public boolean hasQueen() {
		return hasQueen;
	}
	
	public boolean hasRook() {
		return hasRook;
	}
	
	public boolean hasBishop() {
		return hasBishop;
	}
	
	public boolean hasKnight() {
		return hasKnight;
	}
	
	public boolean hasKing() {
		return hasKing;
	}
	
	// Setters
	public void setRow(int newRow) {
		rowCoord = newRow;
	}
	
	public void setCol(int newCol) {
		colCoord = newCol;
	}
	
	// compareTo method
	// Sorts first by row, and then by column
	// Lower values are less than higher values
	@Override
	public int compareTo(Object o) {
		Square s = (Square) o;		// Cast object so that it is of type Square
		if (this.rowCoord < s.rowCoord) {
			return -1;	// Sort by row s.t. the square in the earlier row is "less than" the other square
		} 
		if (this.rowCoord > s.rowCoord) {
			return 1;
		}
		
		if (this.colCoord < s.colCoord) {
			return -1;
		} 
		
		if (this.colCoord > s.colCoord) {
			return 1;
		}
		
		return 0;
		
	}
	
}
