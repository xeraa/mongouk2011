package at.ac.tuwien.ec.mongouk2011.entities;

import com.google.code.morphia.annotations.Embedded;


/**
 * An embedded AddressEntity, which should not use @Id.
 * Also creationDate and lastChange are not required, as they are already in the container entity.
 */
@Embedded
public class AddressEntity {
	private static final long serialVersionUID = -2984861574365342612L;
	
	public static enum AddressType {
		HOME,
		WORK,
		HOMEANDWORK,
	}
	
	private String street;
	private String zip;
	private String city;
	private String country;
	private AddressType addresType;
	
	
	
	public AddressEntity() {
		super();
	}
	public AddressEntity(String street, String zip, String city, String country, AddressType addresType) {
		super();
		this.street = street;
		this.zip = zip;
		this.city = city;
		this.country = country;
		this.addresType = addresType;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public AddressType getAddresType() {
		return addresType;
	}
	public void setAddresType(AddressType addresType) {
		this.addresType = addresType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AddressEntity [street=" + street + ", zip=" + zip + ", city=" + city + ", country="
				+ country + ", addresType=" + addresType + "]";
	}
	
}