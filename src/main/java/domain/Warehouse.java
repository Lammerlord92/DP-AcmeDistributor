package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warehouse extends DomainEntity{
//Attributes --------------------------------------------------------------
	private String name;
	private String address;
	private String contactPhone;
	private String email;
	private double latitude;
	private double longitude;
	
//Getters&Setters ---------------------------------------------------------
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotBlank
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
//Relationships------------------------------------------------------------
	Collection<Stock> stocks;
	Distributor distributor;
	
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Stock> getStocks() {
		return stocks;
	}
	public void setStocks(Collection<Stock> stocks) {
		this.stocks = stocks;
	}
	
	@ManyToOne(optional=false,cascade = CascadeType.ALL)
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
}