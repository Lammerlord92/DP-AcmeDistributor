package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.DistributorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Distributor;
import domain.Folder;
import domain.Message;
import domain.Warehouse;
import forms.DistributorForm;

@Service
@Transactional
public class DistributorService {
	@Autowired
	private DistributorRepository distributorRepository;
//	Supporting services ----------------------------------------
	@Autowired
	private FolderService folderService;
	
//	Simple CRUD methods ----------------------------------------
	public Distributor create(){
		Distributor result=new Distributor();
		
		//TODO Se puede hacer en el reconstruct
		UserAccount userAccount=new UserAccount();
		
		Authority authority=new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		
		Collection<Warehouse> warehouses=new ArrayList<Warehouse>();
		
//		List<Folder> folders= new ArrayList<Folder>();
//		folders.add(outbox);
//		folders.add(inbox);
		
//		result.setFolders(folders);
		result.setWarehouses(warehouses);
		
		return result;
	}
	
	public Distributor findOne(int distributorId){
		Distributor result;
		result=distributorRepository.findOne(distributorId);
		return result;
	}
	
	public Collection<Distributor> findAll(){
		Collection<Distributor> result;
		result=distributorRepository.findAll();
		return result;
	}
	
	public void save(Distributor distributor){
		isDistributor();
		Assert.notNull(distributor);
		distributorRepository.saveAndFlush(distributor);
		
		Folder inbox=new Folder();
		Collection<Message> messagesI=new ArrayList<Message>();
		inbox.setMessages(messagesI);
		inbox.setName("inbox");
		inbox.setActorId(distributor.getId());
		Folder outbox=new Folder();
		Collection<Message> messagesO=new ArrayList<Message>();
		outbox.setMessages(messagesO);
		outbox.setName("outbox");
		outbox.setActorId(distributor.getId());
		
		folderService.save(inbox);
		folderService.save(outbox);
	}
	
	public void delete(Distributor distributor){
		Assert.notNull(distributor);
		distributorRepository.delete(distributor);
	}
	
//	Other business methods -------------------------------------
	
	public Distributor findByUserAccount(UserAccount userAccount){		
		Distributor result=distributorRepository.findByUserAccount(userAccount);
		return result;
	}

	public Distributor reconstruct(DistributorForm distributorForm) {
		Distributor result=create();
		Assert.isTrue(distributorForm.getPassword().equals(distributorForm.getConfirmPassword()));
		Assert.isTrue(distributorForm.isAccepConditions());
		UserAccount userAccount=result.getUserAccount();
		userAccount.setUsername(distributorForm.getUserName());
		
		Md5PasswordEncoder encoder;		
		encoder= new Md5PasswordEncoder();
		String password=encoder.encodePassword(distributorForm.getPassword(), null);
		userAccount.setPassword(password);
		
		result.setUserAccount(userAccount);
		
		result.setName(distributorForm.getName());
		result.setSurname(distributorForm.getSurname());
		
		result.setEmail(distributorForm.getEmail());
		result.setContactPhone(distributorForm.getContactPhone());
		result.setBirthday(distributorForm.getBirthday());
		result.setAddress(distributorForm.getAddress());
		UUID codeUUID=UUID.randomUUID();
		String code=codeUUID.toString();
		result.setCode(code);
		
		return result;
	}	
	private void isDistributor() {
		UserAccount account=LoginService.getPrincipal();
		Collection<Authority> authorities= account.getAuthorities();
		Boolean res=false;
		for(Authority a:authorities)
			if(a.getAuthority().equals("DISTRIBUTOR")){
				res=true;
			}
		Assert.isTrue(res);
	}
}
