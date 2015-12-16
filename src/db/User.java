package db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users", schema = "sportstip")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer userid;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	private String email;

	private Boolean isadmin;

	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;

	private String username;

	//bi-directional many-to-one association to Tipgroup
	@OneToMany(mappedBy="user")
	private List<Tipgroup> tipgroups;

	//bi-directional many-to-one association to Tip
	@OneToMany(mappedBy="user")
	private List<Tip> tips;

	//bi-directional many-to-one association to UserInTipgroup
	@OneToMany(mappedBy="user")
	private List<UserInTipgroup> userInTipgroups;

	public User() {
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsadmin() {
		return this.isadmin;
	}

	public void setIsadmin(Boolean isadmin) {
		this.isadmin = isadmin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Tipgroup> getTipgroups() {
		return this.tipgroups;
	}

	public void setTipgroups(List<Tipgroup> tipgroups) {
		this.tipgroups = tipgroups;
	}

	public Tipgroup addTipgroup(Tipgroup tipgroup) {
		getTipgroups().add(tipgroup);
		tipgroup.setUser(this);

		return tipgroup;
	}

	public Tipgroup removeTipgroup(Tipgroup tipgroup) {
		getTipgroups().remove(tipgroup);
		tipgroup.setUser(null);

		return tipgroup;
	}

	public List<Tip> getTips() {
		return this.tips;
	}

	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}

	public Tip addTip(Tip tip) {
		getTips().add(tip);
		tip.setUser(this);

		return tip;
	}

	public Tip removeTip(Tip tip) {
		getTips().remove(tip);
		tip.setUser(null);

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
		userInTipgroup.setUser(this);

		return userInTipgroup;
	}

	public UserInTipgroup removeUserInTipgroup(UserInTipgroup userInTipgroup) {
		getUserInTipgroups().remove(userInTipgroup);
		userInTipgroup.setUser(null);

		return userInTipgroup;
	}

}