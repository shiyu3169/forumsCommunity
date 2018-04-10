package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddFavoriteServlet
 */
@WebServlet("/removeFavorite")
public class RemoveFavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to sign in first");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		int forumID;
		String username = loginManager.getSavedUsername();
		try {
			forumID = Integer.valueOf(request.getParameter("forumID"));
		} catch (Exception e) {
			request.setAttribute("message", "Illegal parameter is received.");
			request.getRequestDispatcher("/Error.jsp").forward(request, response);
			return;
		}
		try {
			dao.removeForumFavoriteWithUser(forumID, username);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("forum?id=" + forumID);
	}

}
