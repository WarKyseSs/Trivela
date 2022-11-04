package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class Deck {
	
	/**
	 * Declaration of class attributes
	 */
	private List<Card> cards;

	/**
	 * Constructor
	 */
	public Deck() {
		cards = new ArrayList<>();
	}

	/**
	 * Adding card
	 * @param c - The card we want to add in the deck
	 */
	public void addcard(Card c) {
		if(!cards.contains(c)) {
			cards.add(c.clone());
		}
	}

	/**
	 * Clone
	 */
	public Deck clone() {
		return new Deck();
	}

	/**
	 * ToString
	 */@Override
	public String toString() {
		return "Deck [listCard=" + cards + "]";
	}

	/**
	 * Hashcode
	 */@Override
	public int hashCode() {
		return Objects.hash(cards);
	}

	/**
	 * Equals
	 */@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deck other = (Deck) obj;
		return Objects.equals(cards, other.cards);
	}	
	
	/**
	 * Getter getListCard
	 * @return The list of cards
	 */
	public List<Card> getListCard() {
		return cards;
	}
	
	/**
	 * Setter setListCard
	 * @param listCard - The list of cards we want to setup
	 */
	public void setListCard(List<Card> listCard) {
		this.cards = listCard;
	}

}