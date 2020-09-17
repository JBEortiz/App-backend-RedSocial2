package es.eoi.redsocial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eoi.redsocial.entity.Assistance;
import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.enums.AssistEnum;
import es.eoi.redsocial.repository.AssistanceRepository;

@Service
public class AssistanceServiceImpl implements AssistanceService {

	@Autowired
	private AssistanceRepository repository;

	@Override
	@Transactional
	public void assistEvent(Event event, User user) {

		Assistance assistanceEU = repository.findByUserAndEvent(user, event);

		if (assistanceEU != null && assistanceEU.getState().equals(AssistEnum.ASSIST)) {

			throw new IllegalArgumentException("Este ya asiste");

		} else if (assistanceEU != null && assistanceEU.getState().equals(AssistEnum.NASSIST)) {

			assistanceEU.setState(AssistEnum.ASSIST);
		} else {
			assistanceEU = new Assistance();
			assistanceEU.setEvent(event);
			assistanceEU.setUser(user);
			assistanceEU.setState(AssistEnum.ASSIST);
		}
		repository.save(assistanceEU);

	}

	@Override
	@Transactional
	public void notAssistEvent(Event event, User user) {

		Assistance assistanceEU = repository.findByUserAndEvent(user, event);

		if (assistanceEU != null && assistanceEU.getState().equals(AssistEnum.NASSIST)) {

			throw new IllegalArgumentException("Este usuario ya no asistia");

		} else if (assistanceEU != null && assistanceEU.getState().equals(AssistEnum.ASSIST)) {
			assistanceEU.setState(AssistEnum.NASSIST);
		} else {
			assistanceEU.setState(AssistEnum.NASSIST);
		}
		repository.save(assistanceEU);

	}

	@Override
	@Transactional
	public List<Event> findTop3BestEvents() {
		List<Event> allEvents = repository.bestEventsFind();
		List<Event> bestEvents = new ArrayList<>();
		Integer count = 0;

		for (Event event : allEvents) {
			if (count != 3) {
				bestEvents.add(event);
				count++;
			}
		}

		return bestEvents;
	}

	@Override
	@Transactional
	public List<Event> findTop3WorstEvents() {
		List<Event> allEvents = repository.worstEventsFind();
		List<Event> bestEvents = new ArrayList<>();
		Integer count = 0;

		for (Event event : allEvents) {
			if (count != 3) {
				bestEvents.add(event);
				count++;
			}
		}

		return bestEvents;
	}

	public List<User> findBestAssistanceUsers() {
		List<User> allUsers = repository.bestAssistanceUsersFind();
		List<User> bestUsers = new ArrayList<>();
		Integer count = 0;

		for (User user : allUsers) {
			if (count != 3) {
				bestUsers.add(user);
				count++;
			}
		}

		return bestUsers;
	}
	
	public List<Assistance> findAllAssistances(){
		
		return repository.findAll();
	}
	
}
