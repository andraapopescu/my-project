package application.demo.service;

import application.demo.domain.*;

import java.util.List;

public interface QuizQuestionlDbService extends DatabaseService<QuizQuestion, Long> {
	List<QuizQuestion> findByQuiz(Quiz quiz);
//	List<QuizQuestion> findByQuestion(Question question);
}
