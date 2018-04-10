/**
 * 
 */
package edu.neu.ccs.community;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zhenhuan
 *
 */
public class CookieAccessObject {
	HttpServletRequest request;
	HttpServletResponse response;
	
	/**
	 * @param request
	 * @param response
	 */
	public CookieAccessObject(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String get(String key) {
		Cookie[] cookies = this.request.getCookies();
		if (cookies == null) 
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(key))
				return cookies[i].getValue();
		}
		return null;
	}
	public void set(String key, String value, int maxAge) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAge);
		this.response.addCookie(cookie);
	}
	public void set(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		this.response.addCookie(cookie);
	}
}
