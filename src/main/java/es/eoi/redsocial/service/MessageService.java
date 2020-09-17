package es.eoi.redsocial.service;

import java.util.List;

import es.eoi.redsocial.entity.Message;
import es.eoi.redsocial.entity.User;

public interface MessageService {
	
	List<Message> findAllMessages();

	Message findMessageById(Long id);

	Message saveMessage(Message message);

	void deleteMessageById(Long id);

	List<User> findBestWritterUsers();

}
