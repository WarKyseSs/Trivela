package view;

import java.util.List;

import application.Main;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.User;

public class GameBoardAPState {
	/* *** Declaration of class attributes *** */
	
    // Parameters of board
	private Rectangle[][] tiles;
	private ImageView[][] imgs;
	
	/* Declaration for players */
	private int playing = 0;
	private int nbPlayers;
	private List<User> players;
	private List<boolean[]> anwsers;
	private List<Integer[]> position;
	private List<ImageView[][]> positionImg;

	// Step  
	private HBox pnlP1;
	private VBox pnlStep;
	private Label lblStepPlayer[];
	private Rectangle stepTile[][];
	private Text stepCheck[][];
	private GridPane step[];
	private List<User> ending;
			
	// Label
	static Label lblPlayer;

	public GameBoardAPState(Rectangle[][] tiles, ImageView[][] imgs, int playing, int nbPlayers, List<User> players,
			List<boolean[]> anwsers, List<Integer[]> position, List<ImageView[][]> positionImg, HBox pnlP1,
			VBox pnlStep, Label[] lblStepPlayer, Rectangle[][] stepTile, Text[][] stepCheck,
			GridPane[] step, List<User> ending) {
		super();
		this.tiles = tiles;
		this.imgs = imgs;
		this.playing = playing;
		this.nbPlayers = nbPlayers;
		this.players = players;
		this.anwsers = anwsers;
		this.position = position;
		this.positionImg = positionImg;
		this.pnlP1 = pnlP1;
		this.pnlStep = pnlStep;
		this.lblStepPlayer = lblStepPlayer;
		this.stepTile = stepTile;
		this.stepCheck = stepCheck;
		this.step = step;
		this.ending = ending;
		Main.setGameBoardAP(this);
	}
	
	public Rectangle[][] getTiles() {
		return tiles;
	}
	public void setTiles(Rectangle[][] tiles) {
		this.tiles = tiles;
	}
	public ImageView[][] getImgs() {
		return imgs;
	}
	public void setImgs(ImageView[][] imgs) {
		this.imgs = imgs;
	}
	public int getPlaying() {
		return playing;
	}
	public void setPlaying(int playing) {
		this.playing = playing;
	}
	public int getNbPlayers() {
		return nbPlayers;
	}
	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}
	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	public List<boolean[]> getAnwsers() {
		return anwsers;
	}
	public void setAnwsers(List<boolean[]> anwsers) {
		this.anwsers = anwsers;
	}
	public List<Integer[]> getPosition() {
		return position;
	}
	public void setPosition(List<Integer[]> position) {
		this.position = position;
	}
	public List<ImageView[][]> getPositionImg() {
		return positionImg;
	}
	public void setPositionImg(List<ImageView[][]> positionImg) {
		this.positionImg = positionImg;
	}
	public HBox getPnlP1() {
		return pnlP1;
	}
	public void setPnlP1(HBox pnlP1) {
		this.pnlP1 = pnlP1;
	}
	public VBox getPnlStep() {
		return pnlStep;
	}
	public void setPnlStep(VBox pnlStep) {
		this.pnlStep = pnlStep;
	}
	public Label[] getLblStepPlayer() {
		return lblStepPlayer;
	}
	public void setLblStepPlayer(Label[] lblStepPlayer) {
		this.lblStepPlayer = lblStepPlayer;
	}
	public Rectangle[][] getStepTile() {
		return stepTile;
	}
	public void setStepTile(Rectangle[][] stepTile) {
		this.stepTile = stepTile;
	}
	public Text[][] getStepCheck() {
		return stepCheck;
	}
	public void setStepCheck(Text[][] stepCheck) {
		this.stepCheck = stepCheck;
	}
	public GridPane[] getStep() {
		return step;
	}
	public void setStep(GridPane[] step) {
		this.step = step;
	}
	public List<User> getEnding() {
		return ending;
	}
	public void setEnding(List<User> ending) {
		this.ending = ending;
	}
	public static Label getLblPlayer() {
		return lblPlayer;
	}
	public static void setLblPlayer(Label lblPlayer) {
		GameBoardAPState.lblPlayer = lblPlayer;
	}	
}
