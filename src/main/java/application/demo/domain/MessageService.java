package application.demo.domain;

import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.Employee;
import application.demo.domain.Message;

public interface MessageService extends DatabaseService<Message, Long> {
	List<Message> findByEmployee(Employee employee);
}
