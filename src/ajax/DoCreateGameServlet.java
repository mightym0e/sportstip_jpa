package ajax;

import helper.Games;
import helper.Leagues;
import helper.Servlet;
import j2html.attributes.Attr;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/DoCreateGameServlet")
public class DoCreateGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
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
		HttpSession s = request.getSession(false);
				
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
