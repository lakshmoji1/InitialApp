package com.prog.controller;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.prog.entity.Medicine;
import com.prog.entity.Customer;
import com.prog.entity.Item;
import com.prog.entity.LoginDetails;
import com.prog.service.MediService;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

@Controller
public class MedicineController 
{
	private double totalValue = 0.0;
	
	@Autowired
	MediService service;

	@GetMapping("/findMedi")
	public String Search(Model model, @RequestParam(defaultValue = "") String name)
	{
		System.out.println("Searching for name that contains "+name);
	List<Medicine> medicines = new ArrayList<Medicine>();
	medicines = service.serachMedicine(name);
	
	model.addAttribute("medi",medicines);
	return "search";
	}
	
	@GetMapping("/index")
	public String IndexPage(Model model)
	{

		return findPaginated(1,model);
	}
	
	
	@GetMapping("/addmedi")
	public String addEmpForm()
	{
		return "add_medi";
	}
	
	
	@PostMapping("/register")
	public String mediRegister(@ModelAttribute Medicine m, HttpSession session)
	{
		System.out.println(m);
		session.setAttribute("msg","Medicine Added Successfully...");
		service.addMedicine(m); 	
		return "redirect:/index";
	}
	
	
	
	@PostMapping("/validatingDetails")
	public String validateRegister(@ModelAttribute Customer c)
	{
		
		return "redirect:/index";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable int id)
	{
		ModelAndView mv = new ModelAndView("edit");
		Medicine m = service.getMediById(id);
		mv.addObject("medi",m);
		return mv;
	}	
	
	@PostMapping("/update")
	public String updateMedi(@ModelAttribute Medicine m,HttpSession session)
	{
		session.setAttribute("msg", "Medicine updated successfully...");
		service.updateMedicine(m);
		return "redirect:/index";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id,HttpSession session)
	{
		service.deleteMedicine(id);
		session.setAttribute("msg", "Medicine removed successfully...");
		return "redirect:/index";
	}
	
	@GetMapping("/")
	public String startUp()
	{
		return "startup";
	}
	
	@GetMapping("/login")
	public String login(LoginDetails credentials)
	{
		return "login";
	}
	
	@GetMapping("/Register")
	public String register()
	{
		return "Register";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model)
	{
		int pageSize = 5;
		Page<Medicine> page = service.findPaginated(pageNo, pageSize);
		List<Medicine> mediList = page.getContent();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("medi",mediList);
		return "index";
	}
	
	
	@GetMapping("/Add/{id}")
	@ResponseBody
	public ModelAndView AddToCart(@PathVariable int id, HttpServletRequest request)
	{
		
		ModelAndView mv = new ModelAndView("Cart");	
		Medicine medi = service.getMediById(id);
		@SuppressWarnings("unchecked")
		List<Item> cartItems = (List<Item>) request.getSession().getAttribute("items_in_cart");
		if (cartItems == null) {
			cartItems = new ArrayList<>();
			Item item = new Item(medi.getName(),1,medi.getPrice());
			cartItems.add(item);
			totalValue += item.getTotal();
			System.out.println(totalValue);
			System.out.println("List is empty adding the new item");
			request.getSession().setAttribute("items_in_cart", cartItems);
			request.getSession().setAttribute("totalValue", totalValue);
			@SuppressWarnings("unchecked")
			List<Item> ite = (List<Item>) request.getSession().getAttribute("items_in_cart");
			
			System.out.println("List is empty added the new item now the no of items is "+ite.size());
		}
		else
		{
			boolean isPresent = false;
			for(Item i : cartItems)
			{
				if(i.getName().equals(medi.getName()))
				{
					System.out.println("Adding the quantity");
					i.setQuantity(i.getQuantity()+1);
					totalValue+=i.getTotal();
					System.out.println("Total value is "+totalValue);
					isPresent = true;
					System.out.println("Added the quantity");
					break;
				}
			}
			
			if(!isPresent)
			{
				Item item = new Item(medi.getName(),1,medi.getPrice());
				totalValue+=medi.getPrice();
				System.out.println("Total value is "+totalValue);
				cartItems.add(item);
				System.out.println("Added the item to cart");
			}
				
			request.getSession().setAttribute("items_in_cart", cartItems);
			request.getSession().setAttribute("totalValue", totalValue);
		}
		mv.addObject("cartItems",cartItems);
		
		return mv;
		
	}
	
	
	@GetMapping("/updateQnty/{name}")
	@ResponseBody
	public ModelAndView IncreaseQuantity(@PathVariable String name, HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView("Cart");	
		Medicine medi = service.getMedicineByName(name);
		@SuppressWarnings("unchecked")
		List<Item> cartItems = (List<Item>) request.getSession().getAttribute("items_in_cart");
			for(Item i : cartItems)
			{
				if(i.getName().equals(medi.getName()))
				{
					System.out.println("Adding the quantity");
					i.setQuantity(i.getQuantity()+1);
					totalValue += medi.getPrice();
					System.out.println("Total value is "+totalValue);
					System.out.println("Added the quantity");
					break;
				}
			}

				
			request.getSession().setAttribute("items_in_cart", cartItems);
			request.getSession().setAttribute("totalValue", totalValue);
			mv.addObject("cartItems",cartItems);
			return mv;
		}
	
	@GetMapping("/deleteQnty/{name}")
	@ResponseBody
	public ModelAndView DeleteQuantity(@PathVariable String name, HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();	
		Medicine medi = service.getMedicineByName(name);
		@SuppressWarnings("unchecked")
		List<Item> cartItems = (List<Item>) request.getSession().getAttribute("items_in_cart");
			for(Item i : cartItems)
			{
				if(i.getName().equals(medi.getName()))
				{
					cartItems.remove(i);

					totalValue -= medi.getPrice() * i.getQuantity();
					System.out.println("Total value is "+totalValue);
					break;
				}
			}

				
			request.getSession().setAttribute("items_in_cart", cartItems);
			request.getSession().setAttribute("totalValue", totalValue);
			mv.addObject("cartItems",cartItems);
			@SuppressWarnings("unchecked")
			List<Item> check = (List<Item>) request.getSession().getAttribute("items_in_cart");
			if(check.size()!=0)
				mv.setViewName("Cart");
			else
				mv.setViewName("redirect:/index");
			return mv;
		}
		
		
		
	}
