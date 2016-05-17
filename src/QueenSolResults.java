import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class QueenSolResults extends JFrame implements ActionListener {

	private JLabel results;
	private JComboBox<Board> uniqueSolutions;
	private JButton draw;
	
	public QueenSolResults(String title, ArrayList<Board> solutions, ArrayList<Board> uniqueSolutions) {
		super(title);
		super.setLayout(new GridLayout(3, 0));
		super.setSize(900, 300);
		
		this.results = new JLabel("There are " + solutions.size() + " solutions and " + uniqueSolutions.size() + " unique solutions.");
		Board[] usArray = new Board[uniqueSolutions.size()];
		for (int i = 0; i < uniqueSolutions.size(); i++) {
			Board b = uniqueSolutions.get(i);
			usArray[i] = b;
		}
		this.uniqueSolutions = new JComboBox<Board>(usArray);
		this.draw = new JButton("Draw!");
		
		super.add(results);
		super.add(this.uniqueSolutions);
		super.add(draw);
		
//		this.uniqueSolutions.addActionListener(this);
		this.draw.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(draw)) {
			try {	
				Board b = (Board) this.uniqueSolutions.getSelectedItem();
				b.draw();
			} catch (Exception e1) {

			}
		}
	}
	
	

}
