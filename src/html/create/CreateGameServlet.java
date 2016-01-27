package html.create;

import helper.Games;
import helper.Servlet;
import j2html.attributes.Attr;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;

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
@WebServlet("/CreateGameServlet")
public class CreateGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateGameServlet() {
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
		HttpSession s = request.getSession(true);
		
		User user = s.getAttribute("user")!=null?(User)s.getAttribute("user"):null;
		
		if(user==null){
			response.sendRedirect("LoginServlet");
			return;
		}
		
		response.setContentType(Servlet.CONTENT_TYPE);
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Max-Age", 0);
	      
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\"?>");
	    out.println(Servlet.DOC_TYPE);
	    
	    Tag html = html().attr("xmlns", "http://www.w3.org/1999/xhtml").attr("xml:lang","en").attr(Attr.LANG,"en");
	    Tag body = body().withId("mainwindow");
	    
		out.println(html.renderOpenTag());

		out.println(Servlet.getHeader("game",false).render());
	    
	    out.println(body.renderOpenTag()); 
	    out.println(Servlet.getLogoutMenu(user)); 
	    out.println(Servlet.getMenu(user)); 

	    Tag homeLabel = div().withClass("formDiv").with(div().withText("Heim:").withClass("label"));
	    Tag guestLabel = div().withClass("formDiv").with(div().withText("Gast:").withClass("label"));
	    Tag leagueLabel = div().withClass("formDiv").with(div().withText("Liga:").withClass("label"));
	    Tag dateLabel = div().withClass("formDiv").with(div().withText("Datum:").withClass("label"));
	    Tag dayLabel = div().withClass("formDiv").with(div().withText("Tag:").withClass("label"));
	    
	    
	    Tag gameHome = div().withClass("formDiv").with(input().withId("gameHome").withType("text").withClass("input"));
	    Tag gameGuest = div().withClass("formDiv").with(input().withId("gameGuest").withType("text").withClass("input"));
	    Tag leagueSel = div().withClass("formDiv").with(Games.getLeagueSelect(null,true));
	    Tag gameDate = div().withClass("formDiv").with(input().withId("gameDate").withType("text").withClass("input date"));
	    Tag gameDay = div().withClass("formDiv").with(input().withId("gameDay").withType("text").withClass("input"));
	    
	    out.println(h1().withText("Spiel erstellen"));
	    
	    out.println(div().withId("divMain").with(
	    		
	    		div().withId("divLeft").with(homeLabel,guestLabel,leagueLabel,dateLabel,dayLabel),
	    		div().withId("divRight").with(gameHome,gameGuest,leagueSel,gameDate,gameDay)
	    		
	    									).render());
	    
	    out.println(button().withId("save").withText("speichern"));
	    
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
