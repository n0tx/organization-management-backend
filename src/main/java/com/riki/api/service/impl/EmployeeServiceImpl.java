package com.riki.api.service.impl;

import com.riki.api.domain.Employee;
import com.riki.api.domain.pagination.Paging;
import com.riki.api.dto.EmployeeCreateDto;
import com.riki.api.dto.EmployeeDto;
import com.riki.api.repository.EmployeeRepository;
import com.riki.api.service.EmployeeService;
import com.riki.api.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Value("${app.serverAddress}")
	private String serverAddress;

	private final Path imageDirectory = Paths.get("./uploads");

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Paging<EmployeeDto> findEmployeeAll(Integer page, Integer limit, String sortBy,
											   String direction, String search) {

		
		String byName = ObjectUtils.isEmpty(search) ? "%%" : "%" + search + "%";
		String byEmail = ObjectUtils.isEmpty(search) ? "%%" : "%" + search + "%";
		String byPhone = ObjectUtils.isEmpty(search) ? "%%" : "%" + search + "%";
		
		Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page<Employee>  pageResult = employeeRepository.search(byName, byEmail, byPhone, pageable);
		
		List<EmployeeDto> employeeDtos = pageResult.stream().map((p)-> {
			EmployeeDto employeeDto =  new EmployeeDto();
			employeeDto.setId(p.getId());
			employeeDto.setFullname(p.getFullname());
			employeeDto.setEmail(p.getEmail());
			employeeDto.setBirthDate(p.getBirthDate());
			employeeDto.setPhoneNumber(p.getPhoneNumber());
			employeeDto.setUrlPicture(p.getUrlPicture());
			return employeeDto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPagingDTO(employeeDtos, pageResult.getTotalElements(), pageResult.getTotalPages(), page);
	}
	
	@Override
	public void createNewEmployee(EmployeeCreateDto employeeCreateDto) {
		Employee employee = new Employee();
		employee.setFullname(employeeCreateDto.getFullname());
		employee.setEmail(employeeCreateDto.getEmail());
		employee.setBirthDate(employeeCreateDto.getBirthDate());
		employee.setPhoneNumber(employeeCreateDto.getPhoneNumber());
		employee.setUrlPicture(employeeCreateDto.getUrlPicture());
		employee.setActive(Boolean.TRUE);
		employeeRepository.save(employee);
	}
	
	@Override
	public EmployeeDto findEmployee(Long id) {
		EmployeeDto employeeDto = new EmployeeDto();
		Employee employee = employeeRepository.findByIdAndActiveTrue(id).orElseThrow(()->new IllegalArgumentException("Invalid Employee Id: " + id));
		employeeDto.setId(employee.getId());
		employeeDto.setFullname(employee.getFullname());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setBirthDate(employee.getBirthDate());
		employeeDto.setPhoneNumber(employee.getPhoneNumber());
		employeeDto.setUrlPicture(employee.getUrlPicture());
		return employeeDto;
	}
	
	@Override
	public void updateEmployee(Long id, EmployeeDto employeeDto) {
		Employee foundEmployee = employeeRepository.findByIdAndActiveTrue(id).orElseThrow(()->new IllegalArgumentException("Invalid Employee Id: " + id));
		foundEmployee.setFullname(employeeDto.getFullname());
		foundEmployee.setBirthDate(employeeDto.getBirthDate());
		foundEmployee.setEmail(employeeDto.getEmail());
		foundEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
		foundEmployee.setUrlPicture(employeeDto.getUrlPicture());
		employeeRepository.save(foundEmployee);
	}
	
	@Override
	public void deleteEmployee(Long id) {
		Employee employee = employeeRepository.findByIdAndActiveTrue(id).orElseThrow(()->new IllegalArgumentException("Invalid employee Id: " + id));
		employee.setActive(Boolean.FALSE);
		employeeRepository.save(employee);
	}

//	@Override
//	public String savePhoto(MultipartFile file) {
//
//		String fileName = file.getOriginalFilename();
//
//		try {
//			// Salin file dan gantikan jika file dengan nama yang sama sudah ada
//			Files.copy(file.getInputStream(),this.imageDirectory.resolve(fileName),
//					StandardCopyOption.REPLACE_EXISTING);
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to upload file: " + e.getMessage());
//		}
//
//		Path filePath = imageDirectory.resolve(fileName);
//		String url = filePath.toString();
//		String fullUrl = serverAddress + url.substring(1);
//
//		/*
//		// ini kalau isi data dulu -> save, baru gambar -> upload
//		if (employeeDto.getId() != null) {
//			updateEmployee(employeeDto.getId(), employeeDto);
//		}
//		ini di frontend aja coba dibuatnya
//		 */
//
//		return fullUrl;
//	}

	@Override
	public String savePhoto(MultipartFile file) {

		String fileName = file.getOriginalFilename();

		try {
			// Salin file dan gantikan jika file dengan nama yang sama sudah ada
			Files.copy(file.getInputStream(),this.imageDirectory.resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Failed to upload file: " + e.getMessage());
		}

		Path filePath = imageDirectory.resolve(fileName);
		String url = filePath.toString();
		String fullUrl = serverAddress + url.substring(1);

		return fullUrl;
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(imageDirectory);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(imageDirectory.toFile());
	}

	@Override
	public Resource load(String filename) {
		try {
			Path file = imageDirectory.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

}
