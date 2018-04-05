package application.demo.ui.layouts.view;

import java.util.ArrayList;
import java.util.Date;

import application.demo.service.*;
import application.demo.ui.components.IntegerField;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import application.demo.domain.Employee;
import application.demo.domain.EmployeeSkill;
import application.demo.domain.HistoryEmployeeSkill;
import application.demo.domain.Skill;
import application.demo.domain.User;
import application.demo.ui.MainPage;
import application.demo.ui.components.CustomImageUploader;
import application.demo.ui.components.CustomSlider;
import application.demo.ui.login.LoginPageCssHelper;

public class AddEmployeeView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public final static String NAME = "addEmployee";
	private static TextField firstName, lastName, email, address, phone;
	private IntegerField salary;
	private ArrayList<CustomSlider> sliders;
	private CustomImageUploader up;
	private static DateField birthday, employmentDate;

	public AddEmployeeView() {
		VerticalLayout header = new VerticalLayout();
		header.setMargin(false);
		header.setWidth("800px");
		addComponent(header);
		setComponentAlignment(header, Alignment.TOP_CENTER);

		up = new CustomImageUploader("./src/main/resources/images/default.png");
		header.addComponent(up);

		final FormLayout form = new FormLayout();
		form.setMargin(false);
		form.setWidth("800px");
		form.addStyleName("light");
		addComponent(form);
		setComponentAlignment(form, Alignment.TOP_CENTER);

		Label section = new Label("Personal Info");
		section.addStyleName("h2");
		section.addStyleName("colored");
		form.addComponent(section);

		firstName = new TextField("Name");
		firstName.setWidth("50%");
		form.addComponent(firstName);
		firstName.setRequired(true);

		lastName = new TextField("Last Name");
		lastName.setWidth("50%");
		form.addComponent(lastName);
		lastName.setRequired(true);

		birthday = new DateField("Birthday");
		birthday.setValue(new Date(80, 0, 31));
		form.addComponent(birthday);

		section = new Label("Contact Info");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		email = new TextField("Email");
		email.setWidth("50%");
		email.setRequired(true);
		form.addComponent(email);

		address = new TextField("Address");
		address.setWidth("50%");
		form.addComponent(address);

		phone = new TextField("Phone");
		phone.setWidth("50%");
		form.addComponent(phone);
		phone.setRequired(true);

		section = new Label("Employment Info");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		employmentDate = new DateField("Employment Date");
		employmentDate.setValue(new Date(80, 0, 31));
		form.addComponent(employmentDate);

		salary = new IntegerField(String.valueOf(0));
		salary.setCaption("Salary");
		salary.setWidth("50%");
		form.addComponent(salary);
		salary.setRequired(true);

		section = new Label("Skills");
		section.addStyleName("h3");
		section.addStyleName("colored");
		form.addComponent(section);

		final HorizontalLayout row = new HorizontalLayout();
		row.addStyleName("wrapping");
		row.setSpacing(true);
		form.addComponent(row);

		addValidators();

		ArrayList<Skill> skills = SkillService.getAllSkills();
		int i = 1;

		sliders = new ArrayList<CustomSlider>();
		
		for (Skill s : skills) {
			CustomSlider slider = new CustomSlider(s.getName(), i);
			if (s.getId() != 1) {
				slider.addStyleName("slider");
			}

			sliders.add(slider);
			row.addComponent(slider);
			i++;
		}

		Page.getCurrent().getStyles().add(LoginPageCssHelper.createStyleForSliders());

		Button save = new Button("Save");
		save.setIcon(FontAwesome.SAVE);
		save.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					validateFields();
					addEmployeeToDatabase();
				} catch (InvalidValueException e) {
					
				}
			}
		});

		HorizontalLayout footer = new HorizontalLayout();
		footer.setMargin(new MarginInfo(true, false, true, false));
		footer.setSpacing(true);
		footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		form.addComponent(footer);
		footer.addComponent(save);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}

	private void addValidators() {
		email.addValidator(new EmailValidator("Email address is not valid!"));
		firstName.addValidator(new StringLengthValidator("At least 3 characters!", 3, 15, false));
		lastName.addValidator(new StringLengthValidator("At least 3 characters!", 3, 15, false));
		phone.addValidator(new RegexpValidator("[0-9]+", "Incorrect phone number!"));
		salary.addValidator(new RegexpValidator("^[1-9]\\d*$", "Incorrect salary format!"));
	}

	private void validateFields() {
		firstName.validate();
		lastName.validate();
		email.validate();
		phone.validate();
	}

	public void addEmployeeToDatabase() {
		String fName = firstName.getValue();
		String lName = lastName.getValue();
		Date bDay = birthday.getValue();
		Date empDate = employmentDate.getValue();
		String mail = email.getValue();
		String addr = address.getValue();
		String phoneNr = phone.getValue();
		int sal = Integer.parseInt(salary.getValue());

//		List<Skill> skills = new ArrayList<Skill>();
//		skills = RestConsumer.getAllSkills();

		Employee employee = new Employee(fName, lName, bDay, addr, mail, phoneNr, empDate, sal);
		EmployeeService.saveEmployee(employee);

		ArrayList<Employee> employees = EmployeeService.getAllEmployees();
		Employee lastEmployee = employees.get(employees.size() - 1);
		
		String userPassword = MainPage.hashingwithSHA("user");
		User user = new User(lastEmployee.getEmail(), userPassword, "user");
		
		UserService.saveUser(user);

		// save image
		if (up.isUploaded()) {
			up.save(lastEmployee.getId());
		}

		for (CustomSlider c : sliders) {
			if (c.getValue() > 0) {
				Skill skill = SkillService.findSkillByName(c.getName()).get(0);
				EmployeeSkill employeeSkill = new EmployeeSkill((int) c.getValue(), lastEmployee, skill);

				EmployeeSkillService.saveEmployeeSkill(employeeSkill);
				
				HistoryEmployeeSkill hes = new HistoryEmployeeSkill(new Date(), (int) c.getValue(), 
						lastEmployee, skill);
				HistoryEmployeeSkillService.saveHistoryEmployeeSkill(hes);
				
			}

			EmployeeModel.refresh();
			UI.getCurrent().getNavigator().navigateTo(EmployeeView.NAME + "/" + lastEmployee.getId());
		}
	}
}
