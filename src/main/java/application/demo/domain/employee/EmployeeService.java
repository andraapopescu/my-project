package application.demo.domain.employee;

import java.util.List;

import application.demo.domain.DatabaseService;

public interface EmployeeService extends DatabaseService<Employee, Long>  {
	List<Employee> findByLastName(String name);
	List<Employee> findByEmail(String email);

}
