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
import application.demo.domain.HistoryEmployeeSkill;
import application.demo.service.HistoryEmployeeSkillDbService;

@RestController
@RequestMapping("/history")
public class HistoryEmployeeSkillRestController {

	@Autowired
	HistoryEmployeeSkillDbService hess;
	
	/* GET */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<HistoryEmployeeSkill> getEmployeeSkillById(@PathVariable("id") long id) {
		HistoryEmployeeSkill result = hess.findOne(id);

		if (result == null) {
			return new ResponseEntity<HistoryEmployeeSkill>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<HistoryEmployeeSkill>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ArrayList<HistoryEmployeeSkill> getHistorySkillByEmployee(@PathVariable("id") long id) {
		Employee employee = EmployeeService.getEmployeeById(id);
		
		return (ArrayList<HistoryEmployeeSkill>) hess.findByEmployee(employee);
	}
	
	
	/*POST*/
	@RequestMapping(value = "/saveHistory", method = RequestMethod.POST)
	public ResponseEntity<Void> addEmployeeSkills(@RequestBody HistoryEmployeeSkill history,
			UriComponentsBuilder ucBuilder) {

		hess.save(history);
		
		HttpHeaders headers = new HttpHeaders();	
		headers.setLocation(ucBuilder.path("history/{id}").buildAndExpand(history.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteHistory/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HistoryEmployeeSkill> deleteHistory(@PathVariable("id") long id) {

		hess.delete(id);
		return new ResponseEntity<HistoryEmployeeSkill>(HttpStatus.NO_CONTENT);
	}
	

}
