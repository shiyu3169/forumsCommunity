package edu.neu.ccs.community;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/register")
// @WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("email", request.getParameter("email"));

		request.getRequestDispatcher("/Register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		Character gender = request.getParameter("gender") == null ? null : request.getParameter("gender").charAt(0);
		String autobiography = request.getParameter("autobiography");
		Date dateOfBirth = request.getParameter("dateOfBirth") == null ? null
				: Date.valueOf(request.getParameter("dateOfBirth"));
		request.setAttribute("username", username);
		request.setAttribute("password", password);
		request.setAttribute("email", email);
		// User user = new User(username, password, email,
		// request.getRemoteAddr(), now, now, null, false, false);
		User user = new User(username, password, email, request.getRemoteAddr(), false, false, gender, autobiography,
				dateOfBirth, 0);
		DataAccessObject dao = new DataAccessObject();
		try {
			dao.create(user);
			request.setAttribute("message", null);
			request.getRequestDispatcher("/login").forward(request, response);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			if (e.getMessage().contains("User_EMail")){
				request.setAttribute("message", "This email is already used, please enter a new one");
			} else {
				request.setAttribute("message", "This username is already used, please try another");
			}
			request.getRequestDispatcher("/Register.jsp").forward(request, response);
			e.printStackTrace();
		}

	}
}
