package services;

import java.util.Collection;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TaxRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Tax;

@Service
@Transactional
public class TaxService {
//	Managed repository -----------------------------------------
	@Autowired
	private TaxRepository taxRepository;
//	Supporting services ----------------------------------------

//	Simple CRUD methods ----------------------------------------
	public Tax create(){
		isDistributor();
		Tax result=new Tax();
		
		return result;
	}
	
	public Tax findOne(int taxId){
		Tax result;
		result=taxRepository.findOne(taxId);
		return result;
	}
	
	public Collection<Tax> findAll(){
		Collection<Tax> result;
		result=taxRepository.findAll();
		return result;
	}
	
	public void save(Tax tax){
		Assert.notNull(tax);
		taxRepository.save(tax);
	}
	
	public void delete(Tax tax){
		Assert.notNull(tax);
		taxRepository.delete(tax);
	}
	
// Other business methods -------------------------------------	
	
	public Collection<Tax> getTaxesFromItemId(int itemId){
		Collection<Tax> result=taxRepository.getTaxesFromItemId(itemId);
		return result;
	}
	
	private void isDistributor() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("DISTRIBUTOR")) res=true;
		Assert.isTrue(res);
	}
}
