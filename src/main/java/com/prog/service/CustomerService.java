package com.prog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prog.entity.Customer;
import com.prog.repository.CustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo repo;
	
	public boolean verifyUser(String email, String password) {
		Customer customer = repo.findByEmailAndPassword(email,password);
		if(customer!=null)
			return true;
		return false;
	}
	
	public void addCustomer(Customer cust)
	{
		repo.save(cust);
	}
	
}
