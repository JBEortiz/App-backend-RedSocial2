package es.eoi.redsocial.service;

import java.util.List;

import es.eoi.redsocial.entity.User;

public interface UserService {

	List<User> findAllUser();

	User saveUser(User user);

	User findByIdUser(Long id);

	User updateUser(User user);

	User findByUserAndPass(String user, String pass);

	User findUserUser(Long user);
	
	User findUserName(String nombre);

}
