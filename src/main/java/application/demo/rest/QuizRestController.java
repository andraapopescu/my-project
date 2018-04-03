package application.demo.rest;

import application.demo.domain.Quiz;
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
    QuizDbService qs;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Quiz>> getAllQuizzes() {
        ArrayList<Quiz> result = (ArrayList<Quiz>) qs.findAll();

        if(result.isEmpty()) {
            return new ResponseEntity<ArrayList<Quiz>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<ArrayList<Quiz>> (result, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Quiz getQuizById(@PathVariable("id") long id) {
        return qs.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addQuiz(@RequestBody Quiz quiz, UriComponentsBuilder ucBuilder) {
        qs.save(quiz);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("message/{id}").buildAndExpand(quiz.getId()).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
