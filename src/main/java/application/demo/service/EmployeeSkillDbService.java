package application.demo.service;

import java.util.List;

import application.demo.domain.Employee;
import application.demo.domain.EmployeeSkill;

public interface EmployeeSkillDbService extends DatabaseService<EmployeeSkill, Long> {
	List<EmployeeSkill> findByEmployee(Employee employeeId);
	List<EmployeeSkill> findBySkillName(String skillName);
}
