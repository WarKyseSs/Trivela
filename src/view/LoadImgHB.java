package view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import model.User;

public class LoadImgHB extends HBox {

	/**
	 * Declaration of class attributes
	 */
	public static Map<String, Map<Integer, Image>> imgs = new HashMap<>();
	public static Map<String, Map<Integer, String>> imgPath = new HashMap<>();
	private static Map<Image, Button> btnImg = new HashMap<>();
	private MenuButtonSP btnConfirm;
	private String category;
	private Image select;
	private String selectPath;
	private int tabCount = 0;
	
	/**
	 * Constructor
	 */
	public LoadImgHB(){
		if(imgs.size() == 0) {
			// Get all images for players
			try {
				LoadImgHB.preLoad();
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}	
		
		// Create pane
		TabPane tabPane = new TabPane();
		Map<Integer, FlowPane> vehBox = new HashMap<>();	
				
		// List for see all pawn on pane
		for (String key : imgs.keySet()) {
			vehBox.put(tabCount, new FlowPane());
			for (Image value : imgs.get(key).values()) {
				Image pawn0 = value;
				ImageView pawn = new ImageView(pawn0);
				pawn.setFitHeight(50);
				pawn.setFitWidth(50);
				
				btnImg.put(value, new Button());
				btnImg.get(value).setStyle(
		                "-fx-background-color: none;"
		        );
								
				btnImg.get(value).setGraphic(pawn);
				btnImg.get(value).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						if (getSelect() != null) {
							btnImg.get(getSelect()).setStyle(
					                "-fx-background-color: none;"
					        );
						}
						
						setCategory(key);
						setSelect(value);
						int i = 0;
						for (Image value2 : imgs.get(key).values()) {
							if(value == value2) {
								setSelectPath(imgPath.get(key).get(i));
								break;
							}
							i++;
						}
						
						btnImg.get(getSelect()).setStyle(
				                "-fx-background-color: grey;"
				        );
					}
				});
			    
			    btnImg.get(value).setTranslateX(0);
			    vehBox.get(tabCount).getChildren().add(btnImg.get(value));
			    
			}
			Tab tab = new Tab(key, vehBox.get(tabCount));  
			tabPane.getTabs().add(tab);
			tabCount++;
		}
		
		// Adding to pane
		this.getChildren().addAll(tabPane, getBtnConfirm());
	}
	
	/**
	 * Method who load the file
	 * @param file - The file we want to load
	 * @return The file as stream
	 */
	public static InputStream load(String file) {
		return LoadImgHB.class.getResourceAsStream(file);
	}
	
	/**
	 * Method who preload
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void preLoad() throws IOException, URISyntaxException {
		URI uri = LoadImgHB.class.getResource("/pawnImg/").toURI();
        Path myPath;
        
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/pawnImg/");
        } else {
            myPath = Paths.get(uri);
        }
        
        int nbVeh = 0;

    	imgs.put("pawn", new HashMap<>());
    	imgPath.put("pawn", new HashMap<>());
        
        try (Stream<Path> walk = Files.walk(myPath, 1)) {
			for (Iterator<Path> it = walk.iterator(); it.hasNext();){
				Path fileName = it.next();
				if(!fileName.equals(myPath)) {
					fileName = fileName.getFileName();
					Image img = new Image(load("/pawnImg/" + fileName));
			    	imgs.get("pawn").put(nbVeh, img);
			    	imgPath.get("pawn").put(nbVeh, "/pawnImg/" + fileName);
			    	nbVeh++;
				}
			}
		}
	}

	/**
	 * Getter getBtnImg
	 * @param key - The key
	 * @param pawn - The pawn of the player
	 * @return The btn
	 */
	public static Map<Image, Button> getBtnImg(String key, Image pawn) {
		return btnImg;
	}
	
	/**
	 * Setter setBtnImg
	 * @param btnImg - The button img
	 */
	public static void setBtnImg(Map<Image, Button> btnImg) {
		LoadImgHB.btnImg = btnImg;
	}
	
	/**
	 * Setter setSelect
	 * @param value - The value of the select depends on the number of player
	 */
	public void setSelect(Image value) {
		this.select = value;
	}
	
	/**
	 * Getter getSelect
	 * @return The select
	 */
	public Image getSelect() {
		return select;
	}
	
	/**
	 * Setter setSelectPath
	 * @param selectPath - The path of select
	 */
	public void setSelectPath(String selectPath) {
		this.selectPath = selectPath;
	}
	
	/**
	 * Getter getSelectPath
	 * @return The selectPath
	 */
	public String getSelectPath() {
		return selectPath;
	}
	
	/**
	 * Setter setCategory
	 * @param category - The category of the pawn
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Getter getCategory
	 * @return The category
	 */
	public String getCategory() {
		return category;
	}	
	
	/**
	 * Getter getBtnConfirm
	 */
	public MenuButtonSP getBtnConfirm() {
		if(btnConfirm == null) {
			btnConfirm = new MenuButtonSP("Confirm");
		
			btnConfirm.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					if(getSelect() != null) {
						btnImg.get(getSelect()).setStyle(
				                "-fx-background-color: black;"
				        );
						btnImg.get(getSelect()).setDisable(true);
						
						User user = PlayerMenuAP.getPlayers().get(PlayerMenuAP.getPlayerSelect());
						
						String defaultImg = "/images/naimage.png";
						if(!user.getPath().equals(defaultImg)) {
							for (String key : imgs.keySet()) {
								for (Image value : imgs.get(key).values()) {
									if(value == user.getImg()) {
										btnImg.get(value).setStyle(
								                "-fx-background-color: none;"
								        );
										btnImg.get(value).setDisable(false);
									}
								}
							}
						}
						
						// Set option to user
						user.setImg(getSelect());
						user.getPawn();
						user.getPawn().setFitHeight(90);
						user.getPawn().setFitWidth(90);
						user.setPath(getSelectPath());
						
						PlayerMenuAP.getChoicePicture().get(PlayerMenuAP.getPlayerSelect()).setGraphic(user.getPawn());	
						PlayerMenuAP.getChoicePicture().get(PlayerMenuAP.getPlayerSelect()).setSelected(false);
						PlayerMenuAP.getSelector().setVisible(false);
						setSelect(null);
						setSelectPath(null);
					}
				}
			});
		}
		return btnConfirm;
	}
	
	/**
	 * Setter setBtnConfirm
	 * @param btnConfirm - The button
	 */
	public void setBtnConfirm(MenuButtonSP btnConfirm) {
		this.btnConfirm = btnConfirm;
	}
	
	/**
	 * Getter getImgs
	 * @return The imgs
	 */
	public static Map<String, Map<Integer, Image>> getImgs() {
		return imgs;
	}
}
