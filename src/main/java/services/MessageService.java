package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Distributor;
import domain.Folder;
import domain.Message;
import domain.Producer;
import forms.MessageForm;
@Service
@Transactional
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	//	Supporting services ----------------------------------------

	@Autowired
	private FolderService folderService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private DistributorService distributorService;
	@Autowired
	private ProducerService producerService;
	

	//	Constructor ------------------------------------------------
	
	//	Simple CRUD methods ----------------------------------------
	
	public Message create(){
		Message result = new Message();
		UserAccount senderAccount=LoginService.getPrincipal();
		Actor sender=actorService.findByUserAccount(senderAccount);
		
		Date moment = new Date(System.currentTimeMillis()-1);

		Folder folder = folderService.getOutboxByActorId();
		
		result.setMoment(moment);
//		result.setSender(sender);
		result.setSenderId(sender.getId());
		result.setFolder(folder);
		return result;
	}


	public Message findOne(int messageId) {
		Message result=messageRepository.findOne(messageId);
		return result;
	}

	public void save(Message message) {
		Assert.notNull(message);
		
		Date moment=new Date(System.currentTimeMillis()-1);
		message.setMoment(moment);
		messageRepository.saveAndFlush(message);
	}	
	
	public void delete(Message message){
		Assert.notNull(message);
		
		messageRepository.delete(message);
	}
	
	//	Other business methods -------------------------------------
	public Collection<Message> messagesByActor(){
		UserAccount userAccount=LoginService.getPrincipal();
		Actor actor=actorService.findByUserAccount(userAccount);
		Collection<Message> result=new ArrayList<Message>();

		result = messageRepository.messagesByActorId(actor.getId());

		return result;
	}
	
	public Message prepareReply(Message message) {
		String subject=message.getSubject();

		Message result=create();
		result.setSubject("Re: "+subject);
		result.setSenderId(message.getRecipientId());
		result.setRecipientId(message.getSenderId());
		
		Folder folder = folderService.getOutboxByActorId();
		result.setFolder(folder);
		
		return result;
	}
	
	public void sendMessage(Message message){
		Message result=create();
		Actor recipient=actorService.findOne(message.getRecipientId());
		
		result.setBody(message.getBody());
		result.setMoment(message.getMoment());
		result.setSubject(message.getSubject());
		result.setSenderId(message.getSenderId());
		result.setRecipientId(message.getRecipientId());
		
		Folder folder=folderService.getInboxByActorId(recipient.getId());
		result.setFolder(folder);
		
		save(result);
	}
	public void moveMessageToFolder(Message m, Folder to){
		checkPrincipal(m);
		m.setFolder(to);
		to.getMessages().add(m);
		
		messageRepository.save(m);
		folderService.save(to);
	}

	private void checkPrincipal(Message message) {
		UserAccount userAccount=LoginService.getPrincipal();
		Actor principal=actorService.findByUserAccount(userAccount);
		Actor sender=actorService.findOne(message.getSenderId());
		Actor recipient=actorService.findOne(message.getRecipientId());
		
		Assert.isTrue(principal.getId()==sender.getId()||principal.getId()==recipient.getId());
	}
	
	public boolean isCustomer(){
		boolean result=false; 
		UserAccount userAccount=LoginService.getPrincipal();
		Collection<Authority> authorities=userAccount.getAuthorities();
		for(Authority a:authorities){
			if(a.getAuthority().equals("CUSTOMER")) result=true;
		}
		return result;
	}


	public Collection<Message> getFromInbox() {
		Collection<Message> mA=messagesByActor();
		UserAccount uA=LoginService.getPrincipal();
		Actor aact=actorService.findByUserAccount(uA);
		
		List<Message> result=new ArrayList<Message>();
		for(Message m:mA){
			if(m.getFolder().getName().equals("inbox") && m.getRecipientId()==aact.getId()){
				result.add(m);
			}
		}
		return result;
	}


	public Collection<Message> getFromOutbox() {
		Collection<Message> mA=messagesByActor();
		UserAccount uA=LoginService.getPrincipal();
		Actor aact=actorService.findByUserAccount(uA);
		List<Message> result=new ArrayList<Message>();
		for(Message m:mA){
			if(m.getFolder().getName().equals("outbox") && m.getSenderId()==aact.getId()){
				result.add(m);
			}
		}
		return result;
	}


	public Collection<Message> getFromTrashBox() {
		Collection<Message> mA=messagesByActor();
		List<Message> result=new ArrayList<Message>();
		for(Message m:mA){
			if(m.getFolder().getName().equals("trashbox")){
				result.add(m);
			}
		}
		return result;
	}


	public Message reconstruct(MessageForm messa) {
		Message result=create();
		Actor actor=actorService.findOne(messa.getRecipient());
		if(actor==null){
			Distributor d=distributorService.findOne(messa.getRecipient());
			if(d==null){
				Producer p=producerService.findOne(messa.getRecipient());
				result.setRecipientId(p.getId());
			}else result.setRecipientId(d.getId());
		}else result.setRecipientId(actor.getId());
		result.setSubject(messa.getSubject());
		result.setBody(messa.getBody());
		return result;
	}


}
