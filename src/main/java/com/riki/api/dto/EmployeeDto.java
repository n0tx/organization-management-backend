package com.riki.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeDto {

	private Long id;
	private String fullname;
	private String email;
	private LocalDate birthDate;
	private String phoneNumber;
	private String urlPicture;
	
}
