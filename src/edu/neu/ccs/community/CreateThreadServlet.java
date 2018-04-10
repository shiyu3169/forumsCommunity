package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateForumServlet
 */
@WebServlet("/createThread")
public class CreateThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateThreadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to sign in first");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		int forumID;
		String title;
		String author = loginManager.getSavedUsername();
		boolean isSticky = false;
		boolean isDeleted = false;
		int threadID;
		String content;
		try {
			forumID = Integer.valueOf(request.getParameter("forumID"));
			title = request.getParameter("title");
			content = request.getParameter("content");
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		try {
			threadID = dao.create(new Thread(forumID, title, author, isSticky, isDeleted));
			dao.create(new Post(threadID, author, content, isDeleted));
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/Home.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("thread?id=" + threadID);
	}

}
