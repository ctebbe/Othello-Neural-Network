package csu.project.othello_tmp.player;

import csu.project.othello_tmp.Board;
import csu.project.othello_tmp.Move;

public abstract class Player{
	protected char color;
	
	public abstract Move getMove(Board board);
	
	public void setColor(char color){
		this.color = color;
	}
	
	public char getColor(){
		return color;
	}
}