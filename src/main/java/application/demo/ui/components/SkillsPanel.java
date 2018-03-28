package application.demo.ui.components;

import java.io.File;
import java.util.ArrayList;

import application.demo.service.EmployeeService;
import application.demo.service.EmployeeSkillService;
import application.demo.service.SkillService;
import com.vaadin.server.FileResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import application.demo.domain.Employee;
import application.demo.domain.EmployeeSkill;
import application.demo.domain.Skill;

public class SkillsPanel extends CustomComponent {
	private static final long serialVersionUID = 1L;

	public SkillsPanel(Employee employee) {
		Panel root = new Panel();
		root.setWidth("100%");
		root.setHeight("100%");
		root.setCaptionAsHtml(true);
		String viewLink = "<a href=\"http://localhost:8080/mainPage#!employeeView/" + employee.getId()
				+ "\" style=\"text-decoration:none\">View</a>";
		String editLink = "<a href=\"http://localhost:8080/mainPage#!EditEmployee/" + employee.getId()
				+ "\" style=\"text-decoration:none\">Edit</a>";
		String showEvolution = "<a href=\"http://localhost:8080/graph?id=" + employee.getId()+
				"&name="+employee.getFirstName()+" "+employee.getLastName()
		+ "\" style=\"text-decoration:none\">Show evolution</a>";

		root.setCaption("<b>" + employee.getFirstName() + " " + employee.getLastName() + "</b>&nbsp;&nbsp;" + viewLink
				+ " " + editLink+ " " + showEvolution);
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);

		Image profil;
		FileResource resource;
		File user = new File("./src/main/resources/profil/" + employee.getId());
		if (user.exists()) {
			resource = new FileResource(user);

		} else {
			File file = new File("./src/main/resources/profil/default.png");
			resource = new FileResource(file);

		}
		profil = new Image(null, resource);
		profil.setWidth("60px");
		profil.setHeight("60px");

		layout.addComponent(profil);

		// addSkills
		ArrayList<Skill> skills = new ArrayList<Skill>();
		ArrayList<EmployeeSkill> empSkills = new ArrayList<EmployeeSkill>();
		ArrayList<Employee> employees = new ArrayList<Employee>();

		skills = SkillService.getAllSkills();
		employees = EmployeeService.getAllEmployees();


		int i = 0;

		ArrayList<EmployeeSkill> empsList = new ArrayList<EmployeeSkill>();

		for (Employee e : employees) {
			empsList = EmployeeSkillService.getEmployeeSkillByEmployee(employee.getId());
		}
		
		for (EmployeeSkill es : empsList) {
			String skillName = es.getSkill().getName();

			int value = es.getLevel();

			Label logo = new Label(skillName + "<br><b>" + value + "</b>", Label.CONTENT_XHTML);
			logo.setWidth("100%");
			logo.setHeight("50px");

			if (skillName.contains("+"))
				skillName = "cplusplus";

			logo.setPrimaryStyleName("valo-menu-" + skillName);

			if (value == 0) {
				logo.addStyleName("valo-menu-hidden");
			}

			layout.addComponent(logo);
		}

		root.setContent(layout);
		setCompositionRoot(root);

	}
}
