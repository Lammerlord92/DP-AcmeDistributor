package controllers.distributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import domain.HistoryPrice;
import domain.Item;
import forms.CustomerDiscountForm;
import forms.ItemForm;

@Controller
@RequestMapping("/customer")
public class CustomerDistributorController {
	// ----------------------- Managed service -----------------------
	
		@Autowired
		private CustomerService customerService;
		
		// ------------------------- Constructor -------------------------
		
		// --------------------------- Listing ---------------------------
		
		@RequestMapping("/list")
		public ModelAndView listAll() {
			ModelAndView result;
			
			Collection<Customer> customers=customerService.findAll();
			Assert.notNull(customers);
			
			String requestURI="customer/list.do";
			result = new ModelAndView("customer/list");
			result.addObject("customers",customers);
			result.addObject("requestURI", requestURI);
			return result;
		}
		
		
		// --------------------------- Listing ---------------------------
		
		@RequestMapping(value= "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int customerId) {
			ModelAndView result;
			
			Customer customer = customerService.findOne(customerId);
			Assert.notNull(customer);
			
			CustomerDiscountForm customerForm = customerService.constructDiscount(customer);
			
			result = createEditModelAndView(customerForm);
			return result;
		}

		
		// --------------------------- Saving ---------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid CustomerDiscountForm customerForm, BindingResult binding){
			ModelAndView result;
			
			if(binding.hasErrors())
				result = createEditModelAndView(customerForm);
			else{
				try{
					Customer customer = customerService.reconstructDiscount(customerForm);
					customerService.save(customer);
					result = new ModelAndView("redirect:list.do");
				}catch(Throwable oops){
					result = createEditModelAndView(customerForm, "item.commit.error");
				}
			}
			
			return result;
		}

		// --------------------------- Other methods ---------------------------
		
		private ModelAndView createEditModelAndView(CustomerDiscountForm customerForm) {
			ModelAndView result;
			result=createEditModelAndView(customerForm,null);
			return result;
		}


		private ModelAndView createEditModelAndView(CustomerDiscountForm customerForm, String message) {
			ModelAndView result;
			
			result=new ModelAndView("customer/edit");
			result.addObject("customerDiscountForm",customerForm);
			result.addObject("message",message);
			return result;
		}
}
