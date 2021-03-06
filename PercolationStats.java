package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 
 * @author Kate
 *
 */
public class PercolationStats 
{
	private Percolation percolation;
	private double[] thresholds;
	private int numOpen;
	private int i;
	private int j;
	private int N;
	private int T;
		
	/**
	 * Constructor
	 * Performs a specified number of tests. Each test creates an instance of a percolation (with no
	 * open sites) and then randomly opens sites one at a time until the instance percolates. Based off 
	 * of the results, the percolation threshold for each instance is calculated.
	 * @param N (int) - number of rows/columns
	 * @param T (int) - number of times to repeat the test
	 */
	public PercolationStats(int N, int T) // perform T independent experiments on an N�by�N grid
	{
		if (N <= 0 || T <= 0)	 
		{
			throw new java.lang.IllegalArgumentException();
		}
		else
		{	
			this.N = N;
			this.T = T;
			thresholds= new double[T];
		
			for (int index = 0; index < T; index++)
			{
				numOpen = 0;
				percolation = new Percolation(N);
				
		        while (!percolation.percolates()) 
		        {
		           	i = StdRandom.uniform(N);
		            j = StdRandom.uniform(N);
		            if (!percolation.isOpen(i, j)) 
		            {
		               	percolation.open(i, j);
		                numOpen++;
		            }
		        }
		        
		        thresholds[index] = (double) numOpen / (N*N); 
			}
		}
	}
	
	/**
	 * Calculates the mean of the values in an array of doubles
	 * @return mean (double)
	 */
	public double mean() // sample mean of percolation threshold
	{
		double mean = StdStats.mean(thresholds);
		return mean;
	}
	
	/**
	 * Calculates the standard deviation of the values in an array of doubles
	 * @return standard deviation (double)
	 */
	public double stddev() // sample standard deviation of percolation threshold
	{
		double stddev = StdStats.stddev(thresholds);
		return stddev;
	}
		
	/**
	 * Calculates the the lower bound in a 95% confidence interval from the values in
	 * an array of doubles
	 * @return low end of 95% confidence interval (double)
	 */
	public double confidenceLow() // low endpoint of 95% confidence interval
	{
		return mean() - (1.96 * stddev() / Math.sqrt(T));
	}
	
	/**
	 * Calculates the the upper bound in a 95% confidence interval from the values in
	 * an array of doubles
	 * @return high end of 95% confidence interval (double)
	 */
	public double confidenceHigh() // high endpoint of 95% confidence interval
	{
		return mean() + (1.96 * stddev() / Math.sqrt(T));
	}

	/**
	 * Prints out the mean, standard deviation, and confidence interval
	 */
	private void printResults()
	{		
		System.out.printf("mean: %f%n", mean());
		System.out.printf("standard deviation: %f%n", stddev());
		System.out.printf("95%% confident that the threshold is between %f and %f%n", confidenceLow(), confidenceHigh());
	}
	
	/**
	 * Test Client - tests the PercolationStats class
	 * @param args
	 */
	public static void main(String[] args)
	{
		PercolationStats stats = new PercolationStats(4, 100);
		stats.printResults();
		System.out.println();
	}
	

}


