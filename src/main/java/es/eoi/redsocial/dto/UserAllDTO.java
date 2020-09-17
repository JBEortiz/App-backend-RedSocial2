package es.eoi.redsocial.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAllDTO {
	
	private Long id;

	private String name;

	private String surname;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date birthDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate = LocalDate.now();

	private String user;

	private String pass;

	private List<AssistanceDTO> assistances;
	private List<EventDTO> userEvents;
	private List<ReactionDTO> reactions;
	private List<MessageAllDTO> messages;
	private List<RelationshipFullDTO> relationship1;
	private List<RelationshipFullDTO> relationship2;
}
