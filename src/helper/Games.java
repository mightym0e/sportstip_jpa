package helper;


import static j2html.TagCreator.button;
import static j2html.TagCreator.input;
import static j2html.TagCreator.option;
import static j2html.TagCreator.select;
import static j2html.TagCreator.span;
import static j2html.TagCreator.td;
import static j2html.TagCreator.tr;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import db.Game;
import db.League;
import db.User;

public class Games {
	
	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");
	
	public static void synchronizeGames(int year){
		
		JSONParser parser = new JSONParser();
		if(year<2014||year>2016)return;
		
		try {
			
			URL url = new URL("http://www.openligadb.de/api/getmatchdata/bl1/" + year);
			URLConnection urlConnection = url.openConnection();
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			Object obj = parser.parse(bufferedReader);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			JSONArray jsonArr = (JSONArray) obj;
			for(Object objIn : jsonArr){

				String home = null;
				String guest = null;
				League league = new League();
				league.setLeagueid(2);
				Date game_date = null;
				Integer matchday = 0;
				Integer pointsHome = null;
				Integer pointsGuest = null;
				
				if(objIn instanceof JSONObject){
					JSONObject jsonObject = (JSONObject)objIn;
					if(jsonObject.containsKey("Team1")){
						Object tempObj = jsonObject.get("Team1");
						if(tempObj instanceof JSONObject){
							JSONObject tmpJsonObject = (JSONObject)tempObj;
							if(tmpJsonObject.containsKey("TeamName")){
								String teamName = new String(((String)tmpJsonObject.get("TeamName")).getBytes(), Charset.forName("UTF-8"));
								home = teamName;
							}
						}
					}
					if(jsonObject.containsKey("Team2")){
						Object tempObj = jsonObject.get("Team2");
						if(tempObj instanceof JSONObject){
							JSONObject tmpJsonObject = (JSONObject)tempObj;
							if(tmpJsonObject.containsKey("TeamName")){
								String teamName = new String(((String)tmpJsonObject.get("TeamName")).getBytes(), Charset.forName("UTF-8"));
								guest = teamName;
							}
						}
					}
					if(jsonObject.containsKey("MatchDateTime")){
						String time = new String(((String)jsonObject.get("MatchDateTime")).getBytes(), Charset.forName("UTF-8"));
						
						Date datetime = sdf.parse(time.trim());
						game_date = datetime;
					}
					if(jsonObject.containsKey("MatchResults")){
						if(jsonObject.get("MatchResults") instanceof JSONArray){
							JSONArray tempArr = (JSONArray)jsonObject.get("MatchResults");
							if(tempArr!=null && tempArr.size()!=0){
								JSONObject finalGame = (JSONObject)tempArr.get(0);
								pointsHome =  ((Long)finalGame.get("PointsTeam1")).intValue();
								pointsGuest =  ((Long)finalGame.get("PointsTeam2")).intValue();
							}
						}
					}
					
					if(home != null&&guest != null&&game_date != null){
						final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

						try {
							entityManager.getTransaction().begin();

							Game updatedGame = getGameByParameterWithOpenSession(entityManager, home, guest, game_date);
							
							if(updatedGame==null){
								updatedGame = new Game();
								updatedGame.setCreatedAt(new Date());
							}

							updatedGame.setHome(home);
							updatedGame.setGuest(guest);
							updatedGame.setLeague(league);
							updatedGame.setGameDate(game_date);
							updatedGame.setMatchday(matchday);
							
							if (pointsHome!=null&&pointsGuest!=null) {
								updatedGame.setPointsHome(pointsHome);
								updatedGame.setPointsGuest(pointsGuest);
							}
							
							updatedGame.setUpdatedAt(new Date());

							entityManager.persist(updatedGame);	
							entityManager.getTransaction().commit();

						} catch(Exception e){
							System.out.println(e.getMessage());
						} finally {
							try { entityManager.close(); } catch (final Exception exception) {}
						}
					}
					
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean create(String home, String guest, Integer league_id, Date game_date, Integer matchday){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(home==null||guest==null||league_id==null||game_date==null)return false;
		
		try {
			entityManager.getTransaction().begin();
			
            Game newGame = new Game();
            League league = new League();
			league.setLeagueid(league_id);
			
			newGame.setHome(home);
			newGame.setGuest(guest);
			newGame.setLeague(league);
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
				if(selval!=null&&selval.equals(league.getLeagueid()) && !isAdmin){
					return span().withText(league.getName());
				} else if(selval!=null&&selval.equals(league.getLeagueid())) {
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
	
	public static League getGameLeague(Integer selval){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		League league = null;
		
		try {
			league = (League)entityManager.createQuery("SELECT l FROM League l where l.leagueid=:leagueid").setParameter("leagueid", selval).getSingleResult();
					
			return league;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return league;
		
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
	public static Collection<Game> getOpenGames(){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		Collection<Game> games = null;
		
		try {
			games = (Collection<Game>)entityManager.createQuery("SELECT g FROM Game g where g.gameDate>:gameDate")
					.setParameter("gameDate", new Date()).getResultList();

			return games;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return games;
		
	}
	
	public static Game getGameByParameterWithOpenSession(final EntityManager entityManager, String home, String guest, Date game_date){

		Game game = null;
		
		try {
			game = (Game)entityManager.createQuery("SELECT g FROM Game g WHERE g.home=:home and g.guest=:guest and g.gameDate=:game_date")
					.setParameter("home", home).setParameter("guest", guest).setParameter("game_date", game_date).getSingleResult();

			return game;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
		}
		
		return game;
		
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Game> getGamesWithMissingUserTips(User user){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		Collection<Game> games = null;
		
		try {
			games = (Collection<Game>)entityManager.createQuery("SELECT g FROM Game g LEFT JOIN g.tips t where t is null or t.user.userid <> :user").setParameter("user", user.getUserid()).getResultList();

			return games;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}
		
		return games;
		
	}
	
	public static ContainerTag getGamesRow(Game game, boolean isAdmin){
		ContainerTag tr = tr();
//		if(isAdmin){
//			tr.children.add(td().with(input().withType("text").withValue(game.getHome())));
//	    	tr.children.add(td().with(input().withType("text").withValue(game.getGuest())));
//	    	tr.children.add(td().with(input().withType("text").withValue(game.getPointsHome()!=null?game.getPointsHome().toString():"")));
//	    	tr.children.add(td().with(input().withType("text").withValue(game.getPointsGuest()!=null?game.getPointsGuest().toString():"")));
//	    	tr.children.add(td().with(Games.getLeagueSelect(game.getLeagueId(),isAdmin)));
//	    	tr.children.add(
//	    					td().with(input().withType("text").withClass("input date").withValue(Servlet.DATE_FORMAT.format(game.getGameDate()))/*,
//	    					button().withClass("button").withText("delete")*/));
//		} else {
			tr.children.add(td().withText(game.getHome()));
	    	tr.children.add(td().withText(game.getGuest()));
	    	tr.children.add(td().withText(game.getPointsHome()!=null?game.getPointsHome().toString():""));
	    	tr.children.add(td().withText(game.getPointsGuest()!=null?game.getPointsGuest().toString():""));
	    	tr.children.add(td().with(Games.getLeagueSelect(game.getLeague().getLeagueid(),isAdmin)));
	    	tr.children.add(td().withText(Servlet.DATE_FORMAT.format(game.getGameDate())));
//		}
		
		return tr;
		
	}
	
}
