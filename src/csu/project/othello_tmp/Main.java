package csu.project.othello_tmp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardLayer;
import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardNetwork;

import csu.project.othello_tmp.player.NeuralNetworkPlayer;
import csu.project.othello_tmp.player.Player;

public class Main{
	public static void main(String[] args){
		int ITERATIONS = 10, GAMES = 20, PLAYERS = 100;
		int[] HIDDEN_LAYERS = new int[]{40,20};
		
		LinkedHashMap<Player,Integer> scores = new LinkedHashMap<Player,Integer>();
		ArrayList<Player> availablePlayers = new ArrayList<Player>();
		Random random = new Random();
		
		//initialize players
		for(int i=0; i<PLAYERS; i++){
			FeedforwardNetwork network = new FeedforwardNetwork();
			network.addLayer(new FeedforwardLayer(64));
			
			for(int nodeCount : HIDDEN_LAYERS)
				network.addLayer(new FeedforwardLayer(nodeCount));
			
			network.addLayer(new FeedforwardLayer(1));
			
			network.reset();
			availablePlayers.add(new NeuralNetworkPlayer(network));
		}
		
		//perform iterations of selective mutation
		for(int i=0; i<ITERATIONS; i++){
			for(Player player : availablePlayers)
				scores.put(player, 0);
			
			//randomly match each player again opponents
			for(int j=0; j<GAMES; j++){
				while(availablePlayers.size() > 0){
					Player playerOne = availablePlayers.get(Math.abs(random.nextInt()) % availablePlayers.size());
					availablePlayers.remove(playerOne);
					
					Player playerTwo = availablePlayers.get(Math.abs(random.nextInt()) % availablePlayers.size());
					availablePlayers.remove(playerTwo);
					
					Board board = new Board();
					OthelloGame game = new OthelloGame(board, playerOne, playerTwo);
					game.play();
					
					int playerOneScore = board.getCount(playerOne), playerTwoScore = board.getCount(playerTwo);
					if(playerOneScore > playerTwoScore)
						scores.put(playerOne, scores.get(playerOne) + 1);
					else if(playerTwoScore > playerOneScore)
						scores.put(playerTwo, scores.get(playerTwo) + 1);
				}
				
				for(Player player : scores.keySet())
					availablePlayers.add(player);
			}
			
			//print out results
			int index = 0;
			for(Player player : scores.keySet())
				System.out.println(index++ + " : " + player + " : " + scores.get(player));
			
			//remove the bottom half of players
			ArrayList<Player> rankedPlayers = new ArrayList<Player>();
			for(int j=GAMES; j >=0; j--){
				for(Player player : scores.keySet()){
					if(scores.get(player) == j)
						rankedPlayers.add(player);
				}
			}
			
			while(rankedPlayers.size() > PLAYERS/2)
				rankedPlayers.remove(rankedPlayers.size()-1);
			
			//mutate existing players
			for(int j=0; j<PLAYERS/2; j++){
				NeuralNetworkPlayer player = (NeuralNetworkPlayer)rankedPlayers.get(j);
				FeedforwardNetwork cloneNetwork = (FeedforwardNetwork)player.getNetwork().clone();
				
				for(FeedforwardLayer layer : cloneNetwork.getLayers()){
					double[] fire = layer.getFire();
					
					for(int k=0; k<fire.length; k++){
						double fireValue = fire[k];
						
						double modValue;
						if(fireValue < .5)
							modValue = fireValue;
						else
							modValue = 1 - fireValue;
						
						modValue *= random.nextDouble();
						if(random.nextDouble() > .5)
							fireValue += modValue;
						else
							fireValue -= modValue;
						
						layer.setFire(k, fireValue);
					}
				}
				
				//TODO mutate players
				rankedPlayers.add(new NeuralNetworkPlayer(cloneNetwork));
			}
			
			//move rankedPlayers to availablePlayers
			availablePlayers.clear();
			scores.clear();
			for(Player player : rankedPlayers)
				availablePlayers.add(player);
		}
	}
}