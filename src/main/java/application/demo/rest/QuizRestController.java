package application.demo.rest;

import application.demo.domain.*;
import application.demo.service.EmployeeService;
import application.demo.service.HistoryEmployeeSkillService;
import application.demo.service.QuizDbService;
import application.demo.service.QuizService;
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
    public ResponseEntity<Void> addQuiz(@RequestBody Quiz quiz, UriComponentsBuilder ucBuilder ) {
        qzs.save(quiz);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("quiz/{id}").buildAndExpand(quiz.getId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable("id") long id) {
       Quiz result = QuizService.getQuizById(id);

        if (result == null) {
            return new ResponseEntity<Quiz>(HttpStatus.NO_CONTENT);
        }

        qzs.delete(result);
        return new ResponseEntity<Quiz>(HttpStatus.NO_CONTENT);
    }

}
