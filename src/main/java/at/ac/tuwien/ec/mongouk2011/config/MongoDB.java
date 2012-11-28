package at.ac.tuwien.ec.mongouk2011.config;

import at.ac.tuwien.ec.mongouk2011.entities.BaseEntity;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;


/**
 * MongoDB providing the database connection.
 */
public class MongoDB {
	private static final MongoDB INSTANCE = new MongoDB();

	private final Datastore datastore;
	public static final String DB_NAME = "mongouk2011";

	private MongoDB() {
		try {
			MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
			mongoClient.setWriteConcern(WriteConcern.SAFE);
			datastore = new Morphia().mapPackage(BaseEntity.class.getPackage().getName())
					.createDatastore(mongoClient, DB_NAME);
			datastore.ensureIndexes();
		} catch (Exception e) {
			throw new RuntimeException("Error initializing MongoDB", e);
		}
	}

	public static MongoDB instance() {
		return INSTANCE;
	}

	public Datastore getDatabase() {
		return datastore;
	}
}
