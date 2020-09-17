package es.eoi.redsocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.eoi.redsocial.entity.Assistance;
import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;

public interface AssistanceRepository extends JpaRepository<Assistance, Long> {

	Assistance findByUserAndEvent(User user, Event event);

	@Query("SELECT event, COUNT(a) FROM  Assistance a WHERE a.state = 'ASSIST'  GROUP BY a.event ORDER BY COUNT(a) DESC")
	List<Event> bestEventsFind();

	@Query("SELECT event, COUNT(a) FROM  Assistance a WHERE a.state = 'ASSIST' GROUP BY a.event ORDER BY COUNT(a) ASC")
	List<Event> worstEventsFind();

	@Query("SELECT a.user, COUNT(a) FROM  Assistance a WHERE a.state = 'ASSIST' GROUP BY a.user ORDER BY COUNT(a) DESC")
	List<User> bestAssistanceUsersFind();

}
