package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import model.Party;
import model.Card;
import model.Category;
import model.Challenge;
import model.ConnectJson;
import model.Dice;
import model.Question;
import model.User;

public class GameBoardAP extends AnchorPane {
	/* *** Declaration of class attributes *** */
	private static GameCardAP card;
    private Dice dice = new Dice();
	
    // Parameters of board
    private int board_width = 16;
    private int board_height = 5;
	private int colorpicker;
	private List<Color> color;
	private Rectangle[][] tiles;
	private static ImageView[][] imgs;
	
	// Coordonnees
	private static List<Integer> x;
	private static List<Integer> y;
	private static Map<Integer, Integer> playerX;
	private static Map<Integer, Integer> playerY;
	
	// image
	private Image image;
	
	/* Declaration for players */
	private static int playing = 0;
	private static int nbPlayers;
	private static List<User> players;
	private static List<boolean[]> anwsers;
	private static List<Integer[]> position;
	private static List<ImageView[][]> positionImg;

	// Step  
	private HBox pnlP1;
	private VBox pnlStep, pnlByPlayer;
	private Label lblStepPlayer[];
	private Rectangle stepTile[][];
	private static Text stepCheck[][];
	private static GridPane step[];
	private static List<User> ending;
	
	private static int nbCard;
	
	private static Question q;
	
	// Label
	static Label lblPlayer;
	
	/* *** Constructor *** */
    public GameBoardAP(List<User> listPlayers) throws IOException {
        // Generate card
        card = new GameCardAP();
        card.setVisible(false);
    	        
        if(Main.getGameBoardAP() == null) {      	

            GameBoardAP.imgs = new ImageView[board_height][board_width];
            
            // Adding player
            ending = new ArrayList<>();
            
            GameBoardAP.players = new ArrayList<>();
            GameBoardAP.players.addAll(listPlayers);
            nbPlayers = players.size();
            
            // Create options for all players
            anwsers = new ArrayList<>();
            for (int i = 0; i < nbPlayers; i++) {
           	 	getPosition().add(new Integer[4]);
                getPosition().get(i)[0] = 0; // positionJ
                getPosition().get(i)[1] = 1; // positionI
                getPosition().get(i)[2] = 0; // lastPostJ
                getPosition().get(i)[3] = 1; // lastPostI
                getPositionImg().add(new ImageView[board_height][board_width]);
                anwsers.add(new boolean[Category.getNb()]);
                getPlayerX().put(i, 0);
                getPlayerY().put(i, 0);
            }
            
            step = new GridPane[GameBoardAP.getPlayers().size()];
            for(int i = 0; i < nbPlayers; i++) {
            	seeStep(i);
            }

            // See player 
            getLblPlayer().setText(players.get(getPlaying()).getName() + " turn to play");
            getLblPlayer().setTextFill(Color.WHITE);
            getLblPlayer().setFont(new Font("Arial", 60));
            getLblPlayer().setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 1);
            // Change for presentation 10 initial
            getLblPlayer().setTranslateX(100.);
            AnchorPane.setTopAnchor(getLblPlayer(), 0.0);
            
            // Add
            getPnlStep().getChildren().addAll(getPnlP1());
            this.getChildren().addAll(getLblPlayer(), getPnlStep());
            
            // Change for presentation 10 initial
            AnchorPane.setLeftAnchor(getPnlStep(), 100.);
            AnchorPane.setTopAnchor(getPnlStep(), Screen.getPrimary().getVisualBounds().getHeight() * 0.90);
                    	
        	// Create NewBoard
            createBoard();
            
            // Set Image
            for (int i = 0; i < nbPlayers; i++) {
                getPositionImg().get(i)[1][0].setImage(players.get(i).getImg());
               	getPositionImg().get(i)[1][0].setScaleY(-1.0);
               	getPositionImg().get(i)[1][0].setRotate(90);
               	
            	getPosition().get(i)[3] = getPosition().get(i)[1];
            	getPosition().get(i)[2] = getPosition().get(i)[0];
            	
            	imgSize(i);
            }
        }
        
