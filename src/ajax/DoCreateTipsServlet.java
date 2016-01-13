package ajax;

import helper.Games;
import helper.Leagues;
import helper.Servlet;
import helper.Tips;
import j2html.attributes.Attr;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

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
@WebServlet("/DoCreateTipsServlet")
public class DoCreateTipsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String DOC_TYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";//transitional
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateTipsServlet() {
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
		User user = s.getAttribute("user")!=null?(User)s.getAttribute("user"):null;
		if(user==null){
			response.sendRedirect("LoginServlet");
			return;
		}
		
		try {
			HashMap<String, String[]> tips = new HashMap<String, String[]>();
			
			Enumeration<String> parameterNames = request.getParameterNames();

			while (parameterNames.hasMoreElements()) {
			    String paramName = parameterNames.nextElement();

			    String[] paramValues = request.getParameterValues(paramName);
				
			    paramName = paramName.substring(0,paramName.length()-2);
			    
			    tips.put(paramName, paramValues);
			}
			
			Tips.createAll(tips, user);

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Tip konnte nicht gespeichert werden!");
		}
		
		
	    
	}

}
