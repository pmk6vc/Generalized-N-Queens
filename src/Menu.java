import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Menu extends JFrame implements ActionListener {

	// Static button options
	public static final String instructions = "   Welcome to ChessSolver! To begin, please press one of the three options below:";
	public static final JLabel instructionLabel = new JLabel(instructions);//	public static final JLabel instructions;
	public static final JButton genSolButton = new JButton("Find one solution for multiple types of pieces on a resizable board");
	public static final JButton queenSolButton = new JButton("Find all non-isomorphic solutions for the n-queens problem");
	public static final JButton queen3DSolButton = new JButton("Find one solution for queens that can move in 3-dimensional space on an n x n x n board");
	public static final JButton goButton = new JButton("Go!");
	
	public Menu(String title) {
		super(title);
		super.setLayout(new GridLayout(4, 0));
		super.setSize(600, 300);
		
		super.add(instructionLabel);
		super.add(genSolButton);
		super.add(queenSolButton);
		super.add(queen3DSolButton);
		
		genSolButton.addActionListener(this);
		queenSolButton.addActionListener(this);
		queen3DSolButton.addActionListener(this);
//		super.add(goButton);
//		
//		goButton.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(genSolButton)) {
			GenSolMenu m = new GenSolMenu("General piece solution");
			m.setVisible(true);
		} else if (e.getSource().equals(queenSolButton)) {
			QueenSolMenu m = new QueenSolMenu("Non-Isomorphic N x N Queen Solutions");
			m.setVisible(true);
		} else if (e.getSource().equals(queen3DSolButton)) {
			Queen3DSolMenu m = new Queen3DSolMenu("3D Queen Solution");
			m.setVisible(true);
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Menu m = new Menu("ChessSolver");
		m.setVisible(true);
	}

}
