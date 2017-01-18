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
import domain.ShoppingCart;
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
public class ShoppingCartServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ItemService itemService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	//A user who is authenticated as Customer must be able to:
	//Find his shoppingCart.
	//With an existing customer, we will try to obtain his Shopping Cart.
	@Test
	public void testFindByCustomer(){
		authenticate("cust1");
		
		ShoppingCart sC=shoppingCartService.findByShoppingCustomer();
		Assert.notNull(sC);
		
		unauthenticate();
	}
		
	
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
	
	//A user who is authenticated as Customer must be able to:
	//Find his shoppingCart.
	//Without an existing customer, we will try to obtain his Shopping Cart.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByCustomerException(){
		
		ShoppingCart sC=shoppingCartService.findByShoppingCustomer();
		Assert.notNull(sC);
	}
}