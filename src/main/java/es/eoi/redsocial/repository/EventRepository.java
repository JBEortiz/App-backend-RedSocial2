package es.eoi.redsocial.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByEventDate(Date dateActualli);
	
	@Query("SELECT e.user, COUNT(e) FROM Event e GROUP BY e.user ORDER BY COUNT(e) DESC")
	List<User> findBestActiveUsers();
	
	@Query("SELECT e FROM Event e WHERE e.eventDate = CURRENT_DATE")
	List<Event> actualEvents();
	
	@Query("SELECT e FROM Event e WHERE e.eventDate < CURRENT_DATE")
	List<Event> passedEvents();
}
