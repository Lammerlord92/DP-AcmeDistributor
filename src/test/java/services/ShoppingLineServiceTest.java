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

import domain.Money;
import domain.ShoppingCart;
import domain.ShoppingLine;
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
public class ShoppingLineServiceTest extends AbstractTest{
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private ShoppingLineService shoppingLineService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
		
	//A user who is authenticated as an Customer must be able to:
	//Create shoppingLines.
	//With an existing Customer we will try to create a shoppingLine.
	@Test
	public void testCreateAndSave(){
		authenticate("cust1");
		
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();
		Item item = itemService.findAll().iterator().next();
		ShoppingLine shoppingLine = shoppingLineService.create();
		shoppingLine.setItem(item);
		shoppingLine.setQuantity(1);
		shoppingLine.setShoppingCart(shoppingCart);

		shoppingLineService.save(shoppingLine);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Delete one of his shoppingLines.
	//With an existing Customer we will try to delete an existing shoppingLine.
	@Test
	public void testDelete(){
		authenticate("cust1");
		
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();
		Item item = itemService.findAll().iterator().next();
		ShoppingLine shoppingLine = shoppingLineService.create();
		shoppingLine.setItem(item);
		shoppingLine.setQuantity(1);
		shoppingLine.setShoppingCart(shoppingCart);
		shoppingLineService.save(shoppingLine);
		
		List<ShoppingLine> shoppingLines = new ArrayList<ShoppingLine>(shoppingLineService.findAll());
		ShoppingLine shoppingLineDB = shoppingLines.get(shoppingLines.size()-1);
		shoppingLineService.delete(shoppingLineDB);

		unauthenticate();
	}
	
	//The sistem must be able to:
	//Find the shoppingLines for an Item.
	//Without an existing user we will try to find ShoppingLines for an Item.
	@Test
	public void testFindShoppingLineByItem(){
		authenticate("cust1");
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();
		Item item = itemService.findAll().iterator().next();
		ShoppingLine shoppingLine = shoppingLineService.create();
		shoppingLine.setItem(item);
		shoppingLine.setQuantity(1);
		shoppingLine.setShoppingCart(shoppingCart);
		shoppingLineService.save(shoppingLine);
		unauthenticate();
		
		Collection<ShoppingLine> all=shoppingLineService.findShoppingLineByItem(item.getId());
		Assert.isTrue(all.size()==1);
	}
	
	//The sistem must be able to:
	//Find the shoppingLines for a Shopping Cart.
	//Without an existing user we will try to find ShoppingLines for a Shopping Cart.
	@Test
	public void testFindByShoppingCartId(){
		authenticate("cust1");
		
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();
		Item item = itemService.findAll().iterator().next();
		ShoppingLine shoppingLine = shoppingLineService.create();
		shoppingLine.setItem(item);
		shoppingLine.setQuantity(1);
		shoppingLine.setShoppingCart(shoppingCart);
		shoppingLineService.save(shoppingLine);
		unauthenticate();
		
		Collection<ShoppingLine> shoppingLines =shoppingLineService.findByShoppingCartId(shoppingCart.getId());
		Assert.notNull(shoppingLines);
	}
	
	
	
	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------
	
		
	//A user who is authenticated as an Customer must be able to:
	//Create shoppingLines.
	//With an existing Customer we will try to create a shoppingLine
	//without register.
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAndSaveException(){
		authenticate(null);
		
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();
		Item item = itemService.findAll().iterator().next();
		ShoppingLine shoppingLine = shoppingLineService.create();
		shoppingLine.setItem(null);
		shoppingLine.setQuantity(1);
		shoppingLine.setShoppingCart(shoppingCart);

		shoppingLineService.save(shoppingLine);
		
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Edit shoppingLines.
	//With an existing Customer we will try to edit a shoppingLine
	//deleting the quantity.
	@Test
	//TODO R ESTO DEBERÏA FALLAR CON EL NULL
	public void testEditAndSaveException(){
		authenticate("cust1");
		
		ShoppingCart shoppingCart = shoppingCartService.findAll().iterator().next();
		Item item = itemService.findAll().iterator().next();
		ShoppingLine shoppingLine = shoppingLineService.create();
		shoppingLine.setItem(item);
		shoppingLine.setQuantity(1);
		shoppingLine.setShoppingCart(shoppingCart);
		shoppingLineService.save(shoppingLine);
		
		List<ShoppingLine> shoppingLines = new ArrayList<ShoppingLine>(shoppingLineService.findAll());
		ShoppingLine shoppingLineDB = shoppingLines.get(shoppingLines.size()-1);
		shoppingLineDB.setQuantity(null);
		
		shoppingLineService.save(shoppingLineDB);
		
		unauthenticate();
	}
	
	//A user who is authenticated as an Customer must be able to:
	//Delete one of his shoppingLines.
	//With an existing Customer we will try to delete an existing shoppingLine
	//from another user.
	@Test(expected=IndexOutOfBoundsException.class)
	public void testDeleteException(){
		authenticate("cust1");
		
		List<ShoppingLine> shoppingLines = new ArrayList<ShoppingLine>(shoppingLineService.findAll());
		ShoppingLine shoppingLineDB = shoppingLines.get(shoppingLines.size());
		shoppingLineService.delete(shoppingLineDB);

		unauthenticate();
	}
	
	//The sistem must be able to:
	//Find the shoppingLines for a Shopping Cart.
	//Without an existing user we will try to find ShoppingLines for a Shopping Cart
	//with an incorrect Shopping Cart Id.
	@Test(expected=IllegalArgumentException.class)
	public void testFindByShoppingCartIdException(){
		
		Collection<ShoppingLine> shoppingLines =shoppingLineService.findByShoppingCartId(222);
		Assert.notEmpty(shoppingLines);
	}
	
}