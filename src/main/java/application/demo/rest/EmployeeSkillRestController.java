package application.demo.rest;

import java.util.ArrayList;

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

import application.demo.domain.employee.Employee;
import application.demo.domain.employee_skill.EmployeeSkill;
import application.demo.domain.employee_skill.EmployeeSkillService;
import application.demo.domain.skills.Skill;

@RestController
@RequestMapping("/employeeSkills")
public class EmployeeSkillRestController {
	
	@Autowired
	EmployeeSkillService ess;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<EmployeeSkill>> getAllEmployeeSkills() {
		ArrayList<EmployeeSkill> result = (ArrayList<EmployeeSkill>) ess.findAll();

		if (result.isEmpty()) {
			return new ResponseEntity<ArrayList<EmployeeSkill>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<ArrayList<EmployeeSkill>>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<EmployeeSkill> getEmployeeSkillById(@PathVariable("id") long id) {
		EmployeeSkill result = ess.findOne(id);

		if (result == null) {
			return new ResponseEntity<EmployeeSkill>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<EmployeeSkill>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ArrayList<EmployeeSkill> getEmployeeSkillByEmployee(@PathVariable("id") long id) {
		Employee employee = RestConsumer.getEmployeeById(id);
		
		return (ArrayList<EmployeeSkill>) ess.findByEmployee(employee);
	}
	
	@RequestMapping(value = "/skill/{skillName}", method = RequestMethod.GET)
	public ArrayList<EmployeeSkill> getEmployeeSkillBySkill(@PathVariable("skillName") String skillName) {
		
		return (ArrayList<EmployeeSkill>) ess.findBySkillName(skillName);
	}
	
	
	/* post */
	@RequestMapping(value = "/saveEmployeeSkill", method = RequestMethod.POST)
	public ResponseEntity<Void> addEmployeeSkills(@RequestBody EmployeeSkill employeeSkills,
			UriComponentsBuilder ucBuilder) {

		ess.save(employeeSkills);
		
		HttpHeaders headers = new HttpHeaders();	
		headers.setLocation(ucBuilder.path("employeeSkills/{id}").buildAndExpand(employeeSkills.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	// delete //
	
	@RequestMapping(value = "/deleteEmployeeSkillsById/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<EmployeeSkill> deleteEmployeeSkillsById(@PathVariable("id") long id) {

		ess.delete(id);
		return new ResponseEntity<EmployeeSkill>(HttpStatus.NO_CONTENT);
	}
	
	
}
