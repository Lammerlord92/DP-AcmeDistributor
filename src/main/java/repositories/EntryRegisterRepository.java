package repositories;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EntryRegister;

@Repository
public interface EntryRegisterRepository extends JpaRepository<EntryRegister,Integer>{
	@Query("select eR from EntryRegister eR where eR.item.id=?1")
	Collection<EntryRegister> findByItemId(int itemId);
	
	@Query("select eR from EntryRegister eR where eR.creationDate>?1")
	Collection<EntryRegister> findAfterDate(Date date);	
	
	@Query("select eR from EntryRegister eR where eR.item.id=?1 and eR.creationDate>?2")
	Collection<EntryRegister> findByItemAfterDate(int itemId,Date date);

	@Query("select eR from EntryRegister eR where eR.warehouse.id=?1")
	Collection<EntryRegister> findByWarehouse(int warehouseId);
	
	
}
