package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity{
//Attributes --------------------------------------------------------------
	private String text;
//Getters&Setters ---------------------------------------------------------
	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
//Relationships------------------------------------------------------------
	private Item itemSource;
	private Customer customer;
	
	@ManyToOne(optional = false)
	public Item getItemSource() {
		return itemSource;
	}

	public void setItemSource(Item itemSource) {
		this.itemSource = itemSource;
	}
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
