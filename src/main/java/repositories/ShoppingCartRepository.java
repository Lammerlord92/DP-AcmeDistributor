package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer>{
	@Query("select sc from ShoppingCart sc where sc.id=?1")
	ShoppingCart findByShoppingCartId(int shoppingCartId);

	@Query("select c.shoppingCart from Customer c where c.id=?1")
	ShoppingCart findByShoppingCustomerId(int customerId);
}
