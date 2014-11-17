package csu.project.othello_tmp.player;

import java.util.ArrayList;

import csu.project.othello_tmp.Board;
import csu.project.othello_tmp.Move;

public abstract class Player{
	protected char color;
	protected ArrayList<Integer> ranks = new ArrayList<Integer>();
	
	public abstract Move getMove(Board board);
	
	public void setColor(char color){
		this.color = color;
	}
	
	public char getColor(){
		return color;
	}
	
	public void addRank(int rank){
		ranks.add(rank);
	}
	
	public double getAvgRank(){
		if(ranks.size() == 0)
			return 0;
		
		double avgRank = 0.0;
		
		for(int rank : ranks)
			avgRank += rank;
		
		return avgRank / ranks.size();
	}
	
	public int getNumRanks(){
		return ranks.size();
	}
}