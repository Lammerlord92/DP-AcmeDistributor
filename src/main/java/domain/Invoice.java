package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Invoice extends DomainEntity{
//Attributes --------------------------------------------------------------
	private Date creationDate;
	private Money totalPrice;
	private String orderNumber;
	
//Getters&Setters ---------------------------------------------------------
	@Past 
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@NotNull
	@Valid
	public Money getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Money totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@NotBlank
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	//Relationships------------------------------------------------------------
	private Customer customer;
	private Collection<ShoppingLine> lines;

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="invoice")
	public Collection<ShoppingLine> getLines() {
		return lines;
	}
	public void setLines(Collection<ShoppingLine> lines) {
		this.lines = lines;
	}
	
	
	
	
}
