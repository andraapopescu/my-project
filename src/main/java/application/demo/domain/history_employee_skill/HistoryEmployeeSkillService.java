package application.demo.domain.history_employee_skill;

import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.employee.Employee;

public interface HistoryEmployeeSkillService extends DatabaseService<HistoryEmployeeSkill, Long>{
	List<HistoryEmployeeSkill> findByEmployee(Employee employee);

}
