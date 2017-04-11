package polytech.tours.di.parallel.tsp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Launches the optimization algorithm
 *
 * @author Jorge E. Mendoza (dev@jorge-mendoza.com)
 * @version %I%, %G%
 */
public class Launcher
{
	
	/**
	 * @param args:
	 *           0: the file (path included) with he configuration settings
	 */
	public static void main(String[] args)
	{
		ArrayList<String> results = new ArrayList<>();

		String folder = "./config/graph6/";
		String files[] = {"serial", "20-20-1", "1000-200-1", "1000-500-1", "20-20-3"};

		for(String file : files)
		{
			file += ".xml";
			System.out.println("Processing file " + folder + file);

			//read properties
			Properties config = new Properties();
			try
			{
				if(args.length > 0)
					config.loadFromXML(new FileInputStream(args[0]));
				else
					//config.loadFromXML(new FileInputStream("./config/Example_config.xml"));
					config.loadFromXML(new FileInputStream(folder + file));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

			//dynamically load the algorithm class
			Algorithm algorithm = null;
			try
			{
				Class<?> c = Class.forName(config.getProperty("algorithm"));
				algorithm = (Algorithm) c.newInstance();
			}
			catch(ClassNotFoundException | InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			ArrayList<Solution> solutions = new ArrayList<>();
			for(int i = 1; i <= 10; i++)
			{
				System.out.print(i + "/10 ==>\t\t");
				solutions.add(algorithm.run(config));
			}
			String result = solutions.stream().map(solution -> "" + solution.getOF()).collect(Collectors.joining("\t"));
			results.add(result);
			System.out.println(result + "\n");
		}
		System.out.println("\n\n" + results.stream().collect(Collectors.joining("\n")));
	}
}
