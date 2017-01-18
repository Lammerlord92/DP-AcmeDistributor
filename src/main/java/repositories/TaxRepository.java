package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import domain.Tax;

public interface TaxRepository extends JpaRepository<Tax,Integer>{
	@Query("select i.taxes from Item i where i.id=?1")
	Collection<Tax> getTaxesFromItemId(int itemId);
}
