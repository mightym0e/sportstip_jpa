package html;

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
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		String msg = Servlet.checkRequest(s, request);
		User user = s.getAttribute("user")!=null?(User)s.getAttribute("user"):null;
		
		if(user!=null){
			response.sendRedirect("ShowGamesServlet");
			return;
		}
				
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

		out.println(Servlet.getHeader("login",false).render());
	    
		out.println(body.renderOpenTag()); 
		out.println(form().withMethod("post").withAction("LoginServlet").renderOpenTag()); 
	    
	    Tag userLabel = div().withClass("formDiv").with(div().withText("User:").withClass("label"));
	    Tag passwordLabel = div().withClass("formDiv").with(div().withText("Password:").withClass("label"));
	    
	    
	    Tag userInp = div().withClass("formDiv").with(input().withId("username").withName("username").withType("text").withClass("input"));
	    Tag passwordInp = div().withClass("formDiv").with(input().withId("password").withName("password").withType("password").withClass("input"));
	    
	    out.println(h1().withText("Login"));
	    
	    out.println(div().withId("login").with(
	    		
	    		div().withId("divLeft").with(userLabel,passwordLabel),
	    		div().withId("divRight").with(userInp,passwordInp),
	    		br(),
	    		button().withId("login-btn").withText("anmelden").withType("submit")
	    		
	    									).render());
	    
	    out.println(form().renderCloseTag()); 
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
