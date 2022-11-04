package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.NullAnswerException;
import exception.OverAnswerException;
import exception.OverTrueOrFalseAnswerException;
import exception.SameQuestionException;
import model.Card;
import model.Category;
import model.Question;

class TestCard {
	
	private static Card card;
	static Question q1 = new Question("Florian", Category.COMPUTER, "I'm a question ?");
	static Question q2 = new Question("Livio", Category.IDEAS, "I'm the best ?");
	static Question q3 = new Question("Nicolas", Category.HISTORY, "I'm the best ?");
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
			q1.addAnswer("I'm not the test", false);
			q1.addAnswer("I'm the test", true);
			q1.addAnswer("I'm not the test too", false);
		} catch (OverAnswerException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		try {
			q2.addAnswer("I'm not the test", false);
			q2.addAnswer("I'm the test", true);
			q2.addAnswer("I'm not the test too", true);
		} catch (OverAnswerException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		card = new Card();
		card.addQuestion(q1);
		q3.setChoices(null);
	}

	@AfterEach
	void tearDown() throws Exception {
		card = null;
	}
	
	@Test
	void testAddQuestion() throws Exception {
		assertThrows(NullAnswerException.class,
				()->card.addQuestion(q3),
				"NullAnswerException attendue") ;
		
		assertThrows(OverTrueOrFalseAnswerException.class,
				()->card.addQuestion(q2),
				"OverTrueOrFalseAnswerException attendue") ;
		
		assertThrows(SameQuestionException.class,
				()->card.addQuestion(q1),
				"SameQuestionException attendue") ;
	}
	
}
