package es.eoi.redsocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.eoi.redsocial.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.user = ?1 AND u.pass = ?2")
	User findByUserAndPass(String user, String pass);
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	User findUserUser(Long user);
	
	@Query("SELECT u FROM User u WHERE u.name = ?1")
	User findUserName(String nombre);
	
}
