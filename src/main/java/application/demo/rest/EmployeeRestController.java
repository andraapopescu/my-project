package application.demo.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.QueryParam;

import application.demo.service.*;
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

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

	@Autowired
	EmployeeDbService es;
	EmployeeSkillDbService ess;
	SkillDbService s;
	HistoryEmployeeSkillDbService hess;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Employee>> getAllEmployees() {
		ArrayList<Employee> result = (ArrayList<Employee>) es.findAll();

		if (result.isEmpty()) {
			return new ResponseEntity<ArrayList<Employee>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ArrayList<Employee>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Employee getEmployeeById(@PathVariable("id") long id) {
		return es.findOne(id);
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public ResponseEntity<Void> addEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {

		es.save(employee);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("employee/{id}").buildAndExpand(employee.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMultipleEmployees/", method = RequestMethod.POST)
	public ArrayList<Employee> addEmployee(@RequestBody ArrayList<Integer> list, UriComponentsBuilder ucBuilder) {
		ArrayList<Employee> result = new ArrayList<Employee>();

		for (Integer i : list) {
			result.addAll((Collection<? extends Employee>) es.findOne((long) i));
		}

		return result;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ArrayList<Employee> findEmployeeByEmail(@QueryParam("email") String email) {

		return (ArrayList<Employee>) es.findByEmail(email);
	}

	@RequestMapping(value = "/lastName/{lastName}", method = RequestMethod.GET)
	public ArrayList<Employee> findEmployeeByLastName(@PathVariable("lastName") String lastName) {
		return (ArrayList<Employee>) es.findByLastName(lastName);

	}

	@RequestMapping(value = "/updateEmployee/", method = RequestMethod.PUT)
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Employee result = es.findOne(employee.getId());

		if (result == null) {
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		}

		es.save(employee);

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long id) {
		ArrayList<HistoryEmployeeSkill> hesList = HistoryEmployeeSkillService.getHistoryEmployeeSkillById(id);

		/*System.out.println("size " + hesList.size());
		if (hesList != null) {
			for (HistoryEmployeeSkill h : hesList) {
				System.out.println(h.getEmployee().getLastName());
				hess.delete(h);
			}
		}*/
		
		Employee result = es.findOne(id);
		if (result == null) {
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		}

		es.delete(result);
		return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
	}

}
