package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateForumServlet
 */
@WebServlet("/updateForum")
public class UpdateForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateForumServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);
		if (!loginManager.hasLoggedIn()) {
			request.setAttribute("message", "You need to log in before updating a form.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}
		;

		int forumID;
		Integer parentID;
		String forumName;
		String owner;
		String catagory;
		String description;
		boolean isVerified;
		Forum forum;
		User currentUser = loginManager.getCurrentUser();
		try {
			forumID = Integer.valueOf(request.getParameter("forumID"));
			parentID = request.getParameter("parentID") == null ? null
					: Integer.valueOf(String.valueOf(request.getAttribute("parentID")));
			forumName = request.getParameter("name");
			owner = request.getParameter("owner");
			catagory = request.getParameter("catagory");
			description = request.getParameter("description");
			isVerified = Boolean.valueOf(request.getParameter("isVerified"));
			forum = dao.getForumByID(forumID);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		if (!forum.getOwner().equals(currentUser.getUsername()) && !currentUser.isAdministrator()) {
			request.setAttribute("message",
					"You do not have the authority to update the forum. You need to log in either the owner's accout or as an administrator.");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			return;
		}

		System.out.println("parentID=" + parentID);
		System.out.println("forumName=" + forumName);
		System.out.println("owner=" + owner);
		System.out.println("catagory=" + catagory);
		System.out.println("description=" + description);
		System.out.println("isVerified=" + isVerified);
		try {
			dao.update(new Forum(forumID, parentID, forumName, owner, catagory, description, null, null, isVerified));
			response.sendRedirect("forum?id=" + forumID);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {

			response.getWriter().println(e.getLocalizedMessage());
		}
	}

}
