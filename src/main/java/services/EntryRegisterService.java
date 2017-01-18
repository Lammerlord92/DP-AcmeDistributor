package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EntryRegisterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.EntryRegister;
import domain.HistoryPrice;
import domain.Item;
import domain.Money;
import domain.Stock;

@Service
@Transactional
public class EntryRegisterService {
	@Autowired
	private EntryRegisterRepository entryRegisterRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private HistoryPriceService historyPriceService ;
	@Autowired
	private StockService stockService;
//	Simple CRUD methods ----------------------------------------
	public EntryRegister create(Item item){
		EntryRegister result=new EntryRegister();
		Money money=new Money();
		money.setAmount(0.0);
		money.setCurrency("a");
		
		result.setItem(item);
		result.setCreationDate(new Date());
		result.setTotalPrice(money);
		
		return result;
	}
	
	public EntryRegister findOne(int entryRegister){
		EntryRegister result;
		result=entryRegisterRepository.findOne(entryRegister);
		return result;
	}
	
	public Collection<EntryRegister> findAll(){
		Collection<EntryRegister> result;
		result=entryRegisterRepository.findAll();
		return result;
	}
	
	public void save(EntryRegister entryRegister){
		Assert.notNull(entryRegister);
		Money aux=new Money();
		long milisec=new Date().getTime();
		HistoryPrice last=historyPriceService.getLastFromItemId(entryRegister.getItem().getId());
		aux=last.getDistributorPrice();
		Money money=new Money();
		money.setAmount(aux.getAmount()*entryRegister.getQuantity());
		money.setCurrency(aux.getCurrency());
		entryRegister.setTotalPrice(money);
		entryRegister.setCreationDate(new Date(milisec-1));
		
		Stock productStock=stockService.getStockFromWarehouseIdItemId(
				entryRegister.getWarehouse().getId(), 
				entryRegister.getItem().getId());
		if(productStock==null){
			productStock=stockService.create();
			productStock.setItem(entryRegister.getItem());
			productStock.setWarehouse(entryRegister.getWarehouse());
			productStock.setQuantity(entryRegister.getQuantity());
		}else{
			int stock=productStock.getQuantity();
			stock+=entryRegister.getQuantity();
			productStock.setQuantity(stock);
		}
		
		entryRegisterRepository.save(entryRegister);
		stockService.save(productStock);
	}
	
	public void delete(EntryRegister entryRegister){
		Assert.notNull(entryRegister);
		entryRegisterRepository.delete(entryRegister);
	}
	
//	Other business methods -------------------------------------
	
	public Collection<EntryRegister> findByItem(int itemId){		
		Collection<EntryRegister> result=entryRegisterRepository.findByItemId(itemId);
		return result;
	}
	
	public Collection<EntryRegister> findAfterDate(Date date){		
		Collection<EntryRegister> result=entryRegisterRepository.findAfterDate(date);
		return result;
	}
	public Collection<EntryRegister> findAfterDateFromItem(int itemId,Date date){		
		Collection<EntryRegister> result=entryRegisterRepository.findByItemAfterDate(itemId, date);
		return result;
	}

	public Collection<EntryRegister> findByWarehouse(int warehouseId) {
		isDistributor();
		Collection<EntryRegister> result;
		result=entryRegisterRepository.findByWarehouse(warehouseId);
		return result;
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
