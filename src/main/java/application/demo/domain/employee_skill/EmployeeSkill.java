package application.demo.domain.employee_skill;

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
import application.demo.domain.skills.Skill;

@Entity
@Table(name = "employee_skill")
public class EmployeeSkill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private int level;
	
	public EmployeeSkill() {
		
	}

	@ManyToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	@JoinColumn(name = "id_employee")

	private Employee employee;

	
	@ManyToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	@JoinColumn(name = "id_skill")

	private Skill skill;

//	public EmployeeSkill(int level, long employeeId, long skillId) {
//		super();
//		this.level = level;
//		this.employee.setId(employeeId);
//		this.skill.setId(skillId);
//	}
	
	public EmployeeSkill(int level, Employee employee, Skill skill) {
		super();
		this.level = level;
		this.employee = employee;
		this.skill = skill;
	}



	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "EmployeeSkill [id=" + id + ", level=" + level + ", employee=" + employee + ", skill=" + skill + "]";
	}

}
