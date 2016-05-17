
public class Square3D {
	
	// Squares that make up a 3D board
	
	// Define a queen's motion as follows:
	// 1) A queen can move along any one axis (3 directions)
	// 2) A queen can move diagonally (10 directions)
	// In this context there are 2 key categories of diagonals
	// 1) Along a coordinate plane: Fix one coordinate, and see whether the respective distances of the other two match
	// There are 6 possibilities here: 2 diagonal directions for each of the 3 coordinate planes
	// 2) Along 3D: Check whether the respective distances of all 3 coordinates match
	// There are 4 possibilities here: the 4 lines that connect opposite vertices in a cube
	
	// SECTION 1: FIELDS
	private int xCoord;		// x-coordinate of square
	private int yCoord;		// y-coordinate of square
	private int zCoord;		// z-coordinate of square
	private boolean hasQueen;	// Keeps track of whether or not this square has a queen on it
	
	// Null constructor 
	// Sets coordinates to zero, no pieces by default
	public Square3D() {
		xCoord = 0;
		yCoord = 0;
		zCoord = 0;
		hasQueen = false;
	}
	
	public Square3D(int x, int y, int z) {
		xCoord = x;
		yCoord = y;
		zCoord = z;
		hasQueen = false;
	}
	
	// LOCATION METHODS
	
	// Location methods that return true if a square meets the behavior specified in the method, false otherwise
	// All methods return true if the passed square is the same as the calling square
	
	// Summary methods
	public boolean isParallel(Square3D s) {
		return (parallelX(s) || parallelY(s) || parallelZ(s));
	}
	
	public boolean isDiagonal(Square3D s) {
		return (diagonalXY(s) || diagonalXZ(s) || diagonalYZ(s) || diagonalXYZ(s));
	}
	
	// One-dimensional motion
	public boolean parallelX(Square3D s) {
		// If two squares have the same y & z coordinate
		// Then any difference would be in the x-coordinate
		return (s.yCoord == this.yCoord && s.zCoord == this.zCoord);
	}
	
	public boolean parallelY(Square3D s) {
		return (s.xCoord == this.xCoord && s.zCoord == this.zCoord);
	}
	
	public boolean parallelZ(Square3D s) {
		return (s.xCoord == this.xCoord && s.yCoord == this.yCoord);
	}
	
	// Two-dimensional motion
	// Along the XY-plane
	public boolean diagonalXY(Square3D s) {
		int xDist = Math.abs(s.xCoord - this.xCoord);
		int yDist = Math.abs(s.yCoord - this.yCoord);
		return (xDist == yDist && s.zCoord == this.zCoord);
	}
	
	// Along the XZ-plane
	public boolean diagonalXZ(Square3D s) {
		int xDist = Math.abs(s.xCoord - this.xCoord);
		int zDist = Math.abs(s.zCoord - this.zCoord);
		return (xDist == zDist && s.yCoord == this.yCoord);
	}
	
	// Along the YZ-plane
	public boolean diagonalYZ(Square3D s) {
		int yDist = Math.abs(s.yCoord - this.yCoord);
		int zDist = Math.abs(s.zCoord - this.zCoord);
		return (yDist == zDist && s.xCoord == this.xCoord);
	}
	
	// Three-dimensional motion
	public boolean diagonalXYZ(Square3D s) {
		int xDist = Math.abs(s.xCoord - this.xCoord);
		int yDist = Math.abs(s.yCoord - this.yCoord);
		int zDist = Math.abs(s.zCoord - this.zCoord);
		return (xDist == yDist && yDist == zDist);
	}
	
	
	// NUTS & BOLTS
		
	public Square3D copy() {
		Square3D s = new Square3D (xCoord, yCoord, zCoord);
		s.hasQueen = this.hasQueen;
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Square3D) {
			Square3D s = (Square3D) o;
			if (s.hasQueen != this.hasQueen) return false;
			if (s.xCoord != this.xCoord) return false;
			if (s.yCoord != this.yCoord) return false;
			if (s.zCoord != this.zCoord) return false;
			return true;
		} else return false;
	}
	
	@Override
	public String toString() {
		return "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
	}
	
	public int getX() {
		return xCoord;
	}
	
	public int getY() {
		return yCoord;
	}
	
	public int getZ() {
		return zCoord;
	}
	
	public boolean hasQueen() {
		return hasQueen;
	}
	
	// Placing and removing queen on this square
	public void placeQueen() {
		hasQueen = true;
	}
	
	public void removeQueen() {
		hasQueen = false;
	}

}
