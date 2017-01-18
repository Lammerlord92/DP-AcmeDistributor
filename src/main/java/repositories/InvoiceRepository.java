package repositories;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Invoice;
import domain.Customer;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer>{
	@Query("select i from Invoice i where i.customer=?1")
	Collection<Invoice> findByCustomer(Customer customer);
	
	@Query("select i from Invoice i where i.customer=?1 and i.creationDate>?2")
	Collection<Invoice> findByCustomerAfterDate(Customer customer,Date date);
}
