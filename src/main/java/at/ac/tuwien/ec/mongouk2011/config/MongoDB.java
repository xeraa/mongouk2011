package at.ac.tuwien.ec.mongouk2011.config;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;


/**
 * MongoDB providing the database connection.
 */
public class MongoDB {
  private static final MongoDB INSTANCE = new MongoDB();

  private final Datastore datastore;
  public static final String DB_NAME = "mongouk2011";
  
  private MongoDB() {
    try {
      Mongo mongo = new Mongo("127.0.0.1", 27017);
      datastore = new Morphia().mapPackage("at.ac.tuwien.ec.mongouk2011.entities").
      		createDatastore(mongo, DB_NAME);
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
