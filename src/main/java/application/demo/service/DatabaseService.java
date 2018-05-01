package application.demo.service;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DatabaseService<T, ID extends Serializable> extends CrudRepository<T, ID> {
	
}
