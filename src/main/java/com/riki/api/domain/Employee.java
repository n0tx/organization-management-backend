package com.riki.api.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

	private static final long serialVersionUID = -2717624279840430130L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "full_name", nullable = false)
	private String fullname;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "active", columnDefinition = "boolean default false")
	private Boolean active;

	@Column(name = "url_picture")
	private String urlPicture;

}
