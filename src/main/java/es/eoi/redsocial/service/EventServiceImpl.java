package es.eoi.redsocial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	/**
	 * @TODO
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Event> findAllEvent() {
		return eventRepository.findAll();
	}

	@Override
	@Transactional
	public Event saveEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	@Transactional(readOnly = true)
	public Event findByIdEvent(Long id) {

		Event event = null;

		if (eventRepository.findById(id).isPresent() == true) {
			event = eventRepository.findById(id).get();
		}

		return event;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> findPassedEvents() {
		return eventRepository.passedEvents();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findBestActiveUsers() {
		List<User> bestUsers = new ArrayList<>();
		Integer count = 0;

		for (User user : eventRepository.findBestActiveUsers()) {
			if (count != 3) {
				bestUsers.add(user);
				count++;
			}
		}

		return bestUsers;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Event> findActualEvents() {

		List<Event> events = new ArrayList<Event>();

		for (Event event : eventRepository.actualEvents()) {
			events.add(event);
		}

		return events;

	}

	public Event UpdateStateEvent(Event event) {

		Event eventUpdate = new Event();
		eventUpdate.setId(event.getId());
		eventUpdate.setState(event.getState());

		return eventRepository.save(eventUpdate);

	}

}
