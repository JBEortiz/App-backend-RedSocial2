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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.redsocial.dto.EventBasicDTO;
import es.eoi.redsocial.dto.EventDTO;
import es.eoi.redsocial.dto.UserDTO;
import es.eoi.redsocial.entity.Assistance;
import es.eoi.redsocial.entity.Event;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.enums.AssistEnum;
import es.eoi.redsocial.enums.EventEnum;
import es.eoi.redsocial.service.AssistanceService;
import es.eoi.redsocial.service.EventService;
import es.eoi.redsocial.service.UserService;
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping(value = "/events")
public class EventsController {

	@Autowired
	private EventService service;

	@Autowired
	private UserService userService;

	@Autowired
	private AssistanceService assistanceService;

	private ModelMapper modelMapper = new ModelMapper();
	private Map<String, Object> response = new HashMap<String, Object>();

	@GetMapping
	public ResponseEntity<List<EventDTO>> findAllEventDTO() {
		response.clear();
		List<Event> event = service.findAllEvent();
		List<EventDTO> eventDto = new ArrayList<EventDTO>();

		for (Event events : event) {
			eventDto.add(modelMapper.map(events, EventDTO.class));
		}

		return ResponseEntity.ok(eventDto);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findEventById(@PathVariable Long id) {
		response.clear();
		Event event;
		EventDTO eventDTO;

		try {
			event = service.findByIdEvent(id);
		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (event == null) {
			response.put("mensaje", "El evento con ID: ".concat(id.toString().concat(", no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		eventDTO = modelMapper.map(event, EventDTO.class);
		return ResponseEntity.ok(eventDTO);

	}

	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody EventBasicDTO eventBasicDTO){
//	, 
//			@RequestParam("id") Long id) {
		
		response.clear();
		//User userId;
		Event event;
		EventDTO eventDTO;
		try {
		//	userId = userService.finByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

//		if (userId == null) {
//			response.put("mensaje",
//					"El usuario con ID: ".concat(id.toString().concat(" no existe en la base de datos")));
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//		} else if (eventBasicDTO.getName().isEmpty() || eventBasicDTO.getDescription().isEmpty()) {
//			response.put("mensaje", "El nombre y/o la descripcion estan vacios");
//			return ResponseEntity.ok(response);
//		}

		event = modelMapper.map(eventBasicDTO, Event.class);
//	event.setUser(userId);
		event.setState(EventEnum.SCHEDULED);
		Event eventCreate = service.saveEvent(event);
		eventDTO = modelMapper.map(eventCreate, EventDTO.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(eventDTO);

	}

	@PostMapping("/{id}/yesAssitance")
	public ResponseEntity<?> assistToEvent(@PathVariable Long id, @RequestParam Long userId) {

		response.clear();
		User user;
		Event event;
		List<Assistance> assistList = assistanceService.findAllAssistances();

		try {
			user = userService.findByIdUser(userId);
			event = service.findByIdEvent(id);
		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (user == null || event == null) {
			response.put("mensaje", "No existe en la base de datos el usuario con ID: ".concat(userId.toString())
					.concat(" y/o el evento con ID: ").concat(id.toString()));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		for (Assistance assistance : assistList) {
			if (event.getId() == assistance.getEvent().getId() && user.getId() == assistance.getUser().getId()
					&& assistance.getState().equals(AssistEnum.ASSIST)) {
				response.put("mensaje", "El usuario ya asiste a este evento");
				return ResponseEntity.ok(response);
			}
		}

		assistanceService.assistEvent(event, user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/{id}/notAssitance")
	public ResponseEntity<?> assistNotEvent(@PathVariable Long id, @RequestParam Long userId) {

		response.clear();
		User user;
		Event event;
		List<Assistance> assistList = assistanceService.findAllAssistances();

		try {
			user = userService.findByIdUser(userId);
			event = service.findByIdEvent(id);
		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (user == null || event == null) {
			response.put("mensaje", "No existe en la base de datos el usuario con ID: ".concat(userId.toString())
					.concat(" y/o el evento con ID: ").concat(id.toString()));

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		for (Assistance assistance : assistList) {
			if (event.getId() == assistance.getEvent().getId() && user.getId() == assistance.getUser().getId()
					&& assistance.getState().equals(AssistEnum.NASSIST)) {
				response.put("mensaje", "El usuario ya no assistia a este evento");
				return ResponseEntity.ok(response);
			}
		}

		assistanceService.notAssistEvent(event, user);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	@GetMapping("/{id}/users/yesAssistance")
	public ResponseEntity<?> userYesAssistance(@PathVariable Long id) {

		response.clear();
		List<UserDTO> usersAssist = new ArrayList<UserDTO>();
		Event event = null;

		try {
			event = service.findByIdEvent(id);

		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (event == null) {
			response.put("mensaje", "El evento con ID: ".concat(id.toString().concat(", no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		for (Assistance assistance : event.getAssistances()) {
			if (assistance.getState().getTipo().equals("ASSIST")) {
				usersAssist.add(modelMapper.map(assistance.getUser(), UserDTO.class));
			}
		}

		if (usersAssist.isEmpty() == true) {
			response.put("mensaje",
					"Ningun usuario assisten a este evento de momento, no ahi usuarios en la lista de Si Assistir a evento.");
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.ok(usersAssist);

	}

	@GetMapping("/{id}/users/noAssistance")
	public ResponseEntity<?> userNoAssistance(@PathVariable Long id) {

		response.clear();
		List<UserDTO> usersNoAssist = new ArrayList<UserDTO>();
		Event event = null;
		try {
			event = service.findByIdEvent(id);
		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (event == null) {
			response.put("mensaje", "El evento con ID: ".concat(id.toString().concat(", no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		for (Assistance assistance : event.getAssistances()) {
			if (assistance.getState().getTipo().equals("NOTASSIST")) {
				usersNoAssist.add(modelMapper.map(assistance.getUser(), UserDTO.class));
			}
		}

		if (usersNoAssist.isEmpty() == true) {
			response.put("mensaje",
					"Todos los usuarios assisten a este evento de momento, no ahi usuarios en la lista de No Assistir a evento.");
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.ok(usersNoAssist);

	}

	@GetMapping("/user/{id}/yesAssistance")
	public ResponseEntity<?> eventYesAssistance(@PathVariable Long id) {
		
		response.clear();
		List<EventDTO> yesAssistance = new ArrayList<EventDTO>();
		User user = null;
		try {
			user = userService.findByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		if (user == null) {
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(", no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		for (Event event : user.getUserEvents()) {
			for (Assistance assist : event.getAssistances()) {
				if (assist.getState().getTipo().equals("ASSIST")) {
					yesAssistance.add(modelMapper.map(event, EventDTO.class));
				}
			}
		}

		if (yesAssistance.isEmpty() == true) {
			response.put("mensaje",
					"Este usuario no assiste a ningun evento disponible, la lista de Eventos Confirmados esta vacia.");
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.ok(yesAssistance);

	}

	@GetMapping("/user/{id}/notAssistance")
	public ResponseEntity<?> eventNotAssistance(@PathVariable Long id) {
		
		response.clear();
		List<EventDTO> notAssistance = new ArrayList<EventDTO>();
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
			response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(", no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		for (Event event : user.getUserEvents()) {
			for (Assistance assist : event.getAssistances()) {
				if (!assist.getState().getTipo().equals("ASSIST")) {
					notAssistance.add(modelMapper.map(event, EventDTO.class));
				}
			}
		}

		if (notAssistance.isEmpty() == true) {
			response.put("mensaje",
					"Este usuario va a todos los eventos disponibles, no tiene ningun evento marcado como No Asistir.");
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.ok(notAssistance);

	}

	@PutMapping("/refreshEventState")
	public ResponseEntity<?> UpdateStateEvent() {

		response.clear();
		List<EventDTO> events = new ArrayList<EventDTO>();
		List<Event> eventInProgress = new ArrayList<Event>();
		List<Event> eventPassed = new ArrayList<Event>();

		try {

			eventInProgress = service.findActualEvents();
			eventPassed = service.findPassedEvents();

		} catch (DataAccessException exception) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		for (Event event : eventPassed) {
			if (event.getState() != EventEnum.PASSED) {
				event.setState(EventEnum.PASSED);
				events.add(modelMapper.map(event, EventDTO.class));
			}
		}

		for (Event event : eventInProgress) {
			if (event.getState() != EventEnum.INPROGRESS) {
				event.setState(EventEnum.INPROGRESS);
				events.add(modelMapper.map(event, EventDTO.class));
			}
		}

		if (events.isEmpty()) {
			response.put("mensaje", "No se ha encontrado ningun evento para actualizar.");
			return ResponseEntity.ok(response);
		}

		for (EventDTO eventDTO : events) {
			service.UpdateStateEvent(modelMapper.map(eventDTO, Event.class));
		}

		return ResponseEntity.ok(events);

	}

}
