public interface MSModelInterface {
	int getNumFlags();
	void run(int row, int col);
	int getNumRows();
	int getNumCols();
	void setNumRows(int rows);
	void setNumCols(int cols);
	void setNumBombs(int num);
	int numBombsRemaining();
	boolean isFlagged(int row, int col);
	void setFlag(int row, int col);
	boolean noEmptyCellsRemaining();
}