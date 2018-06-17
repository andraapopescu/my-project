package application.demo.rest;

import application.demo.domain.Quiz;
import application.demo.domain.QuizQuestion;
import application.demo.service.QuizQuestionlDbService;
import application.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

@RestController
@RequestMapping("/quizQuestion")
public class QuizQuestionRestController {
	
	@Autowired
    QuizQuestionlDbService qqs;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<QuizQuestion>> getAllQuizQuestion() {
		ArrayList<QuizQuestion> result = (ArrayList<QuizQuestion>) qqs.findAll();

		if (result.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<QuizQuestion> getQuizQuestionById(@PathVariable("id") long id) {
        QuizQuestion result = qqs.findOne(id);

		if (result == null) {
			return new ResponseEntity<QuizQuestion>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<QuizQuestion>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET)
	public ArrayList<QuizQuestion> getQuizQuestionByQuiz(@PathVariable("id") long id) {
        Quiz quiz = QuizService.getQuizById(id);

        return (ArrayList<QuizQuestion>) qqs.findByQuiz(quiz);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Void> addQuizQuestion(@RequestBody QuizQuestion quizQuestion,
			UriComponentsBuilder ucBuilder) {

		qqs.save(quizQuestion);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("quizQuestion/{id}").buildAndExpand(quizQuestion.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

}
