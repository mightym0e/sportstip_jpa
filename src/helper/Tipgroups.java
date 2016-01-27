package helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import db.Tipgroup;
import db.User;

public class Tipgroups {

	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");

	public static String checkRequest(HttpServletRequest request, User user){
		if(request.getParameter("tipgroup")!=null){
			return create(request.getParameter("tipgroup").trim(),user);
		}
		return "";
	}
	
	public static String create(String name, User user){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(name==null||user==null)return "Fehler beim Anlegen der Tipgruppe, Name oder User nicht vorhanden";

		try {
			Tipgroup tipgroup = new Tipgroup();
			tipgroup.setName(name);
			tipgroup.setUser(user);
			
			entityManager.persist(tipgroup);	
			entityManager.getTransaction().commit();

			return "Tipgruppe erfolgreich angelegt";
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return "Fehler beim Anlegen der Tipgruppe";

	}
}
