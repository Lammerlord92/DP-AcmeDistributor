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

import domain.HistoryPrice;
import domain.Item;
import domain.Money;
import security.Authority;
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
public class HistoryPriceServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private HistoryPriceService historyPriceService;
	@Autowired
	private ItemService itemService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is authenticated as a Distributor must be able to:
	//Find the historyPrices for an Item.
	//With an existing Distributor we will try to find History Prices for an Item.
	@Test
	public void testGetHistoryPriceFromItemId(){
		authenticate("distri1");
		Item item = itemService.findAll().iterator().next();		
		
		Collection<HistoryPrice> all = historyPriceService.getHistoryPriceFromItemId(item.getId());
		Assert.notEmpty(all);
		
		unauthenticate();
	}
	

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
		
	
	//A user who is authenticated as a Distributor must be able to:
	//Find the historyPrices for an Item.
	//With an existing Distributor we will try to find History Prices for an Item
	//with an incorrect Id.
	@Test(expected=NullPointerException.class)
	public void testGetHistoryPriceFromItemIdException(){
		authenticate("distri1");
		Item item = itemService.findAll().iterator().next();		
		
		Collection<HistoryPrice> all = historyPriceService.getHistoryPriceFromItemId((Integer) null);
		Assert.notEmpty(all);
		
		unauthenticate();
	}	
}