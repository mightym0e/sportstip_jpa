package db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the leagues database table.
 * 
 */
@Entity
@Table(name="leagues", schema = "sportstip")
@NamedQuery(name="League.findAll", query="SELECT l FROM League l")
public class League implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="leagues_leagueid_seq", sequenceName="sportstip.leagues_leagueid_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="leagues_leagueid_seq")
	private Integer leagueid;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	private String name;

	private String sport;

	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;

	public League() {
	}

	public Integer getLeagueid() {
		return this.leagueid;
	}

	public void setLeagueid(Integer leagueid) {
		this.leagueid = leagueid;
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

	public String getSport() {
		return this.sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}