package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import security.LoginService;
import services.CustomerService;
import services.ShoppingCartService;
import services.ShoppingLineService;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Customer;
import domain.Message;
import domain.ShoppingCart;
import domain.ShoppingLine;

@Controller
@RequestMapping("/customer")
public class ShoppingCartCustomerController extends AbstractController{
	//Services------------------------
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ShoppingLineService shoppingLineService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/shoppingCart",method=RequestMethod.GET)
	public ModelAndView shoppingCart(){
		ModelAndView result;
		
		ShoppingCart shoppingCart=shoppingCartService.findByShoppingCustomer();
		Customer customer=customerService.findByUserAccount(LoginService.getPrincipal());
		Collection<ShoppingLine> lines;
		lines=shoppingLineService.findByShoppingCartId(shoppingCart.getId());
		

		result=new ModelAndView("customer/shoppingCart");
		
		
		result.addObject("shoppingCart", shoppingCart);
		result.addObject("customer", customer);
		result.addObject("lines", lines);
		
		
		return result;
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int shoppingLineId){
		ModelAndView result=null;
		
		ShoppingLine shoppingLine = shoppingLineService.findOne(shoppingLineId);

		try{
			shoppingLineService.delete(shoppingLine);
			result= new ModelAndView("redirect:shoppingCart.do");
		}catch(Throwable oops){
			result= new ModelAndView("redirect:shoppingCart.do");
		}
		
		return result;
	}
}
