/* Sharat Val
 * 4/7/19
 * Period 2
 * 
 * Throughout making the GUI part of minesweeper I only had issues in two 
 * places, displaying the cells and making the cells respond. While following 
 * the slides I did not understand what a root folder was so I put the 
 * minesweeper images folder in various places hoping it would work, but 
 * it never did since I did not remove each individual image from the folder.
 * I fixed this problem by creating files with the address that would only 
 * work on my computer and then pass in the Url into an image and the image
 * into the image view. This worked, but only on my computer. When I talked
 * with you one class period you showed me what the root folder was and 
 * how you had to put each individual image into the source folder. After 
 * doing this the image displaying portion of my code worked perfectly, as
 * it would now work on any computer. For making the actual cells listen to
 * commands, I had no idea where to start. I looked back at the Life GUI code
 * and saw that listeners were used I tried to make the entire table a 
 * listener but it did not work. I looked at the slides and the slides said
 * you could also make each individual cell a listener which is what I did
 * and to my surprise the code worked. The code in the model class is literally
 * copy pasted from the console version with maybe a few changes. Any other
 * new aspects of the GUI version was not challenging for me, this includes
 * the layout the buttons, the time displayed, the mines remaining. Overall
 * I enjoyed this lab, but there were a few times where I got frustrated. I
 * hope we do another lab like this before the year is over.
 * 
 * 
 */
import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
public class Controller extends Application{
	MenuBar bar;
	Menu game;
	Menu option;
	Menu help;
	MenuItem beginner;
	MenuItem exit;
	MenuItem setNumMines;
	MenuItem howToPlay;
	MenuItem about;
	BooleanGridPane view;
	Model model;
	Label remain;
	Label time;
	Image image;
	AnimationHandler timer;
	long delay = 1000000000;
	int num;
	boolean max = false;
	long previousTime;
	public static boolean gameOver = false;
	Alert alert;
	Group root;
	Scene scene;
	BorderPane border;
	boolean lose = false;
	public static void main(String[] args) {
		launch(args);
	}
	
