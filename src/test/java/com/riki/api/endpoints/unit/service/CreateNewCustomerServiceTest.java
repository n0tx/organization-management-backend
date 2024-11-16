package com.riki.api.endpoints.unit.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.riki.api.domain.Customer;
import com.riki.api.dto.CustomerCreateRequestDTO;
import com.riki.api.repository.CustomerRepository;
import com.riki.api.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CreateNewCustomerServiceTest {
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService customerService;
	
	@Test
	public void saveCustomer() {
		CustomerCreateRequestDTO dto = new CustomerCreateRequestDTO();
		dto.setCustomerName("Test Customer Name");
		
		customerService.createNewCustomer(dto);

		Customer customer = new Customer();
		customer.setCustomerName(dto.getCustomerName());
		
		verify(customerRepository).save(customer);
		
	}
	
	
}
