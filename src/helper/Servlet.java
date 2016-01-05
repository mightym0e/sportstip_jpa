package helper;

import static j2html.TagCreator.*;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import db.User;
import j2html.attributes.Attr;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;


public class Servlet {
	
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	public static Tag getHeader(String controller, boolean withDataTable){
		ContainerTag head = head().with(
				meta().attr(Attr.HTTP_EQUIV, "X-UA-Compatible").attr(Attr.CONTENT, "chrome=1"),
				meta().attr(Attr.HTTP_EQUIV, "cache-control").attr(Attr.CONTENT, "no-cache"),
				meta().attr(Attr.HTTP_EQUIV, "expires").attr(Attr.CONTENT, "0"),
				meta().attr(Attr.HTTP_EQUIV, "pragma").attr(Attr.CONTENT, "no-cache"),
				link().withRel("stylesheet").withHref("https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css"),
				script().withType("text/javascript").withSrc("http://code.jquery.com/jquery-latest.js"),
				script().withType("text/javascript").withSrc("https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"),
				script().withType("text/javascript").withSrc("js/main.js")
				);
		
		if(withDataTable){
			head.with(link().withRel("stylesheet").withHref("https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css"));
			head.with(script().withType("text/javascript").withSrc("https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"));
		}
		
		head.with(
				link().withRel("stylesheet").withHref("css/main.css"),
				script().withType("text/javascript").withSrc("js/"+controller+".js")
				);
		
		return head;
		
	}
	
	public static Tag getMenu(User user){
		
		if(user==null)throw new IllegalArgumentException();
		
		ContainerTag logout = div().withId("logout").with(
				span().withText("Eingeloggt als "+user.getUsername()+", "),
				button().attr("onClick", "window.location.href='LogoutServlet'").withText("logout"));
		
		return logout;
	}
	
	public static void login(HttpSession session, String username, String password){
		User user = Users.getUser(username, password);
		session.setAttribute("user", user);
	}
	
	public static void logout(HttpSession session){
		session.invalidate();
	}
	
	public static String checkRequest(HttpSession session, HttpServletRequest request){
		if(request.getParameter("username")!=null){
			login(session,
					request.getParameter("username").trim(),
					request.getParameter("password").trim()
					);
		}
		return "";
	}
	
}
