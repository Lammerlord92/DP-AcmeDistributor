package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Customer;
import domain.HistoryPrice;
import domain.Item;
import domain.Money;
import domain.ShoppingLine;
import domain.Stock;
import domain.Warehouse;

import repositories.ShoppingLineRepository;
import repositories.StockRepository;
import repositories.WarehouseRepository;
import security.LoginService;
import security.UserAccount;


@Service
@Transactional
public class ShoppingLineService {
	@Autowired
	private ShoppingLineRepository shoppingLineRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private WarehouseService warehouseRepository;
	@Autowired
	private StockService stockService;
	@Autowired
	private HistoryPriceService historyPriceService;
	@Autowired
	private CustomerService customerService;
//	Simple CRUD methods ----------------------------------------	
	public ShoppingLine create(){
		ShoppingLine result= new ShoppingLine();
//		Date moment = new Date(System.currentTimeMillis()-1);
//		result.setShoppingDate(moment);
		return result;
	}
	
	public ShoppingLine findOne(int shoppingLine){
		ShoppingLine result;
		result=shoppingLineRepository.findOne(shoppingLine);
		return result;
	}
	
	public Collection<ShoppingLine> findAll(){
		Collection<ShoppingLine> result;
		result=shoppingLineRepository.findAll();
		return result;
	}
	
	public void save(ShoppingLine shoppingLine){
		Assert.notNull(shoppingLine);
		isCustomer(shoppingLine);
		shoppingLineRepository.save(shoppingLine);
	}
	
	public void delete(ShoppingLine shoppingLine){
		Assert.notNull(shoppingLine);
		isCustomer(shoppingLine);
		shoppingLineRepository.delete(shoppingLine);
	}
	
//	Other business methods -------------------------------------
	
	public ShoppingLine findByShoppingLineId(int shoppingLineId){
		ShoppingLine result;
		result=shoppingLineRepository.findByShoppingLineId(shoppingLineId);
		return result;
	}
	
	public Collection<ShoppingLine> findShoppingLineByInvoice(int invoiceId){
		Collection<ShoppingLine> result;
		result=shoppingLineRepository.findShoppingLineByInvoice(invoiceId);
		return result;
	}
	
	public Collection<ShoppingLine> findShoppingLineByItem(int itemId){
		Collection<ShoppingLine> result;
		result=shoppingLineRepository.findShoppingLineByItem(itemId);
		return result;
	}
	
	//TODO falta por hacer, consultar con grupo dudas sobre comparacion
	public void compareStock(Collection<ShoppingLine> lines){
		
	}
	
//	public void reduceStock(Collection<ShoppingLine> lines){
//		//reducir el stock siempre del warehouse que mas cantidad
//		//tenga para el item de la shoppingLine
//		Item item = null;
//		Warehouse warehouse = null;
//		Stock stock = null;
//		Integer stockFinal= 0;
//		Integer quantity = 0;
//		for(ShoppingLine sl: lines){
//			quantity=sl.getQuantity();
//			item=sl.getItem();
//			warehouse=warehouseRepository.getMoreStockForGivenItem(item.getReference());
//			stock=stockRepository.getStockFromWarehouseIdItemId(warehouse.getId(), item.getId());
//			stockFinal = stock.getQuantity()- quantity;
//			stock.setQuantity(stockFinal);
//			stockRepository.save(stock);
//		}
//	}
	
	//TODO falta por hacer, consultar con grupo, idea: calcular la tax de 
	//la shoppingline completa teniendo en cuenta quantity de shoppingline
	//y totalPrice de entryRegister
	public Money getTotalLine(ShoppingLine sl){
		Money result=new Money();
		
		HistoryPrice pricePerUnit=historyPriceService.getLastFromItemId(sl.getItem().getId());
		Integer totalUnits=sl.getQuantity();
		Double totalCost=totalUnits*pricePerUnit.getDistributorPrice().getAmount();
		String currency=pricePerUnit.getDistributorPrice().getCurrency();
		
		result.setAmount(totalCost);
		result.setCurrency(currency);
		
		return result;
	}

	public Collection<ShoppingLine> findByShoppingCartId(int shoppingCartId) {
		Collection<ShoppingLine> result=shoppingLineRepository.findByShoppingCartId(shoppingCartId);
		return result;
	}
	
	public ShoppingLine findByShoppingCartAndItemId(int shoppingCartId, int itemId){
		ShoppingLine result=shoppingLineRepository.findByShoppingCartAndItemId(shoppingCartId, itemId);
		return result;
	}
	
	
	
	private Boolean isCustomer(ShoppingLine sL){
		Customer c=customerService.findByUserAccount(LoginService.getPrincipal());
		Boolean result = sL.getShoppingCart().getId()==c.getShoppingCart().getId();
		return result;
	}
}
