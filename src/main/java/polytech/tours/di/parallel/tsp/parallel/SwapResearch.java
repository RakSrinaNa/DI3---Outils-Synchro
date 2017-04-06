package polytech.tours.di.parallel.tsp.parallel;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;
import polytech.tours.di.parallel.tsp.TSPCostCalculator;
import java.util.Random;

public class SwapResearch extends Searcher
{
	public SwapResearch(long endTime, long duration, Solution solution, Random rnd, Instance instance)
	{
		super(endTime, duration, solution, rnd, instance);
	}
	
	@Override
	public void loop()
	{
		int index = rnd.nextInt(nodeNumber);
		solution.swap(index, (index + 1) % nodeNumber);
		solution.setOF(TSPCostCalculator.calcOF(getDistances(), solution));
		//System.out.println(solution);
		if(best == null)
			best = solution.clone();
		else if(solution.getOF() < best.getOF())
			best = solution.clone();
	}
}
