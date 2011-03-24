package at.ac.tuwien.ec.mongouk2011.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import at.ac.tuwien.ec.mongouk2011.config.MongoDB;
import at.ac.tuwien.ec.mongouk2011.entities.CompanyEntity;
import at.ac.tuwien.ec.mongouk2011.entities.EmployeeEntity;
import at.ac.tuwien.ec.mongouk2011.entities.ManagerEntity;
import at.ac.tuwien.ec.mongouk2011.entities.WorkerEntity;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;


/**
 * The Persistence implementation, showing how to do an upsert, various queries, clearing the database,...
 */
public class MongodbPersistence implements Persistence {

	private Datastore mongoDatastore;
	
	
	public MongodbPersistence(){
		mongoDatastore = MongoDB.instance().getDatabase();
	}
	
	@Override
	public ObjectId persistCompanyEntity(CompanyEntity company){
		mongoDatastore.save(company);
		return company.getId();
	}
	@Override
	public ObjectId persistManagerEntity(ManagerEntity manager){
		mongoDatastore.save(manager);
		return manager.getId();
	}
	@Override
	public ObjectId persistWorkerEntity(WorkerEntity worker){
		mongoDatastore.save(worker);
		return worker.getId();
	}
	
	@Override
	public void clearData(){
		mongoDatastore.delete(mongoDatastore.createQuery(CompanyEntity.class));
		mongoDatastore.delete(mongoDatastore.createQuery(ManagerEntity.class));
		mongoDatastore.delete(mongoDatastore.createQuery(WorkerEntity.class));
	}
	
	@Override
	public List<CompanyEntity> getAllCompanies(){
		return mongoDatastore.createQuery(CompanyEntity.class).asList();
	}
	@Override
	public List<EmployeeEntity> getAllEmployees(){
		return mongoDatastore.createQuery(EmployeeEntity.class).asList();
	}
	@Override
	public List<ManagerEntity> getAllManagers(){
		return mongoDatastore.createQuery(ManagerEntity.class).asList();
	}
	@Override
	public List<WorkerEntity> getAllWorkers(){
		return mongoDatastore.createQuery(WorkerEntity.class).asList();
	}
	
	@Override
	public EmployeeEntity findByEmail(final String email){
		if((email == null) || email.isEmpty()){
			return null;
		}
		Pattern regexp = Pattern.compile("^" + email + "$", Pattern.CASE_INSENSITIVE);
		return mongoDatastore.find(EmployeeEntity.class).filter("email", regexp).get();
	}
	
	@Override
	public List<CompanyEntity> findCompanyByCountry(String country){
		if((country == null) || country.isEmpty()){
			return new ArrayList<CompanyEntity>();
		}
		return mongoDatastore.createQuery(CompanyEntity.class).filter("address.country =", country).asList();
	}
	
	@Override
	public List<EmployeeEntity> findBySalary(final Double minimum, final Double maximum){
		if((minimum != null) && minimum < 0){
			return new ArrayList<EmployeeEntity>();
		}
		Query<EmployeeEntity> query = mongoDatastore.find(EmployeeEntity.class);
		if(minimum != null){
			query.filter("salary >=", minimum);
		}
		if(maximum != null){
			query.filter("salary <=", maximum);
		}
		return query.asList();
	}
	@Override
	public List<EmployeeEntity> findBySalaryFluent(final Double minimum, final Double maximum){
		if(minimum < 0){
			return new ArrayList<EmployeeEntity>();
		}
		Query<EmployeeEntity> query = mongoDatastore.find(EmployeeEntity.class);
		if(minimum != null){
			query.field("salary").greaterThanOrEq(minimum);
		}
		if(maximum != null){
			query.field("salary").lessThanOrEq(maximum);
		}
		return query.asList();
	}
	
	@Override
	public List<EmployeeEntity> findCompanyEmployees(final String companyName){
		if((companyName == null) || companyName.isEmpty()){
			return new ArrayList<EmployeeEntity>();
		}
		CompanyEntity company = mongoDatastore.find(CompanyEntity.class).field("name").equal(companyName).get();
		return mongoDatastore.find(EmployeeEntity.class).filter("company exists", company).asList();
	}
	
	@Override
	public void workingFor(EmployeeEntity employee, CompanyEntity company){
		if(employee.getId() == null){
			mongoDatastore.save(employee);
		}
		if(company.getId() == null){
			persistCompanyEntity(company);
		}
		employee.setCompany(company);
		mongoDatastore.save(employee);
		company.addEmployee(employee);
		persistCompanyEntity(company);
	}
	
}
