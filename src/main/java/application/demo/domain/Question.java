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

    private String variant1;
    private String variant2;
    private String variant3;
    private String variant4;


    @ManyToOne(cascade= CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    public Question( String value, String questionAreaValue, String answerValue, String s, String value1, String s1, String value2 ) {
    }

    public Question() {

    }

    public Question(String specialization, String question, String answer, String variant1, String variant2, String variant3, String variant4, Quiz quiz ) {
        this.specialization = specialization;
        this.question = question;
        this.answer = answer;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.variant4 = variant4;
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

    public String getVariant1() {
        return variant1;
    }

    public void setVariant1( String variant1 ) {
        this.variant1 = variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public void setVariant2( String variant2 ) {
        this.variant2 = variant2;
    }

    public String getVariant3() {
        return variant3;
    }

    public void setVariant3( String variant3 ) {
        this.variant3 = variant3;
    }

    public String getVariant4() {
        return variant4;
    }

    public void setVariant4( String variant4 ) {
        this.variant4 = variant4;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz( Quiz quiz ) {
        this.quiz = quiz;
    }
}

