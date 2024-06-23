package views;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


import engine.Game;
import exceptions.GameActionException;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NotEnoughActionsException;
import model.world.*;
import model.characters.*;
import model.characters.Character;
import model.collectibles.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

public class View extends Application {
	
	public static Scene s;
	public static Stage stage;
	public static Alert exception = new Alert(AlertType.INFORMATION);
	public static Button[] buttons = new Button[8];
	public static Rectangle[][] cells = new Rectangle[15][15];
	public static Image fighter = new Image("fighter2small.png");
	public static Image medic = new Image("medic1.png");
	public static Image explorer = new Image("Explorer1.png");
	public static Image zombie = new Image("zombie1.png");
	public static Image vaccine = new Image("vaccine2.png");
	public static Image supply = new Image("supply.jpg");
	public static MediaPlayer mediaPlayer;
	public static ArrayList<Hero> tmpHeroes = new ArrayList<Hero>();
	
	public void start(Stage primaryStage) throws Exception {
//		music();
		StackPane root = new StackPane();
		exception.setTitle("Invalid Action!");
		Image img = new Image("tloulimage.jpg");
		ImageView view = new ImageView(img);
		view.setFitHeight(1080);
		view.setFitWidth(1920);

		Button b = new Button("Start Game");
		b.setTranslateY(300);
		b.setTextFill(Color.WHITE);
		b.setStyle("-fx-font: 32 serif;");
		b.setBackground(null);
		
		Button b2 = new Button("");
		b2.setPrefSize(1920, 1080);
		b2.setBackground(null);
		
		
		root.getChildren().addAll(view,b,b2);
		s = new Scene(root,1000,600);
		primaryStage.setScene(s);
		stage = primaryStage;
		stage.show();
		
		b2.setOnMouseClicked(new EventHandler<Event>(){  //second window
			public void handle(Event event) {
				Scene2();
			}
		});
	}
//	public void music() {
//		String s = "tomorrow.mp3";
//		Media h = new Media(Paths.get(s).toUri().toString());
//		mediaPlayer = new MediaPlayer(h);
//		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//		mediaPlayer.play();
//		
//	}
	public static void Gameplay(ArrayList<Hero> tmpHeroes) {
		StackPane root = new StackPane();
		
		GridPane mapGrid = new GridPane();
		
		Image img = new Image("tloulimage.jpg");
		ImageView view = new ImageView(img);
		view.setFitHeight(1080);
		view.setFitWidth(1920);
		
		Button rules = new Button("How to perform Actions: \n" + "1)Choose hero from above \n" + "2)Click on target on map \n" + "3)Choose the desired action \n");
		rules.setTranslateY(-300);
		rules.setTranslateX(575);
		rules.setTextFill(Color.WHITE);
		rules.setStyle("-fx-font: 32 serif;");
		rules.setBackground(null);
		
		for (int i = 0; i < Game.map.length; i++)
			for (int j = 0; j < Game.map[i].length; j++) {
				Rectangle cell = new Rectangle();
				cell.setHeight(36);
				cell.setWidth(36);
				if(!Game.map[i][j].isVisible()) {
					cell.setFill(Color.DARKCYAN);
				}
			    else if(Game.map[i][j] instanceof CharacterCell) {
			    	Character c = ((CharacterCell)Game.map[i][j]).getCharacter();
					if(c == null) {
						cell.setFill(Color.LIGHTBLUE);
					}
					else if(c instanceof Fighter) {
						cell.setFill(new ImagePattern(fighter));
						//hero cell
					}
					else if(c instanceof Explorer) {
						cell.setFill(new ImagePattern(explorer));
					}
					else if(c instanceof Medic) {
						cell.setFill(new ImagePattern(medic));
					}
					else if(c instanceof Zombie) {
						cell.setFill(new ImagePattern(zombie));
						//zombie cell
					}
				}
				
				else if(Game.map[i][j] instanceof CollectibleCell) {
					Collectible c = ((CollectibleCell)Game.map[i][j]).getCollectible();
					if(c instanceof Vaccine) {
						cell.setFill(new ImagePattern(vaccine));
						//vaccine cell
					}
					else if(c instanceof Supply) {
						cell.setFill(new ImagePattern(supply));
						//supply cell
					}
				}
				else if(Game.map[i][j] instanceof TrapCell) {
					cell.setFill(Color.LIGHTBLUE);
				}
				mapGrid.add(cell, j, i);
				cells[i][j] = cell;
			}
		
		mapGrid.setRotate(270);
		mapGrid.setGridLinesVisible(true);
		mapGrid.setMaxSize(540, 540);
		
		Image img1 = new Image("GameplayBackground.jpg");
		ImageView View = new ImageView(img1);
		View.setFitHeight(900);
		View.setFitWidth(1800);
		
		HBox heroes = new HBox();         //initialising the heroes horizontal box
		
		Image imgj = new Image("JoelMiller.jpg");
		ImageView viewj = new ImageView(imgj);
		viewj.setFitHeight(180);
		viewj.setFitWidth(180);
		Button b0 = new Button(tmpHeroes.get(0).toString2());
		b0.setPrefSize(150, 140);
		b0.setTextFill(Color.BLACK);
		b0.setStyle("-fx-font: 12 serif;");
		
		Button b1 = new Button(tmpHeroes.get(1).toString2());
		b1.setPrefSize(150, 140);
		b1.setTextFill(Color.BLACK);
		b1.setStyle("-fx-font: 12 serif;");
		
		Button b2 = new Button(tmpHeroes.get(2).toString2());
		b2.setPrefSize(150, 140);
		b2.setTextFill(Color.BLACK);
		b2.setStyle("-fx-font: 12 serif;");
		
		Button b3 = new Button(tmpHeroes.get(3).toString2());
		b3.setPrefSize(150, 140);
		b3.setTextFill(Color.BLACK);
		b3.setStyle("-fx-font: 12 serif;");
		
		Button b4 = new Button(tmpHeroes.get(4).toString2());
		b4.setPrefSize(150, 140);
		b4.setTextFill(Color.BLACK);
		b4.setStyle("-fx-font: 12 serif;");
		
		Button b5 = new Button(tmpHeroes.get(5).toString2());
		b5.setPrefSize(150, 140);
		b5.setTextFill(Color.BLACK);
		b5.setStyle("-fx-font: 12 serif;");
		
		Button b6 = new Button(tmpHeroes.get(6).toString2());
		b6.setPrefSize(150, 140);
		b6.setTextFill(Color.BLACK);
		b6.setStyle("-fx-font: 12 serif;");
		
		Button b7 = new Button(tmpHeroes.get(7).toString2());
		b7.setPrefSize(150, 140);
		b7.setTextFill(Color.BLACK);
		b7.setStyle("-fx-font: 12 serif;");
		
		
		buttons[0] = b0;
		buttons[1] = b1;
		buttons[2] = b2;
		buttons[3] = b3;
		buttons[4] = b4;
		buttons[5] = b5;
		buttons[6] = b6;
		buttons[7] = b7;
		
		for(int i=0; i<Game.heroes.size(); i++) {
			int index = tmpHeroes.indexOf(Game.heroes.get(i));
			heroes.getChildren().add(buttons[index]);
		}
		
		VBox actions = new VBox();           //initialising the actions vertical box
		actions.setSpacing(10);
		Button attack = new Button("Attack");
		attack.setPrefSize(150, 100);
		attack.setTextFill(Color.BLACK);	
		attack.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
		
		
		Button cure = new Button("Cure");
		cure.setPrefSize(150, 100);
		cure.setTextFill(Color.BLACK);
		cure.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
		
		Button special = new Button("Special Action");
		special.setPrefSize(150, 100);
		special.setTextFill(Color.BLACK);	
		special.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
		
		Button endturn = new Button("End Turn");
		endturn.setPrefSize(150, 100);
		endturn.setTextFill(Color.BLACK);	
		endturn.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
		
		actions.getChildren().addAll(attack,cure,special,endturn);
		
		root.getChildren().addAll(View,rules,heroes,actions,mapGrid);

		
		actions.setTranslateY(150);
		
		heroes.setAlignment(Pos.TOP_CENTER);
		mapGrid.setAlignment(Pos.CENTER);
		mapGrid.setTranslateY(50);
		
		s.setRoot(root);
		stage.setScene(s);
		
		stage.show();
		
		if(Game.checkWin()) {
			gameWinScreen();
			return;
		}
		else if(Game.checkGameOver()) {
			gameOverScreen();
			return;
		}
		
		endturn.setOnMouseClicked(new EventHandler<Event>() { //handling the endturn
			public void handle(Event event) {
				try {
				Game.endTurn();
				Gameplay(tmpHeroes);
				}
				catch(GameActionException e) {	
					exception.setContentText(e.getMessage());
					exception.showAndWait();
				}
			}
		});
		
		for(int i=0; i<buttons.length; i++) {
		if(buttons[i] == null)
			continue;
		int ind = i;
		
		buttons[i].setOnMouseClicked(new EventHandler<Event>() {  //handling the hero's actions and setting his target
			public void handle(Event event) {
				
				for(int i=0; i<15; i++) {  //setting target of the hero by clicking on the map
					for(int j=0; j<15; j++) {
						int ii = i , jj = j;
						cells[i][j].setOnMouseClicked(new EventHandler<Event>() {
							public void handle(Event event) {
								if(Game.map[ii][jj] instanceof CharacterCell) {
									Character c = ((CharacterCell)Game.map[ii][jj]).getCharacter();
									tmpHeroes.get(ind).setTarget(c);
								}
							}
						});
						
					}
				}
				
				attack.setOnMouseClicked(new EventHandler<Event>() { //handling attack
					public void handle(Event event) {
						try {
						tmpHeroes.get(ind).attack();
						Gameplay(tmpHeroes);
						}
						catch(GameActionException e) {
							exception.setContentText(e.getMessage());
							exception.showAndWait();
						}
					}
					
				});
				
				cure.setOnMouseClicked(new EventHandler<Event>() { //handling cure
					public void handle(Event event) {
						try {
						tmpHeroes.get(ind).cure();
						Gameplay(tmpHeroes);
						}
						catch(GameActionException e) {
							exception.setContentText(e.getMessage());
							exception.showAndWait();
						}
					}
				});
				
				special.setOnMouseClicked(new EventHandler<Event>() { //handling special action
					public void handle(Event event) {
						try {
						tmpHeroes.get(ind).useSpecial();
						Gameplay(tmpHeroes);
						}
						catch(GameActionException e) {
							exception.setContentText(e.getMessage());
							exception.showAndWait();
						}
					}
				});
				
				s.setOnKeyPressed(new EventHandler<KeyEvent>() { //handling movement
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
							try {
								tmpHeroes.get(ind).move(Direction.RIGHT);
								
							} catch (MovementException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							} catch (NotEnoughActionsException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							}
							Gameplay(tmpHeroes);
						}
						
						if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
							try {
								tmpHeroes.get(ind).move(Direction.LEFT);
							} catch (MovementException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							} catch (NotEnoughActionsException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							}
							Gameplay(tmpHeroes);
						}
						
						if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
							try {
								tmpHeroes.get(ind).move(Direction.DOWN);
							} catch (MovementException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							} catch (NotEnoughActionsException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							}
							Gameplay(tmpHeroes);
						}
						if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
							try {
								tmpHeroes.get(ind).move(Direction.UP);
							} catch (MovementException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
							} catch (NotEnoughActionsException e) {
								exception.setContentText(e.getMessage());
								exception.showAndWait();
								
							}
							Gameplay(tmpHeroes);
						}
					}
				});

			}
		});
	}	
		}
	
	public static void ChooseHeroScreen() {
		StackPane root3 =new StackPane();
		Image img = new Image("HeroScreen.png");
		ImageView view = new ImageView(img);
		view.setFitHeight(1080);
		view.setFitWidth(1920);
		Label l =  new Label("Choose Your Hero!");
		l.setTextFill(Color.ANTIQUEWHITE);
		l.setStyle("-fx-font: 32 serif;");
		try {
			Game.loadHeroes("Heroes.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Button b1 = new Button(Game.availableHeroes.get(0).toString());
		Button b2 = new Button(Game.availableHeroes.get(1).toString());
		Button b3 = new Button(Game.availableHeroes.get(2).toString());
		Button b4 = new Button(Game.availableHeroes.get(3).toString());
		Button b5 = new Button(Game.availableHeroes.get(4).toString());
		Button b6 = new Button(Game.availableHeroes.get(5).toString());
		Button b7 = new Button(Game.availableHeroes.get(6).toString());
		Button b8 = new Button(Game.availableHeroes.get(7).toString());
		
		root3.getChildren().addAll(view,b1,b2,b3,b4,b5,b6,b7,b8,l);
		b1.setTranslateX(-490);
		b2.setTranslateX(-350);
		b3.setTranslateX(-210);
		b4.setTranslateX(-70);
		b5.setTranslateX(70);
		b6.setTranslateX(210);
		b7.setTranslateX(350);
		b8.setTranslateX(490);
		l.setTranslateY(-100);
		l.setScaleX(5);
		l.setScaleY(5);
		
		BackgroundImage bi1= new BackgroundImage(new Image("JoelMiller.jpg"), null, null, null, null);
		b1.setBackground(new Background(bi1));
		
		BackgroundImage bi2= new BackgroundImage(new Image("Ellie.jpg"), null, null, null, null);
		b2.setBackground(new Background(bi2));
		
		BackgroundImage bi3= new BackgroundImage(new Image("Tess.jpg"), null, null, null, null);
		b3.setBackground(new Background(bi3));
		
		BackgroundImage bi4= new BackgroundImage(new Image("RileyAbel.jpg"), null, null, null, null);
		b4.setBackground(new Background(bi4));
		
		BackgroundImage bi5= new BackgroundImage(new Image("TommyMiller.jpg"), null, null, null, null);
		b5.setBackground(new Background(bi5));
		
		BackgroundImage bi6= new BackgroundImage(new Image("Bill.jpg"), null, null, null, null);
		b6.setBackground(new Background(bi6));
		
		BackgroundImage bi7= new BackgroundImage(new Image("david.jpg"), null, null, null, null);
		b7.setBackground(new Background(bi7));
		
		BackgroundImage bi8= new BackgroundImage(new Image("Henry.jpg"), null, null, null, null);
		b8.setBackground(new Background(bi8));
		
		s.setRoot(root3);
		stage.setScene(s);
		stage.show();
		
		
		for(int i=0; i<Game.availableHeroes.size(); i++) 
			tmpHeroes.add(Game.availableHeroes.get(i));
		
		b1.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(0));
				Gameplay(tmpHeroes);
			}
		});
		b2.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(1));
				Gameplay(tmpHeroes);
			}
				});
		b3.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(2));
				Gameplay(tmpHeroes);
			}
				});
		b4.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(3));
				Gameplay(tmpHeroes);
			}
				});
		b5.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(4));
				Gameplay(tmpHeroes);
			}
				});
		b6.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(5));
				Gameplay(tmpHeroes);
			}
				});
		b7.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(6));
				Gameplay(tmpHeroes);
			}
				});
		b8.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				Game.startGame(Game.availableHeroes.get(7));
				Gameplay(tmpHeroes);
			}
				});
	}
	
	public static void Scene2() {
		StackPane root = new StackPane();
		
		Image img = new Image("tloulimage.jpg");
		ImageView view = new ImageView(img);
		view.setFitHeight(1080);
		view.setFitWidth(1920);
		
		Button start = new Button("Single Player");
		start.setPrefSize(300, 100);
		start.setTextFill(Color.WHITE);
		start.setStyle("-fx-font: 32 serif;");
		start.setBackground(null);
		
		
		
		Button exit = new Button("Exit Game");
		exit.setPrefSize(300, 100);
		exit.setTextFill(Color.WHITE);
		exit.setStyle("-fx-font: 32 serif;");
		exit.setBackground(null);
		
		VBox box = new VBox();
		box.getChildren().addAll(start,exit);
		box.setAlignment(Pos.BASELINE_LEFT);
		
		root.getChildren().addAll(view,box);
		
		s.setRoot(root);
		stage.setScene(s);
		stage.show();
		
		start.setOnMouseClicked(new EventHandler<Event>() {   //Single Player 
			public void handle(Event event) {
				ChooseHeroScreen();					
			}
		});
		
		
	
		exit.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				stage.close();
			}
		});

	}
	
	public static void gameOverScreen() {
		gasp();
		Label b = new Label("Gameover!");
		b.setTextFill(Color.RED);
		b.setStyle("-fx-font: 56 serif;");
		b.setBackground(null);
//		b.setTranslateX(540);
		b.setTranslateY(-200);
		
		Button exit = new Button("Click anywhere to exit!");
		exit.setTextFill(Color.ANTIQUEWHITE);
		exit.setStyle("-fx-font: 40 serif;");
		exit.setBackground(null);
		exit.setTranslateY(200);
//		exit.setGraphic(vid);
		
		StackPane root = new StackPane();
		
		String v="YOU DIED (HD).mp4";
		Media vid = new Media(Paths.get(v).toUri().toString());
		mediaPlayer = new MediaPlayer(vid);
		MediaView mv=new MediaView(mediaPlayer);	
		mv.setFitHeight(1080);
		mv.setFitWidth(1920);
//		mv.setTranslateX(-960);
//		mv.setTranslateY(-540);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.setAutoPlay(true);
		
		root.getChildren().addAll(mv,b,exit);
		
		exit.setPrefSize(1920, 1080);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		exit.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				stage.close();
			}
		});
	}
	
	public static void gasp() {
//		String s = "YouneedtoleaveShockedCrowdGasp-SoundEffectforediting.mp3";
		String v="YOU DIED (HD).mp4";
//		Media h = new Media(Paths.get(s).toUri().toString());
		Media vid = new Media(Paths.get(v).toUri().toString());
		mediaPlayer = new MediaPlayer(vid);
		//mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		
	}
	public static void gameWinScreen() {
		yayy();
		Label b = new Label("You win!");
		b.setTextFill(Color.GREEN);
		b.setStyle("-fx-font: 72 serif;");
		b.setBackground(null);
//		b.setTranslateX(540);
		b.setTranslateY(-200);
		
		Button exit = new Button("Click anywhere to exit!");
		exit.setTextFill(Color.BLACK);
		exit.setStyle("-fx-font: 40 serif;");
		exit.setBackground(null);
		exit.setTranslateY(100);
		
		Image img = new Image("last-of-us.png");
		ImageView view = new ImageView(img);
		view.setFitHeight(1080);
		view.setFitWidth(1920);
		StackPane root = new StackPane();
		
		root.getChildren().addAll(view,b,exit);
		
		exit.setPrefSize(1920, 1080);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		exit.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				stage.close();
			}
		});
	}
	
	public static void yayy() {
		String s = "Children Yay!   Sound Effect.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		//mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		
	}
	public static void main(String [] args) {
		launch(args);
	}
	

}
