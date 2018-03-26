package application.demo.domain.user;

import java.util.List;

import application.demo.domain.DatabaseService;

public interface UserService extends DatabaseService<User, Long> {
	List<User> findByUserName(String userName);
}
