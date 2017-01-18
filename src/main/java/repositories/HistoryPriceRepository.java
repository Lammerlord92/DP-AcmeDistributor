package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.HistoryPrice;

@Repository
public interface HistoryPriceRepository extends JpaRepository<HistoryPrice,Integer>{
	@Query("select i.history from Item i where i.id=?1")
	Collection<HistoryPrice> getHistoryPriceFromItemId(int itemId);
	
	@Query("select h from HistoryPrice h where h.item.id=?1 and h.startDate<?2 and h.finishDate>?2")
	Collection<HistoryPrice> getHistoryFromItemAndDate(int itemId, Date date);
	//TODO Hay que asegurarse que las fechas se comparen así

	@Query("select hp from HistoryPrice hp where hp.startDate in(select max(hp2.startDate) from HistoryPrice hp2 where hp2.item.id=?1) and hp.item.id=?1")
	HistoryPrice getLastFromItemId(Integer itemId);
}
