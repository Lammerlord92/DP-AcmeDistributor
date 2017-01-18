package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import utilities.AbstractTest;
import domain.Distributor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DistributorServiceTest extends AbstractTest{
	@Autowired
	private DistributorService distributorService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A Distributor must be able to:
	//Register in the system a distributor.
	//We will try to register a distributor
	@Test
	public void testRegister(){
		authenticate("distri1");
		
		Distributor distributor=distributorService.create();
		
		distributor.setName("name");
		distributor.setAddress("testing");
		distributor.setSurname("surname");
		distributor.setContactPhone("954954954");
		distributor.setEmail("Correo@gmail.com");

		distributor.setBirthday(new Date(new Date().getTime()-2000000));
		distributorService.save(distributor);
		
		unauthenticate();
	}

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------

	//A Distributor must be able to:
	//Register in the system a distributor.
	//We will try to register a distributor
	//And introduce an incorrect Name to induce a fail.
	@Test(expected=ConstraintViolationException.class)
	public void testRegisterException(){
		authenticate("distri1");
		
		Distributor distributor=distributorService.create();
		
		distributor.setName("");
		distributor.setAddress("testing");
		distributor.setSurname("surname");
		distributor.setContactPhone("954954954");
		distributor.setEmail("Correo@gmail.com");
		
		distributorService.save(distributor);
		
		unauthenticate();
	}
}