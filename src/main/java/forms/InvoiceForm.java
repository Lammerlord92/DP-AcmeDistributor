package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import domain.Customer;
import domain.Money;
import domain.ShoppingCart;

@Embeddable
@Access(AccessType.PROPERTY)
public class InvoiceForm {

	private Money totalPrice;
	private String orderNumber;
	private ShoppingCart shoppingCart;
	private Customer customer;
	
	public Money getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Money totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	
}
