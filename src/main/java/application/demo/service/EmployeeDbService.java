package application.demo.service;

import java.util.List;

import application.demo.domain.Employee;

public interface EmployeeDbService extends DatabaseService<Employee, Long>  {
	List<Employee> findByLastName(String name);
	List<Employee> findByEmail(String email);

}
