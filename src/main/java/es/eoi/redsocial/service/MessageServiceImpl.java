package es.eoi.redsocial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.eoi.redsocial.entity.Message;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Message> findAllMessages() {
		return messageRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Message findMessageById(Long id) {
		
		Message message = null;
		
		if (messageRepository.findById(id).isPresent() == true) {
			message = messageRepository.findById(id).get();
		}
		
		return message;
		
	}

	@Override
	@Transactional
	public Message saveMessage(Message message) {
		return messageRepository.save(message);

	}

	@Override
	@Transactional
	public void deleteMessageById(Long id) {
		messageRepository.deleteById(id);

	}

	@Override
	public List<User> findBestWritterUsers() {
		List<User> allUsers = messageRepository.findBestWritterUsers();
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

}
