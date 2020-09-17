package es.eoi.redsocial.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	private Date birthDate;
	@Column
	private LocalDate startDate;
	@Column
	private String user;
	@Column
	private String pass;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Assistance> assistances;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Event> userEvents;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Reaction> reactions;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Message> messages;

	@OneToMany(mappedBy = "userFollowing", fetch = FetchType.LAZY)
	private List<Relationship> relationshipFollowing;

	@OneToMany(mappedBy = "userFollow", fetch = FetchType.LAZY)
	private List<Relationship> relationshipFollow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public List<Assistance> getAssistances() {
		return assistances;
	}

	public void setAssistances(List<Assistance> assistances) {
		this.assistances = assistances;
	}

	public List<Event> getUserEvents() {
		return userEvents;
	}

	public void setUserEvents(List<Event> userEvents) {
		this.userEvents = userEvents;
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<Relationship> getRelationshipFollowing() {
		return relationshipFollowing;
	}

	public void setRelationshipFollowing(List<Relationship> relationshipFollowing) {
		this.relationshipFollowing = relationshipFollowing;
	}

	public List<Relationship> getRelationshipFollow() {
		return relationshipFollow;
	}

	public void setRelationshipFollow(List<Relationship> relationshipFollow) {
		this.relationshipFollow = relationshipFollow;
	}

}
