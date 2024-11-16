package com.riki.company.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riki.company.dto.CustAddressCreateRequestDTO;
import com.riki.company.dto.CustAddressDetailResponseDTO;
import com.riki.company.dto.CustAddressListResponseDTO;
import com.riki.company.dto.CustAddressUpdateRequestDTO;
import com.riki.company.service.CustAddressService;

@RestController
public class CustAddressResource {

	@Autowired
	private CustAddressService custAddressService;
	
	@GetMapping("/v1/custaddress/new")
	public ResponseEntity<CustAddressCreateRequestDTO> loadCreateCustAddressParam() {
		CustAddressCreateRequestDTO dto = new CustAddressCreateRequestDTO();
		return ResponseEntity.ok(dto);
	}
	/*
	CustAddressResource -> Employees
	CustomerResource -> Positions
	UserResource -> Users
	 */

	@PostMapping("/v1/custaddress/new")
	public ResponseEntity<Void> createNewCustAddress(@RequestBody CustAddressCreateRequestDTO dto) throws URISyntaxException {
		custAddressService.createNewCustAddress(dto);
		return ResponseEntity.created(new URI("/v1/custaddress/new")).build();

	}
	
	@GetMapping("/v1/custaddress/{customerId}")
	public ResponseEntity<CustAddressDetailResponseDTO> findCustomerAddressDetail(@PathVariable("customerId") Long customerId) {
		CustAddressDetailResponseDTO dto = custAddressService.findCustAddress(customerId);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/v1/custaddress/list")
	public ResponseEntity<List<CustAddressListResponseDTO>> findCustomerAddressAll(@RequestParam(value = "address", required = false) String address) {
		List<CustAddressListResponseDTO> dtos = custAddressService.findCustomerAddressAll(address);
		return ResponseEntity.ok(dtos);
	}

	@PutMapping("/v1/custaddress/update/{customerAddressId}")
	public ResponseEntity<Void> updateCustomerAddress(@PathVariable("customerAddressId") Long customerAddressId, @RequestBody CustAddressUpdateRequestDTO dto) throws URISyntaxException {
		custAddressService.updateCustomerAddress(customerAddressId, dto);
		return ResponseEntity.created(new URI("/v1/custaddress/update")).build();
	}
	
	@DeleteMapping("/v1/custaddress/delete/{customerAddressId}")
	public ResponseEntity<Void> deleteCustomerAddress(@PathVariable("customerAddressId") Long customerAddressId) throws URISyntaxException {
		custAddressService.deleteCustomerAddress(customerAddressId);
		return ResponseEntity.ok().build();
	}
}
	