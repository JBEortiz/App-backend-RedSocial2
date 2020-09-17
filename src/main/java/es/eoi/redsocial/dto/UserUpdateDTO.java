package es.eoi.redsocial.dto;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserUpdateDTO {
	
	private Long id;
	
	private String name;

	private String surname;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date birthDate;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;

	private String user;

	private String pass;

}
