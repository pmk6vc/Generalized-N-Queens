import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Source: http://www.fas.harvard.edu/~cscie119/lectures/recursion.pdf
 * 
 * @author pmk6vc This class represents the chess board that the program
 *         operates on It is organized as follows: Section 1: Fields Section 2:
 *         Constructors and associated helper methods Section 3: Logic specific
 *         to the board Section 4: Relevant getters, setters, equals(),
 *         toString() Section 5: Methods for handling how the board appears on
 *         the screen
 */

public class Board {
	
	/****************************************************************
	SECTION 1: FIELDS
	Contains static variables for pictures & private variables for other fields
	/****************************************************************/
	
	// Static final fields for uploading the required pictures
	public static BufferedImage DARK_SQUARE;
	public static BufferedImage LIGHT_SQUARE;
	public static BufferedImage QUEEN_ON_DARK;
	public static BufferedImage QUEEN_ON_LIGHT;
	public static BufferedImage ROOK_ON_DARK;
	public static BufferedImage ROOK_ON_LIGHT;
	public static BufferedImage BISHOP_ON_DARK;
	public static BufferedImage BISHOP_ON_LIGHT;
	public static BufferedImage KNIGHT_ON_DARK;
	public static BufferedImage KNIGHT_ON_LIGHT;
	public static BufferedImage KING_ON_DARK;
	public static BufferedImage KING_ON_LIGHT;
	
	private int dimension; // Dimension of the board
	private int numQueens; // Number of queens specified for this board
	private int numRooks;	// Number of rooks specified for this board
	private int numBishops;	// Number of bishops specified for this board
	private int numKnights;	// Number of knights specified for this board
	private int numKings;	// Number of kings specified for this board
	private Square[][] squares; // Squares that make up the board
	private BufferedImage[][] squarePics;	// Picture of each square that makes up the board

	
	
	/****************************************************************
	SECTION 2: CONSTRUCTORS & ASSOCIATED HELPER METHODS
	/****************************************************************/
	
	// Null constructor
	// Sets default board size to 8x8
	// No pieces by default
	public Board() {
		dimension = 8;
		newBoard(8);
		numQueens = 0;
		numRooks = 0;
		numBishops = 0;
		numKnights = 0;
		numKings = 0;
	}

	// Constructor that accepts an integer for the dimension
	// No pieces by default
	public Board(int dimension) {
		this.dimension = dimension;
		newBoard(dimension);
		numQueens = 0;
		numRooks = 0;
		numBishops = 0;
		numKnights = 0;
		numKings = 0;
	}

	// Constructor that accepts an integer for the dimension and an integer for
	// the number of each piece
	public Board(int dimension, int numQueens, int numRooks, int numBishops, int numKnights, int numKings) {
		this.dimension = dimension;
		newBoard(dimension);
		this.numQueens = numQueens;		
		this.numRooks = numRooks;
		this.numBishops = numBishops;
		this.numKnights = numKnights;
		this.numKings = numKings;
	}

	// Helper method that instantiates the squares field based on dimension of
	// the board
	// Also instantiates the array of pictures that visually represent the board
	private void newBoard(int dim) {
		squares = new Square[dimension][dimension];
		for (int i = 0; i < dim; i++) { // Row iteration
			for (int j = 0; j < dim; j++) { // Column iteration
				squares[i][j] = new Square(i, j);
			}
		}
		
		loadImages();
		alternatingSquares();	// Instantiate and initialize 2D array of BufferedImages
	}

