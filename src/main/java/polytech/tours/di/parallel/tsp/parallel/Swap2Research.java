package polytech.tours.di.parallel.tsp.parallel;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;
import polytech.tours.di.parallel.tsp.TSPCostCalculator;
import java.util.Random;

public class Swap2Research extends Searcher
{
	public Swap2Research(long endTime, long duration, Solution solution, Random rnd, Instance instance)
	{
		super(endTime, duration, solution, rnd, instance);
	}
	
	@Override
	public void loop()
	{
		int index = rnd.nextInt(nodeNumber);
		int index2 = rnd.nextInt(nodeNumber);
		solution.swap(index, index2);
		solution.setOF(TSPCostCalculator.calcOF(getDistances(), solution));
		//System.out.println(solution);
		if(best == null)
			best = solution.clone();
		else if(solution.getOF() < best.getOF())
			best = solution.clone();
	}
}
