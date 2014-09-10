package org.folly.sudosolver.logic;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.folly.sudosolver.base.SudokuGrid;

public class PrimaryApproach extends Approach{

	
	/**
	 * @author neeraj
	 * Inner class for Probability 
	 */
	
	private class Probability
	{
		int value;	
		boolean isFinal;
		Set<Integer> xpectedNum;
		
		public Probability()
		{
			value=0;
			isFinal=false;
			xpectedNum= new HashSet<Integer>();
			xpectedNum.add(new Integer(1));
			xpectedNum.add(new Integer(2));
			xpectedNum.add(new Integer(3));
			xpectedNum.add(new Integer(4));
			xpectedNum.add(new Integer(5));
			xpectedNum.add(new Integer(6));
			xpectedNum.add(new Integer(7));			
			xpectedNum.add(new Integer(8));
			xpectedNum.add(new Integer(9));
		}
	}
	
	/**
	 * @author neeraj
	 * Inner class for Position Graph
	 */
	
	private class PositionGraph
	{
		boolean [][]matrix;	
		boolean done;
		
		public PositionGraph()
		{
			done=false;
			matrix= new boolean[9][9];
			int row,col;
			for(row=0;row<9;row++)
			{
				for(col=0;col<9;col++)
				{
					matrix[row][col]=true;
				}
			}

		}
	}
	
	ArrayList<PositionGraph> numGraph;
	Probability [][] boxProbability;
	
	
	/**
	 * 
	 */
	
	public PrimaryApproach()
	{
		numGraph = new ArrayList<PositionGraph>();
		boxProbability = new Probability [9][9];
		
		for(int j=0;j<9;j++)
		{
			for(int k=0;k<9;k++)
			{
				boxProbability[j][k]=new Probability ();
			}
		}
		
		for(int i=0; i < 9; i++){
			  PositionGraph temp = new PositionGraph();
			  numGraph.add(temp);
		}
			  
	}
	
	
	
	/**
	 * @param grid
	 * @return
	 */
	
	public boolean solve(SudokuGrid grid)
	{
		boolean filledAny=false;
		init(grid);
		
		do
		{
			filledAny=probabilityLogic(grid);	
			filledAny=graphLogic(grid);
		}while(filledAny);
		
		return grid.getStatus();
	}
	
	
	
	/**
	 * Apply Graph Logic and fill the Sudoku Grid
	 * 
	 * @param grid - Contain the Sudoku Grid
	 * @return - true ,if solved atleast one box
	 */
	private boolean graphLogic(SudokuGrid grid)
	{
	
		int count,i,j,k;
		
		int ptrX=0,ptrY=0;
		boolean isPossible = true;
		boolean change = false;
		
		while(isPossible)
		{
			isPossible = false;

		// (a) check for row
		for( i=0;i<9;i++)
		{
			for(j=0;j<9;j++)
			{
				count=0;
				for(k=0;k<9;k++)
				{
				
				if(numGraph.get(i).matrix[j][k]==true)
				{
					count++;
					ptrX=j;ptrY=k;
				}
				if(count==2)
					break;
				}
				if(count==1)
				{
					isPossible=true;
					grid.setGridVal(ptrX, ptrY,i+1);
					solveGraph(ptrX,ptrY,i);	
					solveProbability(ptrX,ptrY,i);
					change = true;
				}// end of if	
			}// end of loop
		}// end of loop


		// for column
		for( i=0;i<9;i++)	{
			for(j=0;j<9;j++)	{
				count=0;
				for(k=0;k<9;k++)
				{
				if(numGraph.get(i).matrix[k][j]==true)
				{
					count++;
					ptrX=k;ptrY=j;
				}
				if(count==2)
					break;
				}
				if(count==1)
				{
					solveGraph(ptrX,ptrY,i);
					solveProbability(ptrX,ptrY,i);
					grid.setGridVal(ptrX, ptrY,i+1);
					isPossible=true;
					change = true;
				}// end of if	
			}// end of loop
		}// end of loop

		
		// for square

		for(i=0;i<9;i++)
		{
		for(j=0;j<9;j=j+3)
		{
		for(k=0;k<9;k=k+3)
		{
			count=0;
			for(int m=j;m<=(j+2);m++)
			{
			for(int n=k;n<=(k+2);n++)
			{
				if(numGraph.get(i).matrix[m][n]==true)
				{
					count++;
					ptrX=m;ptrY=n;
				}
				if(count==2)
					break;
			}
			}
			
			if(count==1)
			{
				solveGraph(ptrX,ptrY,i);
				solveProbability(ptrX,ptrY,i);
				grid.setGridVal(ptrX, ptrY,i+1);
				change=true;
				isPossible=true;
			}// end of if	

			}
			}
			}
		// end of square

		} // end of if possible -while
	 
			return change;
	}

