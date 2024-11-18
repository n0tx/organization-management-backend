package com.riki.api;

import com.riki.api.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrganizationManagementBackendApplication implements CommandLineRunner {

	@Resource
	EmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(OrganizationManagementBackendApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		employeeService.deleteAll();
		employeeService.init();
	}

}