	// Helper method that returns a value copy of this board
	public Board copy() {
		Board copy = new Board(this.dimension, this.numQueens, this.numRooks, this.numBishops, this.numKnights, this.numKings); // Construct copy
		Square[][] squaresCopy = copy.getSquares(); // Squares of copy
		BufferedImage[][] squarePicsCopy = copy.getSquarePics();	// Pics of copy
		for (int row = 0; row < this.dimension; row++) {
			for (int col = 0; col < this.dimension; col++) {
				// Copy squares
				Square s = this.squares[row][col];
				Square scopy = s.copy();
				squaresCopy[row][col] = scopy;
				
				// Copy pictures
				BufferedImage b = this.squarePics[row][col];
				if (equalImages(LIGHT_SQUARE, b)) {
					squarePicsCopy[row][col] = LIGHT_SQUARE;
				} else if (equalImages(DARK_SQUARE, b)) {
					squarePicsCopy[row][col] = DARK_SQUARE;
				} else if (equalImages(QUEEN_ON_LIGHT, b)) {
					squarePicsCopy[row][col] = QUEEN_ON_LIGHT;
				} else if (equalImages(QUEEN_ON_DARK, b)) {
					squarePicsCopy[row][col] = QUEEN_ON_DARK;
				} else if (equalImages(ROOK_ON_LIGHT, b)) {
					squarePicsCopy[row][col] = ROOK_ON_LIGHT;
				} else if (equalImages(ROOK_ON_DARK, b)) {
					squarePicsCopy[row][col] = ROOK_ON_DARK;
				} else if (equalImages(BISHOP_ON_LIGHT, b)) {
					squarePicsCopy[row][col] = BISHOP_ON_LIGHT;
				} else if (equalImages(BISHOP_ON_DARK, b)) {
					squarePicsCopy[row][col] = BISHOP_ON_DARK;
				} else if (equalImages(KNIGHT_ON_LIGHT, b)) {
					squarePicsCopy[row][col] = KNIGHT_ON_LIGHT;
				} else if (equalImages(KNIGHT_ON_DARK, b)) {
					squarePicsCopy[row][col] = KNIGHT_ON_DARK;
				} else if (equalImages(KING_ON_LIGHT, b)) {
					squarePicsCopy[row][col] = KING_ON_LIGHT;
				} else if (equalImages(KING_ON_DARK, b)) {
					squarePicsCopy[row][col] = KING_ON_DARK;
				}
			}
		}

		return copy;
	}
	
	
	
	/****************************************************************
	 SECTION 3: LOGIC
	 1) Testing whether two boards are isomorphic
	 2) Testing whether squares are safe
	 3) Determining which squares would not result in an attack
	 4) Placing and removing pieces
	/****************************************************************/

	// ****************************************************************
	// Testing for isomorphism
	// ****************************************************************
	
	// isIsomorphic method
	// Returns true if this board is isomorphic to calling board
	// Returns false otherwise
	private boolean isIsomorphic(Board b) {
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Check horizontal reflection
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Reflect back
		
		b.rotateClockwise(); // Check 90 degrees CW
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Check horizontal reflection
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Reflect back
		
		b.rotateClockwise(); // Check 180 degrees CW
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Check horizontal reflection
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Reflect back
		
		b.rotateClockwise(); // Check 270 degrees CW
		if (this.sameOrientation(b))
			return true;
		b.reflectHorizontal();	// Check horizontal reflection
		if (this.sameOrientation(b))
			return true;

		return false;
	}

	// Helper method that checks whether a given board has the same orientation
	// and pieces as this board
	// Used in isIsomorphic()
	private boolean sameOrientation(Board b) {
		if (b.getDimension() != this.getDimension())
			return false; // Check dimensions
		ArrayList<Square> thisBoard = new ArrayList<Square>(); // Create ArrayList to store this board's squares
		ArrayList<Square> bBoard = new ArrayList<Square>();
		for (int row = 0; row < getDimension(); row++) {
			for (int col = 0; col < getDimension(); col++) { // Add all squares to respective boards
				Square s1 = this.squares[row][col];
				thisBoard.add(s1);
				Square s2 = b.squares[row][col];
				bBoard.add(s2);
			}
		}
		Collections.sort(thisBoard); // Sort both boards based on ordering
										// implemented in Square
		Collections.sort(bBoard);
		return thisBoard.equals(bBoard);
	}

