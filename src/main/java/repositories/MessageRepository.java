package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{
	
//	@Query("select m from Message m where m.sender.id=?1 or m.recipient.id=?1")
//	Collection<Message> messagesByActorId(int actorId);
//	
//	@Query("select m from Folder f join f.messages m where f.id=?1")
//	Collection<Message> messagesByFolderId(int folderId);

	@Query("select m from Message m where m.senderId=?1 or m.recipientId=?1")
	Collection<Message> messagesByActorId(int actorId);
	
	@Query("select m from Folder f join f.messages m where f.id=?1")
	Collection<Message> messagesByFolderId(int folderId);
}