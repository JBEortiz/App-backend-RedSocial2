package es.eoi.redsocial.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAllDTO {

	private Long id;

	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate publishDate = LocalDate.now();

	private UserWithoutDatesDTO user;
	
	private List<ReactionFullDTO> reactionMessage;
	
}
