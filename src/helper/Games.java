package helper;


import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.Game;
import db.League;

public class Games {
	
	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");
	
	public static boolean create(String home, String guest, Integer league_id, Date game_date, Integer matchday){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(home==null||guest==null||league_id==null||game_date==null)return false;
		
		try {
			entityManager.getTransaction().begin();
			
            Game newGame = new Game();
			
			newGame.setHome(home);
			newGame.setGuest(guest);
			newGame.setLeagueId(league_id);
			newGame.setGameDate(game_date);
			newGame.setMatchday(matchday);
			
			newGame.setCreatedAt(new Date());
			newGame.setUpdatedAt(new Date());
			
			entityManager.persist(newGame);	
			entityManager.getTransaction().commit();
					
			return true;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return false;
		
	}

	@SuppressWarnings("unchecked")
	public static Tag getLeagueSelect(Integer selval, boolean isAdmin){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		ContainerTag selectLeague = select().withId("leagueSelect");
		
		try {
			Collection<League> leagues = (Collection<League>)entityManager.createQuery("SELECT l FROM League l").getResultList();
			for(League league : leagues){
				ContainerTag option = option().withValue(""+league.getLeagueid()).withText(league.getName());
				if(selval.equals(league.getLeagueid()) && !isAdmin){
					return span().withText(league.getName());
				} else if(selval.equals(league.getLeagueid())) {
					option.attr("selected", "selected");
				}
				selectLeague.with(option);
			}
					
			return selectLeague;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return selectLeague;
		
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Game> getAllGames(){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		Collection<Game> games = null;
		
		try {
			games = (Collection<Game>)entityManager.createQuery("SELECT g FROM Game g").getResultList();

			return games;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return games;
		
	}
	
	@SuppressWarnings("unchecked")
	public static ContainerTag getGamesRow(Game game, boolean isAdmin){
		ContainerTag tr = tr();
		if(isAdmin){
			tr.children.add(td().with(input().withType("text").withValue(game.getHome())));
	    	tr.children.add(td().with(input().withType("text").withValue(game.getGuest())));
	    	tr.children.add(td().with(input().withType("text").withValue(game.getPointsHome()!=null?game.getPointsHome().toString():"")));
	    	tr.children.add(td().with(input().withType("text").withValue(game.getPointsGuest()!=null?game.getPointsGuest().toString():"")));
	    	tr.children.add(td().with(Games.getLeagueSelect(game.getLeagueId(),isAdmin)));
	    	tr.children.add(td().with(input().withType("text").withClass("input date").withValue(Servlet.DATE_FORMAT.format(game.getGameDate()))));
		} else {
			tr.children.add(td().withText(game.getHome()));
	    	tr.children.add(td().withText(game.getGuest()));
	    	tr.children.add(td().withText(game.getPointsHome()!=null?game.getPointsHome().toString():""));
	    	tr.children.add(td().withText(game.getPointsGuest()!=null?game.getPointsGuest().toString():""));
	    	tr.children.add(td().with(Games.getLeagueSelect(game.getLeagueId(),isAdmin)));
	    	tr.children.add(td().withText(Servlet.DATE_FORMAT.format(game.getGameDate())));
		}
		
		return tr;
		
	}
}
