package at.ac.tuwien.ec.mongouk2011.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.ConcurrentModificationException;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoException.DuplicateKey;

import at.ac.tuwien.ec.mongouk2011.entities.AddressEntity;
import at.ac.tuwien.ec.mongouk2011.entities.AddressEntity.AddressType;
import at.ac.tuwien.ec.mongouk2011.entities.BankConnectionEntity;
import at.ac.tuwien.ec.mongouk2011.entities.CompanyEntity;
import at.ac.tuwien.ec.mongouk2011.entities.EmployeeEntity;
import at.ac.tuwien.ec.mongouk2011.entities.ManagerEntity;
import at.ac.tuwien.ec.mongouk2011.entities.WorkerEntity;
import at.ac.tuwien.ec.mongouk2011.persistence.MongodbPersistence;
import at.ac.tuwien.ec.mongouk2011.persistence.Persistence;

/**
 * Testing our entities and the MongodbPersistence.
 */
public class PersistenceTest {

	private Persistence persistence;

	/**
	 * Get our persistence implementation and ensure it's not null. Were not
	 * inserting any test data as there is no dataset we can easily use for all
	 * test cases.
	 */
	@Before
	public void setUp() throws Exception {
		persistence = new MongodbPersistence();
		assertNotNull(persistence);
	}

	/**
	 * After finishing the test, clean up the database. This is important to
	 * allow multiple test runs in combination with unique key constraints.
	 */
	@After
	public void tearDown() throws Exception {
		persistence.clearData();
	}

	/**
	 * Ensure a company is saved and the ObjectId is generated correctly.
	 */
	@Test
	public void persistCompanyEntity() {
		CompanyEntity company = new CompanyEntity("Test company", null, null, null,
				"http://www.test.com", "foobar@test.com");
		ObjectId id = persistence.persistCompanyEntity(company);
		assertNotNull("An ObjectId should have been generated when saving the entity", id);
		assertEquals("The return value and actual value of the ObjectId should match",
				company.getId(), id);
	}
	
	/**
	 * Check salary conversion.
	 */
	@Test
	public void persistEmployeeEntity() {
		WorkerEntity entity = new WorkerEntity("Steve", "Jobs", Collections.<String> emptyList(), Collections.<String> emptyList(), Collections.<String> emptyList(), "steve@apple.com", new BigDecimal("5.25"), 5);
		persistence.persistWorkerEntity(entity);
		
		EmployeeEntity result = persistence.findByEmail("steve@apple.com");
		assertEquals(new BigDecimal("5.25"), result.getSalary());
	}

	/**
	 * Check that upserts are working correctly.
	 */
	@Test
	public void updateCompanyEntity() {
		CompanyEntity company = new CompanyEntity("Test company", null, null, null,
				"http://www.test.com", "foobar@test.com");
		persistence.persistCompanyEntity(company);
		assertEquals("The value of an entity should match after persisting it", "Test company",
				persistence.getAllCompanies().get(0).getName());
		assertFalse("The value of an entity should not match anything",
				"foobar".equals(persistence.getAllCompanies().get(0).getName()));
		company.setName("New name");
		persistence.persistCompanyEntity(company);
		assertEquals("The value of an entity should match after updateing it", "New name",
				persistence.getAllCompanies().get(0).getName());
		assertFalse("The value of an entity should not match the old name",
				"Test company".equals(persistence.getAllCompanies().get(0).getName()));
	}

	/**
	 * Testing queries returning a list of objects.
	 */
	@Test
	public void getAllCompanies() {
		assertEquals("Right at the start no companies should be found", 0, persistence
				.getAllCompanies().size());
		CompanyEntity company1 = new CompanyEntity("First company", null, null, null,
				"http://www.test1.com", "foobar@test1.com");
		persistence.persistCompanyEntity(company1);
		CompanyEntity company2 = new CompanyEntity("Second company", null, null, null,
				"http://www.test2.com", "foobar@test2.com");
		persistence.persistCompanyEntity(company2);
		assertEquals("After persisting two companies they should be found", 2, persistence
				.getAllCompanies().size());
		persistence.clearData();
		assertEquals("After clearing no companies should be found again", 0, persistence
				.getAllCompanies().size());
	}

