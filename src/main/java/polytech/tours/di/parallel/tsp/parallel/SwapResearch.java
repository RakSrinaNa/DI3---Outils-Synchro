package polytech.tours.di.parallel.tsp.parallel;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;
import polytech.tours.di.parallel.tsp.TSPCostCalculator;
import java.util.Random;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 30/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-30
 */
public class SwapResearch extends Searcher
{
	public SwapResearch(long start, long duration, Solution solution, Random rnd, Instance instance)
	{
		super(start, duration, solution, rnd, instance);
	}
	
	@Override
	public void loop()
	{
		int index = rnd.nextInt(nodeNumber);
		solution.swap(index, (index + 1) % nodeNumber);
		solution.setOF(TSPCostCalculator.calcOF(distances, solution));
		//System.out.println(solution);
		if(best == null)
			best = solution.clone();
		else if(solution.getOF() < best.getOF())
			best = solution.clone();
	}
}
