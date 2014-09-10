package org.folly.sudosolver.base;

import java.util.Scanner;


import org.folly.sudosolver.gui.BasicSudokuFrame;
import org.folly.sudosolver.logic.PrimaryApproach;

public class SudoSolver {

	
	public static void main(String []arg)
	{
		System.out.println("Sudoku Solver is Started ..");
		
		if(arg.length > 0)
		{
			if(arg[0].equals("-c"))
			{
				SudokuGrid m_grid=new SudokuGrid();
				Scanner sc = new Scanner(System.in);
				int val;
					
				for(int i=0;i<9;i++)
				{
					for(int j=0;j<9;j++)
					{
					    val = sc.nextInt();
					    m_grid.setGridVal(i, j, val);
					}
				}
					
				m_grid.display();
				
				if(m_grid.validate())
				{
					System.out.println("Sudoku Grid is Valid");
					PrimaryApproach myInstance = new PrimaryApproach();
					if(myInstance.solve(m_grid))
					{
						System.out.print("Grid is Solve !!");
					}
					else
					{
						System.out.println("Sorry !! Not Able To Solve Sudoku .");
					}
					m_grid.display();
				}
				else
				{
					System.out.println("Sudoku Grid is Not Valid.");
				}
				
			}
		}
		else	// GUI Mode
		{
			System.out.println("Redirecting to Dafault GUI Mode ..");
			BasicSudokuFrame SudoSolverGUI = new BasicSudokuFrame();
		}

	}	
	
}
