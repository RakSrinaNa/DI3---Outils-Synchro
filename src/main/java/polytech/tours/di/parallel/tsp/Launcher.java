package polytech.tours.di.parallel.tsp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
		//read properties
		Properties config = new Properties();
		try
		{
			if(args.length > 0)
				config.loadFromXML(new FileInputStream(args[0]));
			else
				//config.loadFromXML(new FileInputStream("./config/Example_config.xml"));
				config.loadFromXML(new FileInputStream("./config/configWI3D.xml"));
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
		
		for(int i = 0; i < 10; i++)
		{
			//run algorithm
			Solution s = algorithm.run(config);
			
			//report solution
			System.out.print(s.getOF() + "\t");
		}
		System.out.println();
	}
}
