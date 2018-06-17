package application.demo.rest;

import application.demo.domain.Question;
import application.demo.service.QuestionDbService;
import application.demo.service.QuizDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/question")
public class QuestionRestController {

    @Autowired
    QuestionDbService qs;

    @Autowired
    QuizDbService qzs;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Question>> getAllQuestions() {
        ArrayList<Question> result = (ArrayList<Question>) qs.findAll();

        if(result.isEmpty()) {
            return new ResponseEntity<ArrayList<Question>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<ArrayList<Question>> (result, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Question getQuestionById( @PathVariable("id") long id) {
        return qs.findOne(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Void> addQuestion( @RequestBody Question question, UriComponentsBuilder ucBuilder) {
        qs.save(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("question/{id}").buildAndExpand(question.getId()).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

//    @RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET)
//    public ArrayList<Question> getQuestionsByQuiz( @PathVariable("id") long id) {
//        Quiz quiz = QuizService.getQuizById(id);
//
//        return (ArrayList<Question>) qs.findByQuiz(quiz);
//    }

    @RequestMapping(value = "/deleteQuestion/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Question> deleteQuestion( @PathVariable("id") long id) {
        Question result = qs.findOne(id);
        if(result == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        qs.delete(result);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/updateQuestion" , method = RequestMethod.PUT)
    ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        if(question == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        qs.save(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