	// rotateClockwise method
	// Takes the calling board and rotates it clockwise 90 degrees
	// (x, y) --> (y, -x)
	// Translate coordinate system down by dimension - 1 units
	private void rotateClockwise() {
		for (int row = 0; row < getDimension(); row++) {
			for (int col = 0; col < getDimension(); col++) {
				Square s = squares[row][col];
				int oldRow = s.getRow();
				int oldCol = s.getCol();
				s.setRow(oldCol);
				s.setCol(-1 * oldRow + getDimension() - 1);
			}
		}
	}

	// reflectHorizontal method
	// Reflects the calling board across the horizontal axis
	// Subtract original row address from largest row address (dimension - 1)
	private void reflectHorizontal() {
		for (int row = 0; row < getDimension(); row++) {
			for (int col = 0; col < getDimension(); col++) {
				Square s = squares[row][col];
				int oldRow = s.getRow();
				s.setRow(this.dimension - 1 - oldRow);
			}
		}
	}
	
	// ****************************************************************
	// notAttacked methods
	// Check whether any piece on the board is attacking a given square
	// ****************************************************************

	// notAttacked method
	// Check whether any piece on the board is attacking a given square
	public boolean notAttacked(Square s) {
		return notAttacked(s.getRow(), s.getCol());
	}
	
	public boolean notAttacked(int row, int col) {
		return (notAttackedQueens(row, col) && notAttackedRooks(row, col) && notAttackedBishops(row, col) && notAttackedKnights(row, col) && notAttackedKings(row, col));
	}

	// notAttackedQueens method
	// Accepts row and column as arguments
	// Returns true if the queens on the board do not attack the square on the
	// specified row and column
	private boolean notAttackedQueens(int row, int col) {
		Square s = squares[row][col];
		for (int i = 0; i < getQueensOnBoard().size(); i++) { // Check whether given square is safe
			Square q = getQueensOnBoard().get(i);
			if (q.isDiagonal(s) || q.isVertical(s) || q.isHorizontal(s)) {
				return false;
			}
		}
		return true;
	}

	// Same method as above, but accepts square as parameter
	private boolean notAttackedQueens(Square s) {
		return notAttackedQueens(s.getRow(), s.getCol());
	}
	
	// notAttackedRooks method
	// Accepts row and column as arguments
	// Returns true if the rooks on the board do not attack the square on the
	// specified row and column
	private boolean notAttackedRooks(int row, int col) {
		Square s = squares[row][col];
		for (int i = 0; i < getRooksOnBoard().size(); i++) {	// Check whether given square is safe
			Square r = getRooksOnBoard().get(i);
			if (r.isVertical(s) || r.isHorizontal(s)) {
				return false;
			}
		}
		
		return true;
	}
	
	// Same method as above, but accepts square as parameter
	private boolean notAttackedRooks(Square s) {
		return notAttackedRooks(s.getRow(), s.getCol());
	}
	
	// notAttackedBishops method
	// Accepts row and column as arguments
	// Returns true if the bishops on the board do not attack the square on the
	// specified row and column
	private boolean notAttackedBishops(int row, int col) {
		Square s = squares[row][col];
		for (int i = 0; i < getBishopsOnBoard().size(); i++) {	// Check whether given square is safe
			Square b = getBishopsOnBoard().get(i);
			if (b.isDiagonal(s)) {
				return false;
			}
		}
		
		return true;
	}
	
	// Same method as above, but accepts square as parameter
	private boolean notAttackedBishops(Square s) {
		return notAttackedBishops(s.getRow(), s.getCol());
	}
	
	// notAttackedKings method
	// Accepts row and column as arguments
	// Returns true if the knights on the board do not attack the square on the
	// specified row and column
	private boolean notAttackedKnights(int row, int col) {
		Square s = squares[row][col];
		for (int i = 0; i < getKnightsOnBoard().size(); i++) {
			Square b = getKnightsOnBoard().get(i);
			if (b.isKnightHop(s)) {
				return false;
			}
		}
		
		return true;
	}
	
	// Same method as above, but accepts square as parameter
	private boolean notAttackedKnights(Square s) {
		return notAttackedKnights(s.getRow(), s.getCol());
	}
	
