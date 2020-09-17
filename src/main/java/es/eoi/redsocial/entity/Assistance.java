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

import es.eoi.redsocial.enums.AssistEnum;

@Entity
@Table(name = "assistance")
public class Assistance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private AssistEnum state;

	@ManyToOne
	@JoinColumn(name = "id_event", referencedColumnName = "ID")
	private Event event;

	@ManyToOne
	@JoinColumn
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssistEnum getState() {
		return state;
	}

	public void setState(AssistEnum state) {
		this.state = state;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
