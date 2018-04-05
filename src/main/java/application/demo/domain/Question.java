package application.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "quiz")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String specialization;
    private String question;
    private String answer;
    private boolean isActive;

    public Question( String specialization, String question, String answer, boolean isActive) {
        this.specialization = specialization;
        this.question = question;
        this.answer = answer;
        this.isActive = isActive;
    }

    public Question() {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive( boolean active ) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", specialization='" + specialization + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
