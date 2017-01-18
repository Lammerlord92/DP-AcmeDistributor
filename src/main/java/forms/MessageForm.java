package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Actor;

@Embeddable
@Access(AccessType.PROPERTY)
public class MessageForm {
	// Attributes ----------------------------------------------------------
	private int recipient;
	private String subject;
	private String body;
	
	
	// Getters&Setters ----------------------------------------------------

	public int getRecipient() {
		return recipient;
	}
	public void setRecipient(int recipient) {
		this.recipient = recipient;
	}
	
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
