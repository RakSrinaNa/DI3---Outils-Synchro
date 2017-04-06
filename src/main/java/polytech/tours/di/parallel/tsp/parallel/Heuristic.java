package polytech.tours.di.parallel.tsp.parallel;

import java.util.Arrays;
import java.util.Random;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;

public class Heuristic {

	public static Solution closest(Instance instance, Random rd){
		Solution solution = new Solution();
		solution.add(rd.nextInt(instance.getN()));
		
		for(int i = 0; i < instance.getN() -1; i++){
			int city = solution.get(solution.size() -1);
			double[] closeCity = instance.getDistanceMatrix()[city].clone();
			
			for(int j = 0; j < closeCity.length; j++)
				if(solution.contains(j))
					closeCity[j] = Double.MAX_VALUE;

			solution.add(min(closeCity));	
		}		
		
		return solution;
	}
	
	private static int min(double[] tab){
		int index = 0;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < tab.length; i++)
			if(tab[i] < min)
			{
				min = tab[i];
				index = i;
			}
		return index;
	}
	
}