	// notAttackedKings method
	// Accepts row and column as arguments
	// Returns true if the kings on the board do not attack the square on the 
	// specified row and column
	private boolean notAttackedKings(int row, int col) {
		Square s = squares[row][col];
		for (int i = 0; i < getKingsOnBoard().size(); i++) {
			Square b = getKingsOnBoard().get(i);
			if (b.isKingHop(s)) {
				return false;
			}
		}
		
		return true;
	}
	
	// Same method as above, but accepts square as parameter
	private boolean notAttackedKings(Square s) {
		return notAttackedKings(s.getRow(), s.getCol());
	}
	
	// ****************************************************************
	// notAttacking methods
	// Check whether placing a piece on a square is attacking any other piece
	// ****************************************************************
	
	// bNotAttackingR
	// Check whether placing bishop on specified square will threaten rooks
	public boolean bNotAttackingR(int row, int col) {
		Square bishopSquare = squares[row][col];
		// Iterate through squares that have rooks on this board
		// Check whether any of them are diagonal to the current square
		for(Square s: getRooksOnBoard()) {
			if (s.isDiagonal(bishopSquare)) {
				return false;
			}
		}
		
		return true;
	}
	
	// Same method as above, but accepts square as parameter
	public boolean bNotAttackingR(Square bishopSquare) {
		return bNotAttackingR(bishopSquare.getRow(), bishopSquare.getCol());
	}
	
	// nNotAttackingP
	// Check whether placing knight on specified square will threaten pieces
	public boolean nNotAttackingP(int row, int col) {
		return (nNotAttackingQ(row, col) && nNotAttackingR(row, col) && nNotAttackingB(row, col));
	}
	
	// Same method as above, but accepts square as parameter
	public boolean nNotAttackingP(Square s) {
		return nNotAttackingP(s.getRow(), s.getCol());
	}
	
