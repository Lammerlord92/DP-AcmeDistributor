package controllers.distributor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Customer;
import domain.Invoice;
import domain.Money;
import domain.ShoppingLine;
import domain.Tax;

import security.LoginService;
import security.UserAccount;
import services.InvoiceService;
import services.TaxService;

@Controller
@RequestMapping("/invoice/distributor")
public class InvoiceDistributorController {
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private TaxService taxService;
	
	//Listing------------------------
		@RequestMapping("/list")
		public ModelAndView list(){
			ModelAndView result;
			Collection<Invoice> invoices=invoiceService.findAll();			
			String requestURI="invoice/distributor/list.do";
			
			
			result = new ModelAndView("invoice/distributor/list");
			result.addObject("invoices",invoices);
			result.addObject("requestURI",requestURI);
			result.addObject("isProducer", false);
			
			return result;
		}
		//Details----------------------
		@RequestMapping(value= "/details", method = RequestMethod.GET)
		public ModelAndView details(@RequestParam int invoiceId){
			ModelAndView result;
			
			Invoice invoice = invoiceService.findOne(invoiceId);
			Assert.notNull(invoice);
			Customer customer=invoice.getCustomer();		
			Collection<ShoppingLine> lines=invoice.getLines();
			Collection<Tax> taxes=taxService.findAll();
			String orderNumber=invoice.getOrderNumber();
			Money totalPrice=invoice.getTotalPrice();
			
			result=new ModelAndView("invoice/distributor/details");
			
			
			result.addObject("invoice", invoice);
			result.addObject("customer", customer);
			result.addObject("invoiceLines", lines);
			result.addObject("taxes", taxes);
			result.addObject("orderNumber",orderNumber);
			result.addObject("totalPrice",totalPrice);
			
			return result;
		}

}
