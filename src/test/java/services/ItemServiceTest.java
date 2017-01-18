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

import domain.Item;
import domain.Item;
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
public class ItemServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private ItemService itemService;

	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is not authenticated must be able to:
	//List the items that the system stores.
	//Without any logged user, we will try to obtain all items.
	@Test
	public void testListAll(){
		Collection<Item> all=itemService.findAll();
		Assert.isTrue(all.size()==2);
	}
	
	//A user who is authenticated as Distributor must be able to:
	//List his items that the system stores.
	//With an existing distributor we will try to obtain all his items.
	@Test
	public void testListDistributor(){
		authenticate("distri1");
		Collection<Item> all=itemService.findAllByDistributor();
		Assert.isTrue(all.size()==1);
		unauthenticate();
	}
	
	//A user who is authenticated as Producer must be able to:
	//List his items that the system stores.
	//With an existing producer we will try to obtain all his items.
	@Test
	public void testListProducer(){
		authenticate("produc1");
		Collection<Item> all=itemService.findAllByProducer();
		Assert.isTrue(all.size()==2);
		unauthenticate();
	}
		
	//A user who is authenticated as an Producer must be able to:
	//Create items.
	//With an existing Producer we will try to create a item.
	@Test
	public void testCreateAndSave(){
		authenticate("produc1");
		
		Item item = itemService.create();
		itemService.save(item);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Edit items.
	//With an existing Customer we will try to edit a item.
	@Test
	public void testEditAndSave(){
		authenticate("produc1");
		
		Item itemDB = itemService.findAll().iterator().next();
		itemDB.setName("testing");
		
		itemService.save(itemDB);
		
		unauthenticate();
	}
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	
	//A user who is authenticated as Distributor must be able to:
	//List his items that the system stores.
	//Without an existing distributor we will try to obtain all his items.
	@Test(expected=IllegalArgumentException.class)
	public void testListDistributorException(){
		Collection<Item> all=itemService.findAllByDistributor();
		Assert.isTrue(all.size()==1);
	}
	
	//A user who is authenticated as Producer must be able to:
	//List his items that the system stores.
	//Without an existing producer we will try to obtain all his items.
	@Test(expected=IllegalArgumentException.class)
	public void testListProducerException(){
		Collection<Item> all=itemService.findAllByProducer();
		Assert.isTrue(all.size()==2);
	}
		
	//A user who is authenticated as an Producer must be able to:
	//Create items.
	//Without an existing Producer we will try to create a item.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		
		Item item = itemService.create();
		itemService.save(item);
	}
	
	//A user who is authenticated as an Producer must be able to:
	//Edit items.
	//Without an existing Producer we will try to edit a item.
	@Test(expected=IllegalArgumentException.class)
	public void testEditAndSaveException(){
		
		Item itemDB = itemService.findAll().iterator().next();
		itemDB.setName("testing");
		
		itemService.save(itemDB);
	}
	

}