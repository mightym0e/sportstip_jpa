package html.show;

import helper.Games;
import helper.Leagues;
import helper.Servlet;
import j2html.attributes.Attr;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.Game;
import db.League;
import db.User;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/ShowLeaguesServlet")
public class ShowLeaguesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowLeaguesServlet() {
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
		
		out.println(Servlet.getHeader("league",true).render());
	    
	    out.println(body.renderOpenTag()); 
	    
	    out.println(Servlet.getLogoutMenu(user)); 
	    out.println(Servlet.getMenu(user)); 

	    Tag thead = thead().with(tr().with(th().withText("Id"),th().withText("Name"),th().withText("Sport")));
	    
	    ContainerTag tbody = tbody();
	    
	    Collection<League> leagues = Leagues.getAllLeagues();
	    
	    for(League league : leagues){
	    	tbody.children.add(Leagues.getLeaguesRow(league,user.getIsadmin()));
	    }
	    
	    ContainerTag table = table().withClass("hover stripe").withId("leagues_table").with(
	    			thead,
	    			tbody
	    		);
	    
	    out.println(h1().withText("Ligen"));
	    
	    out.println(br().render() + br().render()); 
	    
	    out.println(div().withId("divMain").with(
	    		
	    		table
	    		
	    									).render());
	    
	    
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
