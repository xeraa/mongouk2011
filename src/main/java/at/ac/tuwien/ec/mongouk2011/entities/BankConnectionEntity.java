package at.ac.tuwien.ec.mongouk2011.entities;

import com.github.jmkgreen.morphia.annotations.Embedded;

/**
 * An embedded BankConnectionEntity, which should not use @Id. Also creationDate
 * and lastChange are not required, as they are already in the container entity.
 */
@Embedded
public class BankConnectionEntity {

	private String accountNumber;
	private String bankCode;
	private String bic;
	private String iban;
	private String country;

	public BankConnectionEntity() {
		super();
	}

	public BankConnectionEntity(String name, String accountNumber, String bankCode, String bic,
			String iban, String country) {
		super();
		this.accountNumber = accountNumber;
		this.bankCode = bankCode;
		this.bic = bic;
		this.iban = iban;
		this.country = country;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bic == null) ? 0 : bic.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
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
		BankConnectionEntity other = (BankConnectionEntity) obj;
		if (bic == null) {
			if (other.bic != null)
				return false;
		} else if (!bic.equals(other.bic))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankConnectionEntity [accountNumber=" + accountNumber + ", bankCode=" + bankCode
				+ ", bic=" + bic + ", iban=" + iban + ", country=" + country + "]";
	}

}
