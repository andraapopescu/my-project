package application.demo.service;

import application.demo.domain.DatabaseService;
import application.demo.domain.Employee;
import application.demo.domain.Question;

import java.util.List;


public interface QuestionDbService extends DatabaseService<Question, Long> {

}
