package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Customer;
import domain.ShoppingCart;
import domain.ShoppingLine;

import repositories.CustomerRepository;
import repositories.ShoppingCartRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ShoppingCartService {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private CustomerRepository customerService;
	@Autowired
	private ShoppingLineService shoppingLineService;
//	Simple CRUD methods ----------------------------------------
	public ShoppingCart create(){
		isCustomer();
		ShoppingCart result=new ShoppingCart();
		
		UserAccount account = LoginService.getPrincipal();
		Customer customer = customerService.findByUserAccount(account);
		
//		result.setCustomer(customer);
		
		return result;
	}
	
	public ShoppingCart findOne(int shoppingCartId){
		ShoppingCart result;
		result=shoppingCartRepository.findOne(shoppingCartId);
		return result;
	}
	
	public Collection<ShoppingCart> findAll(){
		Collection<ShoppingCart> result;
		result=shoppingCartRepository.findAll();
		return result;
	}
	
	public void save(ShoppingCart shoppingCart){
		isCustomer(shoppingCart);
		Assert.notNull(shoppingCart);
		shoppingCartRepository.save(shoppingCart);
	}
	
	public void delete(ShoppingCart shoppingCart){
		Assert.notNull(shoppingCart);
		shoppingCartRepository.delete(shoppingCart);
	}
// Other business methods -------------------------------------	
	
	public ShoppingCart findByShoppingCartId(int shoppingCartId){
		ShoppingCart result;
		result= shoppingCartRepository.findByShoppingCartId(shoppingCartId);
		return result;
	}
	
	public ShoppingCart findByShoppingCustomer(){
		ShoppingCart result;
		Customer customer=customerService.findByUserAccount(LoginService.getPrincipal());
		result= shoppingCartRepository.findByShoppingCustomerId(customer.getId());
		return result;
	}
	
	public void cleanShoppingCart(ShoppingCart shoppingCart){
		Collection<ShoppingLine> sl=new ArrayList<ShoppingLine>();
		for (ShoppingLine line:shoppingCart.getLines()){
			sl.add(line);
		}
		shoppingCart.setLines(new ArrayList<ShoppingLine>());
		for(ShoppingLine l:sl) shoppingLineService.delete(l);
		save(shoppingCart);
	}
	private void isCustomer(){
		UserAccount account = LoginService.getPrincipal();
		Collection<Authority>authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("CUSTOMER")) res=true;
		Assert.isTrue(res);
	}
	
	private void isCustomer(ShoppingCart shopp) {
		UserAccount account=LoginService.getPrincipal();
		Customer c=customerService.findByUserAccount(account);
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("CUSTOMER")){
				if(shopp.getId()==c.getShoppingCart().getId()) res=true;
			}
		Assert.isTrue(res);
	}
}
