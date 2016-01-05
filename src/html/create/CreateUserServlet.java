package html.create;

import helper.Games;
import helper.Servlet;
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
@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
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
		
		String message = Users.checkRequest(request);
		
		System.out.println(message);
		
		HttpSession s = request.getSession(true);
				
		User user = s.getAttribute("user")!=null?(User)s.getAttribute("user"):null;
		
		if(user==null){
			response.sendRedirect("LoginServlet");
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

		out.println(Servlet.getHeader("user",false).render());
	    
	    out.println(body.renderOpenTag()); 
	    out.println(Servlet.getMenu(user)); 
	    out.println(form().withAction("CreateUserServlet").renderOpenTag());
	    
	    Tag nameLabel = div().withClass("formDiv").with(div().withText("User Name:").withClass("label"));
	    Tag emailtLabel = div().withClass("formDiv").with(div().withText("E-Mail:").withClass("label"));
	    Tag isAdminLabel = div().withClass("formDiv").with(div().withText("Admin:").withClass("label"));
	    Tag passwordLabel = div().withClass("formDiv").with(div().withText("Password:").withClass("label"));
	    
	    
	    Tag nameInp = div().withClass("formDiv").with(input().withId("username").withName("username").withType("text").withClass("input"));
	    Tag emailInp = div().withClass("formDiv").with(input().withId("email").withName("email").withType("email").withClass("input"));
	    Tag isAdminInp = div().withClass("formDiv").with(input().withId("isadmin").withName("isadmin").withType("checkbox").withClass("checkbox"));
	    Tag passwordInp = div().withClass("formDiv").with(input().withId("password").withName("password").withType("password").withClass("input"));
	    
	    out.println(h1().withText("Liga erstellen"));
	    
	    out.println(div().withId("divMain").with(
	    		
	    		div().withId("divLeft").with(nameLabel,emailtLabel,isAdminLabel,passwordLabel),
	    		div().withId("divRight").with(nameInp,emailInp,isAdminInp,passwordInp)
	    		
	    									).render());
	    
	    out.println(button().withId("save").withText("speichern").withType("submit"));
	    
	    out.println(form().renderCloseTag()); 
	    out.println(body.renderCloseTag());
	    out.println(html.renderCloseTag());
	    
	}

}
