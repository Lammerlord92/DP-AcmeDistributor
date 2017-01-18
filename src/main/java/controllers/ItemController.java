package controllers;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryPriceService;
import services.ItemService;
import domain.Comment;
import domain.HistoryPrice;
import domain.Item;
import domain.Stock;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {
	
	// ----------------------- Managed service -----------------------
	
	@Autowired
	private ItemService itemService ;
	@Autowired
	private HistoryPriceService historyPriceService ;
	// ------------------------- Constructor -------------------------
	
	// --------------------------- Listing ---------------------------	

	@RequestMapping("/list")
	public ModelAndView listAll() {
		ModelAndView result;
		
		Collection<Item> items=itemService.findAll();
		Assert.notNull(items);
		
		String requestURI="item/list.do";
		result = new ModelAndView("item/list");
		
		result.addObject("items",items);
		result.addObject("requestURI", requestURI);
		return result;
	}
	@RequestMapping("/listByWarehouse")
	public ModelAndView listAll(@RequestParam int warehouseId) {
		ModelAndView result;
		
		Collection<Item> items=itemService.findAllByWarehouseId(warehouseId);
		Assert.notNull(items);
		
		String requestURI="item/listByWarehouse.do?warehouseId="+warehouseId;
		result = new ModelAndView("item/list");
		
		result.addObject("items",items);
		result.addObject("requestURI", requestURI);
		return result;
	}
	@RequestMapping("/details")
	public ModelAndView details(@RequestParam Integer itemId) {
		ModelAndView result;
		
		Item item=itemService.findOne(itemId);
		HistoryPrice lastHistoryPrice=historyPriceService.getLastFromItemId(itemId);
		Collection<Stock> stocks=item.getStocks();
		Collection<Comment> comments=item.getComments();
		Collection<HistoryPrice> historyPrices=item.getHistory();
		
		String requestURI="item/details.do";
		result = new ModelAndView("item/details");
		
		result.addObject("item",item);
		result.addObject("lastHistoryPrice",lastHistoryPrice);
		result.addObject("stocks",stocks);
		result.addObject("comments",comments);
		result.addObject("historyPrices",historyPrices);
		result.addObject("requestURI", requestURI);
		return result;
	}
}