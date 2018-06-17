package application.demo.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "quiz")
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date expirationDate;
    private String description;

    @ManyToOne(cascade= CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee")
    private Employee employee;

    public Quiz(Date expirationDate, Employee employee, String description) {
        this.expirationDate = expirationDate;
        this.employee = employee;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", expirationDate=" + expirationDate +
                ", description='" + description + '\'' +
                ", employee=" + employee +
                '}';
    }
}
