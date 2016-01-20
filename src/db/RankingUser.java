package db;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
public class RankingUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer userid;

	private String username;
	
	private Integer points;
	
	private Integer exact;
	
	private Integer difference;
	
	private Integer result;
	

	public RankingUser() {
	}
	
	public RankingUser(String username, Integer points) {
		
		this.username=username;
		this.points=points==null?0:points;
		
	}
	
	public RankingUser(Integer userid, String username, Integer points) {
		
		this.userid=userid;
		this.username=username;
		this.points=points==null?0:points;
		
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getExact() {
		return exact;
	}

	public void setExact(Integer exact) {
		this.exact = exact;
	}

	public Integer getDifference() {
		return difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
	
	

}