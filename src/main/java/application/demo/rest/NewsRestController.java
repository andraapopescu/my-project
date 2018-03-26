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
import application.demo.domain.news.News;
import application.demo.domain.news.NewsService;

@RestController
@RequestMapping("/news")
public class NewsRestController {
	
	@Autowired
	NewsService ns;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<News>> getAllNews() {
		ArrayList<News> result = (ArrayList<News>) ns.findAll();
		
		if(result.isEmpty()) {
			return new ResponseEntity<ArrayList<News>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<ArrayList<News>> (result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public News getNewsById(@PathVariable("id") long id) {
		return ns.findOne(id);
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ArrayList<News> getNewsByEmployee(@PathVariable("id") long id) {
		Employee employee = RestConsumer.getEmployeeById(id);
		
		return (ArrayList<News>) ns.findByEmployee(employee);
	}
	
	@RequestMapping(value = "/saveNews", method = RequestMethod.POST)
	public ResponseEntity<Void> addNews(@RequestBody News news,
			UriComponentsBuilder ucBuilder) {

		ns.save(news);
		
		HttpHeaders headers = new HttpHeaders();	
		headers.setLocation(ucBuilder.path("news/{id}").buildAndExpand(news.getId()).toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

}
