package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ItemRepository;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Distributor;
import domain.EntryRegister;
import domain.HistoryPrice;
import domain.Item;
import domain.Producer;
import domain.ShoppingLine;
import domain.Stock;
import forms.ItemForm;

@Service
@Transactional
public class ItemService {
	
	// ------------------- Managed repository --------------------
	
	@Autowired
	private ItemRepository itemRepository;
	
	// ------------------- Supporting services -------------------
	
	@Autowired
	private HistoryPriceService historyPriceService;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private DistributorService distributorService;
	
	// ----------------------- Constructor -----------------------
	
	// ------------------- Simple CRUD methods -------------------
	
	public Item findOne(int itemId) {
		return itemRepository.findOne(itemId);
	}
	
	public Collection<Item> findAll(){
		return itemRepository.findAll();
	}
	
	public Item create(){
		UserAccount userAccount=LoginService.getPrincipal();
		Producer producer = getProducer(userAccount);
		Item item = new Item();
		
//		item.setReference(generateCode());
		item.setProducer(producer);
		item.setComments(new ArrayList<Comment>());
		item.setHistory(new ArrayList<HistoryPrice>());
		item.setLines(new ArrayList<ShoppingLine>());
		item.setRegisters(new ArrayList<EntryRegister>());
		item.setStocks(new ArrayList<Stock>());
//		item.setTaxes(taxes)	TODO R esto será un addTax desde fuera, aprovechando el 0..*
		
		return item;
	}
	
	public void save(Item item){
		Assert.notNull(item);
		Assert.isTrue(isProducer(LoginService.getPrincipal()));
	
		itemRepository.save(item);
	}
	
	public void delete (Item item){
		checkPrincipal(item);
		checkRelations(item);
		
		itemRepository.delete(item);
	}
	
// ----------------- Other business methods ------------------
	
	// REPOSITORIO:
	
	public Item findByReference(String reference) {
		return itemRepository.findByReference(reference);
	}
	
	public Collection<Item> findAllByProducer() {
		UserAccount userAccount=LoginService.getPrincipal();
		Producer producer = getProducer(userAccount);
		return itemRepository.findAllItemByProducer(producer.getId());
	}
	
	public Collection<Item> findAllByDistributor() {
		UserAccount userAccount=LoginService.getPrincipal();
		Distributor distributor = getDistributor(userAccount);
		return itemRepository.findAllByDistributor(distributor.getId());
	}
	
	// CASOS DE USO:
	
	public Item buyItemFromProducer(Item item){
		return null;
	} // TODO R esto casi mejor hacerlo desde el Controlador de ShoppingLine
	public Item buyItemFromDistributer(Item item){
		return null;
	}
	
	// FORMULARIOS:
	
	public ItemForm construct(Item item){
		ItemForm result= new ItemForm();
		
		if(item.getId()==0)
			return result;
		result.setReference(item.getReference());
		result.setItemId(item.getId());
		result.setName(item.getName());
		result.setCategory(item.getCategory());
		
		List<HistoryPrice> historyPrices = new ArrayList<HistoryPrice>(item.getHistory());
		HistoryPrice historyPrice = historyPrices.get(historyPrices.size()-1);
		
		if(getProducer(LoginService.getPrincipal())!= null)			// Si es producer
			result.setPrice(historyPrice.getProducerPrice());
		else														// Si es distributor
			result.setPrice(historyPrice.getDistributorPrice());
		
		return result;
	}
	
	public Item reconstruct(ItemForm itemForm){
		Item result;
		
		if(itemForm.getItemId()==null || itemForm.getItemId()==0)
			result = create();
		else
			result = findOne(itemForm.getItemId());
		result.setReference(itemForm.getReference());
		result.setName(itemForm.getName());
		result.setCategory(itemForm.getCategory());

		
// TODO R Esto debe funcionar, pero seguro que se puede hacer un poco más elegante/limpio teniendo en cuenta
	// que los dos if comparten las mismas llamadas... pero son las 2:18am, no hay ganas
		if(result.getId()==0){
			Collection<HistoryPrice> historyPrices=new ArrayList<HistoryPrice>();
			HistoryPrice historyPrice = historyPriceService.generateNewOne(result, itemForm.getPrice());
			historyPrices.add(historyPrice);
			result.setHistory(historyPrices);
		}else{
			List<HistoryPrice> historyPricesDB = new ArrayList<HistoryPrice>(result.getHistory());
			HistoryPrice historyPriceDB = historyPriceService.getLastFromItemId(itemForm.getItemId());
			
			UserAccount userAccount=LoginService.getPrincipal();
			if((isProducer(userAccount) && historyPriceDB.getProducerPrice()!= itemForm.getPrice()) ||
				(isDistributor(userAccount) && historyPriceDB.getDistributorPrice() != itemForm.getPrice())){
				HistoryPrice historyPrice = historyPriceService.generateNewOne(result, itemForm.getPrice());
				
				
				historyPricesDB.remove(historyPricesDB);
				historyPriceDB.setFinishDate(new Date(System.currentTimeMillis()-1));
				historyPricesDB.add(historyPriceDB);
				
				historyPricesDB.add(historyPrice);
				result.setHistory(historyPricesDB);
			}
			
		}

		
		return result;
	}
	
	// AUXILIARES
	
	private Producer getProducer(UserAccount userAccount){
		Assert.isTrue(isProducer(userAccount));
		Producer result = producerService.findByUserAccount(userAccount);
		return result;
	}
	
	private Distributor getDistributor(UserAccount userAccount){
		Assert.isTrue(isDistributor(userAccount));
		Distributor result = distributorService.findByUserAccount(userAccount);
		return result;
	}
	
	private Boolean isProducer(UserAccount userAccount){
		Boolean result = userAccount.getAuthorities().iterator().next().getAuthority().equals("PRODUCER");
		return result;
	}
	
	private Boolean isDistributor(UserAccount userAccount){
		Boolean result = userAccount.getAuthorities().iterator().next().getAuthority().equals("DISTRIBUTOR");
		return result;
	}
	
	private String generateCode(){
		String result="";
		String uuid1=UUID.randomUUID().toString().replaceAll("-", "");
		String uuid2=UUID.randomUUID().toString().replaceAll("-", "");
		if(Math.random()<0.5)
			result=uuid1.concat(uuid2);
		else
			result=uuid1+"-"+uuid2;
		return result;
	}
	
	private void checkPrincipal(Item item) {
		UserAccount userAccount=LoginService.getPrincipal();
		Producer principal= getProducer(userAccount);
		
		Assert.isTrue(principal.equals(item.getProducer()));
	}
	
	private void checkRelations(Item item) {
		Item result = findOne(item.getId());
		
		Assert.isTrue(result.getStocks().isEmpty(), "Item en uso, no se puede eliminar");
		Assert.isTrue(result.getRegisters().isEmpty(), "Item en uso, no se puede eliminar");
		Assert.isTrue(result.getComments().isEmpty(), "Item en uso, no se puede eliminar");
		Assert.isTrue(result.getLines().isEmpty(), "Item en uso, no se puede eliminar");
	}

	public Collection<Item> findAllByWarehouseId(int warehouseId) {
		Collection<Item> result;
		result=itemRepository.findAllByWarehouseId(warehouseId);
		return result;
	}
}