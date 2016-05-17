import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GenSolMenu extends JFrame implements ActionListener {
	
	// Static fields
	public static final int FIELD_LENGTH = 5;
	public static final int ROWS_PARAM = 0;
	public static final int COLS_PARAM = 2;
	public static final JLabel dimLabel = new JLabel("Dimension of the chessboard: ");
	public static final JLabel qLabel = new JLabel("Number of queens: ");
	public static final JLabel rLabel = new JLabel("Number of rooks: ");
	public static final JLabel bLabel = new JLabel("Number of bishops: ");
	public static final JLabel nLabel = new JLabel("Number of knights: ");
	public static final JLabel kLabel = new JLabel("Number of kings: ");
	public static final JButton goButton = new JButton("Go!"); 
	
	// Non-static fields associated with creating a board for the general solution
	private JTextField dimGen = new JTextField();
	private JTextField qGen = new JTextField();
	private JTextField rGen = new JTextField();
	private JTextField bGen = new JTextField();
	private JTextField nGen = new JTextField();
	private JTextField kGen = new JTextField();
	
	public GenSolMenu(String title) {
		super(title);
		super.setLayout(new GridLayout(3, 0));
		super.setSize(900, 450);
		
		// Instructions
		JPanel info = new JPanel();
		info.setSize(300, 150);
		String instructions = "Type in the appropriate values for each of the fields below, and then hit the Go button!";
		JLabel instructionsLabel = new JLabel(instructions);
		info.add(instructionsLabel);
		super.add(info);

		// Parameters
		JPanel params = new JPanel();
		params.setLayout(new GridLayout(ROWS_PARAM, COLS_PARAM));
		addParamFields(params);
		super.add(params);

		// Go button
		super.add(goButton);
		goButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(GenSolMenu.goButton)) {
			int dimension = Integer.parseInt(this.dimGen.getText());
			int numOfQueens = Integer.parseInt(this.qGen.getText());
			int numOfRooks = Integer.parseInt(this.rGen.getText());
			int numOfBishops = Integer.parseInt(this.bGen.getText());
			int numOfKnights = Integer.parseInt(this.nGen.getText());
			int numOfKings = Integer.parseInt(this.kGen.getText());
			Board b = new Board(dimension, numOfQueens, numOfRooks, numOfBishops, numOfKnights, numOfKings);
			try {
				GeneralSolutionFinder solution = new GeneralSolutionFinder(b);
				solution.findSolution(0);
				if (solution.getSolution().size() > 0) {
					solution.getSolution().get(0).draw();
				} else {
					System.out.println("No solutions!");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void addParamFields(JPanel params) {
		// Add labels and text fields for the user to enter
		params.add(dimLabel);
		params.add(dimGen);

		params.add(qLabel);
		params.add(qGen);

		params.add(rLabel);
		params.add(rGen);

		params.add(bLabel);
		params.add(bGen);

		params.add(nLabel);
		params.add(nGen);
		
		params.add(kLabel);
		params.add(kGen);
	}

}
