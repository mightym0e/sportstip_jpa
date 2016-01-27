package ajax;

import helper.Games;
import helper.Servlet;

import java.io.IOException;
import java.util.Date;

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
@WebServlet("/DoCreateGameServlet")
public class DoCreateGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateGameServlet() {
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
		}
		
		try {
			String home = request.getParameter("gameHome");
			String guest = request.getParameter("gameGuest");
			Integer league_id = Integer.parseInt(request.getParameter("leagueSelect"));
			Date game_date = Servlet.DATE_FORMAT.parse(request.getParameter("gameDate"));
			Integer matchday = Integer.parseInt(request.getParameter("gameDay"));
			
			boolean saved = Games.create(home, guest, league_id, game_date, matchday);
			
			if(saved) response.getWriter().println("Game erfolgreich gespeichert!");
			else response.getWriter().println("Game konnte nicht gespeichert werden!");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Game konnte nicht gespeichert werden!");
		}
		
		
	    
	}

}
