package com.riki.company.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustAddressCreateRequestDTO {


	List<String> provinces = new ArrayList<>();
	
	public CustAddressCreateRequestDTO() {
		super();
		provinces.add("Banten");
		provinces.add("DKI Jakarta");
		provinces.add("Jawa Barat");
	}
	
	private String address;
	private Long customerId;
	private String province;

}
