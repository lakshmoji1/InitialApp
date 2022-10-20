package com.prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prog.entity.Customer;
import com.prog.entity.Medicine;


@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> 
{
	@Query
	Customer findByEmailAndPassword(String email, String password);
}