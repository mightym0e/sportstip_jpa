package html.show;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.html;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tbody;
import static j2html.TagCreator.th;
import static j2html.TagCreator.thead;
import static j2html.TagCreator.tr;
import helper.Servlet;
import helper.Users;
import j2html.attributes.Attr;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.RankingUser;
import db.User;

/**
 * Servlet implementation class ScatterServlet
 */
@WebServlet("/ShowRankingServlet")
public class ShowRankingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRankingServlet() {
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
		
		HashMap<String, String> params = Servlet.checkFilterRequest(request);
		
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
		
		out.println(Servlet.getHeader("ranking",true).render());
	    
	    out.println(body.renderOpenTag()); 
	    
	    out.println(Servlet.getLogoutMenu(user)); 
	    
	    out.println(Servlet.getMenu(user)); 
	    
	    Tag thead = thead().with(tr().with(th().withText("Platz"),th().withText("Benutzer"),th().withText("Punkte"),th().withText("Exakte Tips"),th().withText("Richtiger Unterschied"),th().withText("Richtiges Ergebnis")));
	    
	    ContainerTag tbody = tbody();
	    Collection<RankingUser> rankingUsers = null;
	    
	    rankingUsers = Users.getUserRanking();
	    
	    int position=1;
	    for(RankingUser rankingUser : rankingUsers){
	    	tbody.children.add(Users.getUserRankingRow(rankingUser, position, rankingUser.getUserid().equals(user.getUserid())));
	    	position++;
	    }
	    
	    
	    
	    ContainerTag table = table().withClass("hover stripe").withId("ranking_table").with(
	    			thead,
	    			tbody
	    		);
	    
	    out.println(h1().withText("Ranking"));
	    
	    out.println(div().withId("divMain").with(
	    		
	    		table
	    		
	    									).render());
	    
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
