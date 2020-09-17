package es.eoi.redsocial.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.eoi.redsocial.enums.ReactionEnum;

@Entity
@Table(name = "reaction")
public class Reaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private ReactionEnum reactiontype;

	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "id_message", referencedColumnName = "id")
	private Message message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReactionEnum getReactiontype() {
		return reactiontype;
	}

	public void setReactiontype(ReactionEnum reactiontype) {
		this.reactiontype = reactiontype;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