	/**
	 * Checking the regular expression search, including upper- and lower-case
	 * versions.
	 */
	@Test
	public void findByEmail() {
		WorkerEntity worker1 = new WorkerEntity("Philipp", "Krenn", null, null, null,
				"pk@test.com", null, 1);
		persistence.persistWorkerEntity(worker1);
		WorkerEntity worker2 = new WorkerEntity("Jane", "Doe", null, null, null, "jane@test.com",
				null, 1);
		persistence.persistWorkerEntity(worker2);
		assertNull("Null parameter shouldn't find anything", persistence.findByEmail(null));
		assertNull("Empty parameter shouldn't find anything", persistence.findByEmail(""));
		assertNull("Wrong address shouldn't find anything", persistence.findByEmail("foo@test.com"));
		assertEquals("The right address should find the correct user", "Philipp", persistence
				.findByEmail("pk@test.com").getFirstname());
		assertEquals("Upper and lowercase are ignored", "Philipp",
				persistence.findByEmail("PK@test.com").getFirstname());
	}

	/**
	 * Check the built in query functions, which also matches lower- and
	 * upper-case.
	 */
	@Test
	public void findCompanyByCountry() {
		AddressEntity address = new AddressEntity("Street", "1234", "London", "UK",
				AddressType.WORK);
		BankConnectionEntity bankConnection = new BankConnectionEntity("Account owner",
				"1234567890", "11111", "222222222222", "333333333", "UK");
		CompanyEntity company = new CompanyEntity("Test company", null, null, null,
				"http://www.test.com", "foobar@test.com");
		company.setAddress(address);
		company.setBankConnection(bankConnection);
		persistence.persistCompanyEntity(company);
		assertEquals("Find the UK companies", 1, persistence.findCompanyByCountry("UK").size());
		assertEquals("Equals is case sensitive, use regular expression when needed", 0, persistence
				.findCompanyByCountry("uk").size());
		assertEquals("Find the US companies", 0, persistence.findCompanyByCountry("US").size());
	}

	/**
	 * Do a query on multiple criteria, both for filter and fluent interface.
	 */
	@Test
	public void findBySalary() {
		WorkerEntity worker1 = new WorkerEntity("Philipp", "Krenn", null, null, null,
				"pk@test.com", new BigDecimal("2500"), 1);
		persistence.persistWorkerEntity(worker1);
		WorkerEntity worker2 = new WorkerEntity("Jane", "Doe", null, null, null, "jane@test.com",
				new BigDecimal("2800.50"), 2);
		persistence.persistWorkerEntity(worker2);
		assertEquals("Invalid lower boundary should return an empty list", 0, persistence
				.findBySalary(new BigDecimal("-1"), new BigDecimal("100")).size());
		assertEquals("Mixed up boundaries should return an empty list", 0, persistence
				.findBySalary(new BigDecimal("500"), new BigDecimal("100")).size());
		assertEquals("Range outside the available values", 0,
				persistence.findBySalary(new BigDecimal("100"), new BigDecimal("2499.99")).size());
		assertEquals("No lower boundary and right inside the upper one", 1, persistence
				.findBySalary(null, new BigDecimal("2500")).size());
		assertEquals("No upper boundary and right outside the lower one", 1, persistence
				.findBySalary(new BigDecimal("2500.1"), null).size());
		assertEquals("No boundaries at all should find every entry", 2,
				persistence.findBySalary(null, null).size());
		assertEquals("Boundaries with both entries inside", 2,
				persistence.findBySalary(new BigDecimal("2000"), new BigDecimal("3000")).size());
		assertEquals("Testing againg the fluent interface",
				persistence.findBySalary(new BigDecimal("-1"), new BigDecimal("2600")),
				persistence.findBySalaryFluent(new BigDecimal("-1"), new BigDecimal("2600")));
	}

