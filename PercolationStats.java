package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats 
{
	private Percolation percolation;
	private double[] thresholds;
	private int numOpen;
	private int x;
	private int y;
		
	public PercolationStats(int N, int T) // perform T independent experiments on an N­by­N grid
	{
		if (N <= 0 || T <= 0)	 
		{
			throw new java.lang.IllegalArgumentException();
		}
		else
		{	
			thresholds= new double[T];
		
			for (int i = 0; i < T; i++)
			{
				numOpen = 0;
				percolation = new Percolation(N);
				
		        while (!percolation.percolates()) 
		        {
		           	x = StdRandom.uniform(N);
		            y = StdRandom.uniform(N);
		            //System.out.println("x: " + x);
	                //System.out.println("y: " + y);
		            if (!percolation.isOpen(x, y)) 
		            {
		               	percolation.open(x, y);
		                numOpen++;
		            }
		        }
		        thresholds[i] = (double) numOpen / (N*N);   
			}
			
			System.out.printf("mean: %.2f%n", mean());
			System.out.printf("standard deviation: %.2f%n", stddev());
			System.out.printf("95%% confident that the threshold is between %.2f and %.2f%n", confidenceLow(), confidenceHigh());
		}
	}
		
	public double mean() // sample mean of percolation threshold
	{
		double mean = StdStats.mean(thresholds);
		return mean;
	}
		
	public double stddev() // sample standard deviation of percolation threshold
	{
		double stddev = StdStats.stddev(thresholds);
		return stddev;
	}
		
	public double confidenceLow() // low endpoint of 95% confidence interval
	{
		return mean() - stddev();
	}
		
	public double confidenceHigh() // high endpoint of 95% confidence interval
	{
		return mean() + stddev();
	}

	
	public static void main(String[] args)
	{
		PercolationStats stats = new PercolationStats(5, 100);
	}

}


