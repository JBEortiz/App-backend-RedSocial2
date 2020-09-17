package es.eoi.redsocial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.redsocial.dto.MessageAllDTO;
import es.eoi.redsocial.dto.ReactionDTO;
import es.eoi.redsocial.dto.ReactionFullDTO;
import es.eoi.redsocial.dto.RelationshipFullDTO;
import es.eoi.redsocial.entity.Message;
import es.eoi.redsocial.entity.Reaction;
import es.eoi.redsocial.entity.Relationship;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.service.MessageService;
import es.eoi.redsocial.service.ReactionService;
import es.eoi.redsocial.service.UserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReactionService reactionService;

	private ModelMapper modelMapper = new ModelMapper();

	private Map<String, Object> response = new HashMap<String, Object>();

	@GetMapping
	public ResponseEntity<?> showAllMessages() {
		response.clear();
		List<Message> messages = new ArrayList<Message>();
		List<MessageAllDTO> messagesAllDTO = new ArrayList<MessageAllDTO>();
		try {
			messages = messageService.findAllMessages();
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (messages.isEmpty()) {
			response.put("mensaje", "No hay ningun mensaje");
			return ResponseEntity.ok(response);
		}
		for (Message message : messages) {
			messagesAllDTO.add(modelMapper.map(message, MessageAllDTO.class));
		}
		return ResponseEntity.ok(messagesAllDTO);
	}

	@PostMapping
	public ResponseEntity<?> createMessage(@RequestBody MessageAllDTO messageDTO, @RequestParam("id") Long id) {

		response.clear();
		User user;

		try {

			user = userService.findByIdUser(id);

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (user == null) {

			response.put("mensaje",
					"El usuario con la ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

		if (messageDTO.getContent().isEmpty()) {

			response.put("mensaje", "El contenido del mensaje no puede estar vacío");
			return ResponseEntity.ok(response);

		}

		Message message = modelMapper.map(messageDTO, Message.class);
		message.setUser(user);
		Message messageCreate = messageService.saveMessage(message);
		messageDTO = modelMapper.map(messageCreate, MessageAllDTO.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> showMessageById(@PathVariable Long id) {

		response.clear();
		Message messageById;
		MessageAllDTO messageAllDTO;

		try {

			messageById = messageService.findMessageById(id);

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (messageById == null) {

			response.put("mensaje",
					"El mensaje con la ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

		messageAllDTO = modelMapper.map(messageById, MessageAllDTO.class);

		return ResponseEntity.ok(messageAllDTO);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMessage(@PathVariable Long id) {

		response.clear();

		try {

			messageService.deleteMessageById(id);

		} catch (DataAccessException exception) {

			response.put("mensaje",
					"El mensaje con la ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		response.put("mensaje", "El mensaje ha sido eliminado correctamente");

		return ResponseEntity.ok(response);

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> showMessageByUserId(@PathVariable Long id) {

		response.clear();
		User user;
		List<MessageAllDTO> listMessages = new ArrayList<MessageAllDTO>();

		try {

			user = userService.findByIdUser(id);

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (user == null) {

			response.put("mensaje",
					"El usuario con la ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

		List<Message> messages = user.getMessages();

		if (messages.isEmpty()) {

			response.put("mensaje", "Este usuario no ha escrito ningun mensaje");
			return ResponseEntity.ok(response);

		}

		for (Message message : messages) {
			listMessages.add(modelMapper.map(message, MessageAllDTO.class));
		}

		return ResponseEntity.ok(listMessages);

	}

	@GetMapping("/{id}/reactions")
	public ResponseEntity<?> showReactionByMessageId(@PathVariable Long id) {

		response.clear();
		Message message;
		List<ReactionFullDTO> listReactions = new ArrayList<ReactionFullDTO>();

		try {

			message = messageService.findMessageById(id);

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (message == null) {

			response.put("mensaje",
					"El mensaje con la ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

		List<Reaction> reactions = message.getReactionMessage();

		for (Reaction reaction : reactions) {
			listReactions.add(modelMapper.map(reaction, ReactionFullDTO.class));
		}

		return ResponseEntity.ok(listReactions);

	}

	@PostMapping("/{messageId}/reactions")
	public ResponseEntity<?> createReaction(@RequestBody ReactionDTO reactionDTO, @PathVariable Long messageId,
			@RequestParam("userId") Long userId) {

		response.clear();
		User user;
		Message message;

		try {

			user = userService.findByIdUser(userId);
			message = messageService.findMessageById(messageId);

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (user == null) {

			response.put("mensaje",
					"El usuario con la ID: ".concat(userId.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} else if (message == null) {

			response.put("mensaje",
					"El mensaje con la ID: ".concat(userId.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}
		
		message.getReactionMessage();

		Reaction reaction = modelMapper.map(reactionDTO, Reaction.class);
		reaction.setUser(user);
		reaction.setMessage(message);
		Reaction reactionCreate = reactionService.saveReaction(reaction);
		reactionDTO = modelMapper.map(reactionCreate, ReactionDTO.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(reactionDTO);

	}

	@GetMapping("/user/{id}/friendPost")
	public ResponseEntity<?> showFriendMessageByUserId(@PathVariable Long id) {

		response.clear();
		User user;
		List<MessageAllDTO> messageAllDTOList = new ArrayList<MessageAllDTO>();

		try {

			user = userService.findByIdUser(id);

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (user == null) {

			response.put("mensaje",
					"El usuario con la ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

		List<Relationship> relationshipFollowing = user.getRelationshipFollowing();
		relationshipFollowing.addAll(user.getRelationshipFollow());

		if (relationshipFollowing.isEmpty()) {

			response.put("mensaje", "El Ususario con ID ".concat(id.toString().concat(" no tiene amigos")));
			return ResponseEntity.ok(response);

		}

		for (Relationship relationship : relationshipFollowing) {

			List<RelationshipFullDTO> relationList = new ArrayList<RelationshipFullDTO>();
			relationList.add(modelMapper.map(relationship, RelationshipFullDTO.class));

			for (RelationshipFullDTO relationshipDTO : relationList) {
				
				List<Message> messageList = new ArrayList<Message>();
				
				User userMessage = userService.findByIdUser(relationshipDTO.getUserFollow().getId());
				if (userMessage.getId() != id) {
					messageList.addAll(userMessage.getMessages());

				}

				userMessage = userService.findByIdUser(relationshipDTO.getUserFollowing().getId());
				if (userMessage.getId() != id) {
					messageList.addAll(userMessage.getMessages());
					
				}
				for (Message message : messageList) {

					messageAllDTOList.add(modelMapper.map(message, MessageAllDTO.class));

				}

			}

		}

		return ResponseEntity.ok(messageAllDTOList);

	}

}