package controllers.distributor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TaxService;

import controllers.AbstractController;
import domain.Item;
import domain.Tax;

@Controller
@RequestMapping("/tax/distributor")
public class TaxDistributorController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private TaxService taxService;
	
//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Tax> taxes;
		taxes=taxService.findAll();
		String requestURI="tax/distributor/list.do";
			
		result = new ModelAndView("tax/distributor/list");
		result.addObject("taxes",taxes);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
	@RequestMapping("/listFromItem")
	public ModelAndView list(@Valid Item item) {
		ModelAndView result;
		
		Collection<Tax> taxes;
		taxes=taxService.getTaxesFromItemId(item.getId());
		String requestURI="tax/distributor/listFromItem.do";
			
		result = new ModelAndView("tax/distributor/list");
		result.addObject("taxes",taxes);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
//Deleting------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")		
	public ModelAndView delete(Tax tax, BindingResult binding){
		ModelAndView result;
		try{
			taxService.delete(tax);
			result=new ModelAndView("redirect:list.do");
		} catch(Throwable oops){
			result=createEditModelAndView(tax,"comment.commit.error");
		}
		return result;
	}
	
//Saving------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")		
	public ModelAndView save(@Valid Tax tax, BindingResult binding){
		ModelAndView result;
		Assert.notNull(tax);
		if(binding.hasErrors()){
			result=createEditModelAndView(tax);
		} else{
			try{
				taxService.save(tax);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(tax,"request.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST,params="save")		
	public ModelAndView saveCreate(@Valid Tax tax, BindingResult binding){
		ModelAndView result;
		Assert.notNull(tax);
		if(binding.hasErrors()){
			result=createEditModelAndView(tax);
		} else{
			try{
				taxService.save(tax);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(tax,"request.commit.error");
			}
		}
		return result;
	}
//Creation----------------------
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Tax tax;
		
		tax = taxService.create();
		Assert.notNull(tax);
		result = createEditModelAndView(tax);
		
		return result;
		
	}
	
//Edition------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int taxId){
		ModelAndView result;
		Tax tax;
		
		tax = taxService.findOne(taxId);
		Assert.notNull(tax);
		result = createEditModelAndView(tax);
		
		return result;
	}
	
//Ancillary methods
	protected ModelAndView createEditModelAndView(Tax tax){
		ModelAndView result;
		result=createEditModelAndView(tax,null);		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Tax tax, String message){
		ModelAndView result;
		
		result=new ModelAndView("tax/distributor/edit");
		result.addObject("tax", tax);
		result.addObject("message", message);
		
		return result;
	}
}
