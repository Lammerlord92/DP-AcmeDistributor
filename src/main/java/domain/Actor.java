package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import security.UserAccount;
@Entity
@Access(AccessType.PROPERTY)
public abstract  class Actor extends DomainEntity{
//Attributes --------------------------------------------------------------
	private String code;
	private String name;
	private String surname;
	private String address;
	private Date birthday;
	private String contactPhone;
	private String email;
	
//Getters&Setters --------------------------------------------------------------
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotBlank
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Past @NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
	
//Relationships --------------------------------------------------------------
//	private Collection<Folder> folders;
	private UserAccount userAccount;

//	@Valid
//	@OneToMany(cascade = CascadeType.ALL )
//	public Collection<Folder> getFolders() {
//		return folders;
//	}
//	public void setFolders(Collection<Folder> folders) {
//		this.folders = folders;
//	}
//	
	@Valid
	@OneToOne(optional = false,cascade={CascadeType.ALL})
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	
}
