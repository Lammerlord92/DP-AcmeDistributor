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
import domain.Item;
import domain.Message;
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
public class MessageServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private FolderService folderService;	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//All authenticated users must be able to:
	//Send message.
	@Test
	public void testCreateAndSave(){
		authenticate("cust1");
		Message message=messageService.create();
		message.setRecipientId(26);
		message.setSubject("asdasdsad");
		message.setBody("adasdasd");
		messageService.save(message);
		unauthenticate();
	}
	
	//A user who is authenticated as a Customer must be able to:
	//Find the messages of his/her folders.
	@Test
	public void testGetMessagesByActorId(){
		authenticate("cust1");
		
		Collection<Message> messagesI= messageService.getFromInbox();
		Collection<Message> messagesO = messageService.getFromOutbox();
		Assert.notNull(messagesI);
		Assert.notNull(messagesO);
		
		unauthenticate();
	}
	//All authenticated users must be able to:
	//Reply message.
		@Test
	public void testReplyAndSave(){
		authenticate("cust1");
		Message message=messageService.create();
		message.setRecipientId(26);
		message.setSubject("asdasdsad");
		message.setBody("adasdasd");
		messageService.save(message);
		unauthenticate();
		authenticate("cust2");
		Message reply=messageService.prepareReply(message);
		reply.setBody("asdsadadasd");
		messageService.save(reply);
	}
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	
		//All authenticated users must be able to:
		//Send message.
		//Trying with an unregistered user
		@Test(expected=IllegalArgumentException.class)
		public void testCreateAndSaveException(){
			authenticate(null);
			Message message=messageService.create();
			message.setRecipientId(26);
			message.setSubject("asdasdsad");
			message.setBody("adasdasd");
			messageService.save(message);
			unauthenticate();
		}
		
		//A user who is authenticated as a Customer must be able to:
		//Find the messages of his/her folders.
		//Trying with an unregistered user
		@Test(expected=IllegalArgumentException.class)
		public void testGetMessagesByActorIdException(){
			authenticate(null);
			
			Collection<Message> messagesI= messageService.getFromInbox();
			Collection<Message> messagesO = messageService.getFromOutbox();
			Assert.notNull(messagesI);
			Assert.notNull(messagesO);
			
			unauthenticate();
		}
		//All authenticated users must be able to:
		//Reply message.
		//Trying with an unregistered user
		@Test(expected=IllegalArgumentException.class)
		public void testReplyAndSaveException(){
			authenticate(null);
			Message message=messageService.create();
			message.setRecipientId(26);
			message.setSubject("asdasdsad");
			message.setBody("adasdasd");
			messageService.save(message);
			unauthenticate();
			authenticate("cust2");
			Message reply=messageService.prepareReply(message);
			reply.setBody("asdsadadasd");
			messageService.save(reply);
		}
}