package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteForumServlet
 */
@WebServlet("/deleteForum")
public class DeleteForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to log in before updating a form.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		;

		int forumID;

		try {
			forumID = Integer.valueOf(request.getParameter("forumID"));

		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		if (!loginManager.getCurrentUser().isAdministrator()) {
			request.setAttribute("message",
					"You need to log in as an administrator to delete a forum.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		
		try {
			dao.deleteForumByID(forumID);
			response.sendRedirect("home");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			response.getWriter().println(e.getLocalizedMessage());
		}
		
	}

}
