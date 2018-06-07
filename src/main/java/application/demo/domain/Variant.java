package application.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "variant")
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String variant;

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

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", variant='" + variant + '\'' +
                '}';
    }
}
