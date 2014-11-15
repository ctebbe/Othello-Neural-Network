package csu.project.othello_tmp;

import java.util.Random;

import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardLayer;
import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardNetwork;

public class Testing{
	public static void main(String[] args){
		FeedforwardNetwork network = new FeedforwardNetwork();
		
		FeedforwardLayer input = new FeedforwardLayer(64);
		network.addLayer(input);
		
		FeedforwardLayer hidden = new FeedforwardLayer(40);
		network.addLayer(hidden);
		
		FeedforwardLayer output = new FeedforwardLayer(1);
		network.addLayer(output);
		
		network.reset();
		
		double[] values = new double[64];
		double[] results = network.computeOutputs(values);
		
		for(double result : results)
			System.out.println(result);
		
		for(FeedforwardLayer layer : network.getLayers()){
			for(double fireVal : layer.getFire())
				System.out.print(fireVal + " : ");
			
			System.out.println("");
		}
		
		results = network.computeOutputs(values);
		
		for(double result : results)
			System.out.println(result);
		
		Random random = new Random();
		for(FeedforwardLayer layer : network.getLayers()){
			double[] fire = layer.getFire();
			
			for(int i=0; i<fire.length; i++){
				double fireValue = fire[i];
				
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
				
				System.out.print(fireValue +  " : " );
				layer.setFire(i, fireValue);
			}
			
			System.out.println("");
		}
	}
}