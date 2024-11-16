package com.riki.company.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.riki.company.domain.Customer;
import com.riki.company.domain.CustomerAddress;
import com.riki.company.dto.CustAddressCreateRequestDTO;
import com.riki.company.dto.CustAddressDetailResponseDTO;
import com.riki.company.dto.CustAddressListResponseDTO;
import com.riki.company.dto.CustAddressUpdateRequestDTO;
import com.riki.company.repository.CustAddressRepository;
import com.riki.company.repository.CustomerRepository;
import com.riki.company.service.CustAddressService;
import com.riki.exception.ResourceNotFoundException;

@Service
public class CustAddressServiceImpl implements CustAddressService {

	@Autowired
	private CustAddressRepository custAddressRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void createNewCustAddress(CustAddressCreateRequestDTO dto) {
		Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("invalid.customer_id"));
		CustomerAddress custAddress = new CustomerAddress();
		custAddress.setProvince(dto.getProvince());
		custAddress.setCustomer(customer);
		custAddress.setAddress(dto.getAddress());
		custAddressRepository.save(custAddress);
	}

	@Override
	public CustAddressDetailResponseDTO findCustAddress(Long id) {
		CustAddressDetailResponseDTO dto = new CustAddressDetailResponseDTO();
		CustomerAddress customerAddress = custAddressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer_address.not.found"));
		dto.setProvince(customerAddress.getProvince());
		dto.setCustomerId(customerAddress.getCustomer().getId());
		dto.setAddress(customerAddress.getAddress());
		return dto;
	}

	@Override
	public List<CustAddressListResponseDTO> findCustomerAddressAll(String address) {
		address = ObjectUtils.isEmpty(address) ? "%" : "%" + address + "%";
		
		List<CustomerAddress> customerAddressResponses = custAddressRepository.findAllByAddressLike(address);
		
		return customerAddressResponses.stream().map((ca) -> {
			CustAddressListResponseDTO dto = new CustAddressListResponseDTO();
			dto.setId(ca.getId());
			dto.setAddress(ca.getAddress());
			dto.setProvince(ca.getProvince());
			dto.setCustomerId(ca.getCustomer().getId());
			dto.setCustomerName(ca.getCustomer().getCustomerName());
			return dto;
		}).collect(Collectors.toList());

	}

	@Override
	public void updateCustomerAddress(Long id, CustAddressUpdateRequestDTO dto) {
		Customer customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(()-> new ResourceNotFoundException("invalid.customer_id"));
		CustomerAddress custAddress = custAddressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer_address.not.found"));
		custAddress.setAddress(dto.getAddress());
		custAddress.setProvince(dto.getProvince());
		custAddress.setCustomer(customer);
		custAddressRepository.save(custAddress);
	}

	@Override
	public void deleteCustomerAddress(Long id) {
		CustomerAddress custAddress = custAddressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("customer_address.not.found"));
		custAddressRepository.delete(custAddress);
	}
	
}
