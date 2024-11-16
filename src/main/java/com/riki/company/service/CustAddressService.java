package com.riki.company.service;

import java.util.List;

import com.riki.company.dto.CustAddressCreateRequestDTO;
import com.riki.company.dto.CustAddressDetailResponseDTO;
import com.riki.company.dto.CustAddressListResponseDTO;
import com.riki.company.dto.CustAddressUpdateRequestDTO;

public interface CustAddressService {

	public void createNewCustAddress(CustAddressCreateRequestDTO dto);
	
	public CustAddressDetailResponseDTO findCustAddress(Long id);
	
	public List<CustAddressListResponseDTO> findCustomerAddressAll(String address);
	
	public void updateCustomerAddress(Long id, CustAddressUpdateRequestDTO dto);
	
	public void deleteCustomerAddress(Long id);
	
}
