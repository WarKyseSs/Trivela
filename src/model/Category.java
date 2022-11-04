package model;

import javafx.scene.paint.Color;

public enum Category {

	/**
	 * Categories
	 */
	IDEAS(Color.MAGENTA, 0),
	SCIENCE(Color.ORANGE, 1),
	PLANET(Color.GREEN, 2),
	HISTORY(Color.DEEPPINK, 3),
	LITERATURE(Color.BLUE, 4),
	COMPUTER(Color.BROWN, 5);
	
	/**
	 * Declaration of class attributes
	 */
	private Color color;
	private int id = 0;
	
	/**
	 * Constructor
	 * @param color - The color of the category
	 * @param id - The id of the category
	 */
	Category(Color color, int id) {
		this.color = color;
		this.id = id;
	}

	/**
	 * ToString
	 */@Override
	public String toString() {
		return this.name();
	}

	/**
	 * GetNbCategory
	 * @return The number of the categoriy
	 */
	public static int getNb() {
		return Category.values().length;
	}

	/**
	 * GetColor
	 * @return The color of the category
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * GetById
	 * @param id - The id of the category
	 * @return The question
	 */
	public static Category getById(int id) {
	    for(Category c : values()) {
	        if(c.getId() == id) return c;
	    }
	    return null;
	}

	/**
	 * Getter getId
	 * @return The id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter setId
	 * @param id - The id of the category
	 */
	public void setId(int id) {
		this.id = id;
	}
}