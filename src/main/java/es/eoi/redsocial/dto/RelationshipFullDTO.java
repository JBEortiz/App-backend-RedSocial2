package es.eoi.redsocial.dto;

import es.eoi.redsocial.enums.RelationEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipFullDTO {

	private Long id;
	
	private RelationEnum state;
	
	private UserDTO userFollowing;
	
	private UserDTO userFollow;
	
}
