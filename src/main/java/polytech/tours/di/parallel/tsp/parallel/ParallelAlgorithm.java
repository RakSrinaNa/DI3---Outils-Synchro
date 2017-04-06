package polytech.tours.di.parallel.tsp.parallel;

import polytech.tours.di.parallel.tsp.Algorithm;
import polytech.tours.di.parallel.tsp.Instance;
import polytech.tours.di.parallel.tsp.InstanceReader;
import polytech.tours.di.parallel.tsp.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 30/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-30
 */
public class ParallelAlgorithm implements Algorithm
{
	@Override
	public Solution run(Properties config)
	{
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		try
		{
			InstanceReader ir = new InstanceReader();
			ir.buildInstance(config.getProperty("instance"));
			Instance instance = ir.getInstance();
			long max_cpu = Long.valueOf(config.getProperty("maxcpu"));
			
			Random rnd = new Random(Long.valueOf(config.getProperty("seed")));
			long startTime = System.currentTimeMillis();
			ExecutorService executorService = Executors.newFixedThreadPool(Integer.valueOf(config.getProperty("nbThreads")));
			
			Solution solution = new Solution();
			for(int i = 0; i < instance.getN(); i++)
				solution.add(i);
			ArrayList<Searcher> searchers = new ArrayList<>();
			for(int i = 0; i < Integer.valueOf(config.getProperty("startingPoints")); i++)
			{
				Searcher searcher;
				Collections.shuffle(solution, rnd);
				switch(Integer.valueOf(config.getProperty("searchID")))
				{
					default:
					case 0:
						searcher = new ShuffleReseach(startTime, max_cpu, solution.clone(), rnd, instance);
						break;
					case 1:
						searcher = new SwapResearch(startTime, max_cpu, solution.clone(), rnd, instance);
						break;
					case 2:
						searcher = new InvertResearch(startTime, max_cpu, solution.clone(), rnd, instance);
						break;
					case 3:
						searcher = new Swap2Research(startTime, max_cpu, solution.clone(), rnd, instance);
				}
				searchers.add(searcher);
			}
			
			ArrayList<Future<Solution>> futures = new ArrayList<>();
			try
			{
				futures.addAll(executorService.invokeAll(searchers));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			executorService.shutdown();
			
			Solution best = null;
			for(Future<Solution> future : futures)
			{
				if(future.isDone())
					if(best == null || future.get().getOF() < best.getOF())
						best = future.get();
			}
			
			System.out.println("Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
			
			//return the solution
			return best;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
