package com.riki.company.service;

import java.util.List;
import com.riki.company.dto.CustomerCreateRequestDTO;
import com.riki.company.dto.CustomerDetailResponseDTO;
import com.riki.company.dto.CustomerListResponseDTO;
import com.riki.company.dto.CustomerUpdateRequestDTO;

public interface CustomerService {
	
	public void createNewCustomer(CustomerCreateRequestDTO dto);
	
	public CustomerDetailResponseDTO findCustomer(Long id);
	
	public List<CustomerListResponseDTO> findCustomerAll(String customerName);

	public void updateCustomer(Long id, CustomerUpdateRequestDTO dto);

	public void deleteCustomer(Long id);

}
