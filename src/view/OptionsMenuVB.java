package view;

import application.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class OptionsMenuVB extends VBox {
	
	/* *** Declaration of class attributes *** */
	private Label lblSound = new Label("SOUND");
	private Slider sldSound = new Slider();
	
	MenuButtonSP btnBack = new MenuButtonSP("BACK");	
	/* *** Constructor *** */
	public OptionsMenuVB() {
		// Add components
		this.getChildren().addAll(getLblSound(), getSldSound(), getBtnBack());
	}

	
	/* *** Getters *** */
	/* LblSound */
	public Label getLblSound() {
		if(lblSound == null) { lblSound = new Label("SOUND"); }
		
		// Replace
		lblSound.setTranslateX(910);
		lblSound.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() / 2.4);
		
		lblSound.getFont();
		lblSound.setFont(Font.font(28));
		
		// Recolor
		lblSound.setTextFill(Color.WHITE);
		
		
		return lblSound;
	}

	/* SldSound */
	public Slider getSldSound() {
		if(sldSound == null) { sldSound = new Slider(); }
		
		// Replace
		sldSound.setTranslateX(Screen.getPrimary().getVisualBounds().getWidth() / 2.215);
		sldSound.setTranslateY(Screen.getPrimary().getVisualBounds().getHeight() / 2.3);
		
		sldSound.setMin(0);
		sldSound.setMax(1);
		
	    // Volume Control
		sldSound.setValue(Main.getMediaPlayer().getVolume());
		sldSound.valueProperty().addListener(new InvalidationListener() {
	        @Override
	        public void invalidated(Observable observable) {
	            Main.getMediaPlayer().setVolume((sldSound.getValue()));
	        }
	    });
		
		return sldSound;
	}
	
	/* BtnBack */
	public MenuButtonSP getBtnBack() {
		if(btnBack == null) { btnBack = new MenuButtonSP("BACK"); }
		
		// Replace 
		btnBack.setTranslateX(1705);
		btnBack.setTranslateY(865);
		
		// Add a MouseClickedEvent 
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
		return btnBack;
	}
}