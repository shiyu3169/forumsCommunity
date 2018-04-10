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
@WebServlet("/updatePost")
public class UpdatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePostServlet() {
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
		int threadID;
		int postID;
		String content;
		Post post;
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);

		try {
			// threadID = Integer.valueOf(request.getParameter("threadID"));
			postID = Integer.valueOf(request.getParameter("postID"));
			content = request.getParameter("content");
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
		if (!currentUser.getUsername().equals(post.getAuthor())) {
			request.setAttribute("message", "You need to log in as the author of the post.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}	

		try {
			// threadID = dao.create(new Thread(forumID, title, author,
			// isSticky, isDeleted));
			post.setContent(content);
			post.setLastModificationTime((new Timestamp(System.currentTimeMillis())));
			dao.update(post);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/Home.jsp").forward(request, response);
			return;
		}
		response.sendRedirect("thread?id=" + post.getThreadID());
	}

}
