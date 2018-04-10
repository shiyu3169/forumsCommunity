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
@WebServlet("/banUser")
public class BanUserServlet extends HttpServlet {
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
		String username = request.getParameter("username");
		User user;
		try {
			user = dao.getUserByName(username);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		User currentUser = loginManager.getCurrentUser();
		if (!currentUser.isAdministrator()) {
			request.setAttribute("message", "Only administrator can ban users.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		user.setBanned(true);
		try {
			dao.update(username, user);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		response.sendRedirect("profile?user=" + java.net.URLEncoder.encode(user.getUsername(), "UTF-8"));
		
	}

}
