package application.demo.rest;

import application.demo.domain.Employee;
import application.demo.domain.Question;
import application.demo.domain.Quiz;
import application.demo.domain.Skill;
import application.demo.service.EmployeeService;
import application.demo.service.QuizDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/quiz")
public class QuizRestController {

    @Autowired
    QuizDbService qzs;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Quiz>> getAllQuizzes() {
        ArrayList<Quiz> result = (ArrayList<Quiz>) qzs.findAll();

        if (result.isEmpty()) {
            return new ResponseEntity<ArrayList<Quiz>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<ArrayList<Quiz>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Quiz getQuizById(@PathVariable("id") long id ) {
        return qzs.findOne(id);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ArrayList<Quiz> getQuizByEmployee(@PathVariable("id") long id) {
        Employee employee = EmployeeService.getEmployeeById(id);

        return (ArrayList<Quiz>) qzs.findByEmployee(employee);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addQuiz( @RequestBody Quiz quiz, UriComponentsBuilder ucBuilder ) {
        qzs.save(quiz);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("variant/{id}").buildAndExpand(quiz.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
