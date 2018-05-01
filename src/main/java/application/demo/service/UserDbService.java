package application.demo.service;

import java.util.List;

import application.demo.domain.User;

public interface UserDbService extends DatabaseService<User, Long> {
	List<User> findByUserName(String userName);
}
