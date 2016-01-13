package helper;


import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import static j2html.TagCreator.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.Game;
import db.League;
import db.Tip;
import db.Tipgroup;
import db.User;

public class Tips {

	static public final EntityManagerFactory SPORTSTIP_FACTORY = Persistence.createEntityManagerFactory("sportstip");

	public static boolean create(Integer pointsHome, Integer pointsGuest, Integer userId, Integer tipgroupId, Integer gameId){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		if(pointsHome==null||pointsGuest==null||gameId==null||(userId==null&&tipgroupId==null))return false;

		try {
			User user = new User();
			user.setUserid(userId);
			Tipgroup tipgroup = new Tipgroup();
			tipgroup.setTipgroupid(tipgroupId);
			Game game = new Game();
			game.setGameid(gameId);

			entityManager.getTransaction().begin();

			Tip newTip = new Tip();

			newTip.setPointsHome(pointsHome);
			newTip.setPointsGuest(pointsGuest);
			newTip.setUser(user);
			newTip.setTipgroup(tipgroup);
			newTip.setGame(game);

			newTip.setCreatedAt(new Date());
			newTip.setUpdatedAt(new Date());

			entityManager.persist(newTip);	
			entityManager.getTransaction().commit();

			return true;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}

		return false;

	}

	public static boolean createAll(HashMap<String, String[]> tips, User user){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			for(String key : tips.keySet()){
				if(tips.get(key)==null||tips.get(key).length<2)continue;

				Game game = new Game();
				game.setGameid(Integer.parseInt(key));

				Tip newTip = new Tip();

				newTip.setPointsHome(Integer.parseInt(tips.get(key)[0]));
				newTip.setPointsGuest(Integer.parseInt(tips.get(key)[1]));
				newTip.setUser(user);
				newTip.setGame(game);

				newTip.setCreatedAt(new Date());
				newTip.setUpdatedAt(new Date());

				entityManager.persist(newTip);	
			}

			entityManager.getTransaction().commit();

		} catch(Exception e){
			entityManager.getTransaction().rollback();
			System.out.println(e.getMessage());
			return false;
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}

		return true;

	}

	@SuppressWarnings("unchecked")
	public static Collection<Tip> getTipsFromUser(User user){
		final EntityManager entityManager = SPORTSTIP_FACTORY.createEntityManager();

		Collection<Tip> tips = null;

		try {
			tips = (Collection<Tip>)entityManager.createQuery("SELECT t FROM Tip t where t.user=:user").setParameter("user", user).getResultList();

			return tips;
		} catch(Exception e){
			System.out.println(e.getMessage());
		} finally {
			try { entityManager.close(); } catch (final Exception exception) {}
		}

		return tips;

	}


	public static ContainerTag getTipGamesRow(Game game, Collection<Tip> tips){
		if(game==null)throw new IllegalArgumentException();

		Tip tip = getTipWithGame(tips, game);

		boolean editable = game.getGameDate().after(new Date());

		ContainerTag tr = tr().withId(""+game.getGameid());
		String tipHomeStr = (editable?"":"(")+(tip!=null?tip.getPointsHome():(editable?"":"kein Tip abgegeben"))+(editable?"":")");
		String tipGuestStr = (editable?"":"(")+(tip!=null?tip.getPointsGuest():(editable?"":"kein Tip abgegeben"))+(editable?"":")");
		String pointsHomeStr = game.getPointsHome()!=null?game.getPointsHome().toString():"";
		String pointsGuestStr = game.getPointsGuest()!=null?game.getPointsGuest().toString():"";

		String homeStr = pointsHomeStr+tipHomeStr;
		String guestStr = pointsGuestStr+tipGuestStr;

		if(!editable){

			tr.children.add(td().withText(game.getHome()));
			tr.children.add(td().withText(game.getGuest()));
			tr.children.add(td().withText(homeStr));
			tr.children.add(td().withText(guestStr));
			tr.children.add(td().withText(Games.getGameLeague(game.getLeagueId()).getName()));
			tr.children.add(td().withText(Servlet.DATE_FORMAT.format(game.getGameDate())));
		} else {
			tr.children.add(td().withText(game.getHome()));
			tr.children.add(td().withText(game.getGuest()));
			tr.children.add(td().with(input().withClass("points_home").withType("text").withValue(homeStr)));
			tr.children.add(td().with(input().withClass("points_guest").withType("text").withValue(guestStr)));
			tr.children.add(td().withText(Games.getGameLeague(game.getLeagueId()).getName()));
			tr.children.add(td().withText(Servlet.DATE_FORMAT.format(game.getGameDate())));
		} 

		return tr;

	}

	public static Tip getTipWithGame(Collection<Tip> tips, Game game){
		Optional<Tip> tipsFromGame = tips.stream().filter(p -> p.getGame().getGameid().equals(game.getGameid())).findFirst();
		if (tipsFromGame.isPresent()) {
			return tipsFromGame.get();
		}
		return null;
	}
}
