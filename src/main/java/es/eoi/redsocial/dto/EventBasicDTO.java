package es.eoi.redsocial.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import es.eoi.redsocial.enums.EventEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EventBasicDTO {

	private String name;

	private String description;

	@JsonProperty(access = Access.READ_ONLY)
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate eventDate = LocalDate.now();
	
	@JsonProperty(access = Access.READ_ONLY)
	private EventEnum state;
	
}