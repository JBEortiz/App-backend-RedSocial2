package es.eoi.redsocial.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.eoi.redsocial.enums.RelationEnum;

@Entity
@Table(name = "relationship")
public class Relationship implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private RelationEnum state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_userFollowing", referencedColumnName = "ID")
	private User userFollowing;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_userFollow", referencedColumnName = "ID")
	private User userFollow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RelationEnum getState() {
		return state;
	}

	public void setState(RelationEnum state) {
		this.state = state;
	}

	public User getUserFollowing() {
		return userFollowing;
	}

	public void setUserFollowing(User userFollowing) {
		this.userFollowing = userFollowing;
	}

	public User getUserFollow() {
		return userFollow;
	}

	public void setUserFollow(User userFollow) {
		this.userFollow = userFollow;
	}

}
