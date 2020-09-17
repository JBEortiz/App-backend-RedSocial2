package es.eoi.redsocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.eoi.redsocial.entity.Message;
import es.eoi.redsocial.entity.User;


public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("SELECT m.user, COUNT(m) FROM Message m GROUP BY m.user ORDER BY COUNT(m) DESC")
	List<User> findBestWritterUsers();
}
