package helper;


import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.League;

public class Leagues {
	
	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");
	
	@SuppressWarnings("unused")
	public static void test(){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		try {
			final League leagues = (League)entityManager.find(League.class, new Integer(1));
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
	}
	
	public static boolean create(String name, String sport){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(name==null||sport==null)return false;
		
		try {
			entityManager.getTransaction().begin();
			
            League newLeague = new League();
			
			newLeague.setName(name);
			newLeague.setSport(sport);
			newLeague.setCreatedAt(new Date());
			newLeague.setUpdatedAt(new Date());
			
			entityManager.persist(newLeague);	
			entityManager.getTransaction().commit();
					
			return true;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return false;
		
	}

}