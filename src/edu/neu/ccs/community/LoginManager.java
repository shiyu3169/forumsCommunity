/**
 * 
 */
package edu.neu.ccs.community;

import java.sql.SQLException;

/**
 * @author Zhenhuan
 *
 */
public class LoginManager {
	/**
	 * @param cao
	 * @param dao
	 */
	public LoginManager(CookieAccessObject cao, DataAccessObject dao) {
		super();
		this.cao = cao;
		this.dao = dao;
	}
	CookieAccessObject cao;
	DataAccessObject dao;
	public boolean hasLoggedIn() {
		String username = this.cao.get("username");
		String password = this.cao.get("password");
		if ((username == null) || (password == null)
				|| username.equals("") || password.equals("")) 
			return false;
		try {
			return this.dao.userValidation(username, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			return false;
		}
	}
	public void logOut() {
		this.cao.set("username", null, 0);
		this.cao.set("password", null, 0);
	}
	public boolean logIn(String username, String password, int maxAge) {
		try {
			if (this.dao.userValidation(username, password)) {
				this.cao.set("username", username, maxAge);
				this.cao.set("password", password, maxAge);
				return true;
			} else {
				return false;
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			return false;
		}
	}
	public String getSavedUsername() {
		return this.cao.get("username");
	}
	public User getCurrentUser() {
		if (!this.hasLoggedIn())
			return null;
		try {
			return dao.getUserByName(this.getSavedUsername());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
