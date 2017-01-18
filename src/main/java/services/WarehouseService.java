package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.WarehouseRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.EntryRegister;
import domain.Item;
import domain.Stock;
import domain.Warehouse;

@Service
@Transactional
public class WarehouseService {
//	Managed repository -----------------------------------------
	@Autowired
	private WarehouseRepository warehouseRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private DistributorService distributorService;
//	Simple CRUD methods ----------------------------------------
	public Warehouse create(){
		isDistributor();
		Warehouse result=new Warehouse();
		List<Stock> stocks=new ArrayList<Stock>();
		List<EntryRegister> entryRegisters=new ArrayList<EntryRegister>();
		
		result.setDistributor(distributorService.findByUserAccount(LoginService.getPrincipal()));
		result.setStocks(stocks);
		
		return result;
	}
	
	public Warehouse findOne(int warehouseId){
		Warehouse result;
		result=warehouseRepository.findOne(warehouseId);
		return result;
	}
	
	public Collection<Warehouse> findAll(){
		Collection<Warehouse> result;
		result=warehouseRepository.findAll();
		return result;
	}
	
	public void save(Warehouse warehouse){
		isDistributor();
		Assert.notNull(warehouse);
		warehouseRepository.save(warehouse);
	}
	
	public void delete(Warehouse warehouse){
		isDistributor(warehouse);
		Assert.notNull(warehouse);
		warehouseRepository.delete(warehouse);
	}
	
// Other business methods -------------------------------------
	
	public Collection<Warehouse> getWarehouseFromDistributorId(int distributorId){
		Collection<Warehouse> result=warehouseRepository.getWarehouseFromDistributorId(distributorId);
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
	private void isDistributor(Warehouse warehouse) {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("DISTRIBUTOR")){
				if(warehouse.getDistributor().getUserAccount().getId()==account.getId()) res=true;
			}
		Assert.isTrue(res);
	}

	public Collection<Warehouse> getWarehouses(Item item){
		Collection<Warehouse> result = new ArrayList<Warehouse>();
		for(Stock aux : item.getStocks())
			result.add(aux.getWarehouse());
		
		return result;
	}
}
