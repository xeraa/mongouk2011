package at.ac.tuwien.ec.mongouk2011.persistence;

import org.bson.types.ObjectId;

import at.ac.tuwien.ec.mongouk2011.entities.BaseEntity;

/**
 * The generic Persistence interface - in our specific case this will be
 * implemented by MongodbGenericPersistence.
 */
public interface GenericPersistence {

	<E extends BaseEntity> ObjectId persist(E entity);

	<E extends BaseEntity> long count(Class<E> clazz);

	<E extends BaseEntity> E get(Class<E> clazz, ObjectId id);

}
