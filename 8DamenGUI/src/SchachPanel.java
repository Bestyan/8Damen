import java.awt.*;

import javax.swing.JPanel;

public class SchachPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		private FeldPanel[][] feld = new FeldPanel[8][8];
		
		public SchachPanel(){
			super();
			this.setLayout(new GridLayout(8, 8));
			
			for(int x = 0; x < 8; x++){
				for(int y = 0; y < 8; y++){
					if((x + y) % 2 == 0){
						feld[y][x] = new FeldPanel(Color.WHITE, new Point(x, y));
					} else{
						feld[y][x] = new FeldPanel(Color.BLACK, new Point(x, y));
					}
					this.add(feld[y][x]);
				}				
			}
		}
		
		public void setQueens(int[][] board){
			for(int x = 0; x < 8; x++){
				for(int y = 0; y < 8; y++){
					if(board[y][x] == DamenProblem.QUEEN){
						feld[y][x].setQueen(true);
					} else{
						feld[y][x].setQueen(false);
					}
				}
			}
		}
		
		public void highlightCheck(Point pos){
			int y = pos.y, x = pos.x;
			for(int i = 0; i < 8; i++){
				
				//horizontal + vertikal
				feld[y][i].markAsCheck();
				feld[i][x].markAsCheck();
				
				//diagonal
				if(x-i >= 0){
					if(y + i < 8){
						feld[y+i][x-i].markAsCheck();
					}
					if(y - i >= 0){
						feld[y-i][x-i].markAsCheck();
					}
				}
				if(x+i < 8){
					if(y + i < 8){
						feld[y+i][x+i].markAsCheck();
					}
					if(y - i >= 0){
						feld[y-i][x+i].markAsCheck();
					}
				}
			}
			
			
		}
		
		public void resetHighlights(){
			for(int y = 0; y < 8; y++){
				for(int x = 0; x < 8; x++){
					feld[y][x].unmarkField();
				}
			}
		}
	}