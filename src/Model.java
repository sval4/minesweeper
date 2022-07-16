public class Model implements MSModelInterface{
	private int numFlags;
	private int numBombs;
	private int originalNumBombs;
	private int rows;
	private int cols;
	private int[][] grid;
	private boolean[][] revealed;
	private String[][] flags;

	@Override
	public int getNumFlags() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public void createMap() {
		// TODO Auto-generated method stub
		numFlags = numBombs;
		originalNumBombs = numBombs;
		flags = new String[getNumRows()][getNumCols()];
		grid = new int[getNumRows()][getNumCols()];
		revealed = new boolean[getNumRows()][getNumCols()];
		for(int i = 0; i < getNumRows(); i++) {
			for(int j = 0; j < getNumCols(); j++) {
				flags[i][j] = "N";
			}
		}
		for(int i = 0; i < getNumRows(); i++) {
			for(int j = 0; j < getNumCols(); j++) {
				revealed[i][j] = false;
			}
		}
		
		int randomRow;
		int randomCol;
		for(int k = 0; k < numBombsRemaining(); k++) {
			randomRow = (int) (Math.random() * getNumRows());
			randomCol = (int) (Math.random() * getNumCols());
			if(getValueAt(randomRow, randomCol) != 9) {
				setValueAt(randomRow, randomCol, 9);
			}else {
				k--;
			}
		}
		
		for(int i = 0; i < getNumRows(); i++) {
			for(int j = 0; j < getNumCols(); j++) {
				if(getValueAt(i, j) != 9) {
					setValueAt(i, j, getSurroundings(i, j));
				}
			}
		}
	}
	
	public int getSurroundings(int row, int col) {
		int count = 0;
    	for(int i = row - 1; i <= row + 1; i++) {
    		for(int j = col - 1; j <= col + 1; j++) {
    			if(i >= 0 && i < getNumRows() && j >= 0 && j < getNumCols() && (i != row || j != col)) {
        			if(getValueAt(i, j) == 9) {
        				count++;
        			}
        		}
    		}
    	}
    	return count;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public boolean[][] getRevealed() {
		return revealed;
	}
	
	public String[][] getFlags() {
		return flags;
	}
	
	public int getValueAt(int row, int col) {
		return grid[row][col];
	}
	
	public void setValueAt(int row, int col, int value) {
		grid[row][col] = value;
	}
	
	public boolean getRevealAt(int row, int col) {
		return revealed[row][col];
	}
	
	public void setRevealAt(int row, int col, boolean value) {
		revealed[row][col] = value;
	}
	
	public boolean getFlagsAt(int row, int col) {
		return flags[row][col].equals("F");
	}
	
	public void setFlagsAt(int row, int col, String value) {
		flags[row][col] = value;
	}

	@Override
	public void run(int row, int col) {
		// TODO Auto-generated method stub
		if(!revealed[row][col] && !isFlagged(row, col)) {
			setRevealAt(row, col, true);
			if(getValueAt(row, col) == 9) {
				
			}else if(getValueAt(row, col) != 0) {
				setRevealAt(row, col, true);
			}else {
				if(row - 1 >= 0 && col - 1 >= 0) {
					run(row - 1, col - 1);
				}
				if(row - 1 >= 0) {
					run(row - 1, col);
				}
				if(row - 1 >= 0 && col + 1 < cols) {
					run(row - 1, col + 1);
				}
				if(col - 1 >= 0) {
					run(row, col - 1);
				}
				if(col + 1 < cols) {
					run(row, col + 1);
				}
				if(row + 1 < rows && col - 1 >= 0) {
					run(row + 1, col - 1);
				}
				if(row + 1 < rows) {
					run(row + 1, col);
				}
				if(row + 1 < rows && col + 1 < cols) {
					run(row + 1, col + 1);
				}
				
			}
		}
	}


	@Override
	public int getNumRows() {
		// TODO Auto-generated method stub
		return rows;
	}

	@Override
	public int getNumCols() {
		// TODO Auto-generated method stub
		return cols;
	}
	
	@Override
	public void setNumRows(int s) {
		// TODO Auto-generated method stub
		rows = s;
	}
	@Override
	public void setNumCols(int s) {
		// TODO Auto-generated method stub
		cols = s;
	}
	
	@Override
	public void setNumBombs(int s) {
		// TODO Auto-generated method stub
		originalNumBombs = s;
		numBombs = s;
	}
	


	@Override
	public int numBombsRemaining() {
		// TODO Auto-generated method stub
		return numBombs;
	}


	@Override
	public boolean isFlagged(int row, int col) {
		// TODO Auto-generated method stub
		return flags[row][col].equals("F");
	}

	@Override
	public void setFlag(int row, int col) {
		// TODO Auto-generated method stub
		if(!revealed[row][col]) {
			if(flags[row][col].equals("F")) {
				flags[row][col] = "N";
				numFlags--;
				numBombs++;
			}else {
				flags[row][col] = "F";
				numFlags++;
				numBombs--;
			}
		}
	}

	@Override
	public boolean noEmptyCellsRemaining() {
		// TODO Auto-generated method stub
		int count = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(!revealed[i][j]) {
					count++;
				}
			}
		}
		return (count == originalNumBombs);
	}


}