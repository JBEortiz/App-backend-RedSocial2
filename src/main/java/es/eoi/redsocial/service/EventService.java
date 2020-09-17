package es.eoi.redsocial.service;

import java.util.List;

import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;

public interface EventService {

	List<Event> findAllEvent();

	Event saveEvent(Event event);

	Event findByIdEvent(Long id);

	List<Event> findPassedEvents();

	List<User> findBestActiveUsers();

	List<Event> findActualEvents();

	Event UpdateStateEvent(Event event);
}
