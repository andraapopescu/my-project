package application.demo.rest;

import application.demo.domain.Question;
import application.demo.service.QuestionDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/quiz")
public class QuestionRestController {

    @Autowired
    QuestionDbService qs;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Question>> getAllQuizzes() {
        ArrayList<Question> result = (ArrayList<Question>) qs.findAll();

        if(result.isEmpty()) {
            return new ResponseEntity<ArrayList<Question>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<ArrayList<Question>> (result, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Question getQuizById( @PathVariable("id") long id) {
        return qs.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addQuiz( @RequestBody Question question, UriComponentsBuilder ucBuilder) {
        qs.save(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("message/{id}").buildAndExpand(question.getId()).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
