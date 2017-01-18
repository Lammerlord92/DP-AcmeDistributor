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
import domain.Distributor;
import domain.HistoryPrice;
import domain.EntryRegister;
import domain.Item;
import domain.Money;
import domain.Warehouse;
import forms.ItemForm;
import security.LoginService;
import services.DistributorService;
import services.EntryRegisterService;
import services.HistoryPriceService;
import services.ItemService;
import services.WarehouseService;

@Controller
@RequestMapping("entryRegister/distributor")
public class EntryRegisterDistributorController extends AbstractController {
	
	// ----------------------- Managed service -----------------------
	
	@Autowired
	private EntryRegisterService entryRegisterService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private HistoryPriceService historyPriceService ;	
	@Autowired
	private WarehouseService warehouseService ;
	@Autowired
	private DistributorService distributorService;
	
	// ------------------------- Constructor -------------------------
	
	// --------------------------- Listing ---------------------------
	
	@RequestMapping("/listByWarehouse")
	public ModelAndView listAll(@RequestParam int warehouseId) {
		ModelAndView result;
		
		Collection<EntryRegister> entryRegisters=entryRegisterService.findByWarehouse(warehouseId);
		Assert.notNull(entryRegisters);
		
		String requestURI="entryRegister/distributor/listByWarehouse.do";
		result = new ModelAndView("entryRegister/list");
		result.addObject("entryRegisters",entryRegisters);
		result.addObject("requestURI", requestURI);
		return result;
	}

	// ---------------------------- Create ---------------------------
	
	@RequestMapping(value= "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int itemId) {
		ModelAndView result;
		Item item=itemService.findOne(itemId);
		EntryRegister entryRegister=entryRegisterService.create(item);
		result = createEditModelAndView(entryRegister);
		return result;
	}
	@RequestMapping(value="/create", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid EntryRegister entryRegister, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(entryRegister);
		}else{
			try{
				entryRegisterService.save(entryRegister);
				result=new ModelAndView("redirect:listByWarehouse.do?warehouseId="+entryRegister.getWarehouse().getId());
			}catch (Throwable oops) {
				result= createEditModelAndView(entryRegister,"producer.commit.error");
			}
		}
		
		return result;
		
	}
	
	// ----------------------------- Edit ----------------------------
	
		
	// ---------------------- Ancillary methods ----------------------
		
	private ModelAndView createEditModelAndView(EntryRegister entryRegister) {
		ModelAndView result;
		result = createEditModelAndView(entryRegister, null);
		return result;
	}
	
	private ModelAndView createEditModelAndView(EntryRegister entryRegister, String message) {
		ModelAndView result;

		HistoryPrice historyPrice=historyPriceService.getLastFromItemId(entryRegister.getItem().getId());
		Money money=new Money();
		if(historyPrice.getDistributorPrice()==null){
			Double newAmount=historyPrice.getProducerPrice().getAmount()+historyPrice.getProducerPrice().getAmount()*0.20;
			String newCurrency=historyPrice.getProducerPrice().getCurrency();
			money.setAmount(newAmount);
			money.setCurrency(newCurrency);
		}
		else money=historyPrice.getDistributorPrice();
		Distributor distributor=distributorService.findByUserAccount(LoginService.getPrincipal());
		Collection<Warehouse> warehouses=warehouseService.getWarehouseFromDistributorId(distributor.getId());
		result = new ModelAndView("entryRegister/distributor/create");
		result.addObject("entryRegister", entryRegister);
		result.addObject("amounts", money.getAmount());
		result.addObject("warehouses", warehouses);
		result.addObject("currencys", money.getCurrency());
		result.addObject("message", message);
		return result;
	}

}