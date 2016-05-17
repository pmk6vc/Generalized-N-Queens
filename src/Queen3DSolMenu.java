import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Queen3DSolMenu extends JFrame implements ActionListener {

	// Static fields
	public static final JLabel dimLabel = new JLabel("Dimension of the chessboard: ");
	public static final JLabel qLabel = new JLabel("Number of queens: ");
	public static final JButton solve = new JButton("Solve!");
	// Non-static text fields
	private JTextField dimGen = new JTextField();
	private JTextField qGen = new JTextField();
	
	public Queen3DSolMenu(String title) {
		super(title);
		super.setLayout(new GridLayout(3, 2));
		super.setSize(500, 200);
		
		super.add(dimLabel);
		super.add(dimGen);
		super.add(qLabel);
		super.add(qGen);
		super.add(solve);
		
		solve.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(Queen3DSolMenu.solve)) {
			int dimension = Integer.parseInt(dimGen.getText());
			int numQueens = Integer.parseInt(qGen.getText());
			Board3D b = new Board3D(dimension, numQueens);
			try {
				Queen3DSolutionFinder q = new Queen3DSolutionFinder(b);
				q.findSolution(0);
				if (q.getSolution().size() > 0) {
					System.out.println("SOLUTION");
					System.out.println();
					for(int i = 0; i < q.getSolution().size(); i++) {
						Square3D s = q.getSolution().get(i);
						System.out.println(s);
					}
					System.out.println();
				} else {
					// ADD CODE HERE
					System.out.println("No solutions!");
					System.out.println();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
