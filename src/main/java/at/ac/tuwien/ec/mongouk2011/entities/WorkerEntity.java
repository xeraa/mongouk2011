package at.ac.tuwien.ec.mongouk2011.entities;

import java.math.BigDecimal;
import java.util.List;

/**
 * A concrete EmployeeEntity.
 */
public class WorkerEntity extends EmployeeEntity {

	private Integer yearsExperience;

	public WorkerEntity() {
		super();
	}

	public WorkerEntity(String firstname, String surname, List<String> telephone, List<String> fax,
			List<String> mobile, String email, BigDecimal salary, Integer yearsExperience) {
		super(firstname, surname, telephone, fax, mobile, email, salary);
		this.yearsExperience = yearsExperience;
	}

	public void setYearsExperience(Integer yearsExperience) {
		this.yearsExperience = yearsExperience;
	}

	public Integer getYearsExperience() {
		return yearsExperience;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((yearsExperience == null) ? 0 : yearsExperience.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkerEntity other = (WorkerEntity) obj;
		if (yearsExperience == null) {
			if (other.yearsExperience != null)
				return false;
		} else if (!yearsExperience.equals(other.yearsExperience))
			return false;
		return true;
	}

}