        if(Main.getGameBoardAP() != null) {        	
        	// Create NewBoard
            createBoard();
            
            // Restore
            restore(Main.getGameBoardAP());
            
            this.getChildren().addAll(getLblPlayer(), getPnlStep());
        }        
    }
    
    /* Create a NewBoard */
    public void createBoard() throws IOException {  
        // Create GameBoardAP
        GridPane GameBoardAP = new GridPane();
        GameBoardAP.setRotate(90.0);
        GameBoardAP.setScaleY(-1.0);
        GameBoardAP.setPrefSize(Screen.getPrimary().getVisualBounds().getWidth(), 0);
        GameBoardAP.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.18);
        
        // Create a tiles
        for (int i = 0; i < board_height ; i++) {
            for (int j = 0; j < board_width; j++) {
                getTiles()[i][j] = new Rectangle(105, 105);      
        		getTiles()[i][j].setFill(Color.TRANSPARENT);
        		
        		// Container
            	StackPane container = new StackPane();
        		
                if (j >= 0 || j <= 15) {
                	if(i == 0 && j == 0) {
            	    	image = new Image(LoadImgHB.load("/images/portail.gif"));
            	    	imgs[i][j] = new ImageView(image);
            			imgs[i][j].setFitHeight(100);
                    	imgs[i][j].setFitWidth(100);
                    	imgs[i][j].setRotate(270);
                    	GameBoardAP.add(new StackPane(getTiles()[i][j], imgs[i][j]), i, j);  
                	} else if((i == 0 && j != 1) || (i == 4) || (i == 1 && j == 2) || (i == 2 && (j != 1 && j != 13))) {                		
                		if (i == 2 && j == 12) {
                	    	image = new Image(LoadImgHB.load("/images/portail.gif"));
                			imgs[i][j] = new ImageView(image);
                			imgs[i][j].setFitHeight(100);
                        	imgs[i][j].setFitWidth(100);
                        	imgs[i][j].setRotate(270);
                        	GameBoardAP.add(new StackPane(getTiles()[i][j], imgs[i][j]), i, j);  
                		} else if (i == 2 && j == 14) {
                	        image = new Image(dice.getIs());
                			imgs[i][j] = new ImageView(image);
                			imgs[i][j].setFitHeight(100);
                        	imgs[i][j].setFitWidth(100);
                        	imgs[i][j].setRotate(270);
                        	GameBoardAP.add(new StackPane(getTiles()[i][j], imgs[i][j]), i, j);               			
                		} else {
                        	for (int x = 0; x < nbPlayers; x++) {
                        		getPositionImg().get(x)[i][j] = new ImageView();
                            	getPositionImg().get(x)[i][j].setFitHeight(80);
                            	getPositionImg().get(x)[i][j].setFitWidth(80);
                            	getPositionImg().get(x)[i][j].setRotate(270);
                            	getPositionImg().get(x)[i][j].setPreserveRatio(true);
                            	container.getChildren().addAll(getPositionImg().get(x)[i][j]);
                        	}
                    		GameBoardAP.add(new StackPane(getTiles()[i][j], container), i, j);
                		}
                	} else {
                    	for (int x = 0; x < nbPlayers; x++) {
                    		getPositionImg().get(x)[i][j] = new ImageView();
                        	getPositionImg().get(x)[i][j].setFitHeight(80);
                        	getPositionImg().get(x)[i][j].setFitWidth(80);
                        	getPositionImg().get(x)[i][j].setRotate(270);
                        	getPositionImg().get(x)[i][j].setPreserveRatio(true);
                        	container.getChildren().addAll(getPositionImg().get(x)[i][j]);
                    	}
                    	GameBoardAP.add(new StackPane(getTiles()[i][j], container), i, j);  
                	}
                }
            }
        }
        
        imgs[2][14].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {				
				int move = dice.diceRoll();  
				getImgs()[2][14].setImage(new Image("/dice/" + Integer.toString(move) + ".png"));
            	if(!ending.contains(players.get(getPlaying()))) {
                	// Move
                	move(getPlaying(), move);
                	// Size img
                	imgSize(getPlaying());   
                	
                	// Call card
            		seeCard();		
					
                	card.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.65);
                	card.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.024);
            	}
            	save();
			}
		});
                        
        // Set color
        tileColor();
        
        // Adding
        this.getChildren().add(GameBoardAP);
        this.getChildren().add(card);
    }
        
    /* See card with question */
    public void seeCard() {
    	/* WIP */
    	Color color = (Color) getTiles()[getPosition().get(getPlaying())[1]][getPosition().get(getPlaying())[0]].getFill();
		Color challenge = Color.SLATEGREY;
		
		boolean test = false;
		for (Category cat : Category.values()) {
			if (cat.getColor() == color) {
				if(getAnwsers().get(getPlaying())[cat.getId()] == true) {
					// Next player
	        		setPlaying();
	                getLblPlayer().setText(players.get(getPlaying()).getName() + " turn to play");
					test = true;
					break;
				}
			}
		}
		if(test == false) {
			if(challenge != color) {
				getImgs()[2][14].setImage(new Image(dice.getIs()));
				getImgs()[2][14].setFitHeight(100);
				getImgs()[2][14].setFitWidth(100);
				getImgs()[2][14].setDisable(true); 
				
				card.setVisible(true);
				
				Card cardChoice = ConnectJson.getDeck().getListCard().get(nbCard);		
				for(int i = 0; i < cardChoice.getListQuestions().size(); i++) {
					if(color == cardChoice.getListQuestions().get(i).getCategory().getColor()) {
						// Get Question
						q = cardChoice.getListQuestions().get(i);
						
						// Set question
						card.getLblQuestion().setText(q.getInterrogation());
					}
				}	
				setQuestion();				
			} else {
				// Call challenge
				Challenge.randomChallenge();
			}
		}
    }
    
    /* Set question on card */
    public void setQuestion() {    	
    	int i = 0;
	    for (Entry<String, Boolean> entry : q.getChoices().entrySet()) {
	    	String key = entry.getKey();
	    	Boolean value = entry.getValue();
	    	if( i == 0) {
	    		card.getBtnAnwser1().setText(key);
				card.setAnwser1(value);
	    	} else if (i == 1) {
	    		card.getBtnAnwser2().setText(key);
	    		card.setAnwser2(value);
	    	} else if (i == 2) {
	    		card.getBtnAnwser3().setText(key);
	    		card.setAnwser3(value);
	    	}
	    	i++;
	    }
    }
    
    /* Move */
    public void move(int actual, int step) {
    	// Check if finish    	
       	int count = 0;
    	if ((getPosition().get(actual)[1] >= 0 && getPosition().get(actual)[1] < 5) && getPosition().get(actual)[0] < 3) {
           	for(int i = 0; i < Category.getNb(); i++) {
           		if(anwsers.get(getPlaying())[i] == true) {
           			count++;
           		}
           		if(count == Category.getNb()) {
       	    		getPositionImg().get(getPlaying())[getPosition().get(getPlaying())[1]][getPosition().get(getPlaying())[0]].setImage(null);
       	    		getPosition().get(getPlaying())[1] = 1;
       	    		getPosition().get(getPlaying())[0] = 1;
       	    		ending.add(players.get(getPlaying()));
       	    		setPlaying();
           		}
           	}
   		}
    	
    	if(count != 6) {
       		// Set changement
        	if(getPlayerX().get(actual) + step >= getX().size()) {
        		step = step - getPlayerX().get(actual) - (getX().size() - 1);	
        		getPlayerX().replace(actual, 1);
            	getPlayerY().replace(actual, 1);
        	} else {
            	getPlayerX().replace(actual, getPlayerX().get(actual) + step);
            	getPlayerY().replace(actual, getPlayerY().get(actual) + step);
        	}
        	getPosition().get(actual)[0] = getX().get(getPlayerX().get(actual));
        	getPosition().get(actual)[1] = getY().get(getPlayerY().get(actual));
       	}
    	
    	if(!ending.contains(players.get(actual))) {
    		// Move
           	getPositionImg().get(actual)[getPosition().get(actual)[1]][getPosition().get(actual)[0]].setImage(players.get(getPlaying()).getImg());
           	getPositionImg().get(actual)[getPosition().get(actual)[1]][getPosition().get(actual)[0]].setScaleY(-1.0);
           	getPositionImg().get(actual)[getPosition().get(actual)[1]][getPosition().get(actual)[0]].setRotate(90);
           	
        	// Clean last position
    		if (getPosition().get(actual)[3] != 0 && getPosition().get(actual)[2] == 0) {
        		getPositionImg().get(actual)[getPosition().get(actual)[3]][getPosition().get(actual)[2]].setImage(null);
        	} else if (getPosition().get(actual)[3] != 0 && getPosition().get(actual)[2] != 0) {
        		getPositionImg().get(actual)[getPosition().get(actual)[3]][getPosition().get(actual)[2]].setImage(null);
        	} else if (getPosition().get(actual)[3] == 0 && getPosition().get(actual)[2] > 0) {
        		getPositionImg().get(actual)[getPosition().get(actual)[3]][getPosition().get(actual)[2]].setImage(null);
        	}
        	getPosition().get(actual)[3] = getPosition().get(actual)[1];
        	getPosition().get(actual)[2] = getPosition().get(actual)[0];
    	}
    }
    
    /* Set size for player */
    public static void imgSize(int actual) {
    	List<Integer> resize = new ArrayList<>();
    	resize.add(actual);
    	for (int i = 0; i < nbPlayers; i++) {
    		if (i != actual) {
    			if (getPosition().get(i)[0] == getPosition().get(actual)[0] && getPosition().get(i)[1] == getPosition().get(actual)[1]) {
    	    		resize.add(i);
    			}
    		}    		
    	}
    	Collections.sort(resize);
       	resize(resize);
    }
    
    /* Resize image */
    public static void resize(List<Integer> resize) {
    	int size = 80;
    	int[] emplacement, line;
    	emplacement = new int[resize.size()+1];
    	line = new int[resize.size()+1];
    	
    	// Set position 
    	if (resize.size() == 1) {
    		line[0] = 0;
    	} else if (resize.size() == 2) {
    		size = 40; 
    		emplacement[0] = -25; emplacement[1] = 25; line[0] = 0;
    	} else if (resize.size() == 3) {
    		size = 35; 
    		emplacement[0] = -25; emplacement[1] = 25; emplacement[2] = 0; 
    		line[0] = -25; line[1] = -25; line[2] = 25;
    	} else if (resize.size() == 4) {
    		size = 35; emplacement[0] = -25; emplacement[1] = 25; emplacement[2] = -25; emplacement[3] = 25; 
    		line[0] = -25; line[1] = -25; line[2] = 25; line[3] = 25; 
    	} else if (resize.size() == 5) {
    		size = 30;
    		emplacement[0] = -35; emplacement[1] = 0; emplacement[2] = 35; emplacement[3] = -25; emplacement[4] = 25;
    		line[0] = -25; line[1] = -25; line[2] = -25; line[3] = 25; line[4] = 25;
    	} else if (resize.size() == 6) {
    		size = 30;
    		emplacement[0] = -35; emplacement[1] = 0; emplacement[2] = 35; emplacement[3] = -35; emplacement[4] = 0; emplacement[5] = 35;
    		line[0] = -25; line[1] = -25; line[2] = -25; line[3] = 25; line[4] = 25; line[5] = 25;
    	} else if (resize.size() == 7) {
    		size = 30;
    		emplacement[0] = -25; emplacement[1] = 25; emplacement[2] = -25; emplacement[3] = 25; emplacement[4] = -25; emplacement[5] = 25; emplacement[6] = -25;
    		line[0] = -35; line[1] = -35; line[2] = -15; line[3] = -15; line[4] = 5; line[5] = 5; line[6] = 25;
    	} else if (resize.size() == 8) {
    		size = 30;
    		emplacement[0] = -25; emplacement[1] = 25; emplacement[2] = -25; emplacement[3] = 25; emplacement[4] = -25; emplacement[5] = 25; emplacement[6] = -25;
    		emplacement[7] = 25;
    		line[0] = -35; line[1] = -35; line[2] = -15; line[3] = -15; line[4] = 5; line[5] = 5; line[6] = 25; line[7] = 25;
    	}
    			
    	// Set
    	for (int i =  0; i < resize.size(); i++) {
    		getPositionImg().get(resize.get(i))[getPosition().get(resize.get(i))[1]][getPosition().get(resize.get(i))[0]].setFitHeight(size);
    		getPositionImg().get(resize.get(i))[getPosition().get(resize.get(i))[1]][getPosition().get(resize.get(i))[0]].setFitWidth(size);
    		
    		getPositionImg().get(resize.get(i))[getPosition().get(resize.get(i))[1]][getPosition().get(resize.get(i))[0]].setTranslateY(emplacement[i]);
    		getPositionImg().get(resize.get(i))[getPosition().get(resize.get(i))[1]][getPosition().get(resize.get(i))[0]].setTranslateX(line[i]);
    	}
    }

    /* Set color to tile */
    public void tileColor() {
    	Color challenge = Color.SLATEGREY;
        color = new ArrayList<>();
        for (int i = 0; i < Category.getNb(); i++) {
        	color.add(Category.getById(i).getColor());
        }
        
        // Start color (random)
        int min = 0;
        int max = color.size() - 1;
		int randomStart = (int)Math.floor(Math.random()*(max-min+1)+min);
		colorpicker = randomStart;
		
		// Set tile color
		for(int i = 1; i <= getX().size(); i++) {
			getTiles()[getY().get(i - 1)][getX().get(i - 1)].setStroke(Color.LIGHTGREY);
			getTiles()[getY().get(i - 1)][getX().get(i - 1)].setStrokeWidth(4.0);
			getTiles()[getY().get(i - 1)][getX().get(i - 1)].setFill(color.get(colorpicker));
			
			if((i % (color.size() + 1)) == 0) {
				getTiles()[getY().get(i - 1)][getX().get(i - 1)].setFill(challenge);
			} else {
				colorpicker++;
	        	if(colorpicker == color.size()) {
	        		colorpicker = 0;
	        	}
			}
		}
    }
    
    /* Step for win */
    public void seeStep(int id) throws IOException {
		// Create Step
		step[id] = new GridPane();
		step[id].setScaleY(-1.0);
		
		// Create a tiles
		int i = 0;
		for(Category cat : Category.values()) {
			getStepTile()[id][i] = new Rectangle(20, 20);
			getStepTile()[id][i].setFill(cat.getColor());
			
			getStepCheck()[id][i] = new Text("");
			getStepCheck()[id][i].setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			getStepCheck()[id][i].setFill(Color.YELLOW);
			getStepCheck()[id][i].setStroke(Color.web("#7080A0"));
			
			getStepCheck()[id][i].setScaleY(-1.0);
			
			step[id].add(new StackPane(getStepTile()[id][i], getStepCheck()[id][i]), 0, i);  
			i++;
		}

		getLblStepPlayer()[id] = new Label();
		getLblStepPlayer()[id].setText(players.get(id).getName());
		getLblStepPlayer()[id].setTextFill(Color.YELLOW);
		getLblStepPlayer()[id].setFont(Font.font("Arial",  FontWeight.NORMAL, 18));
				
		pnlByPlayer = new VBox();
		pnlByPlayer.getChildren().addAll(step[id], getLblStepPlayer()[id]);
		getPnlP1().getChildren().add(pnlByPlayer);
	}
    
    /* DP Memento */
    public GameBoardAPState save() {
		return new GameBoardAPState(getTiles(), getImgs(), getPlaying(), getNbPlayers(), getPlayers(), getAnwsers(), 
				getPosition(), getPositionImg(), getPnlP1(), getPnlStep(), getLblStepPlayer(),
				getStepTile(), getStepCheck(), getStep(), getEnding());
	}
    
	public void restore(GameBoardAPState save) {
		setTiles(save.getTiles());
		setImgs(save.getImgs());
		setPlaying2(save.getPlaying());
		setNbPlayers(save.getNbPlayers());
		setPlayers(save.getPlayers());
		setAnwsers(save.getAnwsers());
		setPosition(save.getPosition());
		setPositionImg(save.getPositionImg());
		setPnlP1(save.getPnlP1());
		setPnlStep(save.getPnlStep());
		setLblStepPlayer(save.getLblStepPlayer());
		setStepTile(save.getStepTile());
		setStepCheck(save.getStepCheck());
		setStep(save.getStep());
		setEnding(save.getEnding());
		
		// Set Image
        for (int i = 0; i < nbPlayers; i++) {
            getPositionImg().get(i)[getPosition().get(i)[1]][getPosition().get(i)[0]].setImage(players.get(i).getImg());
           	getPositionImg().get(i)[getPosition().get(i)[1]][getPosition().get(i)[0]].setScaleY(-1.0);
           	getPositionImg().get(i)[getPosition().get(i)[1]][getPosition().get(i)[0]].setRotate(90);
           	
        	getPosition().get(i)[3] = getPosition().get(i)[1];
        	getPosition().get(i)[2] = getPosition().get(i)[0];
        	
        	imgSize(i);
        }
	}
    
    /* *** Getters / Setters *** */
	public static Label getLblPlayer() {
		if (lblPlayer == null) {
			lblPlayer = new Label();
		}
		return lblPlayer;
	}
	public static void setPlaying2(int playing) {
		GameBoardAP.playing = playing;
	}
	public static void setPlaying() {
		if(ending.size() != players.size()) {
            GameBoardAP.playing = GameBoardAP.playing + 1;
            if(GameBoardAP.playing >= getNbPlayers()) {
                GameBoardAP.playing = 0;
            }            
            
            while (ending.contains(players.get(getPlaying()))) {
                if(getPlaying() + 1 >= getNbPlayers()) {
                    GameBoardAP.playing = 0;
                } else {
                    GameBoardAP.playing = getPlaying() + 1;
                }
            }            
		} else {
			// End game
			Party.newParty(getEnding());
			String alertMessage = "The first player is " + getEnding().get(0).getName() + "\nThe second is " 
			+ getEnding().get(1).getName();
			if(getEnding().size() > 2) {
				alertMessage +="\nThe third is "+ getEnding().get(2).getName();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(alertMessage);
			alert.initOwner(Main.getStage());
			alert.showAndWait();
			
			Main.setGameBoardAP(null);
			
			// Initialize
			Pane root = new  Pane();
			Image img = new Image(Main.getMainBg());
			ImageView imgView = new ImageView(img);	
			MainMenuVB gameMenu = new MainMenuVB();
				
			// Add components
			root.getChildren().addAll(imgView, gameMenu);
			
			// Set this root in the main scene -> stage 
			Main.getStage().getScene().setRoot(root);
		}
	}
	public static int getPlaying() {
		return playing;
	}
	public static GameCardAP getCard() {
		return card;
	}
	public static ImageView[][] getImgs() {
		return imgs;
	}
	public static List<User> getPlayers() {
		return players;
	}
	public static void setPlayers(List<User> players) {
		GameBoardAP.players = players;
	}
	public static List<boolean[]> getAnwsers() {
		return anwsers;
	}
	public static void setAnwsers(List<boolean[]> anwsers) {
		GameBoardAP.anwsers = anwsers;
	}
	public static GridPane[] getStep() {
		return step;
	}
	public static void setStep(GridPane[] step) {
		GameBoardAP.step = step;
	}
	public static List<User> getEnding() {
		return ending;
	}
	public static void setEnding(List<User> ending) {
		GameBoardAP.ending = ending;
	}
	public static int getNbPlayers() {
		return nbPlayers;
	}
	public static void setNbPlayers(int nbPlayers) {
		GameBoardAP.nbPlayers = nbPlayers;
	}
	public static void setNbCard(int nbCard) {
		GameBoardAP.nbCard = nbCard;
	}
	public static int getNbCard() {
		return nbCard;
	}
	public static Question getQ() {
		return q;
	}
	public HBox getPnlP1() {
		if(pnlP1 == null) {
			pnlP1 = new HBox();
			pnlP1.setSpacing(25.0);
		}
		return pnlP1;
	}
	public void setPnlP1(HBox pnlP1) {
		this.pnlP1 = pnlP1;
	}
	public VBox getPnlStep() {
		if(pnlStep == null) {
			pnlStep = new VBox();
		}
		return pnlStep;
	}
	public void setPnlStep(VBox pnlStep) {
		this.pnlStep = pnlStep;
	}
	public static List<Integer> getY() {
		if(y == null) {
			y = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2));			
		}
		return y;
	}
	public static List<Integer> getX() {
		if(x == null) {
			x = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 15, 15, 15, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 2, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
		}
		return x;
	}
	public static Map<Integer, Integer> getPlayerX() {
		if(playerX == null) {
			playerX = new HashMap<>();
		}
		return playerX;
	}
	public static Map<Integer, Integer> getPlayerY() {
		if(playerY == null) {
			playerY = new HashMap<>();
		}
		return playerY;
	}
	public static List<Integer[]> getPosition() {
		if(position == null) {
			position = new ArrayList<>();
		}
		return position;
	}
	public void setPosition(List<Integer[]> position) {
		GameBoardAP.position = position;
	}
	
	public static List<ImageView[][]> getPositionImg() {
		if(positionImg == null) {
			positionImg = new ArrayList<>();
		}
		return positionImg;
	}
	public static void setPositionImg(List<ImageView[][]> positionImg) {
		GameBoardAP.positionImg = positionImg;
	}
	
	public Rectangle[][] getTiles() {
		if(tiles == null) {
			tiles = new Rectangle[board_height][board_width];
		}
		return tiles;
	}
	public void setTiles(Rectangle[][] tiles) {
		this.tiles = tiles;
	}
	
	public Rectangle[][] getStepTile() {
		if(stepTile == null) {
			 stepTile = new Rectangle[GameBoardAP.getPlayers().size()][Category.getNb()];
		}
		return stepTile;
	}
	public void setStepTile(Rectangle[][] stepTile) {
		this.stepTile = stepTile;
	}
	public static Text[][] getStepCheck() {
		if(stepCheck == null) {
			stepCheck = new Text[GameBoardAP.getPlayers().size()][Category.getNb()];
		}
		return stepCheck;
	}
	public static void setStepCheck(Text[][] stepCheck) {
		GameBoardAP.stepCheck = stepCheck;
	}
	public Label[] getLblStepPlayer() {
		if(lblStepPlayer == null) {
			lblStepPlayer = new Label[GameBoardAP.getPlayers().size()];
		}
		return lblStepPlayer;
	}
	public void setLblStepPlayer(Label[] lblStepPlayer) {
		this.lblStepPlayer = lblStepPlayer;
	}
	public static void setImgs(ImageView[][] imgs) {
		GameBoardAP.imgs = imgs;
	}
	public static Text[][] getStepImg() {
		return stepCheck;
	}
}
