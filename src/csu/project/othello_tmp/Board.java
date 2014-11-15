package csu.project.othello_tmp;

import csu.project.othello_tmp.player.Player;

public class Board{
	public static final int SIZE = 8;
	private char[][] board;
	
	public Board(){
		board = new char[SIZE][SIZE];
		
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++)
				board[i][j] = '-';
		}
		
		board[3][3] = 'W';
		board[4][4] = 'W';
		board[3][4] = 'B';
		board[4][3] = 'B';
	}
	
	public boolean validMove(int x, int y, char player){
		if(board[x][y] != '-')
			return false;
		
		char opponent = player == 'W' ? 'B' : 'W';
		
		if(checkFlank(x, y, 1, 0, player, opponent))
			return true;
		
		if(checkFlank(x, y, 1, 1, player, opponent))
			return true;
		
		if(checkFlank(x, y, 0, 1, player, opponent))
			return true;
		
		if(checkFlank(x, y, -1, 1, player, opponent))
			return true;
		
		if(checkFlank(x, y, -1, 0, player, opponent))
			return true;
		
		if(checkFlank(x, y, -1, -1, player, opponent))
			return true;
		
		if(checkFlank(x, y, 0, -1, player, opponent))
			return true;

		if(checkFlank(x, y, 1, -1, player, opponent))
			return true;
		
		return false;
	}
	
	public char[][] addPiece(int x, int y, char player){
		char[][] newBoard = new char[SIZE][SIZE];
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++)
				newBoard[i][j] = board[i][j];
		}
		
		newBoard[x][y] = player;
		
		char opponent = player == 'W' ? 'B' : 'W';
		applyFlank(newBoard, x, y, 1, 0, player, opponent);
		applyFlank(newBoard, x, y, 1, 1, player, opponent);
		applyFlank(newBoard, x, y, 0, 1, player, opponent);
		applyFlank(newBoard, x, y, -1, 1, player, opponent);
		applyFlank(newBoard, x, y, -1, 0, player, opponent);
		applyFlank(newBoard, x, y, -1, -1, player, opponent);
		applyFlank(newBoard, x, y, 0, -1, player, opponent);
		applyFlank(newBoard, x, y, 1, -1, player, opponent);
		
		return newBoard;
	}
	
	private boolean checkFlank(int x, int y, int xInc, int yInc, char player, char opponent){
		boolean flanking = false;
		
		while(true){
			x += xInc;
			y += yInc;
			
			if(x < 0 || x >= SIZE || y < 0 || y >= SIZE)
				return false;
			
			char space = board[x][y];
			if(space == '-')
				return false;
			else if(space == opponent)
				flanking = true;
			else if(space == player)
				return flanking;
		}
	}
	
	private void applyFlank(char[][] board, int x, int y, int xInc, int yInc, char player, char opponent){
		int origX = x, origY = y;
		
		while(true){
			x += xInc;
			y += yInc;
			
			if(x < 0 || x >= SIZE || y < 0 || y >= SIZE)
				return;
			
			if(board[x][y] == player)
				break;
			else if(board[x][y] != opponent)
				return;
		}
		
		xInc *= -1;
		yInc *= -1;
		
		while(x != origX || y != origY){
			board[x][y] = player;
			
			x += xInc;
			y += yInc;
		}
	}
	
	public void setBoard(char[][] board){
		this.board = board;
	}
	
	public char getWinner(){
		int black = 0, white = 0;
		
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if(board[i][j] == 'B')
					black++;
				else if(board[i][j] == 'W')
					white++;
			}
		}
		
		if(black == white)
			return '-';
		
		return black > white ? 'B' : 'W';
	}
	
	public int getCount(Player player){
		int count = 0;
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if(board[i][j] == player.getColor())
					count++;
			}
		}
		
		return count;
	}
	
	@Override
	public String toString(){
		String str = "";
		
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++)
				str += board[i][j];
						
			str += "\n";
		}
		
		return str;
	}
}