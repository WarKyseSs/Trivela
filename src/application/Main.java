package application;
	
import java.io.InputStream;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.ConnectJson;
import view.GameBoardAPState;
import view.LoadImgHB;
import view.MainMenuVB;

public class Main extends Application {
	
	/* *** Declaration of class attributes *** */
	private Pane root;
	private MainMenuVB gameMenu;
	private static InputStream mainBg;
	private static InputStream bg;
	private static GameBoardAPState gameBoardAP;
	
	private static Stage guiStage;
    

	/* *** Start Method *** */
	@Override
	public void start(Stage primaryStage) {
		try {			
			
			// Call background music
			music();

			// Initialize
			root = new  Pane();
			Image img = new Image(getMainBg());
			ImageView imgView = new ImageView(img);	
			
			gameMenu = new MainMenuVB();
			
			// Add components
			root.getChildren().addAll(imgView, gameMenu);
			
			// Create a scene and setFullScreen
			Scene scene = new Scene(getRoot());
			primaryStage.setFullScreen(true);
			
			// Set options
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Trivela");
			primaryStage.setFullScreenExitHint("");
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			primaryStage.setResizable(false);
			primaryStage.show();
			guiStage = primaryStage;
			
			// Connect with json file
			ConnectJson.jsonCall();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/* MediaPlayer */
	static MediaPlayer mediaPlayer;
	public void music() throws URISyntaxException {
		String s = "'Portal' Mysterious Space Background Music No Copyright.wav";
		Media h = new Media(Main.class.getResource("/musics/" + s).toURI().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.setVolume(0.1);	
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	
	/* *** Getters / Setters *** */
	/* MediaPlayer */
	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	/* Root */
	public Pane getRoot() {
		return root;
	}

	public void setRoot(Pane root) {
		this.root = root;
	}

	/* InputStream MainBg */
	public static InputStream getMainBg() {
		setMainBg();
		return mainBg;
	} 
	
	public static void setMainBg() {
		mainBg = LoadImgHB.load("/images/backgroundWithNeonTitle.jpg");
	}
	
	/* InputStream BG */
	public static InputStream getBg() {
		setBg();
		return bg;
	}
	
	public static void setBg() {
		bg = LoadImgHB.load("/images/background.jpg");
	}
	
	/* Stage */
    public static Stage getStage() {
        return guiStage;
    }
    
	/* Main */
	public static void main(String[] args) {
		launch(args);
	}
	public static void setGameBoardAP(GameBoardAPState gameBoardAP) {
		Main.gameBoardAP = gameBoardAP;
	}
	public static GameBoardAPState getGameBoardAP() {
		return gameBoardAP;
	}
}
