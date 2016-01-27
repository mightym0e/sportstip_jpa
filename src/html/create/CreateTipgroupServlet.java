package html.create;

import helper.Servlet;
import helper.Tipgroups;
import helper.Users;
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
@WebServlet("/CreateTipgroupServlet")
public class CreateTipgroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTipgroupServlet() {
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
		
		String message = Tipgroups.checkRequest(request, user);
		
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

		out.println(Servlet.getHeader("tipgroup",false).render());
	    
	    out.println(body.renderOpenTag()); 
	    out.println(Servlet.getLogoutMenu(user)); 
	    out.println(Servlet.getMenu(user)); 

	    out.println(form().withAction("CreateTipgroupServlet").renderOpenTag());
	    
	    Tag nameLabel = div().withClass("formDiv").with(div().withText("Tipgruppe Name:").withClass("label"));
	    
	    Tag nameInp = div().withClass("formDiv").with(input().withId("tipgroup").withName("tipgroup").withType("text").withClass("input"));
	    
	    out.println(h1().withText("Tipgruppe erstellen"));
	    
	    out.println(div().withId("divMain").with(
	    		
	    		div().withId("divLeft").with(nameLabel),
	    		div().withId("divRight").with(nameInp)
	    		
	    									).render());
	    
	    out.println(button().withId("save").withText("speichern").withType("submit"));
	    
	    out.println(form().renderCloseTag()); 
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
