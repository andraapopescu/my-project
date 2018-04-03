package application.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String specialization;
    private String question;
    private String answer;

    public Quiz( String specialization, String question, String answer ) {
        this.specialization = specialization;
        this.question = question;
        this.answer = answer;
    }

    public Quiz() {
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
        return "Quiz{" +
                "id=" + id +
                ", specialization='" + specialization + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
