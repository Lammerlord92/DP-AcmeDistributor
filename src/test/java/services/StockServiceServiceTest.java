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
import domain.Stock;
import domain.Item;
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
public class StockServiceServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private StockService stockService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private WarehouseService warehouseService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
		
	//The system must be able to:
	//Find the stocks for a Warehouse.
	//Without any logged user we will try to find Stocks for a Warehouse.
	@Test
	public void testGetStocksFromWarehouseId(){
		Item item = itemService.findAll().iterator().next();
		Warehouse warehouse = warehouseService.findAll().iterator().next();
		Stock stock = stockService.create();
		stock.setQuantity(12);
		stock.setItem(item);
		stock.setWarehouse(warehouse);
		stockService.save(stock);
		
		Collection<Object[]> all = stockService.getStocksFromWarehouseId(warehouse.getId());
		Assert.notEmpty(all);
	}
	
	//The system must be able to:
	//Find the stock for an Item.
	//Without any logged we will try to find Stocks for an Item.
	@Test
	public void testGetStockFromItemId(){
		Item item = itemService.findAll().iterator().next();
		Warehouse warehouse = warehouseService.findAll().iterator().next();
		Stock stock = stockService.create();
		stock.setQuantity(12);
		stock.setItem(item);
		stock.setWarehouse(warehouse);
		stockService.save(stock);
		
		Collection<Stock> all=stockService.getAllStockFromItemId(item);
		Assert.notEmpty(all);
	}
	
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
		
	//The system must be able to:
	//Find the stock of an Item and a Warehouse.
	//Without any logged user we will try to find Stocks for an Item and a Warehouse.
	@Test(expected=IllegalArgumentException.class)
	public void testGetStockFromWarehouseIdItemIdException(){
		Item item = itemService.findAll().iterator().next();
		
		Stock st=stockService.getStockFromWarehouseIdItemId(112132, item.getId());
		Assert.notNull(st);	
	}
	
	//The system must be able to:
	//Find thes stocks for an Item.
	//Without any logged we will try to find Stocks for an Item
	//with a null object instead of that Item.
	@Test(expected=NullPointerException.class)
	public void testGetStockFromItemIdException(){
		Collection<Stock> all=stockService.getAllStockFromItemId(null);
		Assert.notEmpty(all);
	}
}