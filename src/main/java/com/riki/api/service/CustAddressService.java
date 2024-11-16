package com.riki.api.service;

import java.util.List;

import com.riki.api.dto.CustAddressCreateRequestDTO;
import com.riki.api.dto.CustAddressDetailResponseDTO;
import com.riki.api.dto.CustAddressListResponseDTO;
import com.riki.api.dto.CustAddressUpdateRequestDTO;

public interface CustAddressService {

	public void createNewCustAddress(CustAddressCreateRequestDTO dto);
	
	public CustAddressDetailResponseDTO findCustAddress(Long id);
	
	public List<CustAddressListResponseDTO> findCustomerAddressAll(String address);
	
	public void updateCustomerAddress(Long id, CustAddressUpdateRequestDTO dto);
	
	public void deleteCustomerAddress(Long id);
	
}
