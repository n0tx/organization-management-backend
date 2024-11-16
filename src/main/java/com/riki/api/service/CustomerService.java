package com.riki.api.service;

import java.util.List;
import com.riki.api.dto.CustomerCreateRequestDTO;
import com.riki.api.dto.CustomerDetailResponseDTO;
import com.riki.api.dto.CustomerListResponseDTO;
import com.riki.api.dto.CustomerUpdateRequestDTO;

public interface CustomerService {
	
	public void createNewCustomer(CustomerCreateRequestDTO dto);
	
	public CustomerDetailResponseDTO findCustomer(Long id);
	
	public List<CustomerListResponseDTO> findCustomerAll(String customerName);

	public void updateCustomer(Long id, CustomerUpdateRequestDTO dto);

	public void deleteCustomer(Long id);

}
