package application.demo.service;

import application.demo.domain.Question;
import application.demo.domain.Quiz;

import java.util.List;


public interface QuestionDbService extends DatabaseService<Question, Long> {
    List<Question> findByQuiz(Quiz quiz);
}
