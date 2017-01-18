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
import domain.Tax;
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
public class TaxServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private TaxService taxService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private WarehouseService warehouseService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//The system must be able to:
	//List the taxs that the system stores.
	//Without any logged user, we will try to obtain all taxs.
	@Test
	public void testListAll(){
		Collection<Tax> all=taxService.findAll();
		Assert.isTrue(all.size()==1);
	}

	//A Ditributor must be able to:
	//Create taxes.
	//With a logged Distributor we will try to create a tax.
	@Test
	public void testCreateAndSave(){
		authenticate("distri1");
		
		Tax tax = taxService.create();
		taxService.save(tax);
		
		unauthenticate();
	}
	
	//A Ditributor must be able to:
	//Edit taxs.
	//With a logged Distributor we will try to edit a tax.
	@Test
	public void testEditAndSave(){
		authenticate("distri1");
		
		Tax tax = taxService.findAll().iterator().next();
		tax.setPercent(1.1);
		taxService.save(tax);
		
		unauthenticate();
	}
	
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	

	//A Ditributor must be able to:
	//Create taxes.
	//Without a logged Distributor we will try to create a tax.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		
		Tax tax = taxService.create();
		taxService.save(tax);

	}
	
	//A Ditributor must be able to:
	//Edit taxs.
	//With a logged Distributor we will try to edit a tax
	//providing instead a null object to save.
	@Test(expected=IllegalArgumentException.class)
	public void testEditAndSaveException(){
		authenticate("distri1");
		
		taxService.save(null);
		
		unauthenticate();
	}

	
}