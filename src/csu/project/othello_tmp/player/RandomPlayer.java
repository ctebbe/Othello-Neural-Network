package csu.project.othello_tmp.player;

import java.util.ArrayList;
import java.util.Random;

import csu.project.othello_tmp.Board;
import csu.project.othello_tmp.Move;

public class RandomPlayer extends Player{
	private ArrayList<Move> moves;
	private Random random;
	
	public RandomPlayer(){
		moves = new ArrayList<Move>();
		random = new Random();
	}
	
	@Override
	public Move getMove(Board board){
		for(int i=0; i<Board.SIZE; i++){
			for(int j=0; j<Board.SIZE; j++){
				if(board.validMove(i, j, color))
					moves.add(new Move(i,j));
			}
		}
		
		if(moves.isEmpty())
			return null;
		
		Move move = moves.get(Math.abs(random.nextInt()) % moves.size());
		
		moves.clear();
		return move;
	}
}