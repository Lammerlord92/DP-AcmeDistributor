package controllers.customer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

import security.LoginService;
import security.UserAccount;
import services.CustomerService;
import services.InvoiceService;
import services.ShoppingCartService;
import services.TaxService;
import controllers.AbstractController;
import controllers.TaxController;
import domain.Customer;
import domain.Invoice;
import domain.Item;
import domain.Money;
import domain.ShoppingCart;
import domain.ShoppingLine;
import domain.Tax;
import forms.InvoiceForm;

@Controller
@RequestMapping("/invoice/customer")
public class InvoiceCustomerController extends AbstractController{
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private TaxService  taxService;
	
	//Listing------------------------
	@RequestMapping("/myInvoices")
	public ModelAndView list(){
		ModelAndView result;
		Collection<Invoice> invoices=invoiceService.findByCustomer();			
		String requestURI="invoice/customer/list.do";	
		
		result = new ModelAndView("invoice/customer/myInvoices");
		result.addObject("invoices",invoices);
		result.addObject("requestURI",requestURI);
		result.addObject("isProducer", false);
		
		return result;
	}	
	//Creation----------------------
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		ShoppingCart shoppingCart=shoppingCartService.findByShoppingCustomer();
		
		
		InvoiceForm invoiceForm = invoiceService.construct(shoppingCart);
		Assert.notNull(invoiceForm);
		
		result = createEditModelAndView(invoiceForm);
		
		return result;
	}
	@RequestMapping(value="/create",method=RequestMethod.POST,params="save")		
	public ModelAndView save(@Valid InvoiceForm invoiceForm, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result=createEditModelAndView(invoiceForm);
		} else{
			try{
				Invoice invoice=invoiceService.create(invoiceForm.getShoppingCart());
				invoiceService.save(invoice);
				result=new ModelAndView("redirect:myInvoices.do");
			} catch(Throwable oops){
				result=createEditModelAndView(invoiceForm,"invoice.commit.error");
			}
		}
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
		
		result=new ModelAndView("invoice/customer/details");
		
		
		result.addObject("invoice", invoice);
		result.addObject("customer", customer);
		result.addObject("invoiceLines", lines);
		result.addObject("taxes", taxes);
		result.addObject("orderNumber",orderNumber);
		result.addObject("totalPrice",totalPrice);
		
		return result;
	}
	
	@RequestMapping(value= "/savePDF", method = RequestMethod.GET)
	public ModelAndView detailsPDF(@RequestParam int invoiceId) throws DocumentException, BadElementException, IOException{
		ModelAndView result;
		Invoice invoice = invoiceService.findOne(invoiceId);
		Customer customer=invoice.getCustomer();
		//pdf
		
		Document document = new Document(PageSize.A4,50,50,50,50);

		PdfWriter.getInstance(document, new FileOutputStream("C:\\Documents and Settings\\Student\\Desktop\\Invoice.pdf"));

		document.open();
		
		
		//TITLE
		
		Paragraph title = new Paragraph("Invoice", FontFactory.getFont(FontFactory.HELVETICA,18,Font.BOLD, new CMYKColor(0,255,255,17)));
		Chapter chapter1=new Chapter(title,1);
		chapter1.setNumberDepth(0);
		//CUSTOMER DATA
		Paragraph titleCD = new Paragraph("Customer Data", FontFactory.getFont(FontFactory.HELVETICA,16,Font.BOLD, new CMYKColor(41,0,0,69)));
		Section section1= chapter1.addSection(titleCD);
		//Customer Name
		Paragraph section1Text = new Paragraph("Name : " + customer.getName());
		section1.add(section1Text);
		//Customer Surname
		Paragraph section2Text = new Paragraph("Surname : " + customer.getSurname());
		section1.add(section2Text);
		//Customer Address
		Paragraph section3Text = new Paragraph("Address : " + customer.getAddress());
		section1.add(section3Text);
		//Customer Contact Phone
		Paragraph section4Text = new Paragraph("Contact Phone : " + customer.getContactPhone());
		section1.add(section4Text);
		//Customer Mail
		Paragraph section5Text = new Paragraph("Mail : " + customer.getEmail());
		section1.add(section5Text);
		
		PdfPTable t = new PdfPTable(6);
		t.setSpacingBefore(50);
		t.setSpacingAfter(50);
		PdfPCell c1 = new PdfPCell(new Phrase("Creation Date"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c1);
		PdfPCell c2 = new PdfPCell(new Phrase("Amount"));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c2);
		PdfPCell c3 = new PdfPCell(new Phrase("Currency"));
		c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c3);
		PdfPCell c4 = new PdfPCell(new Phrase("Order Number"));
		c4.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c4);
		t.setHeaderRows(1);
		PdfPCell c5 = new PdfPCell(new Phrase("Item"));
		c5.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c5);
		PdfPCell c6 = new PdfPCell(new Phrase("Quantity"));
		c6.setHorizontalAlignment(Element.ALIGN_CENTER);
		t.addCell(c6);
		
		
		t.addCell(invoice.getCreationDate().toString());
		t.addCell(invoice.getTotalPrice().toString());
		t.addCell(invoice.getTotalPrice().getCurrency());
		t.addCell(invoice.getOrderNumber());
		Collection<ShoppingLine> lines =invoice.getLines();
		for(ShoppingLine l:lines){
			t.addCell(l.getItem().getName());
			t.addCell(l.getQuantity().toString());
		}
		
		
		section1.add(t);
		
		Paragraph titleTax = new Paragraph("Taxs", FontFactory.getFont(FontFactory.HELVETICA,16,Font.BOLD, new CMYKColor(41,0,0,69)));
		Section section2= chapter1.addSection(titleTax);
		for(ShoppingLine l : lines){
			Item itemLine = l.getItem();
			Collection<Tax> taxes = itemLine.getTaxes();
			for(Tax tax:taxes){
				Paragraph section2TaxName = new Paragraph("Tax name : " + tax.getName());
				section2.add(section2TaxName);
				Paragraph section2TaxPercent = new Paragraph("Percent : " + tax.getPercent());
				section2.add(section2TaxPercent);
			}
		}
		
		document.add(chapter1);
		document.close();
		System.out.println("Documento creado");
		
		
		//
		
		result=new ModelAndView("invoice/customer/myInvoices");
		Collection<Invoice> invoices=invoiceService.findByCustomer();			
		String requestURI="invoice/customer/list.do";
		
		result.addObject("invoices",invoices);
		result.addObject("requestURI",requestURI);
		result.addObject("isProducer", false);
		
		return result;
	}
	
	
	//Ancillary methods
	protected ModelAndView createEditModelAndView(InvoiceForm invoiceForm){
		ModelAndView result;
		result=createEditModelAndView(invoiceForm,null);		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(InvoiceForm invoiceForm, String message){
		ModelAndView result;
		String requestURI="invoice/customer/create.do";
		Collection<Tax> taxes=taxService.findAll();
		result=new ModelAndView("invoice/customer/edit");
		result.addObject("requestURI",requestURI);
		result.addObject("invoiceForm", invoiceForm);
		result.addObject("taxes",taxes);
		result.addObject("invoiceLines", invoiceForm.getShoppingCart().getLines());
		return result;
	}
}
