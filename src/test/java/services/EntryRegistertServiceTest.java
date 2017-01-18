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

import domain.EntryRegister;
import domain.Item;
import domain.Money;
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
public class EntryRegistertServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private EntryRegisterService entryRegisterService;
	@Autowired
	private ItemService itemService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//The system must be able to:
	//Edit one entryRegisters.
	//We will try to edit an existing Entry Register.
	@Test(expected=NullPointerException.class)
	public void testCreateAndSave(){
		authenticate("distri1");
		Collection<Item> items=itemService.findAll();
		EntryRegister entryRegister = entryRegisterService.create(items.iterator().next());
		entryRegister.setQuantity(1);
		entryRegisterService.save(entryRegister);
		authenticate(null);
	}		
	//A distributor must be able to:
	//Find the entryRegisters for a Warehouse.
	//We will try to find EntryRegisters for a Warehouse.
	@Test
	public void testFindByWarehouse(){
		authenticate("distri1");
		Collection<EntryRegister> all=entryRegisterService.findByWarehouse(16);
		Assert.isTrue(all.size()==1);
		authenticate(null);
	}

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	//The system must be able to:
	//Edit one entryRegisters.
	//We will try to edit an existing Entry Register.
	@Test(expected=NullPointerException.class)
	public void testCreateAndSaveException(){
		authenticate(null);
		Collection<Item> items=itemService.findAll();
		EntryRegister entryRegister = entryRegisterService.create(items.iterator().next());
		entryRegister.setQuantity(1);
		entryRegisterService.save(entryRegister);	
	}
	//The system must be able to:
	//Find the entryRegisters for a Warehouse.
	//We will try to find EntryRegisters for a Warehouse
	//without being identified.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByWarehouseException(){
		
		Collection<EntryRegister> all=entryRegisterService.findByWarehouse(21+10);
		Assert.isTrue(all.size()==1);
	}
}