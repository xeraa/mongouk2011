package at.ac.tuwien.ec.mongouk2011.entities;

import java.math.BigDecimal;
import java.util.List;

import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Transient;

/**
 * A concrete EmployeeEntity, showing the difference between object and
 * primitive attributes. Using @Transient.
 */
public class ManagerEntity extends EmployeeEntity {

	private Boolean approveFunds;
	private boolean approveHires;

	/**
	 * You shouldn't use Double for money values, but BigDecimal instead.
	 * However, MongoDB doesn't natively support that (yet), so we'll use
	 * Strings in MongoDB. Be careful with the conversions, both here and in the
	 * persistence.
	 */
	@Transient
	private BigDecimal bonus;
	private String bonusString;

	public ManagerEntity() {
		super();
	}

	public ManagerEntity(String firstname, String surname, List<String> telephone,
			List<String> fax, List<String> mobile, String email, BigDecimal salary, BigDecimal bonus) {
		super(firstname, surname, telephone, fax, mobile, email, salary);
		this.bonus = bonus;
	}

	public BigDecimal getBonus() {
		return bonus;
	}

	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
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

	@PrePersist
	public void prePersist() {
		super.prePersist();
		if (bonus != null) {
			this.bonus = this.bonus.setScale(2, BigDecimal.ROUND_HALF_UP);
			this.bonusString = bonus.toString();
		}
	}

	@PostLoad
	public void postLoad() {
		super.postLoad();
		if (bonusString != null) {
			this.bonus = new BigDecimal(bonusString);
		} else {
			this.bonus = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((approveFunds == null) ? 0 : approveFunds.hashCode());
		result = prime * result + (approveHires ? 1231 : 1237);
		result = prime * result + ((bonus == null) ? 0 : bonus.hashCode());
		result = prime * result + ((bonusString == null) ? 0 : bonusString.hashCode());
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
		ManagerEntity other = (ManagerEntity) obj;
		if (approveFunds == null) {
			if (other.approveFunds != null)
				return false;
		} else if (!approveFunds.equals(other.approveFunds))
			return false;
		if (approveHires != other.approveHires)
			return false;
		if (bonus == null) {
			if (other.bonus != null)
				return false;
		} else if (!bonus.equals(other.bonus))
			return false;
		if (bonusString == null) {
			if (other.bonusString != null)
				return false;
		} else if (!bonusString.equals(other.bonusString))
			return false;
		return true;
	}

}
