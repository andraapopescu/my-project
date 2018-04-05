package application.demo.service;

import application.demo.domain.DatabaseService;
import application.demo.domain.Employee;
import application.demo.domain.Quiz;

import java.util.List;

public interface QuizDbService extends DatabaseService<Quiz, Long>  {
    List<Quiz> findByEmployee(Employee employee);
}
