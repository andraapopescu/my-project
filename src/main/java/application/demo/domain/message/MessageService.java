package application.demo.domain.message;

import java.util.List;

import application.demo.domain.DatabaseService;
import application.demo.domain.employee.Employee;

public interface MessageService extends DatabaseService<Message, Long> {
	List<Message> findByEmployee(Employee employee);
}
