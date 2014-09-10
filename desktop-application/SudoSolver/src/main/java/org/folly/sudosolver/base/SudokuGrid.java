package org.folly.sudosolver.base;

import java.util.HashSet;
import java.util.Set;

/**
 * @author neeraj
 * @version 0.1
 * Provide a Grid to save Sudoku 9*9 grid.
 */

public final class SudokuGrid {

	private int [][] m_grid;
	private boolean isSolved;
	Set<Integer> validNumbers;
	Set<String> validStrings;
	
	public SudokuGrid ()
	{
		m_grid=new int[10][10];
		isSolved=false;
		
	    validNumbers = new HashSet<Integer>();
		validNumbers.add(new Integer(1));
		validNumbers.add(new Integer(2));
		validNumbers.add(new Integer(3));
		validNumbers.add(new Integer(4));
		validNumbers.add(new Integer(5));
		validNumbers.add(new Integer(6));
		validNumbers.add(new Integer(7));
		validNumbers.add(new Integer(8));
		validNumbers.add(new Integer(9));
		validNumbers.add(new Integer(0)); 

		
	    validStrings = new HashSet<String>();
		validStrings.add(new String("1"));
		validStrings.add(new String("2"));
		validStrings.add(new String("3"));
		validStrings.add(new String("4"));
		validStrings.add(new String("5"));
		validStrings.add(new String("6"));
		validStrings.add(new String("7"));
		validStrings.add(new String("8"));
		validStrings.add(new String("9"));
		validStrings.add(new String("0")); 
	}

	
	public void setGrid(int [][] dup_grid)
	{
		m_grid=dup_grid;
	}
	
	public void setGridVal(int x, int y, int value)
	{
		m_grid[x][y]=value;
	}
	
	public int[][] getGrid()
	{
		return m_grid;
	}
	
	public int getGridVal(int x, int y)
	{
		return m_grid[x][y];		
	}
	
	public boolean getStatus() {
		boolean flag = true;

		int i, j;
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				if (m_grid[i][j] == 0) {
					isSolved = false ;
					flag = false;
					break;
				}
			}
		}
		
		if(flag == true)
			isSolved = true;
		return flag;
	}

	
	public void display()
	{
		System.out.println("Grid is :");
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				System.out.print(m_grid[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	
	public boolean validate()
	{
		boolean []list =new boolean[10];
		int i,row,col;
	
		// validate the Range of Numbers
		
		for(row=0;row<9;row++)
		{
			for(col=0;col<9;col++)
			{
				if(!(validNumbers.contains( m_grid[row][col]) ))
				{
					return false;
				}
			}
		}
		
		
	     
		// check for the row [ Number Duplicate ]
		
		for(row=0;row<9;row++)
		{
			for(i=0;i<10;i++)
				list[i]=false;
			
			for(col=0;col<9;col++)
			{
				if(m_grid[row][col] != 0)
				{
					if(list[m_grid[row][col]]==true)
					{
						return false;
					}
					else
					{
						list[m_grid[row][col]]=true;	
					}
				}
			}
			
		}
		
		
		// check for the Column

		for(col=0;col<9;col++)
		{
			for(i=0;i<10;i++)
				list[i]=false;
			
			for(row=0;row<9;row++)
			{
				if(m_grid[row][col] != 0)
				{
					if(list[m_grid[row][col]]==true)
					{
						return false;
					}
					else
					{
						list[m_grid[row][col]]=true;	
					}
				}
			}
			
		}
		
		
		// check for the Square

		for (int x = 0; x < 9; x = x + 3) {
			for (int y = 0; y < 9; y = y + 3) {

				int sqrX = x;
				int sqrY = y;

				for (int k = 0; k < 10; k++)
					list[k] = false;

				for (row = sqrX; row <= (sqrX + 2); row++) {
					for (col = sqrY; col <= (sqrY + 2); col++) {
						if (m_grid[row][col] != 0) {
							if (list[m_grid[row][col]] == true) {
								return false;
							} else {
								list[m_grid[row][col]] = true;
							}
						}
					}
				}

			}
		}

	
		
		return true;
	}
	
	
	public boolean isValidNumber(int num)
	{
		if(validNumbers.contains( num ))
		{
			return true;
		}
		return false;	
	}
	
	public boolean isValidString(String num)
	{
		if(validStrings.contains( num ))
		{
			return true;
		}
		return false;	
	}
}
