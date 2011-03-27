package at.ac.tuwien.ec.mongouk2011.entities;

import java.math.BigDecimal;
import java.util.List;

import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Transient;


/**
 * A concrete EmployeeEntity, showing the difference between object and primitive attributes.
 */
public class ManagerEntity extends EmployeeEntity {

	private Boolean approveFunds;
	private boolean approveHires;
	
	/**
	 * You shouldn't use Double for money values, but BigDecimal instead.
	 * However, MongoDB doesn't natively support that (yet), so we'll use Strings in MongoDB.
	 * Be careful with the conversions, both here and in the persistence.
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
	public void prePersist(){
		if(bonus != null){
			this.bonus = this.bonus.setScale(2, BigDecimal.ROUND_HALF_UP);
			bonusString = this.bonus.toString();
		}
	}
	@PostLoad
	public void postLoad(){
		if(bonus != null){
			this.bonus = this.bonus.setScale(2, BigDecimal.ROUND_HALF_UP);
			this.bonus = new BigDecimal(bonusString);
		}
	}
	
}
