package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import model.User;

public class PlayerMenuAP extends AnchorPane {
	
	/* *** Declaration of class attributes *** */
	// List of players
	private static List<User> players= new ArrayList<>();
	// Informations of players
	private Map<Integer, TextField> txtUsers = new HashMap<>();
	// Label
	private Label lblInfo;
	private Map<Integer, Label> numberPlayer = new HashMap<>();	
	// Picture
	static Image img;
	static ImageView view;
	// MenuButtonSP
	private MenuButtonSP btnStart;
	private MenuButtonSP btnBack;
	
	// Selector picture
	private static LoadImgHB selector;
	private static int playerSelect;
	
	// Select pawn
	private ToggleGroup groupPicture = new ToggleGroup();
	private static Map<Integer, RadioButton> choicePicture = new HashMap<Integer, RadioButton>();
	
	/* Constructor */ 
	public PlayerMenuAP(int nbPlayer) throws IOException {		
		// CSS
		Main.getStage().getScene().getStylesheets().add("choicePicture.css");
		
		players.clear();
		
		HBox page = new HBox();
		selector = new LoadImgHB();	
		
		VBox global = new VBox();
		HBox panelInfo = new HBox();
		
		HBox panel = new HBox();
		VBox panelPlayer = new VBox();
		VBox panelImg = new VBox();
		
		// Position
		double hgap = 150.0;
		double vgap = 50.0;
		double spaceh = 80.0;
		
		panelInfo.getChildren().add(getLblInfo());
				
		for(int i = 0; i < nbPlayer; i++) {			
			txtUsers.put(i, new TextField());
			txtUsers.get(i).setTranslateY((vgap * 1.1) * i);

			numberPlayer.put(i, new Label("Player : " + (i+1)));
			numberPlayer.get(i).setTextFill(Color.WHITE);
			numberPlayer.get(i).setFont(new Font("Arial", 30));
			numberPlayer.get(i).setTranslateY((vgap * 1.1) * i);
			
			panelPlayer.getChildren().addAll(numberPlayer.get(i), txtUsers.get(i));
			
			// Generate player
			players.add(new User("", new Image(LoadImgHB.load("/images/naimage.png")), "/images/naimage.png"));
			
			// Create radio buton
		    choicePicture.put(i, new RadioButton(""));
		    choicePicture.get(i).setToggleGroup(groupPicture);
		    choicePicture.get(i).setTranslateY((vgap * 0.5) * i);
		    choicePicture.get(i).setGraphic(players.get(i).getPawn());
		    choicePicture.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
			    	setPlayerSelect(0);
			    	for (RadioButton value : choicePicture.values()) {
			    		if(value.isSelected()) {
			    			setPlayerSelect(getPlayerSelect());
			    			selector.setVisible(true);
			    			break;
			    		}
			    		setPlayerSelect(getPlayerSelect() + 1);
			    	}
				}
			});
		    players.get(i).getPawn().setFitHeight(90);
			players.get(i).getPawn().setFitWidth(90);
		    
		    panelImg.getChildren().add(choicePicture.get(i));
		}

		panel.getChildren().addAll(panelPlayer, panelImg);
		panel.setSpacing(spaceh);
		
		global.getChildren().addAll(panelInfo, panel);
		
		selector.setVisible(false);
		AnchorPane.setLeftAnchor(page, hgap);
		AnchorPane.setTopAnchor(page, vgap);
		page.setSpacing(360.0);
		page.getChildren().addAll(global, selector);
		
		this.getChildren().addAll(page, getBtnStart(nbPlayer), getBtnBack());
	}
	
	/* Getters / Setters */
	public Label getLblInfo() {
		if(lblInfo == null) {
			lblInfo = new Label("Name     Pawn");
		}
		lblInfo.setTextFill(Color.WHITE);
        lblInfo.setFont(new Font("Arial", 60));
		return lblInfo;
	}
	public void setLblInfo(Label lblInfo) {
		this.lblInfo = lblInfo;
	}
	public MenuButtonSP getBtnStart(int nbPlayer) {
		if(btnStart == null) {
			btnStart = new MenuButtonSP("Start");
		}
		// Position
		btnStart.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.85);
		btnStart.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.90);
		
		btnStart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			boolean start = true;
    			for(int i = 0; i < nbPlayer; i++) {
    				// Remove previous save, for presentation
    				// Main.setGameBoardAP(null);
    				if(txtUsers.get(i).getText().isEmpty()) {
    					Alert alert = new Alert(AlertType.INFORMATION);
    					alert.setTitle("Warning");
    					alert.setHeaderText("Username is missing");
    					alert.setContentText("Player " + (i + 1) + "'s name is missing");
    					alert.initOwner(Main.getStage());
    					alert.showAndWait();
    					start = false;
    					break;
    				}
    				for(int x = (i+1); x < nbPlayer; x++) {
    					if(txtUsers.get(i).getText().equals(txtUsers.get(x).getText())) {
    						Alert alert = new Alert(AlertType.INFORMATION);
        					alert.setTitle("Warning");
        					alert.setHeaderText("Username is already use !");
        					alert.initOwner(Main.getStage());
        					alert.showAndWait();
        					start = false;
        					break;
    					}
    				}
    				if(players.get(i).getPath() == "/images/naimage.png"){
    					Alert alert = new Alert(AlertType.INFORMATION);
    					alert.setTitle("Warning");
    					alert.setHeaderText("Pawn is missing");
    					alert.initOwner(Main.getStage());
    					alert.showAndWait();
    					start = false;
    					break;
    				}
    			}
    			if (start == true) {
    			 	for(int i = 0; i < nbPlayer; i++) {
        		 		players.get(i).setName(txtUsers.get(i).getText());
        		 	}
    			 	    			 	
    			 	GameBoardAP board;
        			try {
    			 		board = new GameBoardAP(players);
    			 		Pane root = new Pane();
    		 		    Image bg = new Image(LoadImgHB.load("/images/background.jpg"));
    		 		    ImageView imgView = new ImageView(bg);	
    		 		    // Add components
    		 		    root.getChildren().addAll(imgView, board);
    		 		    // Set this root in the main scene -> stage 
    		 			Main.getStage().getScene().setRoot(root); 
    		 		} 
    		 		catch (IOException e) { e.printStackTrace(); }   			 	
    			}
    		}
    	});
		
		return btnStart;
	}
		
	/* BtnBack */
	public MenuButtonSP getBtnBack() {
		// Initialize if null 
		if (btnBack == null) { btnBack = new MenuButtonSP("Back");
			
			// Set size
			btnBack.setPrefWidth(90);
			
			btnBack.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() * 0.75);
			btnBack.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() * 0.90);
			
			// Add MouseClickedEvent
			btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						// Initialize
						PlayMenuAP play = new PlayMenuAP();
						Pane root = new  Pane();
						Image bg = new Image(LoadImgHB.load("/images/background.jpg"));
						ImageView imgView = new ImageView(bg);	
						// Add components
						root.getChildren().addAll(imgView, play);
						// Set this root in the main scene -> stage 
						Main.getStage().getScene().setRoot(root);
				}
			});	  
		}
		return btnBack;
	}
	public static LoadImgHB getSelector() {
		return selector;
	}
	public static int getPlayerSelect() {
		return playerSelect;
	}
	public static void setPlayerSelect(int playerSelect) {
		PlayerMenuAP.playerSelect = playerSelect;
	}
	public static Map<Integer, RadioButton> getChoicePicture() {
		return choicePicture;
	}
	public static List<User> getPlayers() {
		return players;
	}
	public static void setPlayers(List<User> players) {
		PlayerMenuAP.players = players;
	}
}