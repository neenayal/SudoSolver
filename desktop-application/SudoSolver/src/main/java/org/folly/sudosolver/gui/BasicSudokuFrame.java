package org.folly.sudosolver.gui;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.folly.sudosolver.base.SudokuGrid;
import org.folly.sudosolver.logic.PrimaryApproach;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BasicSudokuFrame {
  
   public BasicSudokuFrame()
  {
      JFrame guiFrame = new JFrame();
      
      //make sure the program exits when the frame closes
      guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      guiFrame.setTitle("SudoSolver - FollyLabs");
      guiFrame.setSize(650,700);
    
      //This will center the JFrame in the middle of the screen
      guiFrame.setLocationRelativeTo(null);
      
      ImageIcon icon = createImageIcon("images/sudosolver.png", 
                                 "SudoSolver - A Product of FollyLabs");
      
      //The first JPanel contains Header
      final JPanel headerPanel = new JPanel();
      JLabel logoLbl = new JLabel(icon);
      headerPanel.add(logoLbl);
      
      //Create the third JPanel. for all Button

      final JPanel optionPanel = new JPanel();
      JButton solveBtn = new JButton( "Solve");
      solveBtn.setBackground(Color.green);
      optionPanel.add(solveBtn);        

      
      //Create the second JPanel. for all Sudoku input
           
      JPanel   gridPanel = new JPanel(new GridLayout(9, 9));
    	final JTextField[][] sudokuGrid=new JTextField[9][9];
		Font font = new Font("Arial", Font.PLAIN, 30);         
       
		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 9; j++) {

				sudokuGrid[i][j] = new JTextField("");
				sudokuGrid[i][j].setFocusable(true);

				sudokuGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				if (i == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(5, 1, 1,
							1, Color.black));
				if (j == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 5, 1,
							1, Color.black));

				if (i == 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							1, Color.black));
				if (j == 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1,
							5, Color.black));

				if (i == 0 && j == 0) {
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(5, 5, 1,
							1, Color.black));
					sudokuGrid[i][j].setFocusable(true);
				}
				if (i == 0 && j == 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(5, 1, 1,
							5, Color.black));
				if (i == 8 && j == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 5, 5,
							1, Color.black));
				if (i == 8 && j == 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							5, Color.black));
				if (i == 2 && j != 0 && j != 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							1, Color.black));
				if (i == 2 && j == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 5, 5,
							1, Color.black));
				if (i == 2 && j == 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							5, Color.black));
				if (i == 5 && j != 0 && j != 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							1, Color.black));
				if (i == 5 && j == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 5, 5,
							1, Color.black));
				if (i == 5 && j == 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							5, Color.black));

				if (j == 2 && i != 0 && i != 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1,
							5, Color.black));
				if (j == 2 && i == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(5, 1, 1,
							5, Color.black));
				if ((j == 2 && i == 2) || (j == 2 && i == 5)
						|| (j == 2 && i == 8))
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							5, Color.black));

				if (j == 5 && i != 0 && i != 8)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1,
							5, Color.black));
				if (j == 5 && i == 0)
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(5, 1, 1,
							5, Color.black));
				if ((j == 5 && i == 2) || (j == 5 && i == 5)
						|| (j == 5 && i == 8))
					sudokuGrid[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 5,
							5, Color.black));
				
				//sudokuGrid[i][j].setColumns(1);
				sudokuGrid[i][j].setFont(font);
				sudokuGrid[i][j].setForeground(Color.RED);
				sudokuGrid[i][j].setBackground(Color.white);
				sudokuGrid[i][j].setOpaque(true);
				sudokuGrid[i][j].setHorizontalAlignment(JTextField.CENTER);

				gridPanel.add(sudokuGrid[i][j]);
			}
		}


		solveBtn.addActionListener(new ActionListener()
      {

          public void actionPerformed(ActionEvent event)
          {

        	 SudokuGrid m_grid=new SudokuGrid();
        	 
        	 int val;
             
             for(int i=0;i<9;i++)
             {
          	   for(int j=0;j<9;j++)
          	   {
          		   String tmpStr = sudokuGrid[i][j].getText();
          		   tmpStr.trim();
          		   
          		   if(tmpStr.trim().length() > 0 && tmpStr.trim().length() < 2 && m_grid.isValidString(tmpStr))
          		   {
          			    val= Integer.parseInt( tmpStr);
          		   		//System.out.println("-"+num+"-");
          		   		if(m_grid.isValidNumber(val))
          		   			m_grid.setGridVal(i, j, val);
          		   		sudokuGrid[i][j].setEditable(false); 
          		   }
          		   else
          		   {
					    m_grid.setGridVal(i, j, 0);          			   
          		   }
          	   }
             }
             
             if(m_grid.validate())
             {
 				//System.out.println("Sudoku Grid is Valid");
				PrimaryApproach myInstance = new PrimaryApproach();
				boolean rslt = myInstance.solve(m_grid);

				for(int i=0;i<9;i++)
				{
					for(int j=0;j<9;j++)
					{  
						val = m_grid.getGridVal(i, j);
						if(val!=0)
						{
							if(sudokuGrid[i][j].isEditable())
							{
								sudokuGrid[i][j].setForeground(Color.blue);
							}
							
							sudokuGrid[i][j].setText(String.valueOf(val));
						}
						else
						{
							sudokuGrid[i][j].setText("");
						}
					}
				}
				
				if(rslt)
				{
					JOptionPane.showMessageDialog(null,"Grid is Solve !!");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Sorry !! Not Able To Solve Sudoku .");
				}
				
             }
             else
             {
                 JOptionPane.showMessageDialog(null, "Sudoku Grid Is Not Valid.");
             }
   
             for(int i=0;i<9;i++){
            	 for(int j=0;j<9;j++){
						sudokuGrid[i][j].setEditable(true);	
            	 }
             }
             
          }
      });
		

      guiFrame.add(headerPanel, BorderLayout.NORTH);
      guiFrame.add(gridPanel,BorderLayout.CENTER);
      guiFrame.add(optionPanel,BorderLayout.SOUTH);
      guiFrame.setResizable(false);       
      //make sure the JFrame is visible
      guiFrame.setVisible(true);
  }
  
   /** Returns an ImageIcon, or null if the path was invalid. */
   protected ImageIcon createImageIcon(String path,
                                              String description) {
       java.net.URL imgURL = getClass().getResource(path);
       if (imgURL != null) {
           return new ImageIcon(imgURL, description);
       } else {
           System.err.println("Couldn't find file: " + path);
           return null;
       }
   }

  
}