import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class FeldPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Color c;
	private boolean hasQueen = false;
	private Point pos;
	
	public FeldPanel(Color c, Point pos) {
		super();
		this.setBackground(c);
		this.addMouseListener(new FeldPanelMouseListener());
		
		this.c = c;
		this.pos = pos;
	}
	
	public boolean isWhite() {
		return c.equals(Color.WHITE);
	}
	
	public void markField() {
		if (hasQueen) {
			this.setBackground(new Color(0, 0, 127));
			((SchachPanel)this.getParent()).highlightCheck(pos);
		} else if (isWhite()) {
			this.setBackground(Color.LIGHT_GRAY);
		} else {
			this.setBackground(Color.DARK_GRAY);
		}
	}
	
	public void unmarkField() {
		if (hasQueen) {
			this.setBackground(Color.BLUE);
		} else {
			this.setBackground(c);
		}
	}
	
	public void markAsCheck() {
		if(hasQueen){
			if(isWhite()){
				this.setBackground(new Color(180, 0, 0));
			} else{
				this.setBackground(new Color(100, 0, 0));
			}
		} else if (isWhite()) {
			this.setBackground(new Color(0, 150, 0)); // TODO check this
		} else {
			this.setBackground(new Color(0, 100, 0)); // TODO check this
		}
	}
	
	public void setQueen(boolean flag) {
		this.hasQueen = flag;
		if (flag) {
			this.setBackground(Color.BLUE);
		} else {
			this.unmarkField();
		}
	}
	
	private class FeldPanelMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			markField();
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			unmarkField();
			if(hasQueen){
				((SchachPanel)getParent()).resetHighlights();
			}
		}
	}
}