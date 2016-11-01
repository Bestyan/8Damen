import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DamenProblemGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton next = new JButton(">>"), before = new JButton("<<");
	private JLabel anzeige = new JLabel("Lösungsvariante 1");
	SchachPanel center = new SchachPanel();
	
	private int currentlyDisplayed = 0;
	private int[][][] results = new int[8][8][8];
	private DamenProblem d = new DamenProblem();
	
	public DamenProblemGUI() {
		super("8 Damen Problem");
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i < WIDTH; i++) {
			results[i] = d.fillBoardStarting(0, i);
		}
		
		JPanel north = new JPanel(), south = new JPanel();
		
		north.add(anzeige);
		add(north, "North");
		
		south.setLayout(new GridLayout(1, 2));
		before.addActionListener(new ButtonActionListener());
		south.add(before);
		next.addActionListener(new ButtonActionListener());
		south.add(next);
		add(south, "South");
		
		center.setQueens(results[currentlyDisplayed]);
		add(center, "Center");
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(500, 550);
		this.setVisible(true);
	}
	
	private class ButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == next) {
				
				currentlyDisplayed = (currentlyDisplayed + 9) % 8;
				
			} else if (e.getSource() == before) {
				
				currentlyDisplayed = (currentlyDisplayed + 7) % 8;
				
			}
			center.setQueens(results[currentlyDisplayed]);
			anzeige.setText("Lösungsvariante " + (currentlyDisplayed + 1));
		}
	}
}
