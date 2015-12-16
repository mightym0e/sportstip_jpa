package html;

import helper.Leagues;
import j2html.attributes.Attr;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/CreateLeagueServlet")
public class CreateLeagueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateLeagueServlet() {
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
				
		response.setContentType(CONTENT_TYPE);
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Max-Age", 0);
	      
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\"?>");
	    out.println(DOC_TYPE);
	    
	    Tag html = html().attr("xmlns", "http://www.w3.org/1999/xhtml").attr("xml:lang","en").attr(Attr.LANG,"en");
	    Tag body = body().withId("mainwindow");
	    
		out.println(html.renderOpenTag());
		out.println(head().renderOpenTag());
		
		Leagues.test();
		
		out.println(meta().attr(Attr.HTTP_EQUIV, "X-UA-Compatible").attr(Attr.CONTENT, "chrome=1").render());
		out.println(meta().attr(Attr.HTTP_EQUIV, "cache-control").attr(Attr.CONTENT, "no-cache").render());
		out.println(meta().attr(Attr.HTTP_EQUIV, "expires").attr(Attr.CONTENT, "0").render());
		out.println(meta().attr(Attr.HTTP_EQUIV, "pragma").attr(Attr.CONTENT, "no-cache").render());
		out.println(link().withRel("stylesheet").withHref("https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css").render());
		out.println(link().withRel("stylesheet").withHref("css/main.css").render());
		out.println(script().withType("text/javascript").withSrc("http://code.jquery.com/jquery-latest.js").render());
		out.println(script().withType("text/javascript").withSrc("https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js").render());
		out.println(script().withType("text/javascript").withSrc("js/main.js").render());
		
	    out.println(head().renderCloseTag());
	    
	    out.println(body.renderOpenTag()); 
	    
	    Tag name = div().withClass("formDiv").with(div().withText("Name:").withClass("label"));
	    Tag sport = div().withClass("formDiv").with(div().withText("Sportart:").withClass("label"));
	    
	    Tag nameInp = div().withClass("formDiv").with(input().withId("leagueName").withType("text").withClass("input"));
	    Tag sportInp = div().withClass("formDiv").with(input().withId("leagueSport").withType("text").withClass("input"));
	    
	    out.println(h1().withText("Liga erstellen"));
	    
	    out.println(div().withId("divMain").with(
	    		
	    		div().withId("divLeft").with(name,sport),
	    		div().withId("divRight").with(nameInp,sportInp)
	    		
	    									).render());
	    
	    out.println(button().withId("save").withText("speichern"));
	    
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
