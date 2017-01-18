package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Integer>{
	@Query("select p from Producer p where p.userAccount=?1")
	Producer findByUserAccount(UserAccount userAccount);
}
