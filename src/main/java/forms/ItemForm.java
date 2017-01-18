package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import domain.Money;

@Embeddable
@Access(AccessType.PROPERTY)
public class ItemForm {
	
	// Attributes ----------------------------------------------------------
	
	private Integer itemId;
	private String reference;
	private String name;
	private String category;
	private Money price;
	
	// Getters&Setters ----------------------------------------------------

	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Valid
	public Money getPrice() {
		return price;
	}
	public void setPrice(Money price) {
		this.price = price;
	}
}