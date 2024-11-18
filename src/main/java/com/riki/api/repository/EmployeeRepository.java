package com.riki.api.repository;

import com.riki.api.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Boolean existsByEmailAndActiveTrue(String email);
	public Optional<Employee> findByIdAndActiveTrue(Long id);
	
	@Query("SELECT e FROM Employee e WHERE (e.active = true) and ((:fullname is null or e.fullname LIKE :fullname) or (:email is null or e.email LIKE :email) or (:phoneNumber is null or e.phoneNumber LIKE :phoneNumber))")
	public Page<Employee> search(@Param("fullname") String fullname, @Param("email") String email, @Param("phoneNumber") String phoneNumber, Pageable pageable);
	
}
