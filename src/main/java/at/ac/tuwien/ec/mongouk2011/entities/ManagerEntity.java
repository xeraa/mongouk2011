package at.ac.tuwien.ec.mongouk2011.entities;

import java.math.BigDecimal;
import java.util.List;
import org.mongodb.morphia.annotations.Serialized;

/**
 * A concrete EmployeeEntity, showing the difference between object and
 * primitive attributes. Using @Transient.
 */
public class ManagerEntity extends EmployeeEntity {

	private Boolean approveFunds;
	private boolean approveHires;

	/**
	 * You shouldn't use Double for money values, but BigDecimal instead.
	 * However, MongoDB doesn't natively support that (yet), so we'll serialize
	 * the value in Java - done transparently.
	 */
	@Serialized
	private BigDecimal bonus;

	public ManagerEntity() {
		super();
	}

	public ManagerEntity(String firstname, String surname, List<String> telephone,
			List<String> fax, List<String> mobile, String email, BigDecimal salary, BigDecimal bonus) {
		super(firstname, surname, telephone, fax, mobile, email, salary);
		this.setBonus(bonus);
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		if(bonus != null){
			this.bonus = bonus.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	public void setApproveFunds(Boolean approveFunds) {
		this.approveFunds = approveFunds;
	}

	public Boolean getApproveFunds() {
		return approveFunds;
	}

	public void setApproveHires(boolean approveHires) {
		this.approveHires = approveHires;
	}

	public boolean isApproveHires() {
		return approveHires;
	}

}
