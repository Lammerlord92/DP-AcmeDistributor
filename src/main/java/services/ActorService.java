package services;

import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ActorRepository;
import security.UserAccount;
import domain.Actor;

@Transactional
@Service
public class ActorService {
	
	// ------------------- Managed repository --------------------
	
	@Autowired
	private ActorRepository actorRepository;
	
	// ------------------- Supporting services -------------------
	
	// ----------------------- Constructor -----------------------
	
	// ------------------- Simple CRUD methods -------------------
		
	// ----------------- Other business methods ------------------

	// REPOSITORIO:
	
	public Actor findByUserAccount(UserAccount userAccount) {
		Actor result=actorRepository.findByUserAccount(userAccount);
		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result=actorRepository.findAll();
		return result;
	}

	public Actor findOne(int id) {
		Actor result=actorRepository.findById(id);
		return result;
	}
	
	// CASOS DE USO:
	
	// AUXILIARES:
}
