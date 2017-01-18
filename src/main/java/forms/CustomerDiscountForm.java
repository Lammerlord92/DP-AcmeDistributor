package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class CustomerDiscountForm {
	private Double discount;
	private int customer;
	
	@Range(min = 0, max = 1)
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public int getCustomer() {
		return customer;
	}
	public void setCustomer(int customer) {
		this.customer = customer;
	}
	
	
	
	
}
