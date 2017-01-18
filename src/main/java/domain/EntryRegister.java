package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EntryRegister extends DomainEntity{
//Attributes --------------------------------------------------------------
	private Date creationDate;
	private Money totalPrice;
	private Integer quantity;
	
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
	
//	@NotNull
	@Valid
	public Money getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Money totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@NotNull
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
//Relationships------------------------------------------------------------
	private Item item;
	private Warehouse warehouse;
	
	@ManyToOne(optional = false,cascade = CascadeType.ALL)
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@ManyToOne(optional=false,cascade = CascadeType.ALL)
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
}
