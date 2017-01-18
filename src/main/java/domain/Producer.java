package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Producer extends Actor{
//Attributes --------------------------------------------------------------
//Getters&Setters ---------------------------------------------------------
//Relationships------------------------------------------------------------
	private Collection<Item> items;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="producer")
	public Collection<Item> getItems() {
		return items;
	}
	public void setItems(Collection<Item> items) {
		this.items = items;
	}
}
