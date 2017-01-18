package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Tax extends DomainEntity{
//Attributes --------------------------------------------------------------
	private String name;
	private Double percent;
//Getters&Setters ---------------------------------------------------------
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Range(min = 0, max = 1)
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	
//Relationships------------------------------------------------------------
	Collection<Item> sellingItems;
	
	@ManyToMany(cascade={CascadeType.PERSIST})
	public Collection<Item> getSellingItems() {
		return sellingItems;
	}
	public void setSellingItems(Collection<Item> sellingItems) {
		this.sellingItems = sellingItems;
	}
	
	
}
