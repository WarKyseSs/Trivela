package view;

import java.io.IOException;
import java.util.Optional;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import model.ConnectJson;

public class GameCardAP extends AnchorPane {
	/* *** Declaration of class attributes *** */
	/* Part of element */
	private DropShadow dropShadow = new DropShadow();
	private Rectangle rctExit, rctTimer, rctCard;
	private Label lblExit, lblTimer; 
	private Label btnAnwser1, btnAnwser2, btnAnwser3;
	private Boolean anwser1, anwser2, anwser3;
	
	/* Part of question */
	private Label lblQuestion;
	
	// Time pause
	private int chrono, timeLeft;
	private AnimationTimer mgTime;
	private AnimationTimer timerCard;
	
	// Size card
	private final double widthCard = 400;
	
	// Wait
	private boolean waiting = false;
	
	/* *** Constructors *** */
	public GameCardAP() throws IOException {				
		// CSS
		Main.getStage().getScene().getStylesheets().add("choicePicture.css");
		this.getChildren().addAll(getRctExit(), getLblExit(), getRctCard(), getRctTimer(), getLblTimer(), getLblQuestion(), getBtnAnwser1(), getBtnAnwser2(), getBtnAnwser3());
	
		Main.getStage().getScene().setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				exit();
		    }
			if(this.isVisible()) {
				if (e.getCode() == KeyCode.NUMPAD1) {
			    	getTimerCard().stop();
	    			if(anwser1 && !waiting) {
						btnAnwser1.setTextFill(Color.GREEN);
						btnAnwser2.setTextFill(Color.RED);
						btnAnwser3.setTextFill(Color.RED);
						win();
					} else {
						if(!waiting) {
							loose();
						}
					}
	    			waiting = true;
			    }
			    if (e.getCode() == KeyCode.NUMPAD2) {
			    	getTimerCard().stop();
	    			if(anwser2 && !waiting) {
						btnAnwser2.setTextFill(Color.GREEN);
						btnAnwser1.setTextFill(Color.RED);
						btnAnwser3.setTextFill(Color.RED);
						win();
					} else {
						if(!waiting) {
							loose();
						}
					}
	    			waiting = true;
			    }
			    if (e.getCode() == KeyCode.NUMPAD3) {
			    	getTimerCard().stop();
	    			if(anwser3 && !waiting) {
						btnAnwser3.setTextFill(Color.GREEN);
						btnAnwser2.setTextFill(Color.RED);
						btnAnwser1.setTextFill(Color.RED);
						win();
					} else {
						if(!waiting) {
							loose();
						}
					}
	    			waiting = true;
			    }
			}
		});
		
	}
	
	/* *** Method *** */
	public void win() {
		// Win
		GameBoardAP.getStepImg()[GameBoardAP.getPlaying()][GameBoardAP.getQ().getCategory().getId()].setText("✔");
		GameBoardAP.getAnwsers().get(GameBoardAP.getPlaying())[GameBoardAP.getQ().getCategory().getId()] = true;
		
		// See text
		GameBoardAP.getLblPlayer().setText("Good answer.");
		getMgTime().start();
		
		// Set color
		if(anwser1) {
			btnAnwser1.setTextFill(Color.GREEN);
			btnAnwser1.setFont(Font.font("Arial",  FontWeight.BOLD, 30));
		} else {
			btnAnwser1.setTextFill(Color.RED);
		}
		if(anwser2) {
			btnAnwser2.setTextFill(Color.GREEN);
			btnAnwser2.setFont(Font.font("Arial",  FontWeight.BOLD, 30));
		} else {
			btnAnwser2.setTextFill(Color.RED);
		}
		if(anwser3) {
			btnAnwser3.setTextFill(Color.GREEN);
			btnAnwser3.setFont(Font.font("Arial",  FontWeight.BOLD, 30));
		} else {
			btnAnwser3.setTextFill(Color.RED);
		}
	}
	
	public void loose() {		
		// See text
		GameBoardAP.getLblPlayer().setText("You got the wrong answer!");
		getMgTime().start();
		
		btnAnwser1.setTextFill(Color.RED);
		btnAnwser2.setTextFill(Color.RED);
		btnAnwser3.setTextFill(Color.RED);
				
		// Next player
		GameBoardAP.setPlaying();
	}
	
	public void invisible() {
		// Set invisible card
		GameBoardAP.getCard().setVisible(false);
				
		// Next card
		GameBoardAP.setNbCard(GameBoardAP.getNbCard() + 1);
		if (GameBoardAP.getNbCard() == ConnectJson.getDeck().getListCard().size()) {
			GameBoardAP.setNbCard(0);
		}
	}
	
	public void exit() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Are you sure you want to leave the game?");
		alert.initOwner(Main.getStage());
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			PlayerMenuAP.getPlayers().clear();
			
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
	
	/* *** Getters / Setters *** */
	public AnimationTimer getMgTime() {
		if(mgTime == null) {
			mgTime = new AnimationTimer() {
				long lastUpdate = 0;
				@Override
				public void handle(long now) {
					if((now - lastUpdate) >= 1000000) {
						lastUpdate = now;
						setChrono(getChrono() + 1);
						if(getChrono() == 200) {
							// Set pause
							getTimerCard().stop();
							getMgTime().stop();	
							setChrono(0);
							
							// Reset font
							btnAnwser1.setFont(Font.font("Arial",  FontWeight.NORMAL, 28));
							btnAnwser2.setFont(Font.font("Arial",  FontWeight.NORMAL, 28));
							btnAnwser3.setFont(Font.font("Arial",  FontWeight.NORMAL, 28));
							btnAnwser1.setTextFill(Color.YELLOW);
							btnAnwser2.setTextFill(Color.YELLOW);
							btnAnwser3.setTextFill(Color.YELLOW);
							
							GameBoardAP.getLblPlayer().setText(GameBoardAP.getPlayers().get(GameBoardAP.getPlaying()).getName() + " turn to play");
							
							// Enabled Label dice for next player
							GameBoardAP.getImgs()[2][14].setDisable(false);
							
							// Waiting
							waiting = false;
							
							// Disable card
							invisible();
						}
					}
					now++;
				}
			};
		}
		return mgTime;
	}
	public AnimationTimer getTimerCard() {
		if(timerCard == null) {
			timerCard = new AnimationTimer() {
				long lastUpdate = 0;
				@Override
				public void handle(long now) {
					if((now - lastUpdate) >= 1000000) {
						setTimeLeft(getTimeLeft() - 1);
						getLblTimer().setText((getTimeLeft() / 100) + "");
						if(getTimeLeft() == 0) {
							getTimerCard().stop();
							
							// Waiting
							waiting = true;
							
							// Loose
							loose();
						}
					}
					now++;
				}
			};
		}
		return timerCard;
	}
	public int getChrono() {
		return chrono;
	}
	public void setChrono(int chrono) {
		this.chrono = chrono;
	}
	public Rectangle getRctExit() {
		if(rctExit == null) {
			rctExit = new Rectangle(Screen.getPrimary().getVisualBounds().getWidth() * 0.05, 35);
		
			rctExit.setFill(Color.BLACK);
			rctExit.setStroke(Color.GREY);
			rctExit.setStrokeWidth(3.0);
			rctExit.setTranslateY(20);
			rctExit.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.03);
			rctExit.setArcWidth(30.0); 
			rctExit.setArcHeight(30.0);
			rctExit.setEffect(getDropShadow());
			
			rctExit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					exit();
				}
			});
		}
		return rctExit;
	}
	public Rectangle getRctCard() {
		if(rctCard == null) {
			rctCard = new Rectangle(Screen.getPrimary().getVisualBounds().getWidth() * 0.90, 185);
			
			rctCard.setFill(Color.BLACK);
			rctCard.setStroke(Color.GREY);
			rctCard.setStrokeWidth(3.0);
			rctCard.setTranslateY(60);
			rctCard.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.03);
			rctCard.setArcWidth(30.0); 
			rctCard.setArcHeight(30.0);
			rctCard.setEffect(getDropShadow());
		}
		return rctCard;
	}
	public Rectangle getRctTimer() {
		if(rctTimer == null) {
			rctTimer = new Rectangle(Screen.getPrimary().getVisualBounds().getWidth() * 0.05, 35);
			
			rctTimer.setFill(Color.BLACK);
			rctTimer.setStroke(Color.GREY);
			rctTimer.setStrokeWidth(3.0);
			rctTimer.setTranslateY(20);
			rctTimer.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.78);
			rctTimer.setArcWidth(30.0); 
			rctTimer.setArcHeight(30.0);
			rctTimer.setEffect(getDropShadow());
		}
		return rctTimer;
	}
	public Label getLblExit() {
		if (lblExit == null) {
			lblExit = new Label("EXIT");
			
			lblExit.setTextFill(Color.RED);
			lblExit.setTranslateY(19);
			lblExit.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.0385);
			lblExit.setFont(new Font("Arial", 30));
			
			lblExit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					exit();
				}
			});
		}
		
		return lblExit;
	}
	public Label getLblTimer() {
		if (lblTimer == null) {
			lblTimer = new Label();

			lblTimer.setTextFill(Color.RED);
			lblTimer.setTranslateY(19);
			lblTimer.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.795);
			lblTimer.setFont(new Font("Arial", 30));	
		}		
		return lblTimer;
	}
	public DropShadow getDropShadow() {
		if (dropShadow == null) {
			dropShadow = new DropShadow();

			dropShadow.setRadius(10.0);
			dropShadow.setOffsetX(3.0);
			dropShadow.setOffsetY(3.0);
			dropShadow.setColor(Color.RED);
		}
		return dropShadow;
	}
	public Label getLblQuestion() {
		if(lblQuestion == null) {
			lblQuestion = new Label("THE QUESTION HERE");
			
			lblQuestion.setTextFill(Color.WHITE);
			lblQuestion.setTranslateY(53);
			lblQuestion.setWrapText(true);
			lblQuestion.setPrefWidth(800.);
			lblQuestion.setPrefHeight(120.0);	
			lblQuestion.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.35);
			lblQuestion.setFont(new Font("Arial", 25));	
		}
		return lblQuestion;
	}
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	public Label getBtnAnwser1() {
		if(btnAnwser1 == null) {
			btnAnwser1 = new Label("");
			
			btnAnwser1.setStyle("-fx-text-fill: yellow;");
			btnAnwser1.setTranslateY(135);
			btnAnwser1.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * (0.10 * 1));
			btnAnwser1.setPrefWidth(widthCard);
			btnAnwser1.setPrefHeight(150.0);
			btnAnwser1.setWrapText(true);
			btnAnwser1.setFont(new Font("Arial", 28));
			
			btnAnwser1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    		public void handle(MouseEvent event) {
	    			getTimerCard().stop();
	    			if(anwser1 && !waiting) {
						btnAnwser1.setTextFill(Color.GREEN);
						btnAnwser2.setTextFill(Color.RED);
						btnAnwser3.setTextFill(Color.RED);
						win();
					} else {
						if(!waiting) {
							loose();
						}
					}
	    			waiting = true;
	    		}
	    	});
			
		}
		return btnAnwser1;
	}
	public Label getBtnAnwser2() {
		if(btnAnwser2 == null) {
			btnAnwser2 = new Label("");

			btnAnwser2.setStyle("-fx-text-fill: yellow;");
			btnAnwser2.setTranslateY(135);
			btnAnwser2.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.40);
			btnAnwser2.setPrefWidth(widthCard);
			btnAnwser2.setPrefHeight(150.0);
			btnAnwser2.setWrapText(true);
			btnAnwser2.setFont(new Font("Arial", 28));
			
			btnAnwser2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    		public void handle(MouseEvent event) {
	    			getTimerCard().stop();
	    			if(anwser2 && !waiting) {
						btnAnwser1.setTextFill(Color.RED);
						btnAnwser2.setTextFill(Color.GREEN);
						btnAnwser3.setTextFill(Color.RED);
						win();
					} else {
						if(!waiting) {
							loose();
						}
					}
	    			waiting = true;
	    		}
	    	});
		}
		return btnAnwser2;
	}
	public Label getBtnAnwser3() {
		if(btnAnwser3 == null) {
			btnAnwser3 = new Label("");
			
			btnAnwser3.setStyle("-fx-text-fill: yellow;");
			btnAnwser3.setTranslateY(135);
			btnAnwser3.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.70);
			btnAnwser3.setPrefWidth(widthCard);
			btnAnwser3.setPrefHeight(150.0);
			btnAnwser3.setWrapText(true);
			btnAnwser3.setFont(new Font("Arial", 28));
			
			btnAnwser3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    		public void handle(MouseEvent event) {
	    			getTimerCard().stop();
	    			if(anwser3 && !waiting) {
						btnAnwser1.setTextFill(Color.RED);
						btnAnwser2.setTextFill(Color.RED);
						btnAnwser3.setTextFill(Color.GREEN);
						win();
					} else {
						if(!waiting) {
							loose();
						}
					}
					waiting = true;
	    		}
	    	});
		}
		return btnAnwser3;
	}
	public Boolean getAnwser1() {
		return anwser1;
	}
	public Boolean getAnwser2() {
		return anwser2;
	}
	public Boolean getAnwser3() {
		return anwser3;
	}
	public void setAnwser1(Boolean anwser1) {
		this.anwser1 = anwser1;
	}
	public void setAnwser2(Boolean anwser2) {
		this.anwser2 = anwser2;
	}
	public void setAnwser3(Boolean anwser3) {
		this.anwser3 = anwser3;
		setTimeLeft(1500);
		getTimerCard().start();
	}
}