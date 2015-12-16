package db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tips database table.
 * 
 */
@Entity
@Table(name="tips", schema = "sportstip")
@NamedQuery(name="Tip.findAll", query="SELECT t FROM Tip t")
public class Tip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer tipid;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="points_guest")
	private Integer pointsGuest;

	@Column(name="points_home")
	private Integer pointsHome;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to Game
	@ManyToOne
	@JoinColumn(name="gameid")
	private Game game;

	//bi-directional many-to-one association to Tipgroup
	@ManyToOne
	@JoinColumn(name="tipgroupid")
	private Tipgroup tipgroup;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public Tip() {
	}

	public Integer getTipid() {
		return this.tipid;
	}

	public void setTipid(Integer tipid) {
		this.tipid = tipid;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Tipgroup getTipgroup() {
		return this.tipgroup;
	}

	public void setTipgroup(Tipgroup tipgroup) {
		this.tipgroup = tipgroup;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}