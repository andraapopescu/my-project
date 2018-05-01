package application.demo.service;

import java.util.List;

import application.demo.domain.Employee;
import application.demo.domain.Message;

public interface MessageDbService extends DatabaseService<Message, Long> {
	List<Message> findByEmployee(Employee employee);
}
