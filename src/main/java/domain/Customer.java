package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor{
//Attributes --------------------------------------------------------------
	private Double discount;
	private CreditCard creditCard;
	
//Getters&Setters ---------------------------------------------------------
	@Range(min = 0, max = 1)
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
//Relationships------------------------------------------------------------
	private ShoppingCart shoppingCart;
	private Collection<Invoice> invoices;
	private Collection<Comment> comments;

	@OneToOne(optional = false,cascade={CascadeType.ALL})
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="customer")
	public Collection<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(Collection<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="customer")
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
}
