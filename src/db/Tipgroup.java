package db;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tipgroups database table.
 * 
 */
@Entity
@Table(name="tipgroups", schema = "sportstip")
@NamedQuery(name="Tipgroup.findAll", query="SELECT t FROM Tipgroup t")
public class Tipgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="tipgroups_tipgroupid_seq", sequenceName="sportstip.tipgroups_tipgroupid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipgroups_tipgroupid_seq")
	private Integer tipgroupid;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	//bi-directional many-to-one association to Tip
	@OneToMany(mappedBy="tipgroup")
	private List<Tip> tips;

	//bi-directional many-to-one association to UserInTipgroup
	@OneToMany(mappedBy="tipgroup")
	private List<UserInTipgroup> userInTipgroups;

	public Tipgroup() {
	}

	public Integer getTipgroupid() {
		return this.tipgroupid;
	}

	public void setTipgroupid(Integer tipgroupid) {
		this.tipgroupid = tipgroupid;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tip> getTips() {
		return this.tips;
	}

	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}

	public Tip addTip(Tip tip) {
		getTips().add(tip);
		tip.setTipgroup(this);

		return tip;
	}

	public Tip removeTip(Tip tip) {
		getTips().remove(tip);
		tip.setTipgroup(null);

		return tip;
	}

	public List<UserInTipgroup> getUserInTipgroups() {
		return this.userInTipgroups;
	}

	public void setUserInTipgroups(List<UserInTipgroup> userInTipgroups) {
		this.userInTipgroups = userInTipgroups;
	}

	public UserInTipgroup addUserInTipgroup(UserInTipgroup userInTipgroup) {
		getUserInTipgroups().add(userInTipgroup);
		userInTipgroup.setTipgroup(this);

		return userInTipgroup;
	}

	public UserInTipgroup removeUserInTipgroup(UserInTipgroup userInTipgroup) {
		getUserInTipgroups().remove(userInTipgroup);
		userInTipgroup.setTipgroup(null);

		return userInTipgroup;
	}

}