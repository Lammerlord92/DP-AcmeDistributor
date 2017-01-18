package controllers.customer;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryPriceService;
import services.ItemService;
import services.ShoppingCartService;
import services.ShoppingLineService;

import controllers.AbstractController;
import domain.HistoryPrice;
import domain.Item;
import domain.ShoppingCart;
import domain.ShoppingLine;

@Controller
@RequestMapping("shoppingLine/customer")
public class ShoppingLineCustomerController extends AbstractController{
	//Services------------------------
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ShoppingLineService shoppingLineService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private HistoryPriceService historyPriceService;
	//Creation----------------------
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int itemId){
		ModelAndView result;
		ShoppingLine shoppingLine;
		
		shoppingLine = shoppingLineService.create();
		Assert.notNull(shoppingLine);
		
		ShoppingCart shoppingCart=shoppingCartService.findByShoppingCustomer();
		Item item=itemService.findOne(itemId);
		
		shoppingLine.setShoppingCart(shoppingCart);
		shoppingLine.setItem(item);
		shoppingLine.setQuantity(1);
		
		result = createEditModelAndView(shoppingLine);
		
		return result;
	}
	
	
	//Saving------------------------
	@RequestMapping(value="/create",method=RequestMethod.POST,params="save")		
	public ModelAndView save(@Valid ShoppingLine shoppingLine, BindingResult binding){
		ModelAndView result;
		
		Assert.notNull(shoppingLine);
		if(binding.hasErrors()){
			result=createEditModelAndView(shoppingLine);
		} else{
			try{
				shoppingLineService.save(shoppingLine);
				result=new ModelAndView("redirect:/customer/shoppingCart.do");
			} catch(Throwable oops){
				result=createEditModelAndView(shoppingLine,"request.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST,params="add")		
	public ModelAndView add(@Valid ShoppingLine shoppingLine, BindingResult binding){
		ModelAndView result;
		
		Assert.notNull(shoppingLine);
		if(binding.hasErrors()){
			result=createEditModelAndView(shoppingLine);
		} else{
			try{
				Integer quantityToAdd=shoppingLine.getQuantity();
				int itemId=shoppingLine.getItem().getId();
				int shoppingCartId=shoppingCartService.findByShoppingCustomer().getId();
				
				ShoppingLine sL=shoppingLineService.findByShoppingCartAndItemId(shoppingCartId, itemId);
				sL.setQuantity(sL.getQuantity()+quantityToAdd);
				shoppingLineService.save(sL);
				
				result=new ModelAndView("redirect:/customer/shoppingCart.do");
			} catch(Throwable oops){
				result=createEditModelAndView(shoppingLine,"request.commit.error");
			}
		}
		return result;
	}
	
	//Ancillary methods
	protected ModelAndView createEditModelAndView(ShoppingLine shoppingLine){
		ModelAndView result;
		result=createEditModelAndView(shoppingLine,null);		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(ShoppingLine shoppingLine, String message){
		ModelAndView result;
		
		Boolean newItem=true;
		ShoppingCart shoppingCart=shoppingCartService.findByShoppingCustomer();
		for(ShoppingLine sL:shoppingCart.getLines()){
			if(sL.getItem().getId()==shoppingLine.getItem().getId()) newItem=false;
		}
		HistoryPrice lastHistoryPrice=historyPriceService.getLastFromItemId(shoppingLine.getItem().getId());
		String itemname=shoppingLine.getItem().getName();
		String itemcategory=shoppingLine.getItem().getCategory();
		
		result=new ModelAndView("shoppingLine/customer/edit");
		result.addObject("shoppingLine", shoppingLine);
		result.addObject("message", message);
		result.addObject("newItem",newItem);
		result.addObject("lastHistoryPrice",lastHistoryPrice);
		result.addObject("item.name",itemname);
		result.addObject("item.category",itemcategory);
		return result;
	}
}
