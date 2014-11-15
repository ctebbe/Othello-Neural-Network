package csu.project.othello_tmp.player;

import java.util.ArrayList;

import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardNetwork;

import csu.project.othello_tmp.Board;
import csu.project.othello_tmp.Move;

public class NeuralNetworkPlayer extends Player{
	private FeedforwardNetwork network;
	private ArrayList<Move> moves;
	
	public NeuralNetworkPlayer(FeedforwardNetwork network){
		this.network = network;
		moves = new ArrayList<Move>();
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
		
		Move optimalMove = moves.get(0);
		char opponent = color == 'W' ? 'B' : 'W';
		double maxScore = getScore(board, optimalMove, color, opponent);
		
		for(int i=1; i<moves.size(); i++){
			Move move = moves.get(i);
			double score = getScore(board, move, color, opponent);
			
			if(score > maxScore){
				maxScore = score;
				optimalMove = move;
			}
		}
		
		moves.clear();
		return optimalMove;
	}
	
	private double getScore(Board board, Move move, char player, char opponent){
		double[] input = new double[64];
		
		char[][] newBoard = board.addPiece(move.getX(), move.getY(), color);
		int index = 0;
		for(int i=0; i<Board.SIZE; i++){
			for(int j=0; j<Board.SIZE; j++){
				char space = newBoard[i][j];
				
				if(space == player)
					input[index++] = 1;
				else if(space == opponent)
					input[index++] = -1;
				else
					input[index++] = 0;
			}
		}
		
		return network.computeOutputs(input)[0];
	}
	
	public FeedforwardNetwork getNetwork(){
		return network;
	}
}