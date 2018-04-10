package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataAccessObject dao = new DataAccessObject();
		LoginManager loginManager = new LoginManager(new CookieAccessObject(request, response), dao);

		String username = request.getParameter("user");
		User user;
		try {
			user = dao.getUserByName(username);
			if (user == null) {
				throw new NoSuchElementException("The user \""+username+"\" does not exist.");
			}
			if (user.isBanned()) {
				throw new RuntimeException("The user has been banned.");
			}
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/Error.jsp").forward(request, response);
			return;
		}
		System.out.println(username);
		request.setAttribute("owner", username);
		request.setAttribute("username", loginManager.getSavedUsername());
		request.setAttribute("isAdmin", loginManager.hasLoggedIn() && loginManager.getCurrentUser().isAdministrator());
		request.setAttribute("email", user.getEmail());
		request.setAttribute("creationTime", user.getRegisterationTime());
		request.setAttribute("lastLoginTime", user.getLastLoginTime());
		request.setAttribute("lastPostTime", user.getLastPostTime());
		request.setAttribute("gender", user.getGender() == null ? "Unknown" : user.getGender());
		request.setAttribute("birthday", user.getDateOfBirth() == null ? "Unknown" : user.getDateOfBirth());
		request.setAttribute("bio", user.getAutobiography() == null ? "" : user.getAutobiography());
		
		try {
			request.setAttribute("favoriteForums", dao.getFavoriteForumsByUsername(username));
			request.setAttribute("recentPostedThreads", dao.getRecentThreadsUpdatedByUser(username));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/Profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
