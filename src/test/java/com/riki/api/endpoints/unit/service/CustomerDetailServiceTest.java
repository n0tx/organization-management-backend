package com.riki.api.endpoints.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.riki.api.domain.Customer;
import com.riki.api.dto.CustomerDetailResponseDTO;
import com.riki.api.repository.CustomerRepository;
import com.riki.api.service.CustomerService;
import com.riki.exception.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDetailServiceTest {
	@Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void findCustomerByCustomerId() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("Riki");

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        CustomerDetailResponseDTO expected = customerService.findCustomer(customer.getId());

        assertThat(expected).isSameAs(customer);
        verify(customerRepository).findById(customer.getId());
    }

    @Test
    public void should_throw_exception_when_user_doesnt_exist() {
    	Assertions.assertThrows(IllegalArgumentException.class, ()-> new ResourceNotFoundException("customer.not.found"));
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("Test Name");

        given(customerRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        customerService.findCustomer(customer.getId());
    }
}
