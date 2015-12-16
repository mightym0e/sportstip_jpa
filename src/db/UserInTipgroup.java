package db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_in_tipgroups database table.
 * 
 */
@Entity
@Table(name="user_in_tipgroups", schema = "sportstip")
@NamedQuery(name="UserInTipgroup.findAll", query="SELECT u FROM UserInTipgroup u")
public class UserInTipgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserInTipgroupPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to Tipgroup
	@ManyToOne
	@JoinColumn(name="tipgroupid", insertable = false, updatable = false)
	private Tipgroup tipgroup;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid", insertable = false, updatable = false)
	private User user;

	public UserInTipgroup() {
	}

	public UserInTipgroupPK getId() {
		return this.id;
	}

	public void setId(UserInTipgroupPK id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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