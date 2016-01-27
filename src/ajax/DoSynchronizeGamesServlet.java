package ajax;

import helper.Games;
import helper.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.User;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/DoSynchronizeGamesServlet")
public class DoSynchronizeGamesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoSynchronizeGamesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(Servlet.CONTENT_TYPE);
		HttpSession s = request.getSession(false);
		
		User user = s.getAttribute("user")!=null?(User)s.getAttribute("user"):null;
				
		if(user==null){
			response.sendRedirect("LoginServlet");
			return;
		} else if(user.getIsadmin()==null||!user.getIsadmin()){
			response.getWriter().println("Diese Funktion darf nur von Administratoren genutzt werden!");
		} else {
			Games.synchronizeGames(2015);
			response.getWriter().println("Spiele für 2015 synchronisiert!");
		}
	    
	}

}
