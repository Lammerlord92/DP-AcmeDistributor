package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class ShoppingCart extends DomainEntity{
//Attributes --------------------------------------------------------------
//Getters&Setters ---------------------------------------------------------
//Relationships------------------------------------------------------------
	private Collection<ShoppingLine> lines;
//	private Customer customer;
//	private ShippingCompany shippingCompany;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="shoppingCart")
	public Collection<ShoppingLine> getLines() {
		return lines;
	}

	public void setLines(Collection<ShoppingLine> lines) {
		this.lines = lines;
	}
	
//	@OneToOne(optional=false,cascade={CascadeType.PERSIST})
//	public Customer getCustomer() {
//		return customer;
//	}
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
	
//	@OneToOne(cascade={CascadeType.PERSIST})
//	public ShippingCompany getShippingCompany() {
//		return shippingCompany;
//	}
//
//	public void setShippingCaompany(ShippingCompany shippingCompany) {
//		this.shippingCompany = shippingCompany;
//	}
}
