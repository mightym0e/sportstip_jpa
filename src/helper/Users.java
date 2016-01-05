package helper;


import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import db.Game;
import db.League;
import db.User;

public class Users {
	
	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");
	
	public static String checkRequest(HttpServletRequest request){
		if(request.getParameter("username")!=null){
			return create(request.getParameter("username").trim(),
					request.getParameter("email").trim(),
					request.getParameter("isadmin")!=null,
					request.getParameter("password").trim()
					);
		}
		return "";
	}
	
	public static String create(String username, String email, boolean isadmin, String password){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(username==null||email==null||password==null)return "Fehler beim Anlegen des Users";
		
		try {
			entityManager.getTransaction().begin();
			
            User newUser = new User();
			
			newUser.setUsername(username);
			newUser.setEmail(email);
			newUser.setIsadmin(isadmin);
			newUser.setPassword(generatePasswordHash(password));
			
			newUser.setCreatedAt(new Date());
			newUser.setUpdatedAt(new Date());
			
			entityManager.persist(newUser);	
			entityManager.getTransaction().commit();
					
			return "User erfolgreich angelegt";
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return "Fehler beim Anlegen des Users";
		
	}
	
	public static User getUser(String username, String password){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(username==null||password==null)throw new IllegalArgumentException();
		
		User user = null;
		
		try {
			user = (User)entityManager.createQuery("SELECT u FROM User u WHERE u.username=:username and u.password=:password")
					.setParameter("username", username)
					.setParameter("password", generatePasswordHash(password)).getSingleResult();

			return user;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return user;
		
	}
	
	private static String generatePasswordHash(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
	}
}
