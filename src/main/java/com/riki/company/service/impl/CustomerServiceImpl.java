package com.riki.company.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.riki.company.domain.Customer;
import com.riki.company.dto.CustomerCreateRequestDTO;
import com.riki.company.dto.CustomerDetailResponseDTO;
import com.riki.company.dto.CustomerListResponseDTO;
import com.riki.company.dto.CustomerUpdateRequestDTO;
import com.riki.company.repository.CustomerRepository;
import com.riki.company.service.CustomerService;
import com.riki.exception.ResourceNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void createNewCustomer(CustomerCreateRequestDTO dto) {
		Customer customer = new Customer();
		customer.setCustomerName(dto.getCustomerName());
		customerRepository.save(customer);
	}

	@Override
	public CustomerDetailResponseDTO findCustomer(Long id) {
		CustomerDetailResponseDTO dto = new CustomerDetailResponseDTO();
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer.not.found"));
		dto.setCustomerName(customer.getCustomerName());
		return dto;
	}

	@Override
	public List<CustomerListResponseDTO> findCustomerAll(String customerName) {
		customerName = ObjectUtils.isEmpty(customerName) ? "%" : "%" + customerName + "%";
		
		List<Customer> customerResponses = customerRepository.findAllByCustomerNameLike(customerName);
		
		return customerResponses.stream().map((c)-> {
			CustomerListResponseDTO dto = new CustomerListResponseDTO();
			dto.setId(c.getId());
			dto.setCustomerName(c.getCustomerName());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public void updateCustomer(Long id, CustomerUpdateRequestDTO dto) {
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer.not.found"));
		customer.setCustomerName(dto.getCustomerName());
		customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer.not.found"));
		customerRepository.delete(customer);
	}
	
}
