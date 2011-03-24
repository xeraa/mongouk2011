package at.ac.tuwien.ec.mongouk2011.entities;

import java.util.List;


/**
 * A concrete EmployeeEntity, showing the difference between object and primitive attributes.
 */
public class ManagerEntity extends EmployeeEntity {

	private Double bonus;
	private Boolean approveFunds;
	private boolean approveHires;

	
	public ManagerEntity() {
		super();
	}
	public ManagerEntity(String firstname, String surname, List<String> telephone,
			List<String> fax, List<String> mobile, String email, Double salary, Double bonus) {
		super(firstname, surname, telephone, fax, mobile, email, salary);
		this.bonus = bonus;
	}
	
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
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
	
}
