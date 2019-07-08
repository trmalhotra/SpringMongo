package com.tm.demo.Repository;

import java.util.List;

import com.tm.demo.Model.DesignationCount;
import com.tm.demo.Model.Employee;

public interface EmployeeRepository  {

	public List<Employee> findByName(String name);

	public List<Employee> findBySalary(long salary);
	
	public List<Employee> findNames();
	
	public Employee addNewEmployee(Employee employee);
	
	public List<Employee> addMultipleEmployees(List<Employee> employees);
	
	public List<Employee> findAllEmployees();
	
	public void deleteEmployee(String name);
	
	public List<Employee> findBySkills(String skills);
	
	public List<Employee> findBySkillOrDesignation(String skills, String designation);
	
	public List<DesignationCount> findNumberOfEmployeesPerDesignation();

}
