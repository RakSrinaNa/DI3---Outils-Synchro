package polytech.tours.di.parallel.tsp.parallel;

import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.Solution;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 30/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-30
 */
public abstract class Searcher implements Callable<Solution>
{
	private final long start;
	private final long duration;
	protected Solution solution;
	protected final Random rnd;
	protected final double[][] distances;
	protected Solution best;
	protected final int nodeNumber;
	private boolean stop = false;
	
	public Searcher(long start, long duration, Solution solution, Random rnd, Instance instance)
	{
		this.start = start;
		this.duration = duration * 1000;
		this.solution = solution;
		this.rnd = rnd;
		this.distances = instance.getDistanceMatrix();
		this.nodeNumber = instance.getN();
	}
	
	@Override
	public Solution call() throws Exception
	{
		while(!stop || (start + duration) < System.currentTimeMillis())
			loop();
		return best;
	}
	
	public void stop()
	{
		stop = true;
	}
	
	protected abstract void loop();
	
	public Solution get()
	{
		return best;
	}
}
