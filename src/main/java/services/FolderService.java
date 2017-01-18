package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;

import domain.Actor;
import domain.Folder;
import domain.Message;
@Service
@Transactional
public class FolderService {
	
	//	Managed repository -----------------------------------------
	@Autowired
	private FolderRepository folderRepository;
	
	//	Supporting services ----------------------------------------
	@Autowired
	private ActorService actorService;
	
	//	Constructor ------------------------------------------------
	
	//	Simple CRUD methods ----------------------------------------
	
	public Folder create(Folder name) {
		Folder result= new Folder();
		List<Message> messages=new ArrayList<Message>();
		result.setMessages(messages);
		return result;
	}
	
	public List<Folder> findAll() {
		 List<Folder> result= new ArrayList<Folder>(folderRepository.findAll());
		return result;
	}
	
	public void save(Folder folder){
		Assert.notNull(folder);
//		checkPrincipal(folder);
		folderRepository.save(folder);
	}
	
//	public void delete(Folder folder) {
//	}

	public Folder findOne(int id) {
		Folder result=folderRepository.findOne(id);
		return result;
	}
	
	//	Other business methods -------------------------------------
	
	public void saveEdit(Folder folder){
		Assert.notNull(folder);
		folderRepository.save(folder);
	}
	
	public Collection<Folder> findByActorId(){
		UserAccount userAccount=LoginService.getPrincipal();
		Actor actor=actorService.findByUserAccount(userAccount);
		Collection<Folder> result;
		result= folderRepository.getByActorId(actor.getId());
		return result;
	}
	
	public Folder getInboxByActorId(){
		UserAccount userAccount=LoginService.getPrincipal();
		Actor actor=actorService.findByUserAccount(userAccount);
		Folder result = folderRepository.getInboxByActorId(actor.getId());
		return result;
	}
	
	public Folder getInboxByActorId(int id){
		Folder result = folderRepository.getInboxByActorId(id);
		if (result==null){
			Collection<Folder> folders=folderRepository.findAll();
			for(Folder f:folders){
				if(f.getActorId()==id&&f.getName().equals("inbox")) result=f;
			}
		}
		return result;
	}
	
	public Folder getOutboxByActorId(){
		UserAccount userAccount=LoginService.getPrincipal();
		Actor actor=actorService.findByUserAccount(userAccount);
		Folder result = folderRepository.getOutboxByActorId(actor.getId());
		if (result==null){
			Collection<Folder> folders=folderRepository.findAll();
			for(Folder f:folders){
				if(f.getActorId()==actor.getId()&&f.getName().equals("outbox")) result=f;
			}
		}
		return result;
	}

	public void saveFolders(Actor actor) {
		Folder inbox=new Folder();
		Collection<Message> messagesI=new ArrayList<Message>();
		inbox.setMessages(messagesI);
		inbox.setName("inbox");
		inbox.setActorId(actor.getId());
		Folder outbox=new Folder();
		Collection<Message> messagesO=new ArrayList<Message>();
		outbox.setMessages(messagesO);
		outbox.setName("outbox");
		outbox.setActorId(actor.getId());
		
		save(inbox);
		save(outbox);
		
	}
	
//	private void checkPrincipal(Folder folder) {
//		UserAccount userAccount=LoginService.getPrincipal();
//		Actor principal=actorService.findByUserAccount(userAccount);
//		Collection<Folder> folders=folderRepository.getByActorId(principal.getId());
//		Assert.isTrue(folders.contains(folder));
//	}
}
