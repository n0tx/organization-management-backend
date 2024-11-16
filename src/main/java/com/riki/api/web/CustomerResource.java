package com.riki.api.web;

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

import com.riki.api.dto.CustomerCreateRequestDTO;
import com.riki.api.dto.CustomerDetailResponseDTO;
import com.riki.api.dto.CustomerListResponseDTO;
import com.riki.api.dto.CustomerUpdateRequestDTO;
import com.riki.api.service.CustomerService;

@RestController
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/v1/customer/new")
	public ResponseEntity<Void> createNewCustomer(@RequestBody CustomerCreateRequestDTO dto) throws URISyntaxException {
		customerService.createNewCustomer(dto);
		return ResponseEntity.created(new URI("/v1/customer/new")).build();
	}
	
	@GetMapping("/v1/customer/{customerId}")
	public ResponseEntity<CustomerDetailResponseDTO> findCustomerDetail(@PathVariable("customerId") Long customerId) {
		CustomerDetailResponseDTO dto = customerService.findCustomer(customerId);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/v1/customer/list")
	public ResponseEntity<List<CustomerListResponseDTO>> findCustomerAll(@RequestParam(value = "customer_name", required = false) String customerName) {
		List<CustomerListResponseDTO> dtos = customerService.findCustomerAll(customerName);
		return ResponseEntity.ok(dtos);
	}
	
	@PutMapping("/v1/customer/update/{customerId}")
	public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerUpdateRequestDTO dto) throws URISyntaxException {
		customerService.updateCustomer(customerId, dto);
		return ResponseEntity.created(new URI("/v1/customer/update")).build();
	}
	
	@DeleteMapping("/v1/customer/delete/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Long customerId) throws URISyntaxException {
		customerService.deleteCustomer(customerId);
		return ResponseEntity.ok().build();
	}
}
