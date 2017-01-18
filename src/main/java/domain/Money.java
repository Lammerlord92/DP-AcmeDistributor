package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Money{
// Attributes -------------------------------------------------------------
	private double amount;
	private String currency;
	
// Getters&Setters -------------------------------------------------------------
	@Min(0)
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@NotBlank
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String toString(){
		String res = ""+this.getAmount();
		return res;
	}
// Relationships ----------------------------------------------------------
	
}