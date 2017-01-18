package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Producer;
import domain.Folder;
import domain.Item;
import domain.Message;
import domain.Producer;
import forms.ProducerForm;

import repositories.ProducerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ProducerService {
	@Autowired
	private ProducerRepository producerRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private FolderService folderService;
	
//	Simple CRUD methods ----------------------------------------
	public Producer create(){
		Producer result=new Producer();
		
		UserAccount userAccount= new UserAccount();
		
		Authority authority=new Authority();
		authority.setAuthority(Authority.PRODUCER);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		
		Collection<Item> items=new ArrayList<Item>();
		
//		List<Folder> folders=new ArrayList<Folder>();
//		folders.add(inbox);
//		folders.add(outbox);
		
//		result.setFolders(folders);
		result.setItems(items);
		
		return result;
	}
	
	public Producer findOne(int producerId){
		Producer result;
		result=producerRepository.findOne(producerId);
		return result;
	}
	
	public Collection<Producer> findAll(){
		Collection<Producer>result;
		result=producerRepository.findAll();
		return result;
	}
	
	public void save(Producer producer){
		Assert.notNull(producer);
		producerRepository.saveAndFlush(producer);
		
		Folder inbox=new Folder();
		Collection<Message> messagesI=new ArrayList<Message>();
		inbox.setMessages(messagesI);
		inbox.setName("inbox");
		inbox.setActorId(producer.getId());
		Folder outbox=new Folder();
		Collection<Message> messagesO=new ArrayList<Message>();
		outbox.setMessages(messagesO);
		outbox.setName("outbox");
		outbox.setActorId(producer.getId());
		
		folderService.save(inbox);
		folderService.save(outbox);
	}
	
	public void delete(Producer producer){
		Assert.notNull(producer);
		producerRepository.delete(producer);
	}
	
//	Other business methods -------------------------------------
	
	public Producer findByUserAccount(UserAccount userAccount){		
		Producer result=producerRepository.findByUserAccount(userAccount);
		return result;
	}
	
	public Producer reconstruct(ProducerForm producerForm) {
		Producer result=create();
		Assert.isTrue(producerForm.getPassword().equals(producerForm.getConfirmPassword()));
		Assert.isTrue(producerForm.isAccepConditions());
		UserAccount userAccount=result.getUserAccount();
		userAccount.setUsername(producerForm.getUserName());
		
		Md5PasswordEncoder encoder;		
		encoder= new Md5PasswordEncoder();
		String password=encoder.encodePassword(producerForm.getPassword(), null);
		userAccount.setPassword(password);
		
		result.setUserAccount(userAccount);
		
		result.setName(producerForm.getName());
		result.setSurname(producerForm.getSurname());
		
		result.setEmail(producerForm.getEmail());
		result.setContactPhone(producerForm.getContactPhone());
		result.setBirthday(producerForm.getBirthday());
		result.setAddress(producerForm.getAddress());
		UUID codeUUID=UUID.randomUUID();
		String code=codeUUID.toString();
		result.setCode(code);
		
		return result;
	}	
}
