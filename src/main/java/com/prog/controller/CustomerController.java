package com.prog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prog.entity.Customer;
import com.prog.entity.LoginDetails;
import com.prog.service.CustomerService;

@Controller
public class CustomerController 
{
	
	@Autowired
	private CustomerService service;
	
	@PostMapping("/addUser")
	public String RegisterUser(@ModelAttribute Customer customer, HttpSession session)
	{
		session.setAttribute("msg","Customer Registered Successfully...");
		service.addCustomer(customer);
		return "redirect:/index";
	}
	
	@PostMapping("/verify")
	public String validateLogin(Customer customer, HttpSession session)
	{
		boolean isExists = service.verifyUser(customer.getEmail(),customer.getPassword());
		if(isExists)
		{
			String userName  = customer.getEmail().substring(0, customer.getEmail().length()-10);
			session.setAttribute("UserName", "Hello "+ userName);
			return "redirect:/index";
		}
		else
			return "login";
	}
	
}
