package application.demo.service;

import application.demo.domain.DatabaseService;
import application.demo.domain.Quiz;
import application.demo.domain.Variant;

import java.util.List;

public interface VariantDbService extends DatabaseService<Variant, Long> {
    List<Variant> findByQuiz(Quiz quiz);
}
