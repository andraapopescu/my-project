package application.demo.domain.message;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import application.demo.domain.employee.Employee;

@Entity
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String sentFrom;
	private Date date;
	private String subject;
	private String text;
	
	@ManyToOne(cascade=CascadeType.MERGE , fetch = FetchType.EAGER)
	@JoinColumn(name = "id_employee")
	private Employee employee;
	

	public Message( String sentFrom, Date date, String subject, String text, Employee employee) {
		super();
		this.sentFrom = sentFrom;
		this.date = date;
		this.subject = subject;
		this.text = text;
		this.employee = employee;
	}
	
	public Message() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", sentFrom=" + sentFrom + ", date=" + date + ", subject=" + subject + ", text="
				+ text + ", employee=" + employee + "]";
	}

}
