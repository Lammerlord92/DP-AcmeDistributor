package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity{
//Attributes --------------------------------------------------------------
	private String reference;
	private String name;
	private String category;
//Getters&Setters ---------------------------------------------------------
	@NotBlank
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
//Relationships------------------------------------------------------------
	private Collection<HistoryPrice> history;
	private Collection<Comment> comments;
	private Producer producer;
	private Collection<ShoppingLine> lines;
	private Collection<Tax> taxes;
	private Collection<Stock> stocks;
	private Collection<EntryRegister> registers;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="item")
	public Collection<HistoryPrice> getHistory() {
		return history;
	}
	public void setHistory(Collection<HistoryPrice> history) {
		this.history = history;
	}
	@OneToMany(cascade = CascadeType.ALL,mappedBy="itemSource")
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	@ManyToOne(optional = false)
	public Producer getProducer() {
		return producer;
	}
	public void setProducer(Producer producer) {
		this.producer = producer;
	}
	
	@OneToMany(mappedBy="item")
	public Collection<ShoppingLine> getLines() {
		return lines;
	}
	public void setLines(Collection<ShoppingLine> lines) {
		this.lines = lines;
	}
	
	@ManyToMany(cascade={CascadeType.ALL})
	public Collection<Tax> getTaxes() {
		return taxes;
	}
	public void setTaxes(Collection<Tax> taxes) {
		this.taxes = taxes;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="item")
	public Collection<Stock> getStocks() {
		return stocks;
	}
	public void setStocks(Collection<Stock> stocks) {
		this.stocks = stocks;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="item")
	public Collection<EntryRegister> getRegisters() {
		return registers;
	}
	public void setRegisters(Collection<EntryRegister> registers) {
		this.registers = registers;
	}
	
	
}
