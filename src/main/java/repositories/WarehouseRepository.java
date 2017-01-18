package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer>{
	@Query("select w from Warehouse w where w.distributor.id=?1")
	Collection<Warehouse> getWarehouseFromDistributorId(int distributorId);

	//de todos los warehouse devolver el que tenga mayor stock para un item dado, comparados por referencia o por ids??????
//	@Query("select w from Warehouse w where (select max(s.quantity) from Stock s where s.warehouse.id=w.id and s.item.reference=?1)")
//	Warehouse getMoreStockForGivenItem(String referenceItem);
	
}
