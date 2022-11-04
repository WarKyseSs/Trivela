package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import exception.OverUserPartyException;
import exception.SameUserNameException;

public class Party {

	/**
	 * Declaration of class attributes
	 */
	private Date date;
	private static List<User> users;
	private static String history;

	/**
	 * Constructor
	 * @param users - The list of users
	 */
	public static void newParty(List<User> users) {
		setUsers(users);
		setHistory(history);
	}
	
	/**
	 * Adding user
	 * @param u - The user we want to add
	 * @throws OverUserPartyException - The exception if the players are more than 8 in the game
	 * @throws SameUserNameException - The exception if more than 1 player has the same name 
	 */
	public void addUser(User u) throws OverUserPartyException, SameUserNameException {
		if(users.size() < 8) {
			if(!users.contains(u)) {
				users.add(u);
			}
			else {
				throw new SameUserNameException();
			}
		}
		else {
			throw new OverUserPartyException();
		}
	}

	/**
	 * Getter getDate
	 * @return The date of the game
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter setDate
	 * @param date - The date we want to setup in the game
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Getter getUsers
	 * @return The list of users
	 */
	public static List<User> getUsers() {
		return users;
	}
	
	/**
	 * Setter setUsers
	 * @param users - List of users we want to setup
	 */
	public static void setUsers(List<User> users) {
		Party.users = users;
	}

	/**
	 * Getter getHistory
	 * @return The history of the game
	 */
	public String getHistory() {
		return history;
	}

	/**
	 * Setter setHistory
	 * @param history - The history we want to setup
	 */
	public static void setHistory(String history) {
		// Initalize 
		Date d = Calendar.getInstance().getTime();
		String test = "";
		for(int i = 0; i < users.size();i++) {
			test +=  i+1 + " " + users.get(i) + " \n";
		}
		// Format for the date 
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
        Party.history= dateFormat.format(d);
        
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("historyGame.txt", true))) {
			pw.write("Game :\n" + dateFormat.format(d) + "\n" + "List Player for the game :\n" + test);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ToString
	 */@Override
	public String toString() {
		return "Party [date=" + date + ", users=" + users + "]";
	}
}
