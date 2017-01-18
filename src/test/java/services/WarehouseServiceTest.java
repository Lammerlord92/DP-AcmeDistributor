package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.RollbackException;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Distributor;
import domain.Item;
import domain.Warehouse;
import domain.Warehouse;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@SuppressWarnings("unused")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class WarehouseServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private ItemService itemService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------

	//A Ditributor must be able to:
	//Create warehousees.
	//With a logged Distributor we will try to create a warehouse.
	@Test
	public void testCreateAndSave(){
		authenticate("distri1");
		
		Warehouse warehouse = warehouseService.create();
		warehouseService.save(warehouse);
		
		unauthenticate();
	}
	
	//A Ditributor must be able to:
	//Edit warehouses.
	//With a logged Distributor we will try to edit a warehouse.
	@Test
	public void testEditAndSave(){
		authenticate("distri1");
		
		Warehouse warehouse = warehouseService.findAll().iterator().next();
		warehouse.setName("test");
		warehouseService.save(warehouse);
		
		unauthenticate();
	}
	
	//The system must be able to:
	//Find the warehouse of an Item.
	//Without any logged user we will try to find Warehouses for an Item.
	@Test
	public void testGetWarehouseesFromItemId(){
		Item item = itemService.findAll().iterator().next();
		
		Collection<Warehouse> all = warehouseService.getWarehouses(item);
		Assert.notEmpty(all);	
	}
	
	//The system must be able to:
	//Find the warehouse of an Item.
	//Without any logged user we will try to find Warehouses for an Item.
	@Test
	public void testGetWarehouseFromDistributorId(){
		Warehouse warehouse = warehouseService.findAll().iterator().next();
		Distributor distributor = warehouse.getDistributor();
		
		Collection<Warehouse> all = warehouseService.getWarehouseFromDistributorId(distributor.getId());

		Assert.isTrue(all.contains(warehouse));	
	}
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	

	//A Ditributor must be able to:
	//Create warehousees.
	//Without a logged Distributor we will try to create a warehouse.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		
		Warehouse warehouse = warehouseService.create();
		warehouseService.save(warehouse);

	}
	
	//A Ditributor must be able to:
	//Edit warehouses.
	//With a logged Distributor we will try to edit a warehouse
	//providing instead a null object to save.
	@Test(expected=IllegalArgumentException.class)
	public void testEditAndSaveException(){
		authenticate("distri1");
		
		warehouseService.save(null);
		
		unauthenticate();
	}
	
	//The system must be able to:
	//Find the warehouse of an Item.
	//Without any logged user we will try to find Warehouses for an Item
	//with an incorrect Item Id.
	@Test(expected=NullPointerException.class)
	public void testGetWarehouseesFromItemIdException(){
		Collection<Warehouse> all = warehouseService.getWarehouses(null);
		Assert.notEmpty(all);	
	}
	
	//The system must be able to:
	//Find the warehouse of an Item.
	//Without any logged user we will try to find Warehouses for an Item.
	@Test(expected=IllegalArgumentException.class)
	public void testGetWarehouseFromDistributorIdException(){		
		Collection<Warehouse> all = warehouseService.getWarehouseFromDistributorId(1111);

		Assert.notEmpty(all);	
	}
}