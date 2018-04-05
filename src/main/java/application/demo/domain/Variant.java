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
    private Question question;

    public Variant( String variant, Question question ) {
        this.variant = variant;
        this.question = question;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion( Question question ) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", variant='" + variant + '\'' +
                ", question=" + question +
                '}';
    }
}
