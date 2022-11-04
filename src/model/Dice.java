package model;

import java.io.IOException;
import java.io.InputStream;

import view.LoadImgHB;

public class Dice {

	/**
	 * Declaration of class attributes
	 */
	private static InputStream is;

	/**
	 * Constructor
	 * @throws IOException
	 */
	public Dice() throws IOException {	
		setIs(LoadImgHB.load("/dice/1.png"));
	}

	/**
	 * Method who roll the dice
	 * @return The value of the dice
	 */
	public int diceRoll(){
		int diceValue = (int)(1+ 6*Math.random());
		setIs(LoadImgHB.load("/dice/" + Integer.toString(diceValue) + ".png"));
		return diceValue;
	}

	/**
	 * Setter setIs
	 * @param is - The inputstream we want for the dice
	 */
	public void setIs(InputStream is) {
		Dice.is = is;
	}
	
	/**
	 * Getter getIs
	 * @return The inputstream
	 */
	public InputStream getIs() {
		return is;
	}
}
