package at.ac.tuwien.ec.mongouk2011.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Reference;
import com.google.code.morphia.annotations.Transient;

/**
 * The (base) EmployeeEntity, so we don't have to duplicate code for full blown
 * entities. It's using @Indexed, @Indexed(unique=true), @Reference, @Transient,
 * and @Embedded. Additionally making use of @PrePersist and @PostLoad.
 */
@Entity(value = "employee", noClassnameStored = true)
public class EmployeeEntity extends BaseEntity {

	@Indexed
	private String firstname;
	@Indexed
	private String surname;
	@Indexed(unique = true)
	private String email;

	private List<String> telephone = new ArrayList<String>();
	private List<String> fax = new ArrayList<String>();
	private List<String> mobile = new ArrayList<String>();

	/**
	 * You shouldn't use Double for money values, but BigDecimal instead.
	 * However, MongoDB doesn't natively support that (yet), so we'll use
	 * Strings in MongoDB. Be careful with the conversions, both here and in the
	 * persistence.
	 */
	@Transient
	private BigDecimal salary;
	private String salaryString;

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
			List<String> fax, List<String> mobile, String email, BigDecimal salary) {
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

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public BigDecimal getSalary() {
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

	@PrePersist
	public void prePersist() {
		super.prePersist();
		if (salary != null) {
			this.salary = this.salary.setScale(2, BigDecimal.ROUND_HALF_UP);
			this.salaryString = this.salary.toString();
		}
	}

	@PostLoad
	public void postLoad() {
		if (salaryString != null) {
			this.salary = new BigDecimal(salaryString);
		} else {
			this.salary = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
