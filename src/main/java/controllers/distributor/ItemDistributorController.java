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
import controllers.AbstractController;
import domain.HistoryPrice;
import domain.Item;
import forms.ItemForm;
import services.ItemService;

@Controller
@RequestMapping("/item/distributor")
public class ItemDistributorController extends AbstractController {
	
	// ----------------------- Managed service -----------------------
	
	@Autowired
	private ItemService itemService;
	
	// ------------------------- Constructor -------------------------
	
	// --------------------------- Listing ---------------------------
	
	@RequestMapping("/list")
	public ModelAndView listAll() {
		ModelAndView result;
		
		Collection<Item> items=itemService.findAllByDistributor();
		Assert.notNull(items);
		
		String requestURI="item/distributor/list.do";
		result = new ModelAndView("item/list");
		result.addObject("items",items);
		result.addObject("requestURI", requestURI);
		return result;
	}

	// ---------------------------- Create ---------------------------
	
	@RequestMapping(value= "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		ItemForm itemForm = new ItemForm();
		itemForm.setItemId(0);
		result = createEditModelAndView(itemForm);
		result.addObject("itemForm",itemForm);
		return result;
	}
	
	// ----------------------------- Edit ----------------------------
	
	@RequestMapping(value= "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int itemId) {
		ModelAndView result;
		
		Item item = itemService.findOne(itemId);
		Assert.notNull(item);
		
		ItemForm itemForm = itemService.construct(item);
		
		List <HistoryPrice> historyPrices = new ArrayList<HistoryPrice>(item.getHistory());
		HistoryPrice historyPrice = historyPrices.get(historyPrices.size()-1);
		itemForm.setPrice(historyPrice.getDistributorPrice());
		
		result = createEditModelAndView(itemForm);
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ItemForm itemForm, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = createEditModelAndView(itemForm);
		else{
			try{
				Item item = itemService.reconstruct(itemForm);
				itemService.save(item);
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(itemForm, "item.commit.error");
			}
		}
		
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView cancel(ItemForm itemForm, BindingResult binding){
		ModelAndView result;
		
		try{
			Item item = itemService.reconstruct(itemForm);
			itemService.delete(item);
			result = new ModelAndView("redirect:../list.do");
		}catch(Throwable oops){
			result = createEditModelAndView(itemForm, "item.commit.error");
		}
		
		return result;
	}
		
	// ---------------------- Ancillary methods ----------------------
		
	private ModelAndView createEditModelAndView(ItemForm itemForm) {
		ModelAndView result;
		result = createEditModelAndView(itemForm, null);
		return result;
	}
	
	private ModelAndView createEditModelAndView(ItemForm itemForm, String message) {
		ModelAndView result;
		
		result = new ModelAndView("item/distributor/edit");
		result.addObject("itemForm", itemForm);
		result.addObject("message", message);
		return result;
	}

}