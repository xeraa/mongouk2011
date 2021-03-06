package at.ac.tuwien.ec.mongouk2011.entities;

import org.mongodb.morphia.annotations.Converters;

import java.math.BigDecimal;
import java.util.List;

import at.ac.tuwien.ec.mongouk2011.persistence.BigDecimalConverter;

/**
 * A concrete EmployeeEntity.
 */
@Converters({BigDecimalConverter.class})
public class WorkerEntity extends EmployeeEntity {

	private Integer yearsExperience;
	private BigDecimal dailyAllowance;

	public WorkerEntity() {
		super();
	}

	public WorkerEntity(String firstname, String surname, List<String> telephone, List<String> fax,
			List<String> mobile, String email, BigDecimal salary, Integer yearsExperience, BigDecimal dailyAllowance) {
		super(firstname, surname, telephone, fax, mobile, email, salary);
		this.yearsExperience = yearsExperience;
		this.setDailyAllowance(dailyAllowance);
	}

	public void setYearsExperience(Integer yearsExperience) {
		this.yearsExperience = yearsExperience;
	}

	public Integer getYearsExperience() {
		return yearsExperience;
	}

	public BigDecimal getDailyAllowance() {
		return dailyAllowance;
	}

	public void setDailyAllowance(BigDecimal dailyAllowance) {
		if(dailyAllowance != null){
			this.dailyAllowance = dailyAllowance.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	@Override
	public boolean equals(Object o) {
		// Doing the same thing as the base method, just for added clarity
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
