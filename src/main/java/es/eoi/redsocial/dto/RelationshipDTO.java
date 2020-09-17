package es.eoi.redsocial.dto;

import es.eoi.redsocial.enums.RelationEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipDTO {

	private Long id;
	
	private RelationEnum state;
	
	private UserDTO userFollow;
	
}
