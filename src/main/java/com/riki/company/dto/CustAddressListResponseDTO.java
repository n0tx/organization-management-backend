package com.riki.company.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustAddressListResponseDTO {

	private Long id;
	private String address;
	private String province;
	private Long customerId;
	private String customerName;

}
