package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.CreditCard;

@Embeddable
@Access(AccessType.PROPERTY)
public class CustomerForm {
	private String userName;
	private String password;
	private String confirmPassword;
	private String name;
	private String surname;
	private String address;
	private Date birthday;
	private String contactPhone;
	private String email;
	private CreditCard creditCard;

	private boolean accepConditions;
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Size(min = 5, max = 32)
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	@Email @NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAccepConditions() {
		return accepConditions;
	}
	public void setAccepConditions(boolean accepConditions) {
		this.accepConditions = accepConditions;
	}
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}