package repositories;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer>{
	@Query("select i from Item i where i.reference=?1")
	Item findByReference(String reference);
	
	@Query("select i from Item i where i.producer.id=?1")
	Collection<Item> findAllItemByProducer(int producerId);
	
	@Query("select i from Distributor d join d.warehouses w join w.stocks s join s.item i where d.id=?1")
	Collection<Item> findAllByDistributor(int distributorId);
	
	@Query("select s.item from Stock s join s.warehouse w where w.id=?1 and s.quantity>0")
	Collection<Item> findAllByWarehouseId(int warehouseId);
	
	//TODO R query para la busqueda por name (a lo mejor 
}
