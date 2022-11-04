package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import exception.OverAnswerException;
import exception.OverTrueOrFalseAnswerException;

public class Question {

	/**
	 * Declaration of class attributes
	 */
	private String author;
	private Category category;
	private String interrogation;
	private Map<String, Boolean> choices;
	static List<Question> questionsList = new ArrayList<>();
	

	/**
	 * Constructor
	 * @param author - The author of the question
	 * @param category - The author of the category
	 * @param interrogation - The question
	 */
	public Question(String author, Category category, String interrogation) {
		this.author = author;
		this.category = category;
		this.interrogation = interrogation;
		choices = new HashMap<>();
	}
	
	/**
	 * Adding answer
	 * @param interrogation - The question
	 * @param val - The value (propositions)
	 * @throws OverAnswerException
	 */
	public void addAnswer(String interrogation, boolean val) throws OverAnswerException {
		if (choices.size() < 3) {
			choices.put(interrogation, val);
		} else {
			throw new OverAnswerException();
		}
	}
	
	/**
	 * Check
	 * @throws OverTrueOrFalseAnswerException
	 */
	public void check() throws OverTrueOrFalseAnswerException {
		// Map<String, Boolean> choices = q.getChoices();
		
		int nb = 0;
		for(boolean b : choices.values())
			if(b) nb++;
		
		if(nb == 2 || nb == 0) throw new OverTrueOrFalseAnswerException();
	}
	
	/**
	 * Hashcode
	 */@Override
	public int hashCode() {
		return Objects.hash(category, interrogation);
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
		Question other = (Question) obj;
		return category == other.category && Objects.equals(interrogation, other.interrogation);
	}

	/**
	 * Clone
	 */
	public Question clone() {
		Question result = new Question(author, category, interrogation) ;
		result.setChoices(choices);
		questionsList.add(result);
		return result;
	}
	
	/**
	 * ToString
	 */@Override
	public String toString() {
		return "Question [author=" + author + ", category=" + category + ", interrogation=" + interrogation
				+ ", choices=" + choices + "]";
	}
		
	/**
	 * Getter getAuthor
	 * @return The author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Setter setAuthor
	 * @param author - The author of the question
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Getter getCategory
	 * @return The category
	 */
	public Category getCategory() {
		return category;
	}	
	
	/**
	 * Setter setCategory
	 * @param category - The category of the question
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * Getter getInterrogation
	 * @return The interrogation
	 */
	public String getInterrogation() {
		return interrogation;
	}	
	
	/**
	 * Setter setInterrogation
	 * @param interrogation - The question
	 */
	public void setInterrogation(String interrogation) {
		this.interrogation = interrogation;
	}
	
	/**
	 * Getter getChoices
	 * @return Choices
	 */
	public Map<String, Boolean> getChoices() {
		return choices;
	}
	
	/**
	 * Setter setChoices
	 * @param choices - Choices
	 */
	public void setChoices(Map<String, Boolean> choices) {
		this.choices = choices;
	}
	
	/**
	 * Getter getQuestionsList
	 * @return The list of questions
	 */
	public static List<Question> getQuestionsList() {
		return questionsList;
	}
}