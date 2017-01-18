package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Stock extends DomainEntity{
//Attributes --------------------------------------------------------------
	private Integer quantity;
	
//Getters&Setters ---------------------------------------------------------
	@NotNull
	@Min(0)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
//Relationships------------------------------------------------------------
	private Warehouse warehouse;
	private Item item;

	@NotNull
	@ManyToOne(optional=false)
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@NotNull
	@ManyToOne(optional=false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
}