	/**
	 * Test a query which relies on @Reference.
	 */
	@Test
	public void findCompanyEmployees() {
		WorkerEntity worker1 = new WorkerEntity("Philipp", "Krenn", null, null, null,
				"pk@test.com", new BigDecimal("2500"), 1);
		WorkerEntity worker2 = new WorkerEntity("Jane", "Doe", null, null, null, "jane@test.com",
				new BigDecimal("2800"), 2);
		ManagerEntity manager = new ManagerEntity("Mr", "Big", null, null, null, "big@test.com",
				new BigDecimal("5000"), new BigDecimal("100000"));
		CompanyEntity company = new CompanyEntity("The company", null, null, null, null, null);
		persistence.workingFor(worker1, company);
		persistence.persistWorkerEntity(worker2);
		assertEquals("One employee should be working for the company", 1, persistence
				.findCompanyEmployees(company.getName()).size());
		assertEquals("The worker's name should be Philipp", "Philipp", persistence
				.findCompanyEmployees(company.getName()).get(0).getFirstname());
		persistence.workingFor(worker2, company);
		persistence.workingFor(manager, company);
		assertEquals("Three employees should be working for the company", 3, persistence
				.findCompanyEmployees(company.getName()).size());
	}

	/**
	 * Check the uniqueness constraint.
	 */
	@Test
	public void uniqueness() {
		WorkerEntity worker1 = new WorkerEntity("Philipp", "Krenn", null, null, null,
				"pk@test.com", null, 1);
		persistence.persistWorkerEntity(worker1);
		boolean duplicate = false;
		try {
			WorkerEntity worker2 = new WorkerEntity("Paul", "Kaufmann", null, null, null,
					"pk@test.com", null, 1);
			persistence.persistWorkerEntity(worker2);
		} catch (DuplicateKey e) {
			duplicate = true;
		}
		assertEquals(
				"When adding a second user with the same email address, the first one should be preserved",
				"Philipp", persistence.getAllEmployees().get(0).getFirstname());
		assertTrue("A dupliction exception should have been thrown", duplicate);
	}

	/**
	 * Ensure optimistic locking is working.
	 */
	@Test
	public void concurrency() {
		WorkerEntity worker1 = new WorkerEntity("Philipp", "Krenn", null, null, null,
				"pk@test.com", null, 1);
		persistence.persistWorkerEntity(worker1);
		WorkerEntity worker2 = persistence.getAllWorkers().get(0);
		worker1.setFirstname("Paul");
		persistence.persistWorkerEntity(worker1);
		boolean collision = false;
		try {
			worker2.setSurname("Kaufmann");
			persistence.persistWorkerEntity(worker2);
		} catch (ConcurrentModificationException e) {
			collision = true;
		}
		assertEquals("When doing two concurrent edits, only the first should go through", "Paul",
				persistence.getAllEmployees().get(0).getFirstname());
		assertTrue("A collision exception should have been thrown", collision);
	}

	/**
	 * Test the difference between primitive and object variables.
	 */
	@Test
	public void objectsPrimitives() {
		ManagerEntity manager1 = new ManagerEntity();
		manager1.setEmail("foo@test.com");
		persistence.persistManagerEntity(manager1);
		ManagerEntity manager2 = new ManagerEntity();
		manager2.setApproveFunds(true);
		manager2.setApproveHires(true);
		manager2.setEmail("bar@test.com");
		persistence.persistManagerEntity(manager2);
		assertNull("Objects can have null values", persistence.getAllManagers().get(0)
				.getApproveFunds());
		assertNotNull("Primitives can't have null values (always added to the database)",
				persistence.getAllManagers().get(0).isApproveHires());
		assertFalse("Primitives can't have null values (initialized to false)", persistence
				.getAllManagers().get(0).isApproveHires());
	}

}
