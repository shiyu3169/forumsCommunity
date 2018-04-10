package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.neu.ccs.community.DataAccessObject;

/**
 * Servlet implementation class ForumServlet
 */
@WebServlet(description = "The thread list of the given forum", urlPatterns = { "/forum", "/Forum" })
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataAccessObject dao = new DataAccessObject();
		int forumID;
		try {
			forumID = Integer.valueOf(request.getParameter("id"));
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		try {
			Forum forum = dao.getForumByID(forumID);
			if (forum == null) {
				request.setAttribute("message", "forum is not found");
				request.getRequestDispatcher("/Error.jsp").forward(request, response);
				return;
			} else {
				LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);
				request.setAttribute("username", loginManager.getSavedUsername());
				request.setAttribute("forumName", forum.getForumName());
				request.setAttribute("category", forum.getCategory());
				request.setAttribute("description", forum.getDescription());
				request.setAttribute("owner", forum.getOwner());
				request.setAttribute("creationTime", forum.getCreationTime());
				request.setAttribute("lastPostTime", forum.getLastPostTime());
				List<Thread> threadList = dao.getThreadsByForumID(forumID);
				request.setAttribute("threadList", threadList);
				List<Forum> subforums = dao.getChildForums(forum.getForumID());
				if (subforums.size() == 0) {
					request.setAttribute("subforums", null);
				} else {
					request.setAttribute("subforums", dao.getChildForums(forum.getForumID()));
				}
				if (loginManager.hasLoggedIn()) {
					request.setAttribute("isAdmin", loginManager.getCurrentUser().isAdministrator());
					request.getRequestDispatcher("/Forum.jsp").forward(request, response);
				} else {
					request.setAttribute("isAdmin", false);

					// request.setAttribute("isAdmin",
					// loginManager.getCurrentUser().isAdministrator());
					// request.setAttribute("forum", forum);
					request.getRequestDispatcher("/Forum.jsp").forward(request, response);
				}
			}

		} catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
