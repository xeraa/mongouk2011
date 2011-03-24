package at.ac.tuwien.ec.mongouk2011.entities;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;


/**
 * The (base) EmployeeEntity, so we don't have to duplicate code for full blown entities.
 * It's using @Indexed, @Indexed(unique=true), @Reference, and @Embedded.
 */
@Entity(value="employee", noClassnameStored=true)
public class EmployeeEntity extends BaseEntity {
	private static final long serialVersionUID = 1283376065282655142L;
	
	@Indexed
	private String firstname;
	@Indexed
	private String surname;
	@Indexed(unique=true)
	private String email;
	
	private List<String> telephone = new ArrayList<String>();
	private List<String> fax = new ArrayList<String>();
	private List<String> mobile = new ArrayList<String>();
	private Double salary;
	
	@Reference
	private CompanyEntity company;
	
	@Embedded
	private BankConnectionEntity bankConnection;
	@Embedded
	private AddressEntity address;

	public EmployeeEntity() {
		super();
	}
	public EmployeeEntity(String firstname, String surname, List<String> telephone,
			List<String> fax, List<String> mobile, String email, Double salary) {
		super();
		this.firstname = firstname;
		this.surname = surname;
		this.telephone = telephone;
		this.fax = fax;
		this.mobile = mobile;
		this.email = email;
		this.setSalary(salary);
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public List<String> getTelephone() {
		return telephone;
	}
	public void setTelephone(List<String> telephone) {
		this.telephone = telephone;
	}
	public List<String> getFax() {
		return fax;
	}
	public void setFax(List<String> fax) {
		this.fax = fax;
	}
	public List<String> getMobile() {
		return mobile;
	}
	public void setMobile(List<String> mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Double getSalary() {
		return salary;
	}

	public CompanyEntity getCompany() {
		return company;
	}
	public void setCompany(CompanyEntity company) {
		this.company = company;
	}
	
	public BankConnectionEntity getBankConnection() {
		return bankConnection;
	}
	public void setBankConnection(BankConnectionEntity bankConnection) {
		this.bankConnection = bankConnection;
	}
	public AddressEntity getAddress() {
		return address;
	}
	public void setAddress(AddressEntity address) {
		this.address = address;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (( id == null) ? 0 : id.hashCode());
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
		EmployeeEntity other = (EmployeeEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", firstname=" + firstname + ", surname=" + surname
				+ ", telephone=" + telephone + ", fax=" + fax + ", mobile=" + mobile + ", email="
				+ email + ", salary=" + salary + ", creationDate=" + creationDate + ", lastChange="
				+ lastChange + ", company=" + company + ", bankConnection=" + bankConnection
				+ ", address=" + address + "]";
	}
	
}
