package application.demo.domain.employee_skill;

import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.employee.Employee;
import application.demo.domain.skills.Skill;

public interface EmployeeSkillService extends DatabaseService<EmployeeSkill, Long> {
	List<EmployeeSkill> findByEmployee(Employee employeeId);
	List<EmployeeSkill> findBySkillName(String skillName);
}
