package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Item;
import domain.Stock;
import forms.StockForm;
import repositories.StockRepository;

@Service
@Transactional
public class StockService {
//	Managed repository -----------------------------------------
	@Autowired
	private StockRepository stockRepository;
//	Supporting services ----------------------------------------

//	Simple CRUD methods ----------------------------------------
	public Stock create(){
		Stock result=new Stock();
		return result;
	}
	
	public Stock findOne(int stockId){
		Stock result;
		result=stockRepository.findOne(stockId);
		return result;
	}
	
	public Collection<Stock> findAll(){
		Collection<Stock> result;
		result=stockRepository.findAll();
		return result;
	}
	
	public void save(Stock stock){
		Assert.notNull(stock);
		stockRepository.save(stock);
	}
	
	public void delete(Stock stock){
		Assert.notNull(stock);
		stockRepository.delete(stock);
	}
	
// Other business methods -------------------------------------	
	public Stock getStockFromWarehouseIdItemId(int warehouseId,int itemId){
		Stock result=stockRepository.getStockFromWarehouseIdItemId(warehouseId, itemId);
		return result;
	}
	
	public Collection<Object[]> getStocksFromWarehouseId(int warehouseId){
		Collection<Object[]> result=stockRepository.getStocksFromWarehouseId(warehouseId);
		return result;
	}

	public Stock reconstruct(StockForm stockForm) {
		Stock result;
		
		if(stockForm.getItemId()==null || stockForm.getItemId()==0)
			result = create();
		else
			result = findOne(stockForm.getItemId());

		result.setQuantity(stockForm.getQuantity());
		result.setWarehouse(stockForm.getWarehouse());
		
		return result;
	}

	public StockForm contruct(Stock stock) {
		StockForm result= new StockForm();
		
		if(stock.getId()==0)
			return result;
		
		result.setItemId(stock.getId());
		result.setQuantity(stock.getQuantity());
		result.setWarehouse(stock.getWarehouse());
		
		return result;
	}

	public Integer getStockFromItemId(Item item) {
		Integer result=stockRepository.getStockOfItemId(item.getId());
		return result;
		
	}

	public Collection<Stock> getAllStockFromItemId(Item item) {
		Collection<Stock> result=stockRepository.getAllStockFromItemId(item.getId());
		return result;
	}
}
