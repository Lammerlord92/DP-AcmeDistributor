package services;

import java.util.Collection;
import java.util.Date;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;
import domain.CreditCard;
import forms.CustomerDiscountForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CustomerServiceTest extends AbstractTest{
	@Autowired
	private CustomerService customerService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	//A distributor must be able to:
	//List all customers registered into the system.
	@Test
	public void testListCustomer(){
		authenticate("distri1");
		Collection<Customer> customers=customerService.findAll();
		Assert.isTrue(customers.size()==3);
		authenticate(null);
	}
	
	//A distributor must be able to:
	//Change the discount of a customer.
	@Test
	public void testSetDiscountCustomer(){
		authenticate("distri1");
		Customer customer=customerService.findOne(25);
		CustomerDiscountForm customerDiscountForm=customerService.constructDiscount(customer);
		customerDiscountForm.setDiscount(0.05);
		customer=customerService.reconstructDiscount(customerDiscountForm);
		customerService.save(customer);
		authenticate(null);
	}
	//A user who is not authenticated must be able to:
	//Register to the system as an customer.
	//We will try to register and save a new customer
	@Test
	public void testRegister(){
		Customer customer=customerService.create();
		
		customer.setName("name");
		customer.setSurname("surname");
		customer.setEmail("Correo@gmail.com");
		customer.setContactPhone("954954954");
		customer.setBirthday(new Date(new Date().getTime()-2000000));
		customer.setAddress("testing");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(345);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		customer.setCreditCard(credit);
		
		UserAccount user=customer.getUserAccount();
		user.setUsername("nameSurname");
		user.setPassword("nameSurname");
		customer.setUserAccount(user);
		
		customerService.save(customer);
	}

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	//A distributor must be able to:
	//Change the discount of a customer.
	//We will try it with a customer
	@Test(expected=IllegalArgumentException.class)
	public void testSetDiscountCustomerException(){
		authenticate("cust1");
		Customer customer=customerService.findOne(25);
		CustomerDiscountForm customerDiscountForm=customerService.constructDiscount(customer);
		customerDiscountForm.setDiscount(0.05);
		customer=customerService.reconstructDiscount(customerDiscountForm);
		customerService.save(customer);
		authenticate(null);
	}

	//A user who is not authenticated must be able to:
	//Register to the system as an customer.
	//We will try to register as a new customer with wrong attributes and capture the exception
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void testRegisterException(){
		Customer customer=customerService.create();
		
		customer.setName("name");
		customer.setSurname("surname");
		customer.setEmail("Correo@gmail.com");
		customer.setContactPhone("954954954");
		customer.setBirthday(new Date("bad atribute"));
		customer.setAddress("testing");
		
		CreditCard credit=new CreditCard();
		credit.setBrandName("brandName");
		credit.setCvvCode(345);
		credit.setExpirationMonth(10);
		credit.setExpirationYear(2015);
		credit.setHolderName("holderName");
		credit.setNumber("5166707375607495");
		customer.setCreditCard(credit);
		
		UserAccount user=customer.getUserAccount();
		user.setUsername("nameSurname");
		user.setPassword("nameSurname");
		customer.setUserAccount(user);
		
		customerService.save(customer);
	}
}