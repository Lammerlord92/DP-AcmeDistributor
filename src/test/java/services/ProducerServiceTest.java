package services;

import java.util.Collection;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.Producer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ProducerServiceTest extends AbstractTest{
	@Autowired
	private ProducerService producerService;
	
	//----------------------------------------------------
	// POSITIVE TEST CASES
	//----------------------------------------------------
	
	//A distributor must be able to:
	//List all producers registered into the system.
	@Test
	public void testListProducer(){
		authenticate("distri1");
		Collection<Producer> producers=producerService.findAll();
		Assert.isTrue(producers.size()==2);
		authenticate(null);
	}

	//A user who is not authenticated must be able to:
	//Register to the system as an producer.
	//We will try to register as a producer
	@SuppressWarnings("deprecation")
	@Test
	public void testRegister(){
		Producer producer=producerService.create();
		Date fechaProb=new Date();
		fechaProb.setDate(fechaProb.getDate()-7500);
		
		producer.setName("name");
		producer.setSurname("surname");
		producer.setAddress("testing");
		producer.setBirthday(fechaProb);
		producer.setContactPhone("954954954");
		producer.setEmail("Correo@gmail.com");
		
		producerService.save(producer);
	}

	//----------------------------------------------------
	// NEGATIVE TEST CASES
	//----------------------------------------------------

	//A user who is not authenticated must be able to:
	//Register to the system as an producer.
	//We will try to register as a producer
	//And introduce a wrong Date to induce a fail.
	@SuppressWarnings("deprecation")
	@Test(expected=IllegalArgumentException.class)
	public void testRegisterException(){
		Producer producer=producerService.create();
		
		producer.setName("name");
		producer.setSurname("surname");
		producer.setBirthday(new Date(""));
		producer.setAddress("testing");
		producer.setContactPhone("954954954");
		producer.setEmail("Correo@gmail.com");

		producerService.save(producer);
	}
}