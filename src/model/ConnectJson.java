package model;

public class ConnectJson {

	/**
	 * Declaration of class attributes
	 */
	private static Deck deck;	

	/**
	 * The method who call the json
	 */
	public static void jsonCall() {	
		// Contains all cards to use in game
		if (deck == null) { deck = new Deck(); }
		Read.readFile();
	}
	
	/**
	 * Getter getDeck
	 * @return The deck
	 */
	public static Deck getDeck() {
		return deck;
	}
	
	/**
	 * Setter setDeck
	 * @param deck - The deck we want to setup
	 */
	public static void setDeck(Deck deck) {
		ConnectJson.deck = deck;
	}
}