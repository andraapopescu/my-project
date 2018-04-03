package application.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "variant")
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String variant;

    @ManyToOne(cascade= CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    public Variant( String variant, Quiz quiz ) {
        this.variant = variant;
        this.quiz = quiz;
    }

    public Variant() {
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant( String variant ) {
        this.variant = variant;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz( Quiz quiz ) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", variant='" + variant + '\'' +
                ", quiz=" + quiz +
                '}';
    }
}