	public Controller(){
		previousTime = 0;
		num = 0;
		model = new Model();
		model.setNumRows(8);
		model.setNumCols(8);
		model.setNumBombs(10);
		model.createMap();
		view  = new BooleanGridPane(model.getNumRows(), model.getNumCols());
		view.setModel();
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		border = new BorderPane();
		root = new Group();
		scene = new Scene(border, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Minesweeper");
		
		bar = new MenuBar();
		game = new Menu("Game");
		option = new Menu("Options");
		help = new Menu("Help");
		beginner = new MenuItem("New beginner game");
		exit = new MenuItem("Exit");
		setNumMines = new MenuItem("Set Number of Mines");
		howToPlay = new MenuItem("How To Play");
		about = new MenuItem("About");
		
		game.getItems().addAll(beginner, exit);
		option.getItems().addAll(setNumMines);
		help.getItems().addAll(howToPlay, about);
		bar.getMenus().addAll(game, option, help); 
		border.setTop(bar);
		
		beginner.setOnAction(new MenuItemHandler(stage));
		exit.setOnAction(new MenuItemHandler(stage));
		setNumMines.setOnAction(new MenuItemHandler(stage));
		about.setOnAction(new MenuItemHandler(stage));
		howToPlay.setOnAction(new MenuItemHandler(stage));

		time = new Label("Time Elapsed:\n" + previousTime);
		border.setRight(time);
		
		remain = new Label("Mines Remaining:\n" + model.numBombsRemaining());
		border.setLeft(remain);
		
		timer = new AnimationHandler(); 
		timer.start();
		
		for(int i = 0; i < model.getNumRows(); i++) {
			for(int j = 0; j < model.getNumCols(); j++) {
				view.getCells()[i][j].setFitWidth(view.getTileSize());
				view.getCells()[i][j].setFitHeight(view.getTileSize());
				view.getCells()[i][j].setX((scene.getWidth() / 6) + (j * view.getTileSize()));
				view.getCells()[i][j].setY((i * view.getTileSize()) + (scene.getHeight() / 5));
				view.getCells()[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new GridHandler());
				root.getChildren().add(view.getCells()[i][j]);
			}
		}
		border.setCenter(root);

		
		
		stage.show();
	}
	
	private class MenuItemHandler implements EventHandler<ActionEvent>{
		Stage stage;
		public MenuItemHandler(Stage s) {
			stage = s;
		} 
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource() == beginner) {
				timer.start();
				gameOver = false;
				lose = false;
				previousTime = 0;
				num = 0;
				model = new Model();
				model.setNumRows(8);
				model.setNumCols(8);
				model.setNumBombs(10);
				model.createMap();
				view  = new BooleanGridPane(model.getNumRows(), model.getNumCols());
				view.setModel();
				remain = new Label("Mines Remaining:\n" + model.numBombsRemaining());
				border.setLeft(remain);
				for(int i = 0; i < model.getNumRows(); i++) {
					for(int j = 0; j < model.getNumCols(); j++) {
						view.getCells()[i][j].setFitWidth(view.getTileSize());
						view.getCells()[i][j].setFitHeight(view.getTileSize());
						view.getCells()[i][j].setX((scene.getWidth() / 6) + (j * view.getTileSize()));
						view.getCells()[i][j].setY((i * view.getTileSize()) + (scene.getHeight() / 5));
						view.getCells()[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new GridHandler());
						root.getChildren().add(view.getCells()[i][j]);
					}
				}
				border.setCenter(root);
			}else if(arg0.getSource() == exit) {
				stage.close();
			}else if(arg0.getSource() == howToPlay) {
				WebView w = new WebView();
				WebEngine e = w.getEngine();
				File f = new File("src/How_To_Play.html");
				String path = f.getAbsolutePath();
				e.load("file:///" + path);
				Stage s = new Stage();
				Scene scene = new Scene(w, 500, 500);
				s.setTitle("How To Play");
				s.setScene(scene);
				s.show();
			}else if(arg0.getSource() == setNumMines) {
				TextInputDialog input = new TextInputDialog();
				input.setHeaderText("How many mines would you like?");
				input.showAndWait();
				String answer = input.getEditor().getText();
				if(answer.length() != 0) {
					try {
						if(Integer.parseInt(answer) < 0) {
							Alert a = new Alert(AlertType.ERROR, "you can't have less than 1 mine", ButtonType.OK);
							a.showAndWait();
						}else if(Integer.parseInt(answer) > (model.getNumCols() * model.getNumRows())) {
							Alert a = new Alert(AlertType.ERROR, "you can't have more mines than cells or same number of mines and cells", ButtonType.OK);
							a.showAndWait();
						}else{
							if(Integer.parseInt(answer) == model.getNumCols() * model.getNumRows()) {
								max = true;
							}else {
								max = false;
							}
							timer.start();
							gameOver = false;
							lose = false;
							previousTime = 0;
							num = 0;
							model = new Model();
							model.setNumRows(8);
							model.setNumCols(8);
							model.setNumBombs(Integer.parseInt(answer));
							model.createMap();
							view  = new BooleanGridPane(model.getNumRows(), model.getNumCols());
							view.setModel();
							remain = new Label("Mines Remaining:\n" + model.numBombsRemaining());
							border.setLeft(remain);
							for(int i = 0; i < model.getNumRows(); i++) {
								for(int j = 0; j < model.getNumCols(); j++) {
									view.getCells()[i][j].setFitWidth(view.getTileSize());
									view.getCells()[i][j].setFitHeight(view.getTileSize());
									view.getCells()[i][j].setX((scene.getWidth() / 6) + (j * view.getTileSize()));
									view.getCells()[i][j].setY((i * view.getTileSize()) + (scene.getHeight() / 5));
									view.getCells()[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new GridHandler());
									root.getChildren().add(view.getCells()[i][j]);
								}
							}
//							for(int i = 0; i < model.getNumRows(); i++) {
//								for(int j= 0; j < model.getNumCols(); j++) {
//									model.setRevealAt(i, j, true);
//								}
//							}
//							updateView();
							border.setCenter(root);
						}
					}catch(NumberFormatException e) {
						Alert a = new Alert(AlertType.ERROR, "please enter a valid number", ButtonType.OK);
						a.showAndWait();
					}
				}
			}else if(arg0.getSource() == about) {
				Stage s = new Stage();
				VBox v = new VBox();
				Scene scene = new Scene(v, 500, 500);
				s.setScene(scene);
				s.setTitle("About");
				Label l = new Label("Minesweeper\n\n4/7/2019\n\nSharat Val");
				v.getChildren().add(l);
				s.show();
			}
		}
		
	}
	private class AnimationHandler extends AnimationTimer{
		@Override
		public void handle(long now) {
				if((now - previousTime) >= delay) {
					time.setText("Time Elapsed:\n" + num); 
					num++;
					previousTime = now;
			    }
				
		    
		}
	}
	
	private class GridHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if((!model.noEmptyCellsRemaining() && !gameOver) || max) {
				int row;
				int col;
				for(int i = 0; i < model.getNumRows(); i++) {
					for(int j = 0; j < model.getNumCols(); j++) {
						if(arg0.getSource() == view.getCells()[i][j]) {
							if(arg0.getButton() == MouseButton.PRIMARY) {
								row = view.rowForYPos(view.getCells()[i][j].getY());
								col = view.colForXPos(view.getCells()[i][j].getX());
								model.run(row - 4, col - 4);
								updateView();
								if(!lose && model.noEmptyCellsRemaining()) {
										gameOver = true;
										timer.stop();
										alert = new Alert(AlertType.INFORMATION, "You won", ButtonType.OK);
										alert.showAndWait();
								}
							}else if(arg0.getButton() == MouseButton.SECONDARY) {
								row = view.rowForYPos(view.getCells()[i][j].getY());
								col = view.colForXPos(view.getCells()[i][j].getX());
								model.setFlag(row - 4, col - 4);
								updateView();
								remain.setText("Mines Remaining:\n" + model.numBombsRemaining());
							}
						}
					}
				}
			}
		}
	}
	
	
	public void updateView() {
		if(!gameOver) {
			for(int i = 0; i < model.getNumRows(); i++) {
				for(int j = 0; j < model.getNumCols(); j++) {
					if(!model.getRevealAt(i, j)) {
						if(model.getFlagsAt(i, j)) {
							//set image to flag
							view.getCells()[i][j].setImage(view.getImageFlag());
						}else {
							//set to blank image
							view.getCells()[i][j].setImage(view.getImageCovered());
						}
					}else if(model.getRevealAt(i, j)) {
						if(model.getGrid()[i][j] == 0) {
							//blank image
							view.getCells()[i][j].setImage(view.getImage0());
						}else if(model.getGrid()[i][j] == 1) {
							//1 image
							view.getCells()[i][j].setImage(view.getImage1());
						}else if(model.getGrid()[i][j] == 2) {
							// 2 image
							view.getCells()[i][j].setImage(view.getImage2());
						}else if(model.getGrid()[i][j] == 3) {
							//3 image
							view.getCells()[i][j].setImage(view.getImage3());
						}else if(model.getGrid()[i][j] == 4) {
							//4 image
							view.getCells()[i][j].setImage(view.getImage4());
						}else if(model.getGrid()[i][j] == 5) {
							//5 image
							view.getCells()[i][j].setImage(view.getImage5());
						}else if(model.getGrid()[i][j] == 6) {
							//6 image
							view.getCells()[i][j].setImage(view.getImage6());
						}else if(model.getGrid()[i][j] == 7) {
							//7 image
							view.getCells()[i][j].setImage(view.getImage7());
						}else if(model.getGrid()[i][j] == 8) {
							//8 image
							view.getCells()[i][j].setImage(view.getImage8());
						}else if(model.getGrid()[i][j] == 9) {
							//bomb image
							view.getCells()[i][j].setImage(view.getImageBomb());
							gameOver = true;
							lose = true;
							timer.stop();
							alert = new Alert(AlertType.INFORMATION, "You lost", ButtonType.OK);
							alert.showAndWait();
						}
					}
				}
			}
		}
	}

}