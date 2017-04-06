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
public abstract class Searcher implements Callable<Void>
{
	protected Solution solution;
	protected final Random rnd;
	protected final Instance instance;
	protected Solution best;
	private boolean stop = false;
	
	public Searcher(Solution solution, Random rnd, Instance instance)
	{
		this.solution = solution;
		this.rnd = rnd;
		this.instance = instance;
	}
	
	@Override
	public Void call() throws Exception
	{
		while(!stop)
			loop();
		return null;
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
