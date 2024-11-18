package com.riki.api.web;

import com.riki.api.domain.pagination.Paging;
import com.riki.api.dto.EmployeeCreateDto;
import com.riki.api.dto.EmployeeDto;
import com.riki.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
public class EmployeeResource {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/v1/employee/list")
	public ResponseEntity<Paging<EmployeeDto>> findEmployeeList(
			@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "limit", required = true, defaultValue = "2") Integer limit,
			@RequestParam(value = "sortBy", required = true, defaultValue = "id") String sortBy,
			@RequestParam(value = "direction", required = true, defaultValue = "asc") String direction,
			@RequestParam(value = "search", required = false) String search
			) {
		Paging<EmployeeDto> employees = employeeService.findEmployeeAll(page, limit, sortBy, direction, search);
		return ResponseEntity.ok(employees);
	}

	@PostMapping("/v1/employee/new")
	public ResponseEntity<Void> addNewEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) throws URISyntaxException {
		employeeService.createNewEmployee(employeeCreateDto);
		return ResponseEntity.created(new URI("/v1/employee/new")).build();
	}
	
	@GetMapping("/v1/employee/{id}")
	public ResponseEntity<EmployeeDto> findEmployeeDetail(@PathVariable("id") Long id) {
		EmployeeDto employeeDto = employeeService.findEmployee(id);
		return ResponseEntity.ok(employeeDto);
	}

	@PutMapping("/v1/employee/update/{id}")
	public ResponseEntity<Void> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) throws URISyntaxException {
		employeeService.updateEmployee(id, employeeDto);
		return ResponseEntity.created(new URI("/v1/employee/update")).build();
	}
	
	@DeleteMapping("/v1/employee/delete/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok().build();
	}

	// @PostMapping(value = "/v1/employee/upload", consumes = "multipart/form-data")
	@PostMapping(value = "/v1/employee/upload")
	public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
		String urlPicture = employeeService.savePhoto(file);
		return ResponseEntity.ok(urlPicture);
	}

	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		Resource file = employeeService.load(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

}


		
		
		
		
		