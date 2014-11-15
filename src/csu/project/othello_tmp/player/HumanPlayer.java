package csu.project.othello_tmp.player;

import java.util.Scanner;

import csu.project.othello_tmp.Board;
import csu.project.othello_tmp.Move;

public class HumanPlayer extends Player{
	private Scanner scan;
	
	public HumanPlayer(){
		scan = new Scanner(System.in);
	}
	
	public Move getMove(Board board){
		if(!checkForValidMove(board)){
			System.out.println("player '" + color + "' has no valid moves");
			return null;
		}
		
		System.out.println("player '" + color + "' it's your move\n" + board);
		
		int x, y;
		while(true){
			try{
				System.out.println("x:");
				x = Integer.parseInt(scan.next());
				System.out.println("y:");
				y = Integer.parseInt(scan.next());
			}catch(Exception e){
				System.out.println("invalid entry - try again");
				continue;
			}
			
			if(!board.validMove(x, y, color)){
				System.out.println("invalid move - try again");
				continue;
			}
			
			break;
		}
		
		return new Move(x,y);
	}
	
	private boolean checkForValidMove(Board board){
		for(int i=0; i<Board.SIZE; i++){
			for(int j=0; j<Board.SIZE; j++){
				if(board.validMove(i, j, color))
					return true;
			}
		}
		
		return false;
	}
}