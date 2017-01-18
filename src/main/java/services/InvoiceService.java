package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.InvoiceRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Invoice;
import domain.Money;
import domain.ShoppingCart;
import domain.ShoppingLine;
import domain.Stock;
import domain.Tax;
import forms.InvoiceForm;

@Service
@Transactional
public class InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private ShoppingLineService shoppingLineService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private StockService stockService;
	@Autowired
	private TaxService taxService;
	@Autowired
	private ShoppingCartService shoppingCartService;
//	Simple CRUD methods ----------------------------------------
	public Invoice create(ShoppingCart shoppingCart){
		Invoice result=new Invoice();
		Collection<ShoppingLine> shoppingLines= shoppingCart.getLines();
		Collection<ShoppingLine> sLResult=new ArrayList<ShoppingLine>();
		String currency="eur";
//		String currency=null;
		double amount=0.0;
		for(ShoppingLine sl:shoppingLines){
			//Ligar con servicio de totalLine
			Money totalLine=shoppingLineService.getTotalLine(sl);
			amount+=totalLine.getAmount();
			ShoppingLine slAux=new ShoppingLine();
			slAux.setItem(sl.getItem());
			slAux.setQuantity(sl.getQuantity());
			
			sLResult.add(slAux);
//			if(currency==null) currency=totalLine.getCurrency();
		}
		Collection<Tax> taxs=taxService.findAll();
		double totalTax=0.0;
		for(Tax t:taxs){
			totalTax+=t.getPercent();
		}
		
		Customer current=customerService.findByUserAccount(LoginService.getPrincipal());
		double customerDiscount=current.getDiscount();

		Money totalPrice=new Money();
		totalPrice.setAmount(amount+totalTax*amount-customerDiscount*amount);
		totalPrice.setCurrency(currency);
		
		UUID codeUUID=UUID.randomUUID();
		String code=codeUUID.toString();
		result.setOrderNumber(code);
		
		
		result.setTotalPrice(totalPrice);
		result.setLines(sLResult);
		result.setCustomer(customerService.findByUserAccount(LoginService.getPrincipal()));
		result.setCreationDate(new Date());
		

		shoppingCartService.cleanShoppingCart(shoppingCart);
		
		return result;
	}
	
	public Invoice findOne(int invoice){
		Invoice result;
		result=invoiceRepository.findOne(invoice);
		return result;
	}
	
	public Collection<Invoice> findAll(){
		Collection<Invoice> result;
		result=invoiceRepository.findAll();
		return result;
	}
	
	public void save(Invoice invoice){
		Assert.notNull(invoice);
		Collection<ShoppingLine> shoppingLines= invoice.getLines();
		for(ShoppingLine sl:shoppingLines){
			Assert.isTrue(sl.getQuantity()<=stockService.getStockFromItemId(sl.getItem()));
			Integer cant=sl.getQuantity();
			for (Stock s:stockService.getAllStockFromItemId(sl.getItem())) {
				if(cant==0){
					invoiceRepository.save(invoice);
				}else{
					if(sl.getQuantity()<cant){
						cant=cant-sl.getQuantity();
						s.setQuantity(0);
						stockService.save(s);
					}else
						s.setQuantity(s.getQuantity()-cant);
						stockService.save(s);
				}
			}
			sl.setInvoice(invoice);
		}
		invoiceRepository.save(invoice);
	}
	
	public void delete(Invoice invoice){
		Assert.notNull(invoice);
		invoiceRepository.delete(invoice);
	}
	
//	Other business methods -------------------------------------
	public Collection<Invoice> findByCustomer(){
		UserAccount account=LoginService.getPrincipal();	
		Customer customer=customerService.findByUserAccount(account);
		Collection<Invoice> result=invoiceRepository.findByCustomer(customer);
		return result;
	}
	public Collection<Invoice> findByCustomer(Customer customer){		
		Collection<Invoice> result=invoiceRepository.findByCustomer(customer);
		return result;
	}
	public Collection<Invoice> canBeReturn(Customer customer,Date date){		
		Collection<Invoice> result=invoiceRepository.findByCustomerAfterDate(customer, date);
		return result;
	}

	public InvoiceForm construct(ShoppingCart shoppingCart) {
		InvoiceForm result=new InvoiceForm();
		Collection<ShoppingLine> shoppingLines= shoppingCart.getLines();
		String currency="eur";
//		String currency=null;
		double amount=0.0;
		for(ShoppingLine sl:shoppingLines){
			//Ligar con servicio de totalLine

			Money totalLine=shoppingLineService.getTotalLine(sl);
			amount+=totalLine.getAmount();
//			if(currency==null) currency=totalLine.getCurrency();
		}
		Collection<Tax> taxs=taxService.findAll();
		double totalTax=0.0;
		for(Tax t:taxs){
			totalTax+=t.getPercent();
		}
		
		Customer current=customerService.findByUserAccount(LoginService.getPrincipal());
		double customerDiscount=current.getDiscount();
		
		Money totalPrice=new Money();
		totalPrice.setAmount(amount+totalTax*amount-customerDiscount*amount);
		totalPrice.setCurrency(currency);
		
		
		UUID codeUUID=UUID.randomUUID();
		String code=codeUUID.toString();
		result.setOrderNumber(code);
		
		
		result.setTotalPrice(totalPrice);
		result.setCustomer(customerService.findByUserAccount(LoginService.getPrincipal()));
		result.setShoppingCart(shoppingCart);
		
		return result;
	}
}
