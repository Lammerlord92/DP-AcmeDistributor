package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer>{
	@Query("select s from Stock s where s.warehouse.id=?1 and s.item.id=?2")
	Stock getStockFromWarehouseIdItemId(int warehouseId,int itemId);
	
	@Query("select s.item, s.quantity from Stock s where s.warehouse.id=?1")
	Collection<Object[]> getStocksFromWarehouseId(int warehouseId);
	
	@Query("select sum(s.quantity) from Stock s where s.item.id=?1")
	Integer getStockOfItemId(int id);
	
	@Query("select s from Stock s where s.item.id=?1")
	Collection<Stock> getAllStockFromItemId(int id);
}
