package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Comment;
import domain.Customer;
import domain.HistoryPrice;
import domain.Item;
import domain.Money;

import repositories.HistoryPriceRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class HistoryPriceService {
//	Managed repository -----------------------------------------
	@Autowired
	private HistoryPriceRepository historyRepository;
	
//	Supporting services ----------------------------------------
	@Autowired
	private DistributorService distributorService;
	
//	Simple CRUD methods ----------------------------------------
	public HistoryPrice create(Item item){
		isDistributor();
		HistoryPrice result=new HistoryPrice();
		
		result.setItem(item);
		//La fecha de start debería ser creada aquí a partir de la última finishDate creada?
		return result;
	}
	
	public HistoryPrice findOne(int historyPriceId){
		isDistributor();
		HistoryPrice result;
		result=historyRepository.findOne(historyPriceId);
		return result;
	}
	
	public Collection<HistoryPrice> findAll(){
		isDistributor();
		Collection<HistoryPrice> result;
		result=historyRepository.findAll();
		return result;
	}
	
	public void save(HistoryPrice historyPrice){
		isProducer();
		Assert.notNull(historyPrice);
		historyRepository.save(historyPrice);
	}
	
	public void delete(HistoryPrice historyPrice){
		isDistributor();
		Assert.notNull(historyPrice);
		historyRepository.delete(historyPrice);
	}
	
// Other business methods -------------------------------------
	
	public Collection<HistoryPrice> getHistoryPriceFromItemId(int itemId){
		isDistributor();
		Collection<HistoryPrice> result=historyRepository.getHistoryPriceFromItemId(itemId);
		return result;
	}
	
	public Collection<HistoryPrice> getHistoryFromItemAndDate(int itemId, Date date){
		isDistributor();
		Collection<HistoryPrice> result=historyRepository.getHistoryFromItemAndDate(itemId, date);
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
	private void isProducer() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("PRODUCER")) res=true;
		Assert.isTrue(res);
	}
	// CASOS DE USO:
	
	public HistoryPrice generateNewOne(Item item, Money money) {
		HistoryPrice result=new HistoryPrice();
		
		Double newAmount=money.getAmount()+money.getAmount()*0.20;
		String newCurrency=money.getCurrency();
		Money newMoney=new Money();
		newMoney.setAmount(newAmount);
		newMoney.setCurrency(newCurrency);
		
		result.setStartDate(new Date(System.currentTimeMillis()-1));
		result.setItem(item);
		result.setProducerPrice(money);
		result.setDistributorPrice(newMoney);
		
		
		
		return result;
	}

	public HistoryPrice getLastFromItemId(Integer itemId) {
		HistoryPrice result=historyRepository.getLastFromItemId(itemId);
		return result;
	}
	
}
