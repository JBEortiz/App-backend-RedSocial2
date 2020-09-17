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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.redsocial.dto.EventDTO;
import es.eoi.redsocial.dto.UserWithoutDatesDTO;
import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.service.AssistanceService;
import es.eoi.redsocial.service.EventService;
import es.eoi.redsocial.service.MessageService;
import es.eoi.redsocial.service.RelationshipService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class ReportsController {

	@Autowired
	private AssistanceService assistanceService;

	@Autowired
	private EventService eventService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private RelationshipService relationService;

	private ModelMapper modelMapper = new ModelMapper();

	private Map<String, Object> response = new HashMap<String, Object>();

	@GetMapping("/reports/bestEvents")
	public ResponseEntity<?> findBestAssistanceEvents() {

		response.clear();
		List<EventDTO> bestEvents = new ArrayList<>();
		List<Event> events = new ArrayList<Event>();
		try {

			events = assistanceService.findTop3BestEvents();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (events.isEmpty()) {

			response.put("mensaje", "No hay ningun evento");
			return ResponseEntity.ok(response);
		}

		for (Event event : events) {
			bestEvents.add(modelMapper.map(event, EventDTO.class));
		}

		return ResponseEntity.ok(bestEvents);
	}

	@GetMapping("/reports/worstEvents")
	public ResponseEntity<?> findWorstAssistanceEvents() {

		response.clear();
		List<EventDTO> worstEvents = new ArrayList<>();
		List<Event> events = new ArrayList<Event>();
		try {

			events = assistanceService.findTop3WorstEvents();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (events.isEmpty()) {

			response.put("mensaje", "No hay ningun evento");
			return ResponseEntity.ok(response);

		}

		for (Event event : events) {
			worstEvents.add(modelMapper.map(event, EventDTO.class));
		}

		return ResponseEntity.ok(worstEvents);
	}

	@GetMapping("/reports/passedEvents")
	public ResponseEntity<?> findPassedEvents() {

		response.clear();
		List<EventDTO> passedEvents = new ArrayList<>();
		List<Event> events = new ArrayList<Event>();

		try {

			events = eventService.findPassedEvents();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (events.isEmpty()) {

			response.put("mensaje", "No hay ningun evento");
			return ResponseEntity.ok(response);

		}

		for (Event event : events) {
			passedEvents.add(modelMapper.map(event, EventDTO.class));
		}

		return ResponseEntity.ok(passedEvents);
	}

	@GetMapping("/reports/bestActiveUsers")
	public ResponseEntity<?> findBestActiveUsers() {

		response.clear();
		List<UserWithoutDatesDTO> bestActiveUsers = new ArrayList<>();
		List<User> users = new ArrayList<User>();

		try {

			users = eventService.findBestActiveUsers();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (users.isEmpty()) {

			response.put("mensaje", "No hay ningun usuario");
			return ResponseEntity.ok(response);

		}

		for (User user : users) {
			bestActiveUsers.add(modelMapper.map(user, UserWithoutDatesDTO.class));
		}

		return ResponseEntity.ok(bestActiveUsers);
	}

	@GetMapping("/reports/bestWritterUsers")
	public ResponseEntity<?> findBestWritterUsers() {

		response.clear();
		List<UserWithoutDatesDTO> bestWritterUsers = new ArrayList<>();
		List<User> users = new ArrayList<User>();

		try {

			users = messageService.findBestWritterUsers();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (users.isEmpty()) {

			response.put("mensaje", "Ningun usuario ha escrito un mensaje");
			return ResponseEntity.ok(response);

		}

		for (User user : users) {
			bestWritterUsers.add(modelMapper.map(user, UserWithoutDatesDTO.class));
		}

		return ResponseEntity.ok(bestWritterUsers);
	}

	@GetMapping("/reports/bestFriendlyUsers")
	public ResponseEntity<?> findBestFriendlyUsers() {

		response.clear();
		List<UserWithoutDatesDTO> bestFriendlyUsers = new ArrayList<>();
		List<User> users = new ArrayList<User>();

		try {

			users = relationService.findBestFriendlyUsers();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (users.isEmpty()) {

			response.put("mensaje", "No hay ningun usuario con amigos");
			return ResponseEntity.ok(response);

		}

		for (User user : users) {
			bestFriendlyUsers.add(modelMapper.map(user, UserWithoutDatesDTO.class));
		}

		return ResponseEntity.ok(bestFriendlyUsers);
	}

	@GetMapping("/reports/bestAssistanceUsers")
	public ResponseEntity<?> findBestAssistanceUsers() {

		response.clear();
		List<UserWithoutDatesDTO> bestAssistanceUsers = new ArrayList<>();
		List<User> users = new ArrayList<User>();

		try {

			users = assistanceService.findBestAssistanceUsers();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (users.isEmpty()) {

			response.put("mensaje", "Ningun usuario ha asistido a un evento");
			return ResponseEntity.ok(response);

		}

		for (User user : users) {
			bestAssistanceUsers.add(modelMapper.map(user, UserWithoutDatesDTO.class));
		}

		return ResponseEntity.ok(bestAssistanceUsers);
	}

	@GetMapping("/reports/inProgressEvents")
	public ResponseEntity<?> findActualEvents() {

		response.clear();
		List<Event> events = new ArrayList<Event>();
		List<EventDTO> actualEvents = new ArrayList<EventDTO>();

		try {

			events = eventService.findActualEvents();

		} catch (DataAccessException exception) {

			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}

		if (events.isEmpty()) {

			response.put("mensaje", "No hay ningun evento");
			return ResponseEntity.ok(response);

		}

		for (Event event : events) {
			actualEvents.add(modelMapper.map(event, EventDTO.class));
		}

		return ResponseEntity.ok(actualEvents);

	}

}