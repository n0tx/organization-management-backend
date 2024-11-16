package com.riki.company.endpoints.unit.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.riki.company.domain.Customer;
import com.riki.company.dto.CustomerCreateRequestDTO;
import com.riki.company.repository.CustomerRepository;
import com.riki.company.service.CustomerService;

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
