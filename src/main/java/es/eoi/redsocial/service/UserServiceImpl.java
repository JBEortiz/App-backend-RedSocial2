package es.eoi.redsocial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByIdUser(Long id) {
		User user = null;
		if (userRepository.findById(id).isPresent() == true) {
			user = userRepository.findById(id).get();
		}
		return user;
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		User userUpdate = new User();
		userUpdate.setSurname(user.getSurname());
		return userRepository.save(userUpdate);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUserAndPass(String user, String pass) {
		User userIdPass = userRepository.findByUserAndPass(user, pass);
		return userIdPass;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserUser(Long user) {
		User userInvited = null;
		if (userRepository.findById(user).isPresent() == true) {
			userInvited = userRepository.findById(user).get();
		}
		return userInvited;
	}

	@Override
	@Transactional(readOnly = true)
	public User findUserName(String nombre) {
	 return userRepository.findUserName(nombre);
	}

}