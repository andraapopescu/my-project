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

import application.demo.domain.Skill;
import application.demo.service.SkillDbService;

@RestController
@RequestMapping("/skill")
public class SkillRestController {
	
	@Autowired
    SkillDbService ss;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Skill>> getAllSkills() {
		ArrayList<Skill> result = (ArrayList<Skill>) ss.findAll();
		
		if(result.isEmpty()) {
			return new ResponseEntity<ArrayList<Skill>>(HttpStatus.NO_CONTENT);
		} 
		
		return new ResponseEntity<ArrayList<Skill>> (result,HttpStatus.OK);		
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Skill getSkillById(@PathVariable("id") long id) {
		return ss.findOne(id);
	}
	
	
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public ArrayList<Skill> getSkillByName(@PathVariable("name") String name) {
		return (ArrayList<Skill>) ss.findByName(name);
	}
	
	@RequestMapping(value = "/addSkill" , method = RequestMethod.POST)
	public ResponseEntity<Void> saveSkill(@RequestBody Skill skill, UriComponentsBuilder ucBuilder ) {
		ss.save(skill);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("skill/{id}").buildAndExpand(skill.getId()).toUri());
		
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/deleteSkill/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Skill> deleteSkill(@PathVariable("id") long id) {
		Skill result = ss.findOne(id);
		if(result == null) {
			return new ResponseEntity<Skill>(HttpStatus.NO_CONTENT);
		}
		
		ss.delete(result);
		return new ResponseEntity<Skill>(HttpStatus.NO_CONTENT);
		
	}	
}
