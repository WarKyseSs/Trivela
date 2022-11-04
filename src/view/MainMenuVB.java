package view;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class MainMenuVB extends VBox {

	/* *** Declaration of class attributes *** */
	VBox mainMenu = new VBox(25);
	VBox questionsMenu = new VBox(20);	
	
	MenuButtonSP btnPlay = new MenuButtonSP("PLAY");
	MenuButtonSP btnOptions = new MenuButtonSP("OPTIONS");
	MenuButtonSP btnExit = new MenuButtonSP("EXIT");
	MenuButtonSP btnCredits = new MenuButtonSP("CREDITS");
	MenuButtonSP btnBack = new MenuButtonSP("BACK");
	
	Button btnQuestion = new Button();

	
	/* *** Constructor *** */
	public MainMenuVB() {
		// Add components for the main menu    
		getChildren().addAll(getMainMenu(), getQuestionsMenu());
	}

	
	/* *** Getters *** */
	
	/* MainMenu */
	public VBox getMainMenu() {	
		
		// Initalize if null
		if(mainMenu==null) { mainMenu = new VBox(25);}
			
		// Replace the menu
		mainMenu.setTranslateX((Screen.getPrimary().getVisualBounds().getWidth() / 2) - 125);
		mainMenu.setTranslateY((Screen.getPrimary().getVisualBounds().getHeight() / 2));	
		
		// Add components
		mainMenu.getChildren().addAll(getBtnPlay(), getBtnOptions(), getBtnExit(), getBtnCredits());	
		
		return mainMenu;
	}

	/* QuestionsMenu */
	public VBox getQuestionsMenu() {
		
		// Initalize if null
		if(questionsMenu==null) { questionsMenu = new VBox(25); }
			
		// Add components
		questionsMenu.getChildren().add(getBtnQuestion());	
		
		return questionsMenu;
	}

	/* BtnPlay */
	public MenuButtonSP getBtnPlay() {
		
		// Initialise if null
		if(btnPlay==null) { btnPlay = new MenuButtonSP("PLAY"); }

		// Add a MouseClickedEvent 
		btnPlay.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Initialize
				PlayMenuAP play = new PlayMenuAP();
				Pane root = new  Pane();
				ImageView imgView = new ImageView(new Image(Main.getBg()));	
				// Add components
				root.getChildren().addAll(imgView, play);
				// Set this root in the main scene -> stage 
				Main.getStage().getScene().setRoot(root);
			}
		});
		return btnPlay;
	}

	/* BtnOption */
	public MenuButtonSP getBtnOptions() {
		
		// Initialize if null 
		if(btnOptions==null) { btnOptions = new MenuButtonSP("OPTIONS"); }
		
		// Add a MouseClickedEvent 
		btnOptions.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				
				// Initialize 
	    		OptionsMenuVB op = new OptionsMenuVB();
	    		Pane root = new  Pane();
	            Image bg = new Image(LoadImgHB.load("/images/options.jpg"));
				ImageView imgView = new ImageView(bg);	
				
				// Add components 
				root.getChildren().addAll(imgView, op);

				// Set this root in the main scene -> stage 
				Main.getStage().getScene().setRoot(root); 
			}
		});
		return btnOptions;
	}

	/* BtnExit */
	public MenuButtonSP getBtnExit() {
		
		// Initialize if null 
		if(btnExit==null) { btnExit = new MenuButtonSP("EXIT"); }
			
		// Add a MouseClickedEvent 
		btnExit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Exit 
				System.exit(0);
			}
		});
		return btnExit;
	}

	/* BtnCredits */
	public MenuButtonSP getBtnCredits() {
		
		// Initialize if null 
		if(btnCredits==null) { btnCredits = new MenuButtonSP("CREDITS"); }
		
		// Translate
		btnCredits.setTranslateX(840);
		btnCredits.setTranslateY(260);
		
		// Replace 
		btnCredits.setOpacity(0.6);
		
		// Add a MouseClickedEvent 
		btnCredits.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {	
				// Initialize 
				Pane root = new  Pane();
	            Image bg = new Image(LoadImgHB.load("/images/credit.jpg"));
				ImageView imgView = new ImageView(bg);	
				
				// Add components 
				root.getChildren().addAll(imgView, getBtnBack());
				
				// Set this root in the main scene -> stage 
				Main.getStage().getScene().setRoot(root);
			}
		});
		return btnCredits;
	}

	/* BtnBack */
	public MenuButtonSP getBtnBack() {
		
		// Initialize if null 
		if(btnBack==null) { btnBack = new MenuButtonSP("BACK"); }
			
		// Replace 
		btnBack.setTranslateX(1700);
		btnBack.setTranslateY(990);
		
		// Add a MouseClickedEvent 
		btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				
				// Initialize 
				Pane root = new  Pane();
				Image imgBack = new Image(Main.getMainBg());
				ImageView imgView = new ImageView(imgBack);	
				MainMenuVB gameMenu = new MainMenuVB();
				
				// Add components 
				root.getChildren().addAll(imgView, gameMenu);
				
				// Set this root in the main scene -> stage 
				Main.getStage().getScene().setRoot(root);
			}
		});
		return btnBack;
	}

	/* BtnQuestion */
	public Button getBtnQuestion() {
		
		// Initialize if null 
		if(btnQuestion==null) { btnQuestion = new Button(); }
	
		// Replace
		// Change for presentation, 50 initial
		btnQuestion.setTranslateX(100);
	    btnQuestion.setTranslateY(-200);
	    
	    btnQuestion.setStyle(
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 3px; " +
                "-fx-min-height: 3px; " +
                "-fx-max-width: 3px; " +
                "-fx-max-height: 3px;"
        );
	    
	    // Set an image for the button 
	    Image img = new Image(LoadImgHB.load("/images/redStar.png"));
	    ImageView view = new ImageView(img);
	    view.setFitHeight(50);
	    view.setPreserveRatio(true);
	    btnQuestion.setGraphic(view);
	    
	    // Add a MouseClickedEvent
	    btnQuestion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	    	public void handle(MouseEvent event) {
	    		
		    	// Initialize 
	    		AdminMenuVB al = new AdminMenuVB();
				Pane root = new  Pane();
				Image bg = new Image(Main.getMainBg());
				ImageView imgView = new ImageView(bg);	
				
				// Add components 
				root.getChildren().addAll(imgView, al);

				// Set this root in the main scene -> stage 
				Main.getStage().getScene().setRoot(root);  
	    	}	
		});
		return btnQuestion;
	}
}