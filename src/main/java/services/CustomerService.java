package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Customer;
import domain.Folder;
import domain.Invoice;
import domain.Message;
import domain.ShoppingCart;
import forms.CustomerDiscountForm;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private FolderService folderService;
//	Simple CRUD methods ----------------------------------------
	public Customer create(){
		Customer result=new Customer();
		
		//TODO Se puede hacer en el reconstruct
		UserAccount userAccount=new UserAccount();
		
		Authority authority=new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		
		ShoppingCart cart=new ShoppingCart();
		Collection<Invoice> invoices=new ArrayList<Invoice>();
		Collection<Comment> comments=new ArrayList<Comment>();
		

		
//		List<Folder> folders= new ArrayList<Folder>();
//		folders.add(outbox);
//		folders.add(inbox);
		
		
//		result.setFolders(folders);
		result.setInvoices(invoices);
		result.setComments(comments);
		result.setShoppingCart(cart);
		result.setDiscount(0.0);
		
		return result;
	}
	
	public Customer findOne(int customerId){
		Customer result;
		result=customerRepository.findOne(customerId);
		return result;
	}
	
	public Collection<Customer> findAll(){
		Collection<Customer> result;
		result=customerRepository.findAll();
		return result;
	}
	
	public void save(Customer customer){
		Assert.notNull(customer);
		Date date=new Date();
		if(customer.getCreditCard().getExpirationYear()==(date.getYear()+1900)){
			Assert.isTrue(customer.getCreditCard().getExpirationMonth()>(date.getMonth()+1));
		}
		else{
			Assert.isTrue(customer.getCreditCard().getExpirationYear()>date.getYear()+1900);
		}
		customerRepository.saveAndFlush(customer);
	}
	
	public void delete(Customer customer){
		Assert.notNull(customer);
		customerRepository.delete(customer);
	}
	
//	Other business methods -------------------------------------
	
	public Customer findByUserAccount(UserAccount userAccount){		
		Customer result=customerRepository.findByUserAccount(userAccount);
		return result;
	}

	public Customer reconstruct(CustomerForm customerForm) {
		Customer result=create();
		Assert.isTrue(customerForm.getPassword().equals(customerForm.getConfirmPassword()));
		Assert.isTrue(customerForm.isAccepConditions());
		UserAccount userAccount=result.getUserAccount();
		userAccount.setUsername(customerForm.getUserName());
		
		Md5PasswordEncoder encoder;		
		encoder= new Md5PasswordEncoder();
		String password=encoder.encodePassword(customerForm.getPassword(), null);
		userAccount.setPassword(password);
		
		result.setUserAccount(userAccount);
		
		result.setName(customerForm.getName());
		result.setSurname(customerForm.getSurname());
		
		result.setEmail(customerForm.getEmail());
		result.setContactPhone(customerForm.getContactPhone());
		result.setBirthday(customerForm.getBirthday());
		result.setCreditCard(customerForm.getCreditCard());
		result.setAddress(customerForm.getAddress());
		UUID codeUUID=UUID.randomUUID();
		String code=codeUUID.toString();
		result.setCode(code);

		
		return result;
	}
	
	public CustomerDiscountForm constructDiscount(Customer customer){
		CustomerDiscountForm cDF=new CustomerDiscountForm();
		
		cDF.setCustomer(customer.getId());
		cDF.setDiscount(0.0);
		
		return cDF;
	}
	
	public Customer reconstructDiscount(CustomerDiscountForm cDF){
		isDistributor();
		Customer customer=customerRepository.findOne(cDF.getCustomer());
		customer.setDiscount(cDF.getDiscount());
		
		return customer;
	}
	
	private void isDistributor() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("DISTRIBUTOR")){
				res=true;
			}
		Assert.isTrue(res);
	}
	
}
