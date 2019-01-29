package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author Kate
 *
 */
public class Percolation 
{
	private int N; 						//number of rows/columns
	private boolean[][] isSqrOpen; 
	private WeightedQuickUnionUF uf;
	private int top; 					//index of top node in WeightedQuickUnion
	private int bottom; 				//index of bottom node in WeightedQuickUnion
	
	/**
	 * Constructor 
	 * Throws an error if a value less than 1 is entered
	 * Creates a 2D boolean array (which corresponds to each square in the percolation grid) and sets
	 * all values to 0 in the array.
	 * Creates an instance of WeightedUnionFind (to be able to connect open squares) and connects all
	 * of the squares on the top to a virtual node called top and all squares on the bottom row to a 
	 * virtual node called bottom.
	 * @param N (int) number of rows/columns
	 */
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
	
	/**
	 * Opens a specified square and connects that open square to any open squares that are next to it
	 * @param i (int) row number
	 * @param j (int) column number
	 */
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
			
			if (j < (N - 1) && isOpen(i, j+1)) //connect right
				uf.union(getFlatIndex(i, j), getFlatIndex(i, j+1));
		}
	}
	
	/**
	 * Converts the position of a square in the 2D array to the corresponding index in a 1D array
	 * (which can be used with WeightedUnionFind)
	 * Indexes start at 1 because the index 0 is reserved for the top node
	 * @param i (int) column number
	 * @param j (int) row number
	 * @return
	 */
	private int getFlatIndex(int i, int j) 
	{
		int index = i*N + j + 1;
		return index;
	}
	
	/**
	 * Checks if a square is open
	 * @param i (int) - row number
	 * @param j (int) - column number
	 * @return
	 */
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
	
	/**
	 * Checks if there is water in an square
	 * @param i (int) - row number
	 * @param j (int) - column number
	 * @return
	 */
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
	
	/**
	 * Checks if the top node is connected to the bottom node (checks if water can reach from the
	 * top to the bottom)
	 * @return
	 */
	public boolean percolates() // does the system percolate? 
	{				
		//if top connected to bottom
		return uf.connected(top, bottom);
	}

}
