package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.TaxService;

import domain.HistoryPrice;
import domain.Item;
import domain.Tax;
@Controller
@RequestMapping("/tax")
public class TaxController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private TaxService taxService;
	
//Listing------------------------	
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
}
