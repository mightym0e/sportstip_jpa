package db;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_in_tipgroups database table.
 * 
 */
@Embeddable
public class UserInTipgroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer userid;

	@Column(insertable=false, updatable=false)
	private Integer tipgroupid;

	public UserInTipgroupPK() {
	}
	public Integer getUserid() {
		return this.userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getTipgroupid() {
		return this.tipgroupid;
	}
	public void setTipgroupid(Integer tipgroupid) {
		this.tipgroupid = tipgroupid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserInTipgroupPK)) {
			return false;
		}
		UserInTipgroupPK castOther = (UserInTipgroupPK)other;
		return 
			this.userid.equals(castOther.userid)
			&& this.tipgroupid.equals(castOther.tipgroupid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userid.hashCode();
		hash = hash * prime + this.tipgroupid.hashCode();
		
		return hash;
	}
}