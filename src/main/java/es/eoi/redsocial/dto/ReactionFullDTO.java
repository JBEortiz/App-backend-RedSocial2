package es.eoi.redsocial.dto;

import es.eoi.redsocial.enums.ReactionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionFullDTO {

	private ReactionEnum reactiontype;
	
	private UserWithoutDatesDTO user;
	
}
