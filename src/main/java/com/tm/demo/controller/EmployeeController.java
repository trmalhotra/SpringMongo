package com.tm.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tm.demo.Model.DesignationCount;
import com.tm.demo.Model.Employee;
import com.tm.demo.Repository.EmployeeRepository;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/add/")
	public Employee addNewEmployee(@Valid @RequestBody Employee employee) {
		employee = employeeRepository.addNewEmployee(employee);
		return employee;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addmultiple/")
	public List<Employee> addMultipleEmployees(@Valid @RequestBody List<Employee> employees) {
		employees = employeeRepository.addMultipleEmployees(employees);
		return employees;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAllEmployees();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/names")
	public List<Employee> getOnlyNamesofEmployees() {
		return employeeRepository.findNames();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
	public List<Employee> getEmployeeByName(@PathVariable("name") String name) {
		return employeeRepository.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/salary/{salary}")
	public List<Employee> getEmployeeBySalary(@PathVariable("salary") long salary) {
		return employeeRepository.findBySalary(salary);
	}

	@RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable("name") String name) {
		employeeRepository.deleteEmployee(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/skills/{skills}")
	public List<Employee> findBySkills(@PathVariable("skills") String skills) {
		return employeeRepository.findBySkills(skills);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/skills/{skills}/designation/{designation}")
	public List<Employee> findBySkillOrDesignation(@PathVariable("skills") String skills,
			@PathVariable("designation") String designation) {
		return employeeRepository.findBySkillOrDesignation(skills, designation);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/designationcount")
	public List<DesignationCount> findNumberOfEmployeesPerDesignation() {
		return employeeRepository.findNumberOfEmployeesPerDesignation();
	} 

}
