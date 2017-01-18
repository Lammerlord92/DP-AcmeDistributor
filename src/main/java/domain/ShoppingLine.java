package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingLine extends DomainEntity{
//Attributes --------------------------------------------------------------
	private Integer quantity;
//	private Date shoppingDate;
//Getters&Setters ---------------------------------------------------------
	@NotNull
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

//	@Past
//	@NotNull
//	public Date getShoppingDate() {
//		return shoppingDate;
//	}
//	public void setShoppingDate(Date shoppingDate) {
//		this.shoppingDate = shoppingDate;
//	}
//	
//Relationships------------------------------------------------------------
	private ShoppingCart shoppingCart;
	private Item item;
	private Invoice invoice;
	
	@ManyToOne(optional = true)
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	@Valid
	@ManyToOne(optional=false)
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	@ManyToOne(optional=true)
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
}
