package application.demo.ui.layouts.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import application.demo.service.EmployeeService;
import application.demo.service.EmployeeSkillService;
import application.demo.service.SkillService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import application.demo.domain.Employee;
import application.demo.domain.EmployeeSkill;
import application.demo.domain.Skill;
import application.demo.security.LoginService;
import application.demo.security.LoginService.LoginEvent;
import application.demo.security.ServiceProvider;

public class EmployeeView extends VerticalLayout implements View, LoginService.LoginListener {
	private static final long serialVersionUID = 1L;

	public final static String NAME = "employeeView";
	private Employee employee = null;
	private long employeeId;
	
	SimpleDateFormat sdf = new SimpleDateFormat( "E , d MMMMM yyyy ");
	
	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {

			try {
				employeeId = Long.parseLong(event.getParameters());

			} catch (NumberFormatException e) {
				UI.getCurrent().getNavigator().navigateTo(SearchView.NAME);
				return;
			}

			employee = EmployeeService.getEmployeeById(employeeId);

			if (employee == null) {
				UI.getCurrent().getNavigator().navigateTo(SearchView.NAME);
				return;
			} else {
				EmployeeView2();
			}
		}
	}

	public EmployeeView() {
		ServiceProvider.getInstance().getLoginService().addLoginListener(this);
		setEnabled(false);
	}

	public void EmployeeView2() {
		setSpacing(true);
		setMargin(true);

		VerticalLayout header = new VerticalLayout();
		header.setMargin(false);
		header.setWidth("800px");
		addComponent(header);
		setComponentAlignment(header, Alignment.TOP_CENTER);

		final FormLayout form = new FormLayout();
		form.setReadOnly(true);
		form.setMargin(false);
		form.setWidth("800px");
		form.addStyleName("light");
		addComponent(form);
		setComponentAlignment(form, Alignment.TOP_CENTER);

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
		profil.setWidth("170px");
		profil.setHeight("200px");
		profil.addStyleName("h2");
		header.addComponent(profil);

		Label section = new Label("Personal Info");
		section.addStyleName("h2");
		section.addStyleName("colored");
		form.addComponent(section);

		final TextField firstName = new TextField("Name");
		firstName.setWidth("50%");
		form.addComponent(firstName);
		firstName.setValue(employee.getFirstName());

		final TextField lastName = new TextField("Last Name");
		lastName.setWidth("50%");
		form.addComponent(lastName);
		lastName.setValue(employee.getLastName());

		final TextField bd = new TextField("Birthday");
		lastName.setWidth("50%");
		bd.setValue(sdf.format(employee.getBirthday()));
		form.addComponent(bd);

		section = new Label("Contact Info");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		final TextField email = new TextField("Email");
		email.setWidth("50%");
		form.addComponent(email);
		email.setValue(employee.getEmail());

		final TextField adress = new TextField("Adress");
		adress.setWidth("50%");
		form.addComponent(adress);
		adress.setValue(employee.getAddress());

		final TextField phone = new TextField("Phone");
		phone.setWidth("50%");
		form.addComponent(phone);
		phone.setValue(employee.getPhone());

		section = new Label("Employment Info");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		final TextField salary = new TextField("Salary");
		salary.setWidth("50%");
		form.addComponent(salary);
		salary.setValue(employee.getSalary() + "");

		final TextField ed = new TextField("Employment Date");
		ed.setWidth("50%");
		ed.setValue(sdf.format(employee.getEmploymentDate()));
		form.addComponent(ed);

		section = new Label("Skills");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		final VerticalLayout row = new VerticalLayout();
		row.addStyleName("wrapping");
		row.setSpacing(true);
		form.addComponent(row);

		ArrayList<Skill> allSkills = new ArrayList<Skill>();
		ArrayList<EmployeeSkill> employeeSkills = EmployeeSkillService.getEmployeeSkillByEmployee(employee.getId());

		for (EmployeeSkill es : employeeSkills) {
			allSkills.add(SkillService.getSkillById(es.getSkill().getId()));

		}

		try {
		if (employeeSkills != null)
			for (EmployeeSkill empSkill : employeeSkills) {
				if (empSkill.getLevel() != 0) {
					ProgressBar b = new ProgressBar();
					b.setCaption(empSkill.getSkill().getName() + " " + empSkill.getLevel());
					b.setWidth("70%");
					b.setValue((float) (empSkill.getLevel() * 0.1));
					row.addComponent(b);
				}
			}
		} catch(Exception e) {
		}
	}

	@Override
	public void userLoggedIn(LoginEvent e) {

	}

	@Override
	public void userLoggedOut(LoginEvent e) {
		UI.getCurrent().getPage().reload();
	}

	@Override
	public void userLoginFailed(LoginEvent e) {

	}
}