package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.persistence.Column;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class HistoryPrice extends DomainEntity{
//Attributes --------------------------------------------------------------
	private Date startDate;
	private Date finishDate;
	private Money distributorPrice;
	private Money producerPrice;
//Getters&Setters ---------------------------------------------------------
	@Past
	@NotNull
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	@NotNull
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name="amount",
			column=@Column(name="salePrice")),
		@AttributeOverride(name="currency",
			column=@Column(name="saleCurrency"))
	})
	public Money getDistributorPrice() {
		return distributorPrice;
	}
	public void setDistributorPrice(Money distributorPrice) {
		this.distributorPrice = distributorPrice;
	}
	@NotNull
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name="amount",
			column=@Column(name="producerPrice")),
		@AttributeOverride(name="currency",
			column=@Column(name="producerCurrency"))
	})
	public Money getProducerPrice() {
		return producerPrice;
	}
	public void setProducerPrice(Money producerPrice) {
		this.producerPrice = producerPrice;
	}
//Relationships------------------------------------------------------------	
	private Item item;
	
	@ManyToOne(optional = false,cascade = CascadeType.ALL)
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
}
