package com.riki.api.service;

import com.riki.api.dto.EmployeeCreateDto;
import com.riki.api.dto.EmployeeDto;
import com.riki.api.domain.Employee;
import com.riki.api.domain.pagination.Paging;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {

	public Paging<EmployeeDto> findEmployeeAll(Integer page, Integer limit, String sortBy, String direction, String search);
	
	public void createNewEmployee(EmployeeCreateDto employeeCreateDto);
	
	public EmployeeDto findEmployee(Long id);

	public void updateEmployee(Long id, EmployeeDto employeeDto);

	public void deleteEmployee(Long id);

	public String savePhoto(MultipartFile file);

	public void init();

	public void deleteAll();

	public Resource load(String filename);

}
