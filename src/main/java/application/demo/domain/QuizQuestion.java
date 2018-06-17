package application.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "quiz_question")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public QuizQuestion() {

    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_question")
    private Question question;

    public QuizQuestion(Quiz quiz, Question question) {
        this.quiz = quiz;
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz( Quiz quiz ) {
        this.quiz = quiz;
    }

    public Question getQuestion() {
        return question;
    }

    public void setSkill( Question skill ) {
        this.question = skill;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "id=" + id +
                ", quiz=" + quiz +
                ", question=" + question +
                '}';
    }
}
