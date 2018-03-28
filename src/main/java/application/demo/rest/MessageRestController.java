package application.demo.rest;

import java.util.ArrayList;

import application.demo.service.EmployeeService;
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

import application.demo.domain.Employee;
import application.demo.domain.Message;
import application.demo.domain.MessageService;

@RestController
@RequestMapping("/message")
public class MessageRestController {
	
	@Autowired
	MessageService ms;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Message>> getAllMessages() {
		ArrayList<Message> result = (ArrayList<Message>) ms.findAll();
		
		if(result.isEmpty()) {
			return new ResponseEntity<ArrayList<Message>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<ArrayList<Message>> (result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Message getMessageById(@PathVariable("id") long id) {
		return ms.findOne(id);
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ArrayList<Message> getMessageByEmployee(@PathVariable("id") long id) {
		Employee employee = EmployeeService.getEmployeeById(id);
		
		return (ArrayList<Message>) ms.findByEmployee(employee);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Void> addMessage(@RequestBody Message message,
			UriComponentsBuilder ucBuilder) {

		ms.save(message);
		
		HttpHeaders headers = new HttpHeaders();	
		headers.setLocation(ucBuilder.path("message/{id}").buildAndExpand(message.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
}
