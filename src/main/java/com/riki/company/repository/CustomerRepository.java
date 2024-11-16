package com.riki.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.riki.company.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	List<Customer> findAllByCustomerNameLike(String customerName);
	
}