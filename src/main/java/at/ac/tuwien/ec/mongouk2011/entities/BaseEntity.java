package at.ac.tuwien.ec.mongouk2011.entities;

import java.util.Date;

import org.bson.types.ObjectId;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

/**
 * Provide the BaseEntity implementation for all entities:
 * 
 * @Id, creation and last change date, version, their getters and setters
 *      (including @PrePersist), and some abstract methods we'll require in the
 *      specific entities.
 */
public abstract class BaseEntity {

	@Id
	protected ObjectId id;

	/**
	 * We'll only provide getters for these attributes, setting is done in
	 * 
	 * @PrePersist.
	 */
	protected Date creationDate;
	protected Date lastChange;

	/**
	 * No getters and setters required, the version is handled internally.
	 */
	@Version
	private long version;

	public BaseEntity() {
		super();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastChange() {
		return lastChange;
	}

	@PrePersist
	public void prePersist() {
		this.creationDate = (creationDate == null) ? new Date() : creationDate;
		this.lastChange = (lastChange == null) ? creationDate : new Date();
	}

	/**
	 * For easier debugging and handling we'll always implement the following
	 * three methods:
	 */
	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract String toString();

}
