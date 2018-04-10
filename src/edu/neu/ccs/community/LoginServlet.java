package edu.neu.ccs.community;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.neu.ccs.community.LoginManager;


@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	LoginManager loginManager = new LoginManager(new CookieAccessObject(request,response),
    			new DataAccessObject());
    	
        request.setAttribute("username", loginManager.getSavedUsername());
    	loginManager.logOut();
    	try {
    		
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
    	LoginManager loginManager = new LoginManager(new CookieAccessObject(request,response),
    			new DataAccessObject());
    	
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		int loginTime = request.getParameter("remember-me") == null ? -1 : 7*24*60*60; 

		System.out.println("time: " + loginTime);
		request.setAttribute("username", username);
		if (loginManager.logIn(username, password, loginTime)) {
			//request.getRequestDispatcher("/home").forward(request, response);
			response.sendRedirect("./home");
		} else {
			request.setAttribute("message", "User name and password don't match, please try again");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
		
	}

}