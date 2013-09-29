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

}
