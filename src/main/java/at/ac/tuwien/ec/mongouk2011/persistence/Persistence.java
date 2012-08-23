package at.ac.tuwien.ec.mongouk2011.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.bson.types.ObjectId;

import at.ac.tuwien.ec.mongouk2011.entities.CompanyEntity;
import at.ac.tuwien.ec.mongouk2011.entities.EmployeeEntity;
import at.ac.tuwien.ec.mongouk2011.entities.ManagerEntity;
import at.ac.tuwien.ec.mongouk2011.entities.WorkerEntity;

/**
 * The general Persistence interface - in our specific case this will be
 * implemented by MongodbPersistence.
 */
public interface Persistence {

	ObjectId persistCompanyEntity(CompanyEntity company);

	ObjectId persistWorkerEntity(WorkerEntity worker);

	ObjectId persistManagerEntity(ManagerEntity manager);

	void clearData();

	List<CompanyEntity> getAllCompanies();

	List<EmployeeEntity> getAllEmployees();

	List<ManagerEntity> getAllManagers();

	List<WorkerEntity> getAllWorkers();

	EmployeeEntity findByEmail(String email);

	List<CompanyEntity> findCompanyByCountry(String country);

	List<EmployeeEntity> findBySalary(BigDecimal minimum, BigDecimal maximum);

	List<EmployeeEntity> findBySalaryFluent(BigDecimal minimum, BigDecimal maximum);

	List<EmployeeEntity> findCompanyEmployees(String companyName);

	void workingFor(EmployeeEntity employee, CompanyEntity company);

}
