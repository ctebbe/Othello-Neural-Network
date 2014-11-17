package csu.project.othello_tmp;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardNetwork;

import csu.project.othello_tmp.player.HumanPlayer;
import csu.project.othello_tmp.player.NeuralNetworkPlayer;
import csu.project.othello_tmp.player.Player;

public class Testing{
	public static void main(String[] args){
		FeedforwardNetwork network = null;
		
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/hamersaw/Desktop/6.txt"));
			
			network = (FeedforwardNetwork)in.readObject();
			
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Player playerOne = new HumanPlayer(), playerTwo = new NeuralNetworkPlayer(network);
		Board board = new Board();
		
		OthelloGame game = new OthelloGame(board, playerOne, playerTwo);
		game.play();
		
		System.out.println("FINAL BOARD");
		System.out.println(board);
		
		System.out.println("human:" + board.getCount(playerOne));
		System.out.println("ai:" + board.getCount(playerTwo));
	}
}