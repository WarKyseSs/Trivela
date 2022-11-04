package view;

import java.io.IOException;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class PlayMenuAP extends AnchorPane {
	
	/* *** Declaration of class attributes *** */
	private Button btnLess, btnMore;
	private MenuButtonSP btnStart;
	private MenuButtonSP btnBack;
	private MenuButtonSP btnLoad;
	private Label lblNbPlayer;
	private int nbPlayer = 2;
	
	/* Constructors */
	public PlayMenuAP() {
		HBox position1 = new HBox();
		position1.getChildren().addAll(getBtnLess(), getLblNbPlayer(), getBtnMore());
		position1.setSpacing(100);
		
		this.getChildren().addAll(position1, getBtnStart(), getBtnBack(), getBtnLoad());
		
		AnchorPane.setLeftAnchor(position1, Screen.getPrimary().getVisualBounds().getWidth() * 0.42);
		AnchorPane.setTopAnchor(position1, Screen.getPrimary().getVisualBounds().getHeight() * 0.50);
	}
	
	/* Getters / Setters */
	public Button getBtnLess() {
		if(btnLess == null) {
			btnLess = new Button();
		}
		
		btnLess.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 15px; " +
                "-fx-min-height: 15px; " +
                "-fx-max-width: 15px; " +
                "-fx-max-height: 15px;"
        );
	    
	    // Set an image for the button
	    Image img = new Image(LoadImgHB.load("/images/arrow.png"));
	    ImageView view = new ImageView(img);
	    view.setFitHeight(100);
	    view.setPreserveRatio(true);
	    btnLess.setGraphic(view);
		
		btnLess.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			if (getNbPlayer() - 1 >= 2) {
    				setNbPlayer(getNbPlayer() - 1);
        			getLblNbPlayer().setText(getNbPlayer() + "");
    			}
    		}
    	});
		return btnLess;
	}
	public Button getBtnMore() {
		if(btnMore == null) {
			btnMore = new Button("More");
		}
		
		btnMore.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 15px; " +
                "-fx-min-height: 15px; " +
                "-fx-max-width: 15px; " +
                "-fx-max-height: 15px;"
        );
	    
	    // Set an image for the button
	    Image img = new Image(LoadImgHB.load("/images/arrow.png"));
	    ImageView view = new ImageView(img);
	    view.setFitHeight(100);
	    view.setTranslateX(-55.0);
	    view.setScaleX(-1.0);
	    view.setPreserveRatio(true);
	    btnMore.setGraphic(view);
		
		btnMore.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			if (getNbPlayer() + 1 <= 8) {
    				setNbPlayer(getNbPlayer() + 1);
        			getLblNbPlayer().setText(getNbPlayer() + "");
    			}
    		}
    	});
		return btnMore;
	}
	public MenuButtonSP getBtnStart() {
		if(btnStart == null) {
			btnStart = new MenuButtonSP("Confirm");
		}

		btnStart.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.50);
		btnStart.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.60);
		
		btnStart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			PlayerMenuAP mp = null;
				try {
					mp = new PlayerMenuAP(nbPlayer);
					Pane root = new  Pane();
					ImageView imgView = new ImageView(new Image(Main.getBg()));
	    			root.getChildren().addAll(imgView, mp);
	    			Main.getStage().getScene().setRoot(root); 
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
		
		return btnStart;
	}
	public MenuButtonSP getBtnBack() {
		
		// Initialize if null 
		if (btnBack == null) { btnBack = new MenuButtonSP("Back");
			
			// Set size
			btnBack.setPrefWidth(90);
			
			btnBack.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.40);
			btnBack.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.60);
			
			// Add MouseClickedEvent
			btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					
					// Initialize
					Pane root = new  Pane();
					ImageView imgView = new ImageView(new Image(Main.getMainBg()));
					MainMenuVB gameMenu = new MainMenuVB();
					
					// Add components
					root.getChildren().addAll(imgView, gameMenu);
					
					// Set this root in the main scene -> stage 
					Main.getStage().getScene().setRoot(root);
				}
			});	  
		}
		return btnBack;
	}
	public MenuButtonSP getBtnLoad() {
		if(btnLoad == null) {
			btnLoad = new MenuButtonSP("Load");
		}
		// Position
		btnLoad.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.80);
		btnLoad.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.90);
		
		if(Main.getGameBoardAP() != null) {
			btnLoad.setDisable(false);
		} else {
			btnLoad.setDisable(true);
		}
		
		btnLoad.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			if(Main.getGameBoardAP() != null) {
		 			GameBoardAP board = null;
					try {
						board = new GameBoardAP(Main.getGameBoardAP().getPlayers());
					} catch (IOException e) { e.printStackTrace(); }
			 		Pane root = new  Pane();
		 		    Image bg = new Image(LoadImgHB.load("/images/background.jpg"));
		 		    ImageView imgView = new ImageView(bg);	
		 		    
		 		    // Add components
		 		    root.getChildren().addAll(imgView, board);
		 		    // board.restore(Main.getGameBoardAP());
		 		    
		 		    // Set this root in the main scene -> stage 
		 			Main.getStage().getScene().setRoot(root); 
		 		}  
    		}
    	});
		return btnLoad;
	}
	public Label getLblNbPlayer() {
		if(lblNbPlayer == null) {
			lblNbPlayer = new Label(getNbPlayer() + "");
			lblNbPlayer.setTextFill(Color.WHITE);
			lblNbPlayer.setFont(new Font("Arial", 60));
		}
		return lblNbPlayer;
	}
	public int getNbPlayer() {
		return nbPlayer;
	}
	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}
}