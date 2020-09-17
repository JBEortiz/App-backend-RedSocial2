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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.redsocial.dto.RelationshipFullDTO;
import es.eoi.redsocial.dto.UserAllDTO;
import es.eoi.redsocial.dto.UserDTO;
import es.eoi.redsocial.dto.UserUpdateDTO;
import es.eoi.redsocial.entity.Relationship;
import es.eoi.redsocial.entity.User;
import es.eoi.redsocial.enums.RelationEnum;
import es.eoi.redsocial.service.RelationshipService;
import es.eoi.redsocial.service.UserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private RelationshipService relationService;
	private ModelMapper modelMapper = new ModelMapper();
	private Map<String, Object> response = new HashMap<String, Object>();

	@GetMapping
	public ResponseEntity<?> findAllUsersDTO() {
		response.clear();
		List<User> user = new ArrayList<User>();
		List<UserAllDTO> userDto = new ArrayList<UserAllDTO>();
		try {
			user = service.findAllUser();
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (user.isEmpty()) {
			response.put("mensaje", "No hay ningun usuario");
			return ResponseEntity.ok(response);
		}
		for (User usuario : user) {
			userDto.add(modelMapper.map(usuario, UserAllDTO.class));
		}
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable Long id) {
		response.clear();
		User user;
		UserDTO userDto;
		try {
			user = service.findByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (user == null) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		userDto = modelMapper.map(user, UserDTO.class);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/{name}")
	public ResponseEntity<?> getByNameUser(@PathVariable String name) {
		ModelMapper modelMapper = new ModelMapper();
		User user = service.findUserName(name);
		UserDTO usuarioDto = new UserDTO();
		usuarioDto=modelMapper.map(user, UserDTO.class);
		return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
	}

	@GetMapping("/fullUser/{id}")
	public ResponseEntity<?> findFullUserId(@PathVariable Long id) {
		response.clear();
		User user;
		UserAllDTO userDto;
		try {
			user = service.findByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (user == null) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		userDto = modelMapper.map(user, UserAllDTO.class);
		return ResponseEntity.ok(userDto);
	}

	/**
	 * 
	 * @param userDto
	 * @param id
	 * @return
	 */
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {
		response.clear();
		User user = modelMapper.map(userDto, User.class);
		User userCreate = null;
		try {
			userCreate = service.saveUser(user);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
	}

	/**
	 * 
	 * @param userDto
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUsuario(@RequestBody UserUpdateDTO userDto, @PathVariable Long id) {
		response.clear();
		User userUpdate;
		UserUpdateDTO userUpdateDTO;

		try {
			userUpdate = service.findByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (userUpdate == null) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		userUpdate = modelMapper.map(userDto, User.class);
		service.saveUser(userUpdate);
		userUpdateDTO = modelMapper.map(userUpdate, UserUpdateDTO.class);
		return ResponseEntity.ok(userUpdateDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String user, @RequestParam String pass) {
		response.clear();
		User userLogin;
		try {
			userLogin = service.findByUserAndPass(user, pass);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (userLogin == null) {

			response.put("error", "El usuario o la contraseña introducidas no son correctas");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		}

		UserDTO userDto = modelMapper.map(userLogin, UserDTO.class);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/{id}/friendsRelationship")
	public ResponseEntity<?> getAcceptedFriends(@PathVariable Long id) {

		response.clear();
		User user;
		try {
			user = service.findByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (user == null) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		List<Relationship> relations = user.getRelationshipFollow();
		relations.addAll(user.getRelationshipFollowing());
		List<RelationshipFullDTO> relationsDto = new ArrayList<>();
		if (relations.isEmpty()) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no tiene amigos")));
			return ResponseEntity.ok(response);
		}
		for (Relationship relation : relations) {
			if (relation.getState().getCodigo() == RelationEnum.FRIEND.getCodigo()) {
				relationsDto.add(modelMapper.map(relation, RelationshipFullDTO.class));
			}
		}
		return ResponseEntity.ok(relationsDto);
	}

	@GetMapping("/{id}/pendingRelationship")
	public ResponseEntity<?> getPendentFriends(@PathVariable Long id) {

		response.clear();
		User user = null;

		try {
			user = service.findByIdUser(id);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (user == null) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		List<Relationship> relations = user.getRelationshipFollow();
		List<RelationshipFullDTO> relationsDto = new ArrayList<>();
		if (relations.isEmpty()) {
			response.put("message",
					"El Ususario con ID ".concat(id.toString().concat(" no tiene relaciones pendientes")));
			return ResponseEntity.ok(response);
		}
		for (Relationship relation : relations) {
			if (relation.getState().getCodigo() == RelationEnum.PENDING.getCodigo()) {
				relationsDto.add(modelMapper.map(relation, RelationshipFullDTO.class));
			}
		}
		return ResponseEntity.ok(relationsDto);
	}

	@PostMapping("/{id}/invitedFriend")
	public ResponseEntity<?> invitedFriend(@PathVariable Long id, @RequestParam("user") Long user) {
		response.clear();
		User userId;
		User invitedUser;
		Relationship newRelationship;
		RelationshipFullDTO newRelationshipFullDTO;
		try {

			userId = service.findByIdUser(id);
			invitedUser = service.findUserUser(user);
			newRelationship = relationService.saveRelationship(userId, invitedUser);

		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

		}
		if (userId == null) {
			response.put("message", "El Ususario con ID ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else if (invitedUser == null) {
			response.put("message", "El usuario al que quiere enviar la invitación no existe");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else if (newRelationship.getState().getCodigo() == RelationEnum.PENDING.getCodigo()) {
			response.put("message", "Se ha enviado la petición correctamente");
			return ResponseEntity.ok(response);
		}
		newRelationshipFullDTO = modelMapper.map(newRelationship, RelationshipFullDTO.class);
		return ResponseEntity.ok(newRelationshipFullDTO);

	}

	@PutMapping("/relationship/{id}")
	public ResponseEntity<?> updateRelationShip(@PathVariable Long id) {
		response.clear();
		Relationship relatUpdate = null;
		RelationshipFullDTO relatFullDTOUpdate;
		try {
			relatUpdate = relationService.findRelationshipById(id).get();
			relationService.updateRelationship(relatUpdate);
		} catch (DataAccessException exception) {
			response.put("message", "Error al acceder a la base de datos");
			response.put("error",
					exception.getMessage().concat(": ").concat(exception.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		if (relatUpdate == null) {
			response.put("message",
					"No existe ninguna relacion con esa id ".concat(id.toString().concat(" no existe")));
			return ResponseEntity.notFound().build();

		}
		relatFullDTOUpdate = modelMapper.map(relatUpdate, RelationshipFullDTO.class);
		return ResponseEntity.ok(relatFullDTOUpdate);

	}

	@DeleteMapping("/relationship/{id}")
	public ResponseEntity<?> deleteRelationShip(@PathVariable Long id) {
		response.clear();
		try {
			relationService.deleteRelationshipById(id);
		} catch (DataAccessException exception) {
			response.put("message", "No se ha podido borrar la relacion ya que no existe o ya se ha borrado");
			response.put("error", exception.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		return ResponseEntity.ok("Se ha eliminado la relacion");
	}
}
