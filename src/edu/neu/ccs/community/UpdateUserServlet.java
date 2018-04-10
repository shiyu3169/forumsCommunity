package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/updateProfile")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataAccessObject dao = new DataAccessObject();
		CookieAccessObject cao = new CookieAccessObject(request, response);
		LoginManager loginManager = new LoginManager(cao, dao);
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to sign in first");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		String username = request.getParameter("oldUsername");
		User user;
		try {
			user = dao.getUserByName(username);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		User currentUser = loginManager.getCurrentUser();
		if (!currentUser.getUsername().equals(username)) {
			request.setAttribute("message", "You can only edit your profile.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		String newUsername = request.getParameter("username");
		String newPassword = request.getParameter("password");
		String newEmail = request.getParameter("email");
		Character newGender = request.getParameter("gender") == null || request.getParameter("gender").equals("null") ? null : request.getParameter("gender").charAt(0);
		String newBio = request.getParameter("bio");
		user.setUserName(newUsername);
		user.setPassword(newPassword);
		user.setEmail(newEmail);
		user.setGender(newGender);
		user.setAutobiography(newBio);
		try {
			dao.update(username, user);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		cao.set("username", user.getUsername());
		response.sendRedirect("profile?user=" + java.net.URLEncoder.encode(user.getUsername(), "UTF-8"));
		
	}

}
