package es.eoi.redsocial.service;

import java.util.List;

import es.eoi.redsocial.entity.Assistance;
import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;

public interface AssistanceService {

	void assistEvent(Event event, User user);

	void notAssistEvent(Event event, User user);

	public List<Event> findTop3BestEvents();

	public List<Event> findTop3WorstEvents();

	public List<User> findBestAssistanceUsers();
	
	public List<Assistance> findAllAssistances();
}
