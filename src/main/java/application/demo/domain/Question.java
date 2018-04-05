package application.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String specialization;
    private String question;
    private String answer;


    @ManyToOne(cascade= CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    public Question() {
    }

    public Question( String specialization, String question, String answer, Quiz quiz ) {
        this.specialization = specialization;
        this.question = question;
        this.answer = answer;
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz( Quiz quiz ) {
        this.quiz = quiz;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization( String specialization ) {
        this.specialization = specialization;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion( String question ) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", specialization='" + specialization + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", quiz=" + quiz +
                '}';
    }
}

