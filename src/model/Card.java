package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import exception.NullAnswerException;
import exception.OverTrueOrFalseAnswerException;
import exception.SameQuestionException;

public class Card {

	/**
	 * Declaration of class attributes
	 */
	private List<Question> questions;

	/**
	 * Constructor
	 */
	public Card() {
		questions = new ArrayList<>();
	}	

	/**
	 * Adding method
	 * @param q - The question we want to add
	 * @throws SameQuestionException - The exception if we want to add a question who already exist
	 * @throws OverTrueOrFalseAnswerException - The exception if we put more than 1 good answer for the question
	 * @throws NullAnswerException - The exception if an answer is null
	 */
	public void addQuestion(Question q) throws SameQuestionException, OverTrueOrFalseAnswerException, NullAnswerException {
		if(!questions.contains(q)) {
			if(q.getChoices() == null) {
				throw new NullAnswerException();
			}
			if(checkQuestion(q) == false) {
				questions.add(q.clone());
			}
			else {
				throw new OverTrueOrFalseAnswerException();
			}
		}
		else {
			throw new SameQuestionException();
		}
	}

	/**
	 * Reading method
	 * @param q - The question we want to read
	 * @return True or False, depends on whether we manage to read or not
	 * @throws NullAnswerException - The exception if an answer is null
	 */
	public Boolean checkQuestion(Question q) throws NullAnswerException{
		Map<String, Boolean> choices = q.getChoices();
		
		int nb= 0;
		if(q.getChoices() == null) {
			throw new NullAnswerException();
		}
		for(boolean b : choices.values()) {
			if(b) {
				nb++;
			}
		}
		
		if(nb == 2 || nb == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Clone
	 */
	public Card clone() {
		Card c = new Card();
		for(int i = 0; i < 6; i++) {
			try {
				c.addQuestion(getListQuestions().get(i));
			} catch (SameQuestionException e) {
				e.printStackTrace();
			} catch (OverTrueOrFalseAnswerException e) {
				e.printStackTrace();
			} catch (NullAnswerException e) {
				e.printStackTrace();
			}
		}
		return c;
	}

	/**
	 * HashCode
	 */@Override
	public int hashCode() {
		return Objects.hash(questions);
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
		Card other = (Card) obj;
		return Objects.equals(questions, other.questions);
	}

	/**
	 * ToString
	 */@Override
	public String toString() {
		return "Card [listQuestions=" + questions + "]";
	}

	/**
     * Getter ListQuestions
     * @return The list of questions
     */
	public List<Question> getListQuestions() {
		return questions;
	}
}