package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity{
	// Attributes ----------------------------------------------------
	private String name;
	// Getters&Setters ----------------------------------------------------
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// Relationships ----------------------------------------------------
	private Collection<Message> messages;
	private int actorId;

	@Valid
	@OneToMany(cascade = CascadeType.ALL,mappedBy="folder")
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	@NotNull
	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}


	
	
}
