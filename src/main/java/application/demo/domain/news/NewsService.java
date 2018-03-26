package application.demo.domain.news;


import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.employee.Employee;

public interface NewsService extends DatabaseService<News, Long>{
	List<News> findByEmployee(Employee employee);
}