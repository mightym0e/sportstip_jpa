package test;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.googlecode.jeneratedata.dates.DateGenerator;
import com.googlecode.jeneratedata.numbers.IntegerGenerator;
import com.googlecode.jeneratedata.people.NameGenerator;

import db.Game;



public class TestDataGenerator {

	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");
	
	public static void generate(){
		
		int i = 0;
		
		while(i<500){
			Calendar begin = Calendar.getInstance();
			begin.set(1990, 1, 1);
			Calendar end = Calendar.getInstance();
			end.set(2020, 1, 1);
			
			Random random = new Random();
			
			NameGenerator generatorName =  new NameGenerator();
			Integer id =  random.nextInt(2)+2;
			IntegerGenerator generatorNumber =  new IntegerGenerator(6000);
			DateGenerator generatorDate = new DateGenerator(begin,end);
			Integer pointsHome = random.nextInt(7);
			Integer pointsGuest = random.nextInt(7);
			
			String home = generatorName.generate() + " " + generatorNumber.generate();
			String guest = generatorName.generate() + " " + generatorNumber.generate();
			Integer league_id = id;
			Date game_date = generatorDate.generate();
			Integer matchday = 1;
			
			
			final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

			try {
				entityManager.getTransaction().begin();

				Game newGame = new Game();

				newGame.setHome(home);
				newGame.setGuest(guest);
				newGame.setLeagueId(league_id);
				newGame.setGameDate(game_date);
				newGame.setMatchday(matchday);
				
				if (game_date.before(new Date())) {
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
				i++;
				try { entityManager.close(); } catch (final Exception exception) {}
			}
		}
	}

}
