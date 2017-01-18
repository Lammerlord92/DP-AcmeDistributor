package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
import domain.Invoice;
import domain.ShoppingCart;
import domain.ShoppingCart;
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
public class InvoiceServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	//A user who is authenticated as Distributor must be able to:
	//List all invoices that the system stores.
	//With an existing distributor, we will try to obtain his invoices.
		@Test
		public void testListDistributor(){
			authenticate("distri1");
			Collection<Invoice> all=invoiceService.findAll();
			Assert.isTrue(all.size()==0);
			unauthenticate();
		}
	
	//A user who is authenticated as Customer must be able to:
	//List his invoices that the system stores.
	//With an existing customer, we will try to obtain his invoices.
	@Test
	public void testListCustomer(){
		authenticate("cust1");
		Collection<Invoice> all=invoiceService.findByCustomer();
		Assert.isTrue(all.size()==0);
		unauthenticate();
	}
	
		
	//A user who is authenticated as an Customer must be able to:
	//Create invoices.
	//With an existing Customer we will try to create an invoice.
	@Test
	public void testCreateAndSave(){
		authenticate("cust1");
		
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();;
		Invoice invoice = invoiceService.create(shoppingCart );

		invoiceService.save(invoice);
		unauthenticate();
	}
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	//A user who is authenticated as Customer must be able to:
		//List his invoices that the system stores.
		//With an existing producer, we will try to obtain his invoices.
		@Test(expected=IllegalArgumentException.class)
		public void testListCustomerException(){
			authenticate("poduct1");
			Collection<Invoice> all=invoiceService.findByCustomer();
			Assert.isTrue(all.size()==0);
			unauthenticate();
		}
	
	//A user who is authenticated as an Customer must be able to:
	//Create invoices.
	//With an existing Customer we will try to create an invoice
	//with an invalid Shopping Cart.
	@Test(expected=NullPointerException.class)
	public void testCreateAndSaveException(){
		authenticate("cust1");
		
		Invoice invoice = invoiceService.create(null );

		invoiceService.save(invoice);
		unauthenticate();
	}
}