	/**
	 * @param x - X of box
	 * @param y - Y of box
	 * @param num - index value of Box Value
	 */
	private void solveGraph(int x, int y , int num)
	{
			int idx,sqrX,sqrY;
		
			for(idx=0;idx<9;idx++)
			{
				(numGraph.get(idx)).matrix[x][y] = false;
			}
				
			for(idx=0;idx<9;idx++)
			{
				(numGraph.get(num)).matrix[x][idx] = false;				
				(numGraph.get(num)).matrix[idx][y] = false;
			}
			
			sqrX = x-(x%3);
			sqrY = y-(y%3);
			for(int i = sqrX;i <= (sqrX+2);i++)
			{
				for(int j = sqrY;j <= (sqrY+2);j++)
				{
					(numGraph.get(num)).matrix[i][j] = false;
				}
			}
				//numGraph.get(num).matrix[x][y]=true;
			return;
	}


	/**
	 * Apply Probability Logic and fill the Sudoku Grid
	 * 
	 * @param grid - Contain the Sudoku Grid
	 * @return - true ,if solved atleast one box
	 */
	
	private boolean probabilityLogic(SudokuGrid grid)
	{
		boolean isPossible = true; 
		boolean change = false;
		int i,j;
		
		while(isPossible)
		{
			isPossible = false;
			
			for(i=0;i<9;i++)
			{
				for(j=0;j<9;j++)
				{
						
					if(((boxProbability[i][j].xpectedNum).size()==1)&&(boxProbability[i][j].isFinal == false))
					{
						isPossible =true;
						Iterator<Integer> iter = (boxProbability[i][j].xpectedNum).iterator();
						Integer first = iter.next();
						solveProbability(i,j,first-1);		
						solveGraph(i,j,first-1);
						grid.setGridVal(i, j, first);
						change= true;
					}
				}
			}
		}// end of while loop
	
		return change;
	}
	
	/**
	 * @param x - X of box
	 * @param y - Y of box
	 * @param num - index value of Box Value
	 */
	private void solveProbability(int x, int y , int num)
	{
		
		boxProbability[x][y].value = num+1;
		boxProbability[x][y].isFinal = true;
		
		for(int i=0;i<9;i++)
		{
			if((boxProbability[x][i].xpectedNum).size() !=1)
			{
				if((boxProbability[x][i].xpectedNum).contains(num+1))
				{
					(boxProbability[x][i].xpectedNum).remove(num+1);
				}
			}
			
			if((boxProbability[i][y].xpectedNum).size() !=1)
			{
				if((boxProbability[i][y].xpectedNum).contains(num+1))
				{
					(boxProbability[i][y].xpectedNum).remove(num+1);
				}
			}
			
		}
		
		int sqrX = x-(x%3);
		int sqrY = y-(y%3);

		for(int i=sqrX;i<=(sqrX+2);i++)
		{
			for(int j=sqrY;j<=(sqrY+2);j++)
			{
				if((boxProbability[i][j].xpectedNum).size() !=1)
				{
					if((boxProbability[i][j].xpectedNum).contains(num+1))
					{
						(boxProbability[i][j].xpectedNum).remove(num+1);
					}
					
				}
			}
		}
		return;
	}

	
	
	/**
	 *  Intilize the Graph And Probability Object from The Sudoku Grid
	 *  @param grid - the Sudoku Grid
	 */
	
	private void init(SudokuGrid grid)
	{
		int i,j;
		for(i=0;i<9;i++)
		{
		for(j=0;j<9;j++)
		{
			if(grid.getGridVal(i, j) != 0)
			{
				solveGraph(i,j,grid.getGridVal(i, j)-1);
				solveProbability(i,j,grid.getGridVal(i, j)-1);
			}
		}
		}
	}	// end of init function

	
}
