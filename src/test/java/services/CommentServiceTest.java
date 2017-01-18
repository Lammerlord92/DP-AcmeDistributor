package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.common.AssertionFailure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Customer;
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
public class CommentServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CustomerService customerService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is authenticated as Customer must be able to:
	//List the comments that the system stores.
	//With an existing customer, we will try to obtain all comments.
	@Test
	public void testListCustomer(){
		authenticate("cust1");
		Collection<Comment> all=commentService.findAll();
		Assert.isTrue(all.size()==1);
		unauthenticate();
	}
		
	//A user who is authenticated as an Customer must be able to:
	//Create comments.
	//With an existing Customer we will try to create a comment.
	@Test
	public void testCreateAndSave(){
		authenticate("cust1");
		
		Item item = itemService.findAll().iterator().next();
		Comment comment = commentService.create(item);
		comment.setText("testing");

		commentService.save(comment);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Edit comments.
	//With an existing Customer we will try to edit a comment.
	@Test
	public void testEditAndSave(){
		authenticate("cust1");
		Item item = itemService.findAll().iterator().next();
		Comment comment = commentService.create(item);
		comment.setText("testing");
		commentService.save(comment);		
		
		Comment commentDB = commentService.findAll().iterator().next();
		comment.setText("new testing");
		
		commentService.save(commentDB);
		
		unauthenticate();
	}
	

	//A user who is not authenticated  must be able to:
	//Find the comments for an Item.
	//Without an existing Customer we will try to find Comments for an Item.
	@Test
	public void testFindByItemId(){
		authenticate(null);
		
		Collection<Comment> all=commentService.findByItemId(12);
		Assert.isTrue(all.size()==1);	
	}
	
	//A user who is authenticated as an Distributor must be able to:
	//Find the comments for an Item.
	//With an existing Distributor we will try to find Comments for an Item.
	@Test
	public void testFindByItemIdDistributor(){
		authenticate("distri1");
		
		Collection<Comment> all=commentService.findByItemId(12);
		Assert.isTrue(all.size()==1);	
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Producer must be able to:
	//Find the comments for an Item.
	//With an existing Producer we will try to find Comments for an Item.
	@Test
	public void testFindByItemIdProducer(){
		authenticate("produc1");
		Collection<Comment> all=commentService.findByItemId(12);
		Assert.isTrue(all.size()==1);	
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Find the comments for an Item.
	//With an existing Customer we will try to find Comments for an Item.
	@Test
	public void testFindByItemIdCustomer(){
		authenticate("cust1");
		Collection<Comment> all=commentService.findByItemId(12);
		Assert.isTrue(all.size()==1);	
	
		unauthenticate();
	}
	
	
	//A user who is authenticated as an Customer must be able to:
	//Find his/her comments.
	//With an existing Customer we will try to find Comments for a Customer.
	@Test
	public void testFindByCustomerIdCustomer(){
		authenticate("cust1");
		
		UserAccount customerAccount=LoginService.getPrincipal();
		Customer customer=customerService.findByUserAccount(customerAccount);
		Collection<Comment> all=commentService.findByCustomerId(customer.getId());
		Assert.isTrue(all.size()==1);
		
		unauthenticate();
	}
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	
	//A user who is authenticated as Customer must be able to:
	//List the comments that the system stores.
	//With an existing producer we will expect a wrong result and catch the exception.
	@Test(expected=IllegalArgumentException.class)
	public void testListProducerException(){
		authenticate("produc1");
		Collection<Comment> all=commentService.findAll();
		Assert.isTrue(all.size()==1);
		unauthenticate();
	}
		
	//A user who is authenticated as an Customer must be able to:
	//Create comments.
	//With an existing Producer we will try to create a comment and 
	//catch the exception produced.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		authenticate("produc1");
		
		Item item = itemService.findAll().iterator().next();
		Comment comment = commentService.create(item);
		comment.setText("aasdsads");
		commentService.save(comment);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Edit comments.
	//With an existing Distributor we will try to create a comment and 
	//catch the exception produced.
	@Test(expected=IllegalArgumentException.class)
	public void testEditAndSaveException(){
		authenticate("distri1");
		Comment comment = commentService.findAll().iterator().next();
		comment.setText("aaaa");
		commentService.save(comment);
		
		unauthenticate();
	}
	
	//A user who is not authenticated  must be able to:
	//Find the comments for an Item.
	//Without an existing Customer we will try to find Comments for an Item
	//with an incorrect Id.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByItemIdException(){
		unauthenticate();
		
		Collection<Comment> all=commentService.findByItemId(99999999);
		Assert.isTrue(all.size()==1);	
	}
	
	//A user who is authenticated as an Distributor must be able to:
	//Find the comments for an Item.
	//With an existing Distributor we will try to find Comments for an Item
	//with an incorrect Id.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByItemIdDistributorException(){
		authenticate("distri1");

		Collection<Comment> all=commentService.findByItemId(99999999);
		Assert.isTrue(all.size()==1);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Producer must be able to:
	//Find the comments for an Item.
	//With an existing Producer we will try to find Comments for an Item
	//with an incorrect Id.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByItemIdProducerException(){
		authenticate("produc1");

		Collection<Comment> all=commentService.findByItemId(99999999);
		Assert.isTrue(all.size()==1);
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Find the comments for an Item.
	//With an existing Customer we will try to find Comments for an Item
	//with an incorrect Id.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByItemIdCustomerException(){
		authenticate("cust1");

		Collection<Comment> all=commentService.findByItemId(99999999);
		Assert.isTrue(all.size()==1);
		
		unauthenticate();
	}
	//A user who is authenticated as an Customer must be able to:
	//Find his comments.
	//With an existing Producer we will try to find Comments for a Customer.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByCustomerIdCustomerException(){
		authenticate("produc1");
		Collection<Comment> all=commentService.findByCustomerId(25);
		unauthenticate();
	}
}