	private boolean nNotAttackingQ(int row, int col) {
		Square knightSquare = squares[row][col];
		for (Square s: getQueensOnBoard()) {
			if (s.isKnightHop(knightSquare)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean nNotAttackingR(int row, int col) {
		Square knightSquare = squares[row][col];
		for (Square s: getRooksOnBoard()) {
			if (s.isKnightHop(knightSquare)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean nNotAttackingB(int row, int col) {
		Square knightSquare = squares[row][col];
		for (Square s: getBishopsOnBoard()) {
			if (s.isKnightHop(knightSquare)) {
				return false;
			}
		}
		
		return true;
	}
	
	// kNotAttackingP
	// Check whether placing king on specified square will threaten pieces
	public boolean kNotAttackingP(int row, int col) {
		return (kNotAttackingR(row, col) && kNotAttackingB(row, col) && kNotAttackingN(row, col));
	}
	
	// Same method as above, but accepts square as parameter
	public boolean kNotAttackingP(Square s) {
		return kNotAttackingP(s.getRow(), s.getCol());
	}
	
	private boolean kNotAttackingR(int row, int col) {
		Square kingSquare = squares[row][col];
		for (Square s: getRooksOnBoard()) {
			if (s.isKingHop(kingSquare)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean kNotAttackingB(int row, int col) {
		Square kingSquare = squares[row][col];
		for (Square s: getBishopsOnBoard()) {
			if (s.isKingHop(kingSquare)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean kNotAttackingN(int row, int col) {
		Square kingSquare = squares[row][col];
		for (Square s: getKnightsOnBoard()) {
			if (s.isKingHop(kingSquare)) {
				return false;
			}
		}
		
		return true;
	}
	

	
	// ****************************************************************
	// Placing and removing pieces
	// ****************************************************************
	
	// Placing and removing queen on a specified square on the board
	public void placeQueen(int row, int col) {
		Square c = squares[row][col];
		c.placeQueen();
	}

	public void removeQueen(int row, int col) {
		Square c = squares[row][col];
		c.removeQueen();
	}
	
	// Placing and removing rook on a specified square on the board
	public void placeRook(int row, int col) {
		Square c = squares[row][col];
		c.placeRook();
	}
	
	public void removeRook(int row, int col) {
		Square c = squares[row][col];
		c.removeRook();
	}
	
	// Placing and removing bishop on a specified square on the board
	public void placeBishop(int row, int col) {
		Square c = squares[row][col];
		c.placeBishop();
	}
	
	public void removeBishop(int row, int col) {
		Square c = squares[row][col];
		c.removeBishop();
	}
	
	// Placing and removing knight on a specified square on the board
	public void placeKnight(int row, int col) {
		Square c = squares[row][col];
		c.placeKnight();
	}

	public void removeKnight(int row, int col) {
		Square c = squares[row][col];
		c.removeKnight();
	}
	
	// Placing and removing king on a specified square on the board
	public void placeKing(int row, int col) {
		Square c = squares[row][col];
		c.placeKing();
	}
	
	public void removeKing(int row, int col) {
		Square c = squares[row][col];
		c.removeKing();
	}
	
	/****************************************************************
	SECTION 4: NUTS AND BOLTS
	1) equals() & toString()
	2) Getters of various sorts
	/****************************************************************/
	
	// ****************************************************************
	// equals() & toString()
	// ****************************************************************
	
	// equals() method
	// Returns true if this board has the same dimension and pieces in
	// corresponding locations as calling board
	// Returns false otherwise
	@Override
	public boolean equals(Object o) {
		if (o instanceof Board) {
			Board b = (Board) o;
			if (b.getDimension() != this.getDimension())
				return false; // Check dimensions
			return this.isIsomorphic(b);
		}

		return false;
	}

	// toString() method
	// Prints the set of squares on the board that have pieces along with corresponding piece
	@Override
	public String toString() {
		String dim = "{";
		
		// Queens come first (ladies first!)
		for (int i = 0; i < getQueensOnBoard().size(); i++) {
			dim += "Q" + getQueensOnBoard().get(i).toString() + ", ";
		}
		// Followed by rooks
		for (int i = 0; i < getRooksOnBoard().size(); i++) {
			dim += "R" + getRooksOnBoard().get(i).toString() + ", ";
		}
		
		// Followed by bishops
		for (int i = 0; i < getBishopsOnBoard().size(); i++) {
			dim += "B" + getBishopsOnBoard().get(i).toString();
		}
		
		// Followed by knights
		for (int i = 0; i < getKnightsOnBoard().size(); i++) {
			dim += "N" + getKnightsOnBoard().get(i).toString();
		}
		
		// Followed by kings
		for (int i = 0; i < getKingsOnBoard().size(); i++) {
			dim += "K" + getKingsOnBoard().get(i).toString();
		}
		dim += "}";
		return dim;
	}
	
	// ****************************************************************
	// Getters of various sorts
	// ****************************************************************
	
	// Getters
	public int getDimension() {
		return dimension;
	}
	
	// Returns the number of queens that should be on the board
	public int numSpecifiedQueens() {
		return numQueens;
	}
	
	// Returns the number of rooks that should be on the board
	public int numSpecifiedRooks() {
		return numRooks;
	}
	
	// Returns the number of rooks that should be on the board
	public int numSpecifiedBishops() {
		return numBishops;
	}
	
	// Returns the number of bishops that should be on the board
	public int numSpecifiedKnights() {
		return numKnights;
	}
	
	// Returns the number of kings that should be on the board
	public int numSpecifiedKings() {
		return numKings;
	}
	
	// Returns the squares with queens on them
	public ArrayList<Square> getQueensOnBoard() {
		ArrayList<Square> queens = new ArrayList<Square>();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				Square s = squares[row][col];
				if (s.hasQueen()) queens.add(s);
			}
		}
		
		return queens;
	}
	
	// Returns the squares with rooks on them
	public ArrayList<Square> getRooksOnBoard() {
		ArrayList<Square> rooks = new ArrayList<Square>();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				Square s = squares[row][col];
				if (s.hasRook()) rooks.add(s);
			}
		}
		
		return rooks;
	}
	
	// Returns the squares with bishops on them
	public ArrayList<Square> getBishopsOnBoard() {
		ArrayList<Square> bishops = new ArrayList<Square>();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				Square s = squares[row][col];
				if (s.hasBishop()) bishops.add(s);
			}
		}
		
		return bishops;
	}
	
	// Returns the squares with knights on them
	public ArrayList<Square> getKnightsOnBoard() {
		ArrayList<Square> knights = new ArrayList<Square>();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				Square s = squares[row][col];
				if (s.hasKnight()) knights.add(s);
			}
		}
		
		return knights;
	}
	
	// Returns the squares with kings on them
	public ArrayList<Square> getKingsOnBoard() {
		ArrayList<Square> kings = new ArrayList<Square>();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				Square s = squares[row][col];
				if (s.hasKing()) kings.add(s);
			}
		}
		
		return kings;
	}
	
	// Returns the number of squares with queens on them
	public int numQueensOnBoard() {
		return getQueensOnBoard().size();
	}
	
	// Returns the number of rooks that are actually on the board
	public int numRooksOnBoard() {
		return getRooksOnBoard().size();
	}
	
	// Returns the number of bishops that are actually on the board
	public int numBishopsOnBoard() {
		return getBishopsOnBoard().size();
	}
	
	// Returns the number of knights that are actually on the board
	public int numKnightsOnBoard() {
		return getKnightsOnBoard().size();
	}
	
	// Returns the number of kings that are actually on the board
	public int numKingsOnBoard() {
		return getKingsOnBoard().size();
	}
	
	// Retrieve squares as either a 2D array or as a 1D ArrayList
	public Square[][] getSquares() {
		return squares;
	}
	
	public ArrayList<Square> getSquareList() {
		ArrayList<Square> squareList = new ArrayList<Square>();
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				Square s = squares[i][j];
				squareList.add(s);
			}
		}
		
		return squareList;
	}
	
