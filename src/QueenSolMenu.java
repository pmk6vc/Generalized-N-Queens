import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class QueenSolMenu extends JFrame implements ActionListener {

	// Static fields
	public static final JLabel dimLabel = new JLabel("Dimension of the chessboard: ");
	public static final JLabel qLabel = new JLabel("Number of queens: ");
	public static final JButton solve = new JButton("Solve!");
	// Non-static text fields
	private JTextField dimGen = new JTextField();
	private JTextField qGen = new JTextField();
	
	public QueenSolMenu(String title) {
		super(title);
		super.setLayout(new GridLayout(3, 2));
		super.setSize(450, 150);
		
		super.add(dimLabel);
		super.add(dimGen);
		super.add(qLabel);
		super.add(qGen);
		super.add(solve);
		
		solve.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(QueenSolMenu.solve)) {
			long startTime = System.currentTimeMillis();
			int dimension = Integer.parseInt(dimGen.getText());
			int numQueens = Integer.parseInt(qGen.getText());
			Board b = new Board(dimension, numQueens, 0, 0, 0, 0);
			try {
				UniqueQueenSolutionFinder q = new UniqueQueenSolutionFinder(b);
				q.findSolutionsQueen(0);
				q.findUniqueSolutionsQueen();
				QueenSolResults r = new QueenSolResults("Results", q.getQSolutions(), q.getUQSolutions());
				r.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Runtime: " + (endTime - startTime) + " ms");
		}
	}

}
