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
import controllers.AbstractController;
import domain.Item;
import domain.Stock;
import domain.Warehouse;
import forms.StockForm;
import services.StockService;
import services.WarehouseService;

@Controller
@RequestMapping("/stock/distributor")
public class StockDistributorController extends AbstractController {
	
	// ----------------------- Managed service -----------------------
	
	@Autowired
	private StockService stockService;
	
	// ----------------------- Support service -----------------------

	@Autowired
	private WarehouseService warehouseService;
	
	// ------------------------- Constructor -------------------------
	
	// --------------------------- Listing ---------------------------
	
	// ---------------------------- Create ---------------------------
	
	@RequestMapping(value= "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		StockForm stockForm = new StockForm();
		stockForm.setItemId(0);
		stockForm.setStockId(0);
		
		result = createEditModelAndView(stockForm);
		result.addObject("stockForm",stockForm);
		return result;
	}
	
	// --------------------------- Restock ---------------------------
	
	@RequestMapping(value= "/restock", method = RequestMethod.GET)
	public ModelAndView restock(@RequestParam int stockId) {
		ModelAndView result;
		
		Stock stock = stockService.findOne(stockId);
		Assert.notNull(stock);
		
		Item item = stock.getItem();
		Collection<Warehouse> warehousesInItem = warehouseService.getWarehouses(item);
		Collection<Warehouse> warehouses = warehouseService.findAll();
		StockForm stockForm = stockService.contruct(stock);
		
		result = createEditModelAndView(stockForm);
		result.addObject("itemName",item.getName());
		result.addObject("warehouses",warehouses);
		result.addObject("warehousesInItem",warehousesInItem);
		return result;
	}
	
	@RequestMapping(value = "/restock", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid StockForm stockForm, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors())
			result = createEditModelAndView(stockForm);
		else{
			try{
				Stock stock = stockService.reconstruct(stockForm);
				stockService.save(stock);
				result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(stockForm, "stock.commit.error");
			}
		}
		
		return result;
	}
	
	// ---------------------- Ancillary methods ----------------------
	
	private ModelAndView createEditModelAndView(StockForm stockForm) {
		ModelAndView result;
		result = createEditModelAndView(stockForm, null);
		return result;
	}
	
	private ModelAndView createEditModelAndView(StockForm stockForm, String message) {
		ModelAndView result;
		
		result = new ModelAndView("stock/distributor/restock");
		result.addObject("stockForm", stockForm);
		result.addObject("message", message);
		return result;
	}
}