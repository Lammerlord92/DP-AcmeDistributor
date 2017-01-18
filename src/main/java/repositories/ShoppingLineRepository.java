package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ShoppingLine;

@Repository
public interface ShoppingLineRepository extends JpaRepository<ShoppingLine,Integer>{
	@Query("select sl from ShoppingLine sl where sl.id=?1")
	ShoppingLine findByShoppingLineId(int shoppingLineId);
	
	@Query("select sl from Invoice i join i.lines sl where i.id=?1")
	Collection<ShoppingLine> findShoppingLineByInvoice(int invoiceId);
	
	@Query("select sl from Item i join i.lines sl where i.id=?1")
	Collection<ShoppingLine> findShoppingLineByItem(int itemId);

	@Query("select sC.lines from ShoppingCart sC where sC.id=?1")
	Collection<ShoppingLine> findByShoppingCartId(int shoppingCartId);
	
	@Query("select sL from ShoppingLine sL where sL.shoppingCart.id=?1 and sL.item.id=?2")
	ShoppingLine findByShoppingCartAndItemId(int shoppingCartId,int itemId);
}
