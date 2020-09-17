package es.eoi.redsocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.eoi.redsocial.entity.Relationship;
import es.eoi.redsocial.entity.User;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
	
	@Query("SELECT r.userFollowing, COUNT(r) FROM Relationship r WHERE r.state = 'FRIEND' GROUP BY r.userFollowing ORDER BY COUNT(r) DESC")
	List<User> findBestFriendlyUsers();
}
