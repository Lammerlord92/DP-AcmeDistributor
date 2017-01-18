//package forms;
//
//import java.util.Date;
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.Embeddable;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Past;
//import javax.validation.constraints.Size;
//
//import org.hibernate.validator.constraints.Email;
//import org.hibernate.validator.constraints.NotBlank;
//import org.hibernate.validator.constraints.SafeHtml;
//import org.hibernate.validator.constraints.URL;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import domain.CreditCard;
//
//@Embeddable
//@Access(AccessType.PROPERTY)
//public class WarehouseForm {
//	private String name;
//	private String address;
//	private String contactPhone;
//	private String email;
//	
//	@SafeHtml
//	@NotBlank
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	@SafeHtml
//	@NotBlank
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	
//	@SafeHtml
//	@Email
//	@NotBlank
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	@SafeHtml
//	@NotBlank
//	public String getContactPhone() {
//		return contactPhone;
//	}
//	public void setContactPhone(String contactPhone) {
//		this.contactPhone = contactPhone;
//	}	
//	
//}

//TODO Borrar esta clase si no resulta ser necesaria
