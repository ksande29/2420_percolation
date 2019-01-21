package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats 
{
	private int N;
	private int T;
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
			this.N = N;
			this.T = T;
			this.thresholds= new double[T];
			this.numOpen = 0;
		
			for (int i = 0; i < T; i++)
			{
				percolation = new Percolation(N);
				
		            while (!percolation.percolates()) 
		            {
		                System.out.println("no percolation");
		            	x = StdRandom.uniform(N);
		                y = StdRandom.uniform(N);
		                System.out.println("x: " + x);
	                    System.out.println("y: " + y);
		                if (!percolation.isOpen(x, y)) 
		                {
		                	percolation.open(x, y);
		                    numOpen++;
		                }
		            }
		            thresholds[i] = numOpen / (N*N);   
			}
			
			System.out.println("mean: " + mean());
			System.out.println("standard deviation: " + stddev());
			System.out.println("95% sure the threshold is between " + confidenceLow() + " and " + confidenceHigh());
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


