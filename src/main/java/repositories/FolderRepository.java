package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Folder;

public interface FolderRepository extends JpaRepository<Folder,Integer>{
//	@Query("select f from Actor a join a.folders f where a.id=?1 and f.name='inbox'")
//	Folder getInboxByActorId(int id);
//	
//	@Query("select f from Actor a join a.folders f where a.id=?1 and f.name='outbox'")
//	Folder getOutboxByActorId(int id);
//	
//	@Query("select f from Actor a join a.folders f where a.id=?1")
//	Collection<Folder> getByActorId(int id);

	
//	@Query("select f from Folder f where f.actor.id=?1 and f.name='inbox'")
//	Folder getInboxByActorId(int id);
//	
//	@Query("select f from Folder f where f.actor.id=?1 and f.name='outbox'")
//	Folder getOutboxByActorId(int id);
//	
//	@Query("select f from Folder f where f.actor.id=?1")
//	Collection<Folder> getByActorId(int id);

	
	@Query("select f from Folder f where f.actorId=?1 and f.name='inbox'")
	Folder getInboxByActorId(int id);
	
	@Query("select f from Folder f where f.actorId=?1 and f.name='outbox'")
	Folder getOutboxByActorId(int id);
	
	@Query("select f from Folder f where f.actorId=?1")
	Collection<Folder> getByActorId(int id);
}
