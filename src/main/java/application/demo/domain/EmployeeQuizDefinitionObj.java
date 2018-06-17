package application.demo.domain;

import java.util.ArrayList;

public class EmployeeQuizDefinitionObj {

    private ArrayList<EmployeeSkill> employeeSkill;
    private ArrayList<QuizQuestion> quizQuestion;
    private ArrayList<HistoryEmployeeSkill> historyEmployeeSkills;

    public EmployeeQuizDefinitionObj(ArrayList<EmployeeSkill> employeeSkill, ArrayList<QuizQuestion> quizQuestion) {
        this.employeeSkill = employeeSkill;
        this.quizQuestion = quizQuestion;
    }

    public EmployeeQuizDefinitionObj() {

    }

    public ArrayList<EmployeeSkill> getEmployeeSkill() {
        return employeeSkill;
    }

    public void setEmployeeSkill(ArrayList<EmployeeSkill> employeeSkill) {
        this.employeeSkill = employeeSkill;
    }

    public ArrayList<QuizQuestion> getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(ArrayList<QuizQuestion> quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    @Override
    public String toString() {
        return "EmployeeQuizDefinitionObj{" +
                "employeeSkill=" + employeeSkill +
                ", quizQuestion=" + quizQuestion +
                '}';
    }
}
