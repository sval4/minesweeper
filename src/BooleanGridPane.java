import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BooleanGridPane extends Group {
	private double tileSize;
	ImageView[][] cells;
	private int rows;
	private int cols;
	
	
	public BooleanGridPane(int row, int col) {
		this.tileSize = 30;
		this.rows = row;
		this.cols = col;
	}
	
	public ImageView[][] getCells(){
		return cells;
	}
	
	public Image getImageBomb() {
		Image img = new Image("file:src/bomb_death.gif");
		return img;
	}
	
	public Image getImageCovered() {
		Image img = new Image("file:src/blank.gif");
		return img;
	}
	
	public Image getImageFlag() {
		Image img = new Image("file:src/bomb_flagged.gif");
		return img;
	}
	
	public Image getImage0() {
		Image img = new Image("file:src/num_0.gif");
		return img;
	}
	
	public Image getImage1() {
		Image img = new Image("file:src/num_1.gif");
		return img;
	}
	
	public Image getImage2() {
		Image img = new Image("file:src/num_2.gif");
		return img;
	}
	
	public Image getImage3() {
		Image img = new Image("file:src/num_3.gif");
		return img;
	}
	
	public Image getImage4() {
		Image img = new Image("file:src/num_4.gif");
		return img;
	}
	
	public Image getImage5() {
		Image img = new Image("file:src/num_5.gif");
		return img;
	}
	
	public Image getImage6() {
		Image img = new Image("file:src/num_6.gif");
		return img;
	}
	
	public Image getImage7() {
		Image img = new Image("file:src/num_7.gif");
		return img;
	}
	
	public Image getImage8() {
		Image img = new Image("file:src/num_8.gif");
		return img;
	}
	
	
	
	public double getTileSize() {
		return this.tileSize;
	}
	
	public void setModel() {
		cells = new ImageView[rows][cols];
		 for(int i = 0; i < rows; i++) {
			 for(int j = 0; j < cols; j++) {
				 ImageView img = new ImageView(getImageCovered());
				 cells[i][j] = img;
			 }
		 }
	}
	
	
	public ImageView cellAtGridCoords(int row, int col) {
		return cells[row][col];
	}
	
	public double xPosForCol(int col) {
		return col * tileSize;
	}
	
	public double yPosForRow(int row) {
		return row * tileSize;
	}
	
	public int colForXPos(double x) {
		return (int)(x / tileSize);
	}
	
	public int rowForYPos(double y) {
		return (int)(y / tileSize);
	}


	
}