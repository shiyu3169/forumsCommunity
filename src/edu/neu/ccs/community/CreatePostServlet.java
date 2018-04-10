package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreatePost
 */
@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int threadID;
		String content;
		try {
			threadID = Integer.valueOf(request.getParameter("threadID"));
			content = request.getParameter("content");
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to sign in first");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		String author = loginManager.getSavedUsername();
		try {
			//threadID = dao.create(new Thread(forumID, title, author, isSticky, isDeleted));
			dao.create(new Post(threadID, author, content, false));
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/Home.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("thread?id=" + threadID);
	}

}