	public BufferedImage[][] getSquarePics() {
		return squarePics;
	}
	
	
	
	/****************************************************************
	SECTION 5: USER DISPLAY
	/****************************************************************/
	
	// Draws the board
	// Requires loadImages(), alternatingSquares(), & setSquarePics() to have been called
	public void draw() {
		setSquarePics();
		JFrame board = new JFrame("Solution Board");
		// Resize board based on width & height of LIGHT_SQUARE
		board.setSize(new Dimension(LIGHT_SQUARE.getWidth() * dimension, LIGHT_SQUARE.getHeight() * dimension));
		board.setLayout(new GridLayout(dimension, dimension));	// Set layout based on dimension
		
		BufferedImage squarePic = LIGHT_SQUARE;	// Keep it at LIGHT_SQUARE for now
		JLabel squareLabel = new JLabel();
//		int gridRow = 0;	// Required because the highest row on the board is at the top
							// while highest row in GridLayout is on the bottom
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
//				gridRow = dimension - row - 1;	// Switch from bottom of board to top
				squarePic = squarePics[row][col];
				squareLabel = new JLabel(new ImageIcon(squarePic));
				board.add(squareLabel);
			}
		}
		
		board.setVisible(true);
	}
	
	// setSquarePics() method
	// Replaces elements in the squarePics array to pictures of squares with pieces on them
	// Requires loadImages() & alternatingSquares() methods to already have been called 
	private void setSquarePics() {
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				// Replace squares with queens on them with appropriate pictures
				if (squares[row][col].hasQueen()) {
					if (equalImages(squarePics[row][col], LIGHT_SQUARE)) {
						squarePics[row][col] = QUEEN_ON_LIGHT;
//						System.out.println("Queen on light reached!");
					} else {
						squarePics[row][col] = QUEEN_ON_DARK;
//						System.out.println("Queen on dark reached!");
					}
				}
				// Replace squares with rooks on them with appropriate pictures
				else if (squares[row][col].hasRook()) {
					if (equalImages(squarePics[row][col], LIGHT_SQUARE)) {
						squarePics[row][col] = ROOK_ON_LIGHT;
					} else {
						squarePics[row][col] = ROOK_ON_DARK;
					}
				}
				// Replace squares with bishops on them with appropriate pictures
				else if (squares[row][col].hasBishop()) {
					if (equalImages(squarePics[row][col], LIGHT_SQUARE)) {
						squarePics[row][col] = BISHOP_ON_LIGHT;
					} else {
						squarePics[row][col] = BISHOP_ON_DARK;
					}
				}
				// Replace squares with knights on them with appropriate pictures
				else if (squares[row][col].hasKnight()) {
					if (equalImages(squarePics[row][col], LIGHT_SQUARE)) {
						squarePics[row][col] = KNIGHT_ON_LIGHT;
					} else {
						squarePics[row][col] = KNIGHT_ON_DARK;
					}
				}
				// Replace squares with kings on them with appropriate pictures
				else if (squares[row][col].hasKing()) {
					if (equalImages(squarePics[row][col], LIGHT_SQUARE)) {
						squarePics[row][col] = KING_ON_LIGHT;
					} else {
						squarePics[row][col] = KING_ON_DARK;
					}
				}
			}
		}
	}
	
	// alternatingSquares() method
	// Instantiates 2D BufferImage array and populates it with blank squares of alternating colors
	// Requires loadImages() to have already been called
	private void alternatingSquares() {
		squarePics = new BufferedImage[dimension][dimension];	// Allocate array
		boolean isLight;	// Determine whether first box should be light based on whether dimension is even
		if (dimension % 2 == 0) {
			isLight = false;
		} else {
			isLight = true;
		}
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {				
				if (isLight) {
					squarePics[row][col] = LIGHT_SQUARE;	// Make sure that these pictures are initialized before
														// calling this method!
				} else {
					squarePics[row][col] = DARK_SQUARE;
				}
				
				isLight = !isLight;	// Flip the value of the light square tracker
				
				if (dimension % 2 == 0 && col == dimension - 1) {	// Flip value again if end of row is reached
																	// and dimension of board is even
					isLight = !isLight;
				}
			}
		}
	}
	
	// equalImages(Image 1, Image 2) method
	// Used to test whether two BufferedImage objects have the same image
	private boolean equalImages(BufferedImage b1, BufferedImage b2) {
		
		boolean imagesEqual = true;
		
		if (b1.getWidth() == b2.getWidth() && b1.getHeight() == b2.getHeight()) {
			for (int x = 0; imagesEqual && x < b1.getWidth(); x++) {
				for (int y = 0; imagesEqual && y < b1.getHeight(); y++) {
					if (b1.getRGB(x, y) != b2.getRGB(x, y)) {
						imagesEqual = false;
					}
				}
			}
		} else {
			imagesEqual = false;
		}
		
		return imagesEqual;
	}
	
	// Helper method that loads the required pictures
	// Exceptions will not be thrown, as the files have all manually been added to project
	private void loadImages() {
		try {
			DARK_SQUARE = ImageIO.read(new File("Dark Square.png"));
			LIGHT_SQUARE = ImageIO.read(new File("Light Square.png"));
			QUEEN_ON_DARK = ImageIO.read(new File("QonD.png"));
			QUEEN_ON_LIGHT = ImageIO.read(new File("QonL.png"));
			ROOK_ON_DARK = ImageIO.read(new File("RonD.png"));
			ROOK_ON_LIGHT = ImageIO.read(new File("RonL.png"));
			BISHOP_ON_DARK = ImageIO.read(new File("BonD.png"));
			BISHOP_ON_LIGHT = ImageIO.read(new File("BonL.png"));
			KNIGHT_ON_DARK = ImageIO.read(new File("NonD.png"));
			KNIGHT_ON_LIGHT = ImageIO.read(new File("NonL.png"));
			KING_ON_DARK = ImageIO.read(new File("KonD.png"));
			KING_ON_LIGHT = ImageIO.read(new File("KonL.png"));
		} catch (Exception e) {
			
		}
	}
	
}
