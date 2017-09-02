import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends JFrame implements MouseListener {
	
	List<Integer> playerX = new ArrayList<>();
	List<Integer> playerY = new ArrayList<>();
	List<Integer> computerX = new ArrayList<>();
	List<Integer> computerY = new ArrayList<>();
	public int rows = 3, columns = 3;
	public int[][] board = new int[rows][columns];
	public int empty = 0;
	public int x = 1;
	public int o = 2;
	
	Game() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("TicTacToe");
		this.setSize(500,500);
		this.addMouseListener(this);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				board[i][j] = empty;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		//horizontal lines
		g.drawLine(100, 200, 400, 200);
		g.drawLine(100, 300, 400, 300);
		
		//vertical lines
		g.drawLine(200, 100, 200, 400);
		g.drawLine(300, 100, 300, 400);
		
		for (int i=0; i<playerX.size(); i++) {
			g.drawLine(playerX.get(i)*100 + 5, playerY.get(i) * 100 + 5, playerX.get(i) * 100 + 90, playerY.get(i)*100 + 90);
			g.drawLine(playerX.get(i)*100 + 90, playerY.get(i) * 100 + 5, playerX.get(i) * 100 + 5, playerY.get(i)*100 + 90);
		}
		for (int i=0; i<computerX.size(); i++) {
			g.drawOval((computerX.get(i)+1)*100 + 5, (computerY.get(i)+1) * 100 + 5, 90, 90);
			board[computerY.get(i)][computerX.get(i)] = o;
			computerX.remove(i);
			computerY.remove(i);
		}
		//win condition for rows
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns - 2; j++){
				if (board[i][j] == board[i][j+1] && board[i][j+1]== board[i][j+2] && board[i][j] != empty) {
					g.setColor(Color.RED);
					g.drawLine((j+1)*100 + 5, (i+1)*100 + 45, (j+1)*100 + 300,(i+1)*100 + 45);
				} 
			}
		}
		//win condition for columns
		for (int i=0; i<rows - 2; i++) {
			for (int j=0; j < columns; j++) {
				if (board[i][j] == board[i+1][j] && board[i+1][j]== board[i+2][j] && board[i][j] != empty){
					g.setColor(Color.RED);
					g.drawLine((j+1)*100 + 45, (i+1)*100 + 5, (j+1)*100 + 45, (i+1)*100 + 300);
				}
			}
		}
		//win condition for diagonals
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != empty){
			g.setColor(Color.RED);
			g.drawLine(105, 100, 400, 400);
		}
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != empty){
			g.setColor(Color.RED);
			g.drawLine(400, 100, 105, 400);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getX() <= 400 && e.getX() >= 100 && e.getY() <= 400 && e.getY() >= 100 && isEmpty(e.getX()/100 - 1, e.getY()/100 -1)){
		playerX.add(e.getX()/100);
		playerY.add(e.getY()/100);
		board[e.getY()/100 -1][e.getX()/100 -1] = x;
		if (validMoves() > 1 && hasWon("X") == false) {
			computerMove();
		}
		}
		repaint();
	}
	public boolean isEmpty(int x, int y) {
		if (board[y][x] == 0) {
			return true;
		} else {
			return false;
		}
	}
	public int validMoves() {
		int validMoves = 0;
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (board[i][j] == empty) {
					validMoves++;
				}
			}
		}
		return validMoves;
	}
	public boolean hasWon(String player) {
		if (player == "X") {
			return (board[0][0] == x && board[0][1] == x && board[0][2] == x //rows
					|| board[1][0] == x && board[1][1] == x && board[1][2] == x
					|| board[2][0] == x && board[2][1] == x && board[2][2] == x
					|| board[0][0] == x && board[1][0] == x && board[2][0] == x //columns
					|| board[0][1] == x && board[1][1] == x && board[2][1] == x
					|| board[0][2] == x && board[1][2] == x && board[2][2] == x
					|| board[0][0] == x && board[1][1] == x && board[2][2] == x
					|| board[0][2] == x && board[1][1] == x && board[2][0] == x);
		} else {
			return (board[0][0] == o && board[0][1] == o && board[0][2] == o //rows
					|| board[1][0] == o && board[1][1] == o && board[1][2] == o
					|| board[2][0] == o && board[2][1] == o && board[2][2] == o
					|| board[0][0] == o && board[1][0] == o && board[2][0] == o //columns
					|| board[0][1] == o && board[1][1] == o && board[2][1] == o
					|| board[0][2] == o && board[1][2] == o && board[2][2] == o
					|| board[0][0] == o && board[1][1] == o && board[2][2] == o
					|| board[0][2] == o && board[1][1] == o && board[2][0] == o);
		}
	}
	public void computerMove() {
		int x = (int)(Math.random() * 3);
		int y = (int)(Math.random() * 3);
		if (isEmpty(x, y) == false) {
			computerMove();
		} else {
			computerX.add(x);
			computerY.add(y);
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
