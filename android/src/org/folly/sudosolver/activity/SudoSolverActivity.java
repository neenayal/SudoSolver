package org.folly.sudosolver.activity;

import org.folly.sudosolver.base.SudokuGrid;
import org.folly.sudosolver.logic.PrimaryApproach;

import android.app.ActionBar;
import android.app.Activity;
import android.text.Editable;
import android.view.View.OnFocusChangeListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SudoSolverActivity extends Activity {

	// Application menu
	private EditText sudoGrid[][] = new EditText[9][9];
	private SudokuGrid m_grid = new SudokuGrid();
	private Button numBtn[] = new Button[10];
	private int currentX;
	private int currentY;

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.refresh_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		// action with ID action_refresh was selected
		case R.id.action_refresh:
			doRefresh();
			break;
		default:
			break;
		}
		return true;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int counterX, counterY;
		setContentView(R.layout.main);
		this.currentX = 0;
		this.currentY = 0;
		doInit();
		
		// Action Bar
		ActionBar ab = getActionBar();
		ColorDrawable colorDrawable = new ColorDrawable(Color
				.parseColor("#e6e6e6"));
		ab.setBackgroundDrawable(colorDrawable);
		
		for (counterX = 0; counterX <= 8; counterX++) {
			for (counterY = 0; counterY <= 8; counterY++) {
				sudoGrid[counterX][counterY].setOnFocusChangeListener(myEditTextFocus);
			}
		}
	}
	
	private OnFocusChangeListener myEditTextFocus = new OnFocusChangeListener() {
		public void onFocusChange(View view, boolean gainFocus) {
			int counterX, counterY;
			// onFocus
			if (gainFocus) {
				// set the currentX and currentY values
				for (counterX = 0; counterX <= 8; counterX++) {
					for (counterY = 0; counterY <= 8; counterY++) {
						if (view == sudoGrid[counterX][counterY]) {
							currentX = counterX;
							currentY = counterY;
						}
					}
				}
			} else {
				// clear the text
			}
		};
	};

	public void numberPressed(View view) {
		int counter;
		for (counter = 1; counter <= 9; counter++) {
			if (view == numBtn[counter]) {
				doChangeNum(String.valueOf(counter));
			}
		}
	}

	private void doChangeNum(String text) {
		sudoGrid[currentX][currentY].setText(text);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// The activity is about to become visible.
	}

	@Override
	protected void onResume() {
		super.onResume();
		// The activity has become visible (it is now "resumed").
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() {
		super.onStop();
		// The activity is no longer visible (it is now "stopped")
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The activity is about to be destroyed.
	}

	/*
	 * clear sudoku grid
	 */
	private void doRefresh() {
		int counterX, counterY;
		Toast.makeText(this, "Sudoku Grid Refresh !!", Toast.LENGTH_SHORT)
				.show();

		for (counterX = 0; counterX <= 8; counterX++) {
			for (counterY = 0; counterY <= 8; counterY++) {
				sudoGrid[counterX][counterY].setText("");
			}
		}
	}

	public void doSolve(View view) {
		
		doLoad();
		PrimaryApproach myInstance = new PrimaryApproach();		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String sudoResult =null;
		
		if(m_grid.validate())
		{
			
			if(myInstance.solve(m_grid))
			{
				fillSolveGrid();
				sudoResult = "Grid is Solve !!";
			}
			else
			{
				fillSolveGrid();
				sudoResult = "Input is not enough to solve Sudoku .";
			}
		}
		else
		{
			sudoResult = "Sudoku Grid is Not Valid.";
		}
		
		builder.setMessage(sudoResult).setTitle("SudoSolver Result");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();

		if (dialog.isShowing() == false) {
			Toast.makeText(this, "Something went wrong !!", Toast.LENGTH_SHORT)
			.show();
		}
	}

	private void doLoad() {
		int counterX, counterY;
		int num;
		Editable tmp;
		String str;

		for (counterX = 0; counterX <= 8; counterX++) {
			for (counterY = 0; counterY <= 8; counterY++) {
				tmp = sudoGrid[counterX][counterY].getText();
				str=tmp.toString();

				if(str.equalsIgnoreCase(""))
				{
					num=0;
				}
				else
				{
					num = Integer.parseInt(tmp.toString());
				}
				m_grid.setGridVal(counterX, counterY, num);
			}
		}

	}

	private void fillSolveGrid()
	{
		int counterX, counterY;
		for (counterX = 0; counterX <= 8; counterX++) {
			for (counterY = 0; counterY <= 8; counterY++) {
				if(m_grid.getGridVal(counterX, counterY)!=0)
				{
					sudoGrid[counterX][counterY].setText(Integer.toString(m_grid.getGridVal(counterX, counterY)));
				}
			}
		}
	}
	
	private void doInit() {
		int counterX, counterY;
		for (counterX = 0; counterX <= 8; counterX++) {
			for (counterY = 0; counterY <= 8; counterY++) {
				m_grid.setGridVal(counterX, counterY, 0);
			}
		}
		
		sudoGrid[0][0] = (EditText) findViewById(R.id.editTextR1C1);
		sudoGrid[0][1] = (EditText) findViewById(R.id.editTextR1C2);
		sudoGrid[0][2] = (EditText) findViewById(R.id.editTextR1C3);
		sudoGrid[0][3] = (EditText) findViewById(R.id.editTextR1C4);
		sudoGrid[0][4] = (EditText) findViewById(R.id.editTextR1C5);
		sudoGrid[0][5] = (EditText) findViewById(R.id.editTextR1C6);
		sudoGrid[0][6] = (EditText) findViewById(R.id.editTextR1C7);
		sudoGrid[0][7] = (EditText) findViewById(R.id.editTextR1C8);
		sudoGrid[0][8] = (EditText) findViewById(R.id.editTextR1C9);

		sudoGrid[1][0] = (EditText) findViewById(R.id.editTextR2C1);
		sudoGrid[1][1] = (EditText) findViewById(R.id.editTextR2C2);
		sudoGrid[1][2] = (EditText) findViewById(R.id.editTextR2C3);
		sudoGrid[1][3] = (EditText) findViewById(R.id.editTextR2C4);
		sudoGrid[1][4] = (EditText) findViewById(R.id.editTextR2C5);
		sudoGrid[1][5] = (EditText) findViewById(R.id.editTextR2C6);
		sudoGrid[1][6] = (EditText) findViewById(R.id.editTextR2C7);
		sudoGrid[1][7] = (EditText) findViewById(R.id.editTextR2C8);
		sudoGrid[1][8] = (EditText) findViewById(R.id.editTextR2C9);

		sudoGrid[2][0] = (EditText) findViewById(R.id.editTextR3C1);
		sudoGrid[2][1] = (EditText) findViewById(R.id.editTextR3C2);
		sudoGrid[2][2] = (EditText) findViewById(R.id.editTextR3C3);
		sudoGrid[2][3] = (EditText) findViewById(R.id.editTextR3C4);
		sudoGrid[2][4] = (EditText) findViewById(R.id.editTextR3C5);
		sudoGrid[2][5] = (EditText) findViewById(R.id.editTextR3C6);
		sudoGrid[2][6] = (EditText) findViewById(R.id.editTextR3C7);
		sudoGrid[2][7] = (EditText) findViewById(R.id.editTextR3C8);
		sudoGrid[2][8] = (EditText) findViewById(R.id.editTextR3C9);

		sudoGrid[3][0] = (EditText) findViewById(R.id.editTextR4C1);
		sudoGrid[3][1] = (EditText) findViewById(R.id.editTextR4C2);
		sudoGrid[3][2] = (EditText) findViewById(R.id.editTextR4C3);
		sudoGrid[3][3] = (EditText) findViewById(R.id.editTextR4C4);
		sudoGrid[3][4] = (EditText) findViewById(R.id.editTextR4C5);
		sudoGrid[3][5] = (EditText) findViewById(R.id.editTextR4C6);
		sudoGrid[3][6] = (EditText) findViewById(R.id.editTextR4C7);
		sudoGrid[3][7] = (EditText) findViewById(R.id.editTextR4C8);
		sudoGrid[3][8] = (EditText) findViewById(R.id.editTextR4C9);

		sudoGrid[4][0] = (EditText) findViewById(R.id.editTextR5C1);
		sudoGrid[4][1] = (EditText) findViewById(R.id.editTextR5C2);
		sudoGrid[4][2] = (EditText) findViewById(R.id.editTextR5C3);
		sudoGrid[4][3] = (EditText) findViewById(R.id.editTextR5C4);
		sudoGrid[4][4] = (EditText) findViewById(R.id.editTextR5C5);
		sudoGrid[4][5] = (EditText) findViewById(R.id.editTextR5C6);
		sudoGrid[4][6] = (EditText) findViewById(R.id.editTextR5C7);
		sudoGrid[4][7] = (EditText) findViewById(R.id.editTextR5C8);
		sudoGrid[4][8] = (EditText) findViewById(R.id.editTextR5C9);

		sudoGrid[5][0] = (EditText) findViewById(R.id.editTextR6C1);
		sudoGrid[5][1] = (EditText) findViewById(R.id.editTextR6C2);
		sudoGrid[5][2] = (EditText) findViewById(R.id.editTextR6C3);
		sudoGrid[5][3] = (EditText) findViewById(R.id.editTextR6C4);
		sudoGrid[5][4] = (EditText) findViewById(R.id.editTextR6C5);
		sudoGrid[5][5] = (EditText) findViewById(R.id.editTextR6C6);
		sudoGrid[5][6] = (EditText) findViewById(R.id.editTextR6C7);
		sudoGrid[5][7] = (EditText) findViewById(R.id.editTextR6C8);
		sudoGrid[5][8] = (EditText) findViewById(R.id.editTextR6C9);

		sudoGrid[6][0] = (EditText) findViewById(R.id.editTextR7C1);
		sudoGrid[6][1] = (EditText) findViewById(R.id.editTextR7C2);
		sudoGrid[6][2] = (EditText) findViewById(R.id.editTextR7C3);
		sudoGrid[6][3] = (EditText) findViewById(R.id.editTextR7C4);
		sudoGrid[6][4] = (EditText) findViewById(R.id.editTextR7C5);
		sudoGrid[6][5] = (EditText) findViewById(R.id.editTextR7C6);
		sudoGrid[6][6] = (EditText) findViewById(R.id.editTextR7C7);
		sudoGrid[6][7] = (EditText) findViewById(R.id.editTextR7C8);
		sudoGrid[6][8] = (EditText) findViewById(R.id.editTextR7C9);

		sudoGrid[7][0] = (EditText) findViewById(R.id.editTextR8C1);
		sudoGrid[7][1] = (EditText) findViewById(R.id.editTextR8C2);
		sudoGrid[7][2] = (EditText) findViewById(R.id.editTextR8C3);
		sudoGrid[7][3] = (EditText) findViewById(R.id.editTextR8C4);
		sudoGrid[7][4] = (EditText) findViewById(R.id.editTextR8C5);
		sudoGrid[7][5] = (EditText) findViewById(R.id.editTextR8C6);
		sudoGrid[7][6] = (EditText) findViewById(R.id.editTextR8C7);
		sudoGrid[7][7] = (EditText) findViewById(R.id.editTextR8C8);
		sudoGrid[7][8] = (EditText) findViewById(R.id.editTextR8C9);

		sudoGrid[8][0] = (EditText) findViewById(R.id.editTextR9C1);
		sudoGrid[8][1] = (EditText) findViewById(R.id.editTextR9C2);
		sudoGrid[8][2] = (EditText) findViewById(R.id.editTextR9C3);
		sudoGrid[8][3] = (EditText) findViewById(R.id.editTextR9C4);
		sudoGrid[8][4] = (EditText) findViewById(R.id.editTextR9C5);
		sudoGrid[8][5] = (EditText) findViewById(R.id.editTextR9C6);
		sudoGrid[8][6] = (EditText) findViewById(R.id.editTextR9C7);
		sudoGrid[8][7] = (EditText) findViewById(R.id.editTextR9C8);
		sudoGrid[8][8] = (EditText) findViewById(R.id.editTextR9C9);

		numBtn[1] = (Button) findViewById(R.id.oneBtn);
		numBtn[2] = (Button) findViewById(R.id.twoBtn);
		numBtn[3] = (Button) findViewById(R.id.threeBtn);
		numBtn[4] = (Button) findViewById(R.id.fourBtn);
		numBtn[5] = (Button) findViewById(R.id.fiveBtn);
		numBtn[6] = (Button) findViewById(R.id.sixBtn);
		numBtn[7] = (Button) findViewById(R.id.sevenBtn);
		numBtn[8] = (Button) findViewById(R.id.eightBtn);
		numBtn[9] = (Button) findViewById(R.id.nineBtn);
		
		
	}
}