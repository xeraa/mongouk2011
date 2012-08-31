package at.ac.tuwien.ec.mongouk2011.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.ec.mongouk2011.entities.CompanyEntity;
import at.ac.tuwien.ec.mongouk2011.entities.WorkerEntity;
import at.ac.tuwien.ec.mongouk2011.persistence.GenericPersistence;
import at.ac.tuwien.ec.mongouk2011.persistence.MongodbGenericPersistence;
import at.ac.tuwien.ec.mongouk2011.persistence.MongodbPersistence;
import at.ac.tuwien.ec.mongouk2011.persistence.Persistence;

/**
 * Testing our entities and the MongodbGenericPersistence.
 */
public class GenericPersistenceTest {

	private Persistence persistence;
	private GenericPersistence genericPersistence;

	/**
	 * Get our persistence implementation and ensure it's not null. Were not
	 * inserting any test data as there is no dataset we can easily use for all
	 * test cases. We need both MongodbPersistence for clearData() and
	 * MongodbGenericPersistence for more specific queries.
	 */
	@Before
	public void setUp() throws Exception {
		persistence = new MongodbPersistence();
		assertNotNull(persistence);
		genericPersistence = new MongodbGenericPersistence();
		assertNotNull(genericPersistence);
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
	 * Ensure a generic entity is saved and the ObjectId is generated correctly.
	 */
	@Test
	public void persist() {
		CompanyEntity company = new CompanyEntity("Test company", null, null, null,
				"http://www.test.com", "foobar@test.com");
		ObjectId id1 = genericPersistence.persist(company);
		assertNotNull("An ObjectId should have been generated when saving the entity", id1);
		assertEquals("The return value and actual value of the ObjectId should match",
				company.getId(), id1);
		WorkerEntity worker = new WorkerEntity("firstname", "surname", null, null, null,
				"foo@bar.com", new BigDecimal("5.25"), 5);
		ObjectId id2 = genericPersistence.persist(worker);
		assertEquals("The return value and actual value of the ObjectId should match",
				worker.getId(), id2);
	}

	/**
	 * Check that all documents in a collection are counted correctly.
	 */
	@Test
	public void count() {
		assertEquals("In a clean database there should be zero entries", 0,
				genericPersistence.count(CompanyEntity.class));
		assertEquals("In a clean database there should be zero entries", 0,
				genericPersistence.count(WorkerEntity.class));
		CompanyEntity company1 = new CompanyEntity("Test company 1", null, null, null,
				"http://www.test1.com", "foobar@test1.com");
		genericPersistence.persist(company1);
		assertEquals("After adding an entity there should be one entry", 1,
				genericPersistence.count(CompanyEntity.class));
		CompanyEntity company2 = new CompanyEntity("Test company 2", null, null, null,
				"http://www.test2.com", "foobar@test2.com");
		genericPersistence.persist(company2);
		assertEquals("After adding another entity there should be two entries", 2,
				genericPersistence.count(CompanyEntity.class));
		assertEquals("An unrelated collection should still have zero entries", 0,
				genericPersistence.count(WorkerEntity.class));
	}

	/**
	 * Try to get entities by their ObjectId.
	 */
	@Test
	public void getById() {
		assertNull("An invalid ObjectId should return null",
				genericPersistence.get(CompanyEntity.class, new ObjectId()));
		CompanyEntity company = new CompanyEntity("Test company", null, null, null,
				"http://www.test.com", "foobar@test.com");
		ObjectId id = genericPersistence.persist(company);
		assertNotNull("An should be available under the given ObjectId",
				genericPersistence.get(CompanyEntity.class, id));
		assertNull("A valid ObjectId for a different collection should return null",
				genericPersistence.get(WorkerEntity.class, id));
	}

}
