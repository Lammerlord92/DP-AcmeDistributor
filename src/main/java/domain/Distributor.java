package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Distributor extends Actor{
//Attributes --------------------------------------------------------------
//Getters&Setters ---------------------------------------------------------
//Relationships------------------------------------------------------------
	private Collection<Warehouse> warehouses;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="distributor")
	public Collection<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(Collection<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
}
