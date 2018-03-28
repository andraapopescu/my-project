package application.demo.rest;

import java.util.ArrayList;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import application.demo.domain.User;
import application.demo.service.UserDbService;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
    UserDbService us;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<User>> getAllUsers() {
		ArrayList<User> result = (ArrayList<User>) us.findAll();

		if (result.isEmpty()) {
			return new ResponseEntity<ArrayList<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ArrayList<User>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable("id") long id) {
		return us.findOne(id);
	}
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public ArrayList<User> findUserByUsername(@QueryParam("username") String username) {
		return (ArrayList<User>) us.findByUserName(username);
	}
	

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ResponseEntity<Void> saveUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

		us.save(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updateUser" , method = RequestMethod.PUT)
	ResponseEntity<User> updateUser(@RequestBody User user) {
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		us.save(user);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
