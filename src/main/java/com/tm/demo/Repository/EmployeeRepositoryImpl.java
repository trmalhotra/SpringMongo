package com.tm.demo.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.operation.GroupOperation;
import com.tm.demo.Model.DesignationCount;
import com.tm.demo.Model.Employee;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	private final MongoTemplate mongoTemplate;

	public EmployeeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Employee> findByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mongoTemplate.find(query, Employee.class);
	}

	@Override
	public List<Employee> findBySalary(long salary) {
		Query query = new Query();
		query.addCriteria(Criteria.where("salary").is(salary));
		return mongoTemplate.find(query, Employee.class);
	}

	@Override
	public List<Employee> findNames() {
		Query query = new Query();
		query.fields().exclude("salary").exclude("designation").exclude("skills");
		return mongoTemplate.find(query, Employee.class);
	}

	@Override
	public Employee addNewEmployee(Employee employee) {
		mongoTemplate.save(employee);
		return employee;
	}

	@Override
	public List<Employee> addMultipleEmployees(List<Employee> employees) {
		for (Employee employee : employees) {
			mongoTemplate.save(employee);
		}
		return employees;
	}

	@Override
	public List<Employee> findAllEmployees() {
		return mongoTemplate.findAll(Employee.class);
	}

	@Override
	public void deleteEmployee(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		List<Employee> employees = mongoTemplate.find(query, Employee.class);

		for (Employee employee : employees) {
			mongoTemplate.remove(employee);
		}
	}

	@Override
	public List<Employee> findBySkills(String skills) {
		Query query = new Query();
		query.addCriteria(Criteria.where("skills").in(skills));
		return mongoTemplate.find(query, Employee.class);
	}

	@Override
	public List<Employee> findBySkillOrDesignation(String skills, String designation) {
		Query query = new Query();
		query.addCriteria(new Criteria().orOperator(Criteria.where("skills").in(skills),
				Criteria.where("designation").regex(designation)));
		return mongoTemplate.find(query, Employee.class);
	}

	@Override
	public List<DesignationCount> findNumberOfEmployeesPerDesignation() {

		Aggregation agg = Aggregation.newAggregation(Aggregation.group("designation").count().as("count"),
				Aggregation.project("count").and("designation").previousOperation(),
				Aggregation.sort(Sort.Direction.DESC, "count"));

		AggregationResults<DesignationCount> groupResults = mongoTemplate.aggregate(agg, Employee.class,
				DesignationCount.class);

		List<DesignationCount> designationCountList = groupResults.getMappedResults();

		return designationCountList;
	}

}
