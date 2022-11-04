package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class User {

	/**
	 * Declaration of class attributes
	 */
	private String name;
	private String path;
	private Image img;
	private ImageView pawn;

	/**
	 * Constructor
	 * @param name - The name of the user
	 * @param img - The image of the user
	 * @param path - The path for the image (pawn)
	 */
	public User(String name, Image img, String path){
		this.name = name;
		setImg(img);
		setPath("/images/naimage.png");
	}

	/**
	 * Getter getName
	 * @return The name of the user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter setName
	 * @param name - The name of the user we want to setup
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter getImg
	 * @return The img of the user
	 */
	public Image getImg() {
		return img;
	}
	
	/**
	 * Setter setImg
	 * @param img - The img of the user we want to setup
	 */
	public void setImg(Image img) {
		this.img = img;
		setPawn(new ImageView(img));
	}
	
	/**
	 * Setter setPawn
	 * @param pawn - The pawn of the user we want to setup
	 */
	public void setPawn(ImageView pawn) {
		this.pawn = pawn;
	}
	
	/**
	 * Getter getPawn
	 * @return The pawn of the user we want to setup
	 */
	public ImageView getPawn() {
		return pawn;
	}	

	/**
	 * Clone
	 */
	public User clone() {
		return new User(getName(), getImg(), getPath());
	}
	
	/**
	 * Setter setPath
	 * @param path - The path for img we want to setUp
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Getter getPath
	 * @return The path for img 
	 */
	public String getPath() {
		return path;
	}
}
