package es.eoi.redsocial.dto;

import es.eoi.redsocial.enums.AssistEnum;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AssistanceDTO {
	private Long id;
	private AssistEnum state;
	private EventDTO event;
	private UserDTO user;
}
