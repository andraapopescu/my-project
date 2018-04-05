package application.demo.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quiz")
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date expirationDate;

    @ManyToOne(cascade= CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee")
    private Employee employee;

    public Quiz( Date expirationDate, Employee employee ) {
        this.expirationDate = expirationDate;
        this.employee = employee;
    }

    public Quiz() {

    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate( Date expirationDate ) {
        this.expirationDate = expirationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee( Employee employee ) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", expirationDate=" + expirationDate +
                ", employee=" + employee +
                '}';
    }
}
