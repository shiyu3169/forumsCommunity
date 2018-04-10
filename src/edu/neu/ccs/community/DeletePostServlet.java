package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdatePost
 */
@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int threadID;
		int postID;
		Post post;
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);

		try {
			// threadID = Integer.valueOf(request.getParameter("threadID"));
			postID = Integer.valueOf(request.getParameter("postID"));
			post = dao.getPostByID(postID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to sign in first");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		User currentUser = loginManager.getCurrentUser();
		if (!currentUser.isAdministrator() && !currentUser.getUsername().equals(post.getAuthor())) {
			request.setAttribute("message", "You need to log in as an administor or the author of the post.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}	

		try {
			// threadID = dao.create(new Thread(forumID, title, author,
			// isSticky, isDeleted));
			post.setLastModificationTime(new Timestamp(System.currentTimeMillis()));
			post.setDeleted(true);
			dao.update(post);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/Home.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("thread?id=" + post.getThreadID());
	}

}
