package model;

public class Admin {

	/**
	 * Declaration of class attributes
	 */
	private String log;
	private String password;

	/**
	 * Constructor
	 * @param log - The log of the admin
	 * @param password - The password of the admin
	 */
	public Admin(String log, String password) {
		this.password = log;
		this.password = password;
	}

	/**
	 * Getter of Log
	 * @return The log of the admin
	 */
	public String getLog() {
		return log;
	}
	
	/**
	 * Setter of Log
	 * @param log - The log of the admin we want to setup
	 */
	public void setLog(String log) {
		this.log = log;
	}
	
	/**
	 * Getter of password
	 * @return The password of the admin
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter of password
	 * @param password - The password of the admin we want to setup
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	/**
	 * ToString
	 */@Override
	public String toString() {
		return super.toString()+"Admin [password=" + password + "]";
	}
	
	/***
	 * Clone
	 */
	public Admin clone() {
		return new Admin(log, password);
	}
}
