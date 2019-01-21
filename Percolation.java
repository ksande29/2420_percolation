package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation 
{
	private int N;
	private boolean[][] isSqrOpen; 
	private WeightedQuickUnionUF uf;
	private int top;
	private int bottom;
	
	public Percolation(int N) // create N­by­N grid, with all sites blocked
	{		
		if (N <= 0)	 
		{
			throw new java.lang.IllegalArgumentException();
		}
		else
		{
			this.top = 0;
			this.bottom = N*N + 1;
			this.N = N;
			this.uf = new WeightedQuickUnionUF(N*N + 2);
			
			//initialize all values in isOpen boolean array to false
			this.isSqrOpen = new boolean[N][N]; 
			for (int i = 0; i < isSqrOpen.length; i++)
			{
				for (int j = 0; j < isSqrOpen[i].length; j++)
				{
					isSqrOpen[i][j] = false;
				}
			}
			
			//connect all nodes on top row to top node
			for (int j = 0; j < N; j++)
			{
				uf.union(top, getFlatIndex(0, j));
			}
			
			//connect all nodes on bottom row to bottom node
			for (int j = 0; j < N; j++)
			{
				uf.union(bottom, getFlatIndex(N-1, j)); 
			}
		}
	}
	
	public void open(int i, int j) // open site (row i, column j) if it is not open already
	{
		if( (i < 0 || i > N-1) || (j < 0 || j > N-1) )
		{
			throw new java.lang.IndexOutOfBoundsException();
		}
		else
		{
			//mark site as open
			isSqrOpen[i][j] = true;	
			
			//connect open sites next to this site
			if (i > 0 && isOpen(i-1, j)) // connect above
				uf.union(getFlatIndex(i, j), getFlatIndex(i-1, j));
			
			if (i < N-1 && isOpen(i+1, j)) //connect below
				uf.union(getFlatIndex(i, j), getFlatIndex(i+1, j));
			
			if (j > 0 && isOpen(i, j-1)) //connect left
				uf.union(getFlatIndex(i, j), getFlatIndex(i, j-1));
			
			if (j < 0 && isOpen(i, j+1)) //connect right
				uf.union(getFlatIndex(i, j), getFlatIndex(i, j+1));
		}
	}
	
	private int getFlatIndex(int i, int j) 
	{
		//convert the row and column number to a 1D array index that can be used with uf
		//0 represents the top node
		int index = i*N + j + 1;
		return index;
	}
	
	public boolean isOpen(int i, int j) // is site (row i, column j) open?
	{
		if( (i < 0 || i > N-1) || (j < 0 || j > N-1) )
		{
			throw new  java.lang.IndexOutOfBoundsException();
		}
		else
		{
			return isSqrOpen[i][j];
		}
	}
	
	public boolean isFull(int i, int j) // is site (row i, column j) full?
	{
		if( (i < 0 || i > N-1) || (j < 0 || j > N-1) )
		{
			throw new  java.lang.IndexOutOfBoundsException();
		}
		else
		{
			//if a site is connected to the top return true
			if (uf.connected(top, getFlatIndex(i, j)) && isOpen(i, j))
				return true;
			return false;
		}
	}
		
	public boolean percolates() // does the system percolate? 
	{				
		//if top connected to bottom
		if (uf.connected(top, bottom))
			return true;
		return false;
	}

}
