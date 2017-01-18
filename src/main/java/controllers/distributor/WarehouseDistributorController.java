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

import security.LoginService;
import services.DistributorService;
import services.WarehouseService;

import controllers.AbstractController;
import domain.Distributor;
import domain.Item;
import domain.Tax;
import domain.Warehouse;

@Controller
@RequestMapping("/warehouse/distributor")
public class WarehouseDistributorController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private DistributorService distributorService;
	
//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Distributor distributor=distributorService.findByUserAccount(LoginService.getPrincipal());
		Collection<Warehouse> warehouses;
		warehouses=warehouseService.getWarehouseFromDistributorId(distributor.getId());
		String requestURI="warehouse/distributor/list.do";
			
		result = new ModelAndView("warehouse/distributor/list");
		result.addObject("warehouses",warehouses);
		result.addObject("requestURI",requestURI);
		result.addObject("isMine",true);
		return result;
	}
	
//	@RequestMapping("/listFromDistributor")
//	public ModelAndView list(@Valid Distributor distributor) {
//		ModelAndView result;
//		
//		Collection<Warehouse> warehouses;
//		warehouses=warehouseService.getWarehouseFromDistributorId(distributor.getId());
//		String requestURI="warehouse/distributor/list.do";
//			
//		result = new ModelAndView("warehouse/distributor/list");
//		result.addObject("warehouses",warehouses);
//		result.addObject("requestURI",requestURI);
//		return result;
//	}
	
//Deleting------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")		
	public ModelAndView delete(Warehouse warehouse, BindingResult binding){
		ModelAndView result;
		try{
			warehouseService.delete(warehouse);
			result=new ModelAndView("redirect:list.do");
		} catch(Throwable oops){
			result=createEditModelAndView(warehouse,"comment.commit.error");
		}
		return result;
	}
	
//Saving------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="save")		
	public ModelAndView save(@Valid Warehouse warehouse, BindingResult binding){
		ModelAndView result;
		Assert.notNull(warehouse);
		if(binding.hasErrors()){
			result=createEditModelAndView(warehouse);
		} else{
			try{
				warehouseService.save(warehouse);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(warehouse,"request.commit.error");
			}
		}
		return result;
	}
	
	//Saving------------------------
	@RequestMapping(value="/create",method=RequestMethod.POST,params="save")		
	public ModelAndView saveCreate(@Valid Warehouse warehouse, BindingResult binding){
		ModelAndView result;
		Assert.notNull(warehouse);
		if(binding.hasErrors()){
			result=createEditModelAndView(warehouse);
		} else{
			try{
				warehouseService.save(warehouse);
				result=new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result=createEditModelAndView(warehouse,"request.commit.error");
			}
		}
		return result;
	}
	
//Creation----------------------
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Warehouse warehouse;
		
		warehouse = warehouseService.create();
		Assert.notNull(warehouse);
		result = createEditModelAndView(warehouse);
		
		return result;
		
	}
	
//Edition------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int warehouseId){
		ModelAndView result;
		Warehouse warehouse;
		
		warehouse = warehouseService.findOne(warehouseId);
		Assert.notNull(warehouse);
		result = createEditModelAndView(warehouse);
		
		return result;
	}
	
//Ancillary methods
	protected ModelAndView createEditModelAndView(Warehouse warehouse){
		ModelAndView result;
		result=createEditModelAndView(warehouse,null);		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Warehouse warehouse, String message){
		ModelAndView result;
		
		result=new ModelAndView("warehouse/distributor/edit");
		result.addObject("warehouse", warehouse);
		result.addObject("message", message);
		
		return result;
	}
}
