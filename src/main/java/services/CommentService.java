package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import org.springframework.util.Assert;
import domain.Comment;
import domain.Customer;
import domain.Item;

@Service
@Transactional
public class CommentService {
//	Managed repository -----------------------------------------
	@Autowired
	private CommentRepository commentRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private CustomerService customerService;
//	Simple CRUD methods ----------------------------------------
	public Comment create(Item item){
		isCustomer();
		Assert.notNull(item);
		Comment result=new Comment();
		UserAccount userAccount=LoginService.getPrincipal();
		Customer customer=customerService.findByUserAccount(userAccount);
		
		result.setCustomer(customer);
		result.setItemSource(item);
		return result;
	}
	
	public Comment findOne(int commentId){
		Comment result;
		result=commentRepository.findOne(commentId);
		return result;
	}
	
	public Collection<Comment> findAll(){
		isCustomer();
		Collection<Comment> result;
		result=commentRepository.findAll();
		return result;
	}
	
	public void save(Comment comment){
		isCustomer(comment);
		Assert.notNull(comment);
		commentRepository.save(comment);
	}
	
	public void delete(Comment comment){
		isCustomer(comment);
		Assert.notNull(comment);
		commentRepository.delete(comment);
	}
	
//	Other business methods -------------------------------------
	
	public Collection<Comment> findByItemId(int itemId){		
		Collection<Comment> result=commentRepository.findByItemId(itemId);
		return result;
	}
	
	public Collection<Comment> findByCustomerId(int customerId){
		isCustomer();
		Collection<Comment> result=commentRepository.findByCustomerId(customerId);
		return result;
	}
	
	
	
	private void isCustomer() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("CUSTOMER")) res=true;
		Assert.isTrue(res);
	}
	
	private void isCustomer(Comment comment) {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("CUSTOMER")){
				if(comment.getCustomer().getUserAccount().getId()==account.getId()) res=true;
			}
		Assert.isTrue(res);
	}
}
