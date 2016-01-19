package test;

import helper.Games;
import helper.Users;

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
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import db.Game;
import db.RankingUser;
import db.Tip;
import db.User;



public class TestDataGenerator {

	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");
	
	public static void main(String[] args){
		Collection<RankingUser> users = Users.getUserRanking();
		System.out.println("");
	}
	
	public static void generate(){
				
		JSONParser parser = new JSONParser();

		try {
			
			URL url = new URL("http://www.openligadb.de/api/getmatchdata/bl1/2015");
			URLConnection urlConnection = url.openConnection();
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			Object obj = parser.parse(bufferedReader);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			JSONArray jsonArr = (JSONArray) obj;
			for(Object objIn : jsonArr){

				String home = null;
				String guest = null;
				Integer league_id = 2;
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

							Game newGame = new Game();

							newGame.setHome(home);
							newGame.setGuest(guest);
							newGame.setLeagueId(league_id);
							newGame.setGameDate(game_date);
							newGame.setMatchday(matchday);
							
							if (pointsHome!=null&&pointsGuest!=null) {
								newGame.setPointsHome(pointsHome);
								newGame.setPointsGuest(pointsGuest);
							}
							
							newGame.setCreatedAt(new Date());
							newGame.setUpdatedAt(new Date());

							entityManager.persist(newGame);	
							entityManager.getTransaction().commit();

						} catch(Exception e){
							System.out.println(e.getMessage());
						} finally {
							try { entityManager.close(); } catch (final Exception exception) {}
						}
					}
					
				}
			}

			System.out.println("");

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
	
	public static void generateTips(){
		
		Collection<Game> games = Games.getAllGames();
		
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();
		
		entityManager.getTransaction().begin();
		
		
		for (int i = 2; i < 5; i++) {
			Random random = new Random();
			User user = new User();
			user.setUserid(i);
			int run = 100;
			for(Game game : games){
				if(run==0)break;
	            Tip newTip = new Tip();
				
				newTip.setPointsHome(random.nextInt(6));
				newTip.setPointsGuest(random.nextInt(6));
				newTip.setUser(user);
				newTip.setGame(game);
				
				newTip.setCreatedAt(new Date());
				newTip.setUpdatedAt(new Date());
				
				entityManager.persist(newTip);	
				run--;
			}
		}
		
		
		entityManager.getTransaction().commit();
	}

}
