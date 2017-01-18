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

import domain.Folder;
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
public class FolderServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private FolderService folderService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//The system must be able to:
	//Create folders.
	@Test
	public void testCreateAndSave(){
		
		Folder folderName = folderService.findAll().iterator().next();
		Folder folder = folderService.create(folderName);

		folderService.save(folder);
	}
	
	//A user who is authenticated as a Customer must be able to:
	//Find his folders.
	//With an existing Customer we will try to find the Outbox.
	@Test
	public void testGetOutboxByActorId(){
		authenticate("cust1");
		
		Folder folder = folderService.getOutboxByActorId();
		Assert.notNull(folder);
		
		unauthenticate();
	}
	
	//A user who is authenticated as a Customer must be able to:
	//Find his folders.
	//With an existing Customer we will try to find the Inbox.
	@Test
	public void testGetInboxByActorId(){
		authenticate("cust1");
		
		Folder folder = folderService.getInboxByActorId();
		Assert.notNull(folder);
		
		unauthenticate();
	}

	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	
	//The system must be able to:
	//Create folders.
	//Without an existing Actor we will try to create a folder
	//but will force a error trying to save a null.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		
		Folder folderName = folderService.findAll().iterator().next();
		Folder folder = folderService.create(folderName);
		
		folderService.save(null);
	}
	
	//A user who is authenticated as a Customer must be able to:
	//Find the folders for an Item.
	//Without an existing Customer we will try to find the Outbox.
	@Test(expected=IllegalArgumentException.class)
	public void testGetOutboxByActorIdException(){
		
		Folder folder = folderService.getOutboxByActorId();
		Assert.notNull(folder);

	}
	
	//A user who is authenticated as a Customer must be able to:
	//Find the folders for an Item.
	//Without an existing Customer we will try to find the Inbox.
	@Test(expected=IllegalArgumentException.class)
	public void testGetInboxByActorIdException(){
		
		Folder folder = folderService.getInboxByActorId();
		Assert.notNull(folder);
		
	}
	
}