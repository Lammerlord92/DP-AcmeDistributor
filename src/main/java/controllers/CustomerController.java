/* CustomerController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Customer;

import security.UserAccountRepository;
import services.CustomerService;
import services.FolderService;
import forms.CustomerForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	public CustomerController() {
		super();
	}
	//	Services --------------------------------------------------------------
	@Autowired 
	private CustomerService customerService;
	@Autowired
	private FolderService folderService;

	//	Create ----------------------------------------------------------------
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CustomerForm customerForm;
		customerForm=new CustomerForm();
		
		result= createEditModelAndView(customerForm);
		
		return result;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid CustomerForm customerForm, BindingResult binding) {
		ModelAndView result;
		Customer customer;
		if(binding.hasErrors()){
			result=createEditModelAndView(customerForm);
		}else{
			try{
				customer=customerService.reconstruct(customerForm);
				customerService.save(customer);
				List<Customer> c=(List<Customer>) customerService.findAll();
				folderService.saveFolders(c.get(c.size()-1));
				result=new ModelAndView("redirect:../security/login.do");
			}catch (Throwable oops) {
				result= createEditModelAndView(customerForm,"customer.commit.error");
			}
		}
		
		return result;
		
	}
	
	// Ancillary methods
	protected ModelAndView createEditModelAndView(CustomerForm customerForm){
		ModelAndView result;
		result=createEditModelAndView(customerForm,null);
		return result;
	}
	protected ModelAndView createEditModelAndView(CustomerForm customerForm, String message){
		ModelAndView result;
		
		result=new ModelAndView("customer/register");
		result.addObject("customerForm",customerForm);
		result.addObject("message",message);
		return result;
	}

}