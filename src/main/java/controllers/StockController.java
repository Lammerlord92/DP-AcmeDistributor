package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.util.Collection;

import javax.validation.Valid;

import domain.Item;
import domain.Stock;
import domain.Warehouse;
import services.StockService;


@Controller
@RequestMapping("/stock")
public class StockController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private StockService stockService;
	
//Listing------------------------
	@RequestMapping(value="/listFromWarehouse", method = RequestMethod.GET)
	public ModelAndView list(@Valid Warehouse warehouse) {
		ModelAndView result;
	    Collection<Object[]> stock=stockService.getStocksFromWarehouseId(warehouse.getId());
	    String requestURI="stock/listFromWarehouse.do";
	    
	    result = new ModelAndView("stock/list");
	    result.addObject("stock",stock);
	    result.addObject("requestURI",requestURI);
		return result;
	}
	
//Listing------------------------
	@RequestMapping(value="/listFromItemWarehouse", method = RequestMethod.GET)
	public ModelAndView list(@Valid Warehouse warehouse,@Valid Item item) {
		ModelAndView result;
	    Stock stock=stockService.getStockFromWarehouseIdItemId(warehouse.getId(), item.getId());
	    String requestURI="stock/listFromItemWarehouse.do";
	    
	    result = new ModelAndView("stock/list");
	    result.addObject("stock",stock);
	    result.addObject("requestURI",requestURI);
		return result;
	}
}
