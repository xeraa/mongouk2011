package at.ac.tuwien.ec.mongouk2011.config;

import at.ac.tuwien.ec.mongouk2011.entities.BaseEntity;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * MongoDB providing the database connection.
 */
public class MongoDB {
	private static final Logger LOG = Logger.getLogger(MongoDB.class.getName());
	private static final MongoDB INSTANCE = new MongoDB();

	private final Datastore datastore;
	public static final String DB_NAME = "mongouk2011";

	private MongoDB() {
		MongoClientOptions mongoOptions = MongoClientOptions.builder().socketTimeout(60000)
				.connectTimeout(1200000).build(); // SocketTimeout: 60s, ConnectionTimeout: 20min
		MongoClient mongoClient;
		try{
			mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), mongoOptions);
		} catch(UnknownHostException e){
			throw new RuntimeException("Error initializing MongoDB", e);
		}

		mongoClient.setWriteConcern(WriteConcern.SAFE);
		datastore = new Morphia().mapPackage(BaseEntity.class.getPackage().getName())
				.createDatastore(mongoClient, DB_NAME);
		datastore.ensureIndexes();
		LOG.info("Connection to database '" + DB_NAME + "' initialized");
	}

	public static MongoDB instance() {
		return INSTANCE;
	}

	// Creating the mongo connection is expensive - (re)use a singleton for performance reasons
	// Both the underlying Java driver and Datastore are thread safe
	public Datastore getDatabase() {
		return datastore;
	}
}
