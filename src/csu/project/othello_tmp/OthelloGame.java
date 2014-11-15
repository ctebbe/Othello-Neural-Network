package csu.project.othello_tmp;

import csu.project.othello_tmp.player.Player;

public class OthelloGame{
	private Player playerOne, playerTwo;
	private Board board;
	
	public OthelloGame(Board board, Player playerOne, Player playerTwo){
		playerOne.setColor('B');
		playerTwo.setColor('W');
	
		this.board = board;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
	
	public void play(){
		Player turn = playerOne, invalidMove = null;
		while(true){
			Move move = turn.getMove(board);
			if(move == null){
				if(invalidMove == null)
					invalidMove = turn;
				else if(invalidMove != turn)
					break;
			}else{
				if(invalidMove == turn)
					invalidMove = null;
				
				board.setBoard(board.addPiece(move.getX(), move.getY(), turn.getColor()));
			}
			
			if(turn == playerOne)
				turn = playerTwo;
			else
				turn = playerOne;
		}
	}
}