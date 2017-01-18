package controllers;

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

import services.WarehouseService;

import controllers.AbstractController;
import domain.Distributor;
import domain.Item;
import domain.Tax;
import domain.Warehouse;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController extends AbstractController {
//Services --------------------------------------------------
	@Autowired
	private WarehouseService warehouseService;
	
//Listing------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		
		Collection<Warehouse> warehouses;
		warehouses=warehouseService.findAll();
		String requestURI="warehouse/list.do";
			
		result = new ModelAndView("warehouse/list");
		result.addObject("warehouses",warehouses);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
	@RequestMapping("/locationWarehouse")
	public ModelAndView locationWarehouse(@RequestParam int warehouseId) {
		ModelAndView result;
		
		Warehouse warehouse = warehouseService.findOne(warehouseId);
		
		String requestURI="warehouse/locationWarehouse.do";
		String name = warehouse.getName();
		String email = warehouse.getEmail();
		String address = warehouse.getAddress();
		String phone = warehouse.getContactPhone();
		String distName = warehouse.getDistributor().getName();
		Double lat = warehouse.getLatitude();
		Double lon = warehouse.getLongitude();
		
		result = new ModelAndView("warehouse/locationWarehouse");
		result.addObject("id",warehouse.getId());
		result.addObject("lat",lat);
		result.addObject("lon",lon);
		result.addObject("name",name);
		result.addObject("email",email);
		result.addObject("address",address);
		result.addObject("phone",phone);
		result.addObject("distName",distName);
		result.addObject("requestURI",requestURI);
		return result;
	}
	
}
