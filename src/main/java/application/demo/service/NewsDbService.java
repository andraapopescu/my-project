package application.demo.service;


import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.Employee;
import application.demo.domain.News;

public interface NewsDbService extends DatabaseService<News, Long>{
	List<News> findByEmployee(Employee employee);
}