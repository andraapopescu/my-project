package application.demo.service;

import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.Employee;
import application.demo.domain.HistoryEmployeeSkill;

public interface HistoryEmployeeSkillDbService extends DatabaseService<HistoryEmployeeSkill, Long>{
	List<HistoryEmployeeSkill> findByEmployee(Employee employee);

}

