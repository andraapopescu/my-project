package application.demo.rest;

import application.demo.domain.EmployeeQuizDefinitionObj;
import application.demo.domain.EmployeeSkill;
import application.demo.domain.Quiz;
import application.demo.domain.QuizQuestion;
import application.demo.service.EmployeeSkillService;
import application.demo.service.QuizQuestionService;
import application.demo.service.QuizService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/quizPage")
public class CreateQuizRestController {

    @RequestMapping(value = "/quizzes/{id}", method = RequestMethod.GET)
    private EmployeeQuizDefinitionObj getEmployeeWithSkillsAndQuiz(@PathVariable("id") long id) {

        ArrayList<EmployeeSkill> employeeSkills = EmployeeSkillService.getEmployeeSkillByEmployee(id);
        ArrayList<Quiz> quizList = QuizService.getQuizByEmployee(id);

        ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();

        for(Quiz q : quizList) {
            quizQuestions.addAll(QuizQuestionService.getQuizQuestionByQuiz(q.getId()));
        }

        return new EmployeeQuizDefinitionObj(employeeSkills, quizQuestions);
    }

    @RequestMapping(value = "/description/{id}", method = RequestMethod.GET)
    private ArrayList<Quiz> getQuizDescription(@PathVariable("id") long id) {
        ArrayList<Quiz> quizList = QuizService.getQuizByEmployee(id);

        return quizList;
    }

}
