package db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the games database table.
 * 
 */
@Entity
@Table(name="games", schema = "sportstip")
@NamedQuery(name="Game.findAll", query="SELECT g FROM Game g")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="games_gameid_seq", sequenceName="sportstip.games_gameid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="games_gameid_seq")
	private Integer gameid;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name="game_date")
	private Date gameDate;

	private String guest;

	private String home;

	private Integer matchday;

	@Column(name="points_guest")
	private Integer pointsGuest;

	@Column(name="points_home")
	private Integer pointsHome;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to League
	@ManyToOne
	@JoinColumn(name="leagueid")
	private League league;

	//bi-directional many-to-one association to Tip
	@OneToMany(mappedBy="game")
	private List<Tip> tips;

	public Game() {
	}

	public Integer getGameid() {
		return this.gameid;
	}

	public void setGameid(Integer gameid) {
		this.gameid = gameid;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getGameDate() {
		return this.gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public String getGuest() {
		return this.guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public String getHome() {
		return this.home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public Integer getMatchday() {
		return this.matchday;
	}

	public void setMatchday(Integer matchday) {
		this.matchday = matchday;
	}

	public Integer getPointsGuest() {
		return this.pointsGuest;
	}

	public void setPointsGuest(Integer pointsGuest) {
		this.pointsGuest = pointsGuest;
	}

	public Integer getPointsHome() {
		return this.pointsHome;
	}

	public void setPointsHome(Integer pointsHome) {
		this.pointsHome = pointsHome;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public League getLeague() {
		return this.league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public List<Tip> getTips() {
		return this.tips;
	}

	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}

	public Tip addTip(Tip tip) {
		getTips().add(tip);
		tip.setGame(this);

		return tip;
	}

	public Tip removeTip(Tip tip) {
		getTips().remove(tip);
		tip.setGame(null);

		return tip;
	}

}