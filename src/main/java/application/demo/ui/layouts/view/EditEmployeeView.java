package application.demo.ui.layouts.view;

import application.demo.domain.*;
import application.demo.security.FilterLoginService;
import application.demo.security.LoginService;
import application.demo.security.LoginService.LoginEvent;
import application.demo.security.ServiceProvider;
import application.demo.service.*;
import application.demo.ui.components.CustomImageUploader;
import application.demo.ui.components.CustomSlider;
import application.demo.ui.components.IntegerField;
import application.demo.ui.login.LoginPageCssHelper;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class EditEmployeeView extends VerticalLayout implements View, LoginService.LoginListener {
    private static final long serialVersionUID = 1L;

    public final static String NAME = "EditEmployee";

    private Employee employee = null;

    private User user;
    private Employee selectedEmployee;

    private static TextField firstName, lastName, email, adress, phone;
    private static TextField salary;
    private ArrayList<CustomSlider> sliders;
    private Button changePsdButton;
    private CustomImageUploader up;
    private static DateField birthday, employmentDate;
    private long employeeId;

    @Override
    public void enter(ViewChangeEvent event) {
        if(event.getParameters() != null) {

            try {
                selectedEmployee = FilterLoginService.currentEmployee;
                user = FilterLoginService.loggedUser;

                employeeId = Long.parseLong(event.getParameters());

            } catch(NumberFormatException e) {
                e.printStackTrace();
                getUI().getNavigator().navigateTo(SearchView.NAME);

            } catch(NullPointerException e) {
                UI.getCurrent().getPage().setLocation("http://localhost:8080/");
                e.printStackTrace();
            }

            employee = EmployeeService.getEmployeeById(employeeId);

            try {
                if(employee == null) {
                    System.out.println("nullll");
                    getUI().getNavigator().navigateTo(SearchView.NAME);
                    return;
                } else if(user.getRole().equals("user")) {
                    editEmployeeViewForUser();

                } else {
                    editEmployeeViewForAdmin();
                    user = UserService.findUserByUsername(employee.getEmail()).get(0);
                }
            } catch(Exception e) {
                System.out.println("a crapat");
                e.printStackTrace();
                UI.getCurrent().getPage().setLocation("http://localhost:8080/");
            }
        }

    }

    public EditEmployeeView() {
        ServiceProvider.getInstance().getLoginService().addLoginListener(this);
    }

    public void editEmployeeViewForAdmin() {
        setSpacing(true);
        setMargin(true);

        VerticalLayout header = new VerticalLayout();
        header.setMargin(false);
        header.setWidth("800px");
        addComponent(header);
        setComponentAlignment(header, Alignment.TOP_CENTER);

        up = new CustomImageUploader("./src/main/resources/profil/" + employee.getId());
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

        firstName = new TextField("First Name");
        firstName.setWidth("50%");
        firstName.setRequired(true);
        firstName.setValue(employee.getFirstName());
        form.addComponent(firstName);

        lastName = new TextField("Last Name");
        lastName.setWidth("50%");
        form.addComponent(lastName);
        lastName.setRequired(true);
        lastName.setValue(employee.getLastName());

        birthday = new DateField("Birthday");
        // birthday.setValue(new Date(80, 0, 31));
        form.addComponent(birthday);
        birthday.setValue(employee.getBirthday());

        section = new Label("Contact Info");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        email = new TextField("Email");
        email.setWidth("50%");
        email.setRequired(true);
        form.addComponent(email);
        email.setValue(employee.getEmail());

        adress = new TextField("Adress");
        adress.setWidth("50%");
        form.addComponent(adress);
        adress.setValue(employee.getAddress());

        phone = new TextField("Phone");
        phone.setWidth("50%");
        form.addComponent(phone);
        phone.setRequired(true);
        phone.setValue(employee.getPhone());

        section = new Label("Employment Info");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        employmentDate = new DateField("Employment Date");
        form.addComponent(employmentDate);
        employmentDate.setValue(employee.getEmploymentDate());

//        salary = new IntegerField(String.valueOf(employee.getSalary()));
        salary = new TextField("Salary");
//        salary.setCaption("Salary");
        salary.setWidth("50%");
        salary.setRequired(true);
        salary.setValue(employee.getSalary() + "");
        form.addComponent(salary);

        section = new Label("Skills");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        final HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        form.addComponent(row);

        addValidators();

        sliders = new ArrayList<CustomSlider>();

        ArrayList<Skill> skills = SkillService.getAllSkills();
        HashMap<Integer, String> skillsMap = new HashMap<Integer, String>();

        ArrayList<EmployeeSkill> employeeSkills = EmployeeSkillService.getEmployeeSkillByEmployee(employee.getId());
        HashMap<Integer, Integer> employeeSkillsMap = new HashMap<Integer, Integer>();

        if(employeeSkills == null) {
            employeeSkills = new ArrayList<EmployeeSkill>();

            for(int i = 0; i < skills.size() + 2; i++)
                employeeSkillsMap.put(i, 0);
        } else {
            for(EmployeeSkill e : employeeSkills) {
                employeeSkillsMap.put((int) e.getSkill().getId(), e.getLevel());
            }
        }

        for(Skill s : skills) {
            skillsMap.put((int) s.getId(), s.getName());
        }

        int i = 1;

        for(Skill s : skills) {
            CustomSlider slider = new CustomSlider(s.getName(), i);

            if(s.getId() != 0)
                slider.addStyleName("sslider");

            sliders.add(slider);
            row.addComponent(slider);

            if(employeeSkillsMap.get(i) != null) {
                slider.getSlider().setValue((double) employeeSkillsMap.get(i));
            }

            i++;
        }


        Page.getCurrent().getStyles().add(LoginPageCssHelper.createStyleForSliders());

        Button save = new Button("Save");
        save.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    validateFields();
                    saveEmployeeToDataBase();
                } catch(InvalidValueException e) {

                }

            }

            private void saveEmployeeToDataBase() {
                String fName = firstName.getValue();
                String lName = lastName.getValue();
                Date bDay = birthday.getValue();
                Date empDate = birthday.getValue();
                String mail = email.getValue();
                String addr = adress.getValue();
                String ph = phone.getValue();
                int sal = Integer.parseInt(salary.getValue());

                Employee employee = new Employee(fName, lName, bDay, addr, mail, ph, empDate, sal);
                employee.setId(employeeId);

                User u = new User(mail, user.getPassword(), user.getRole());
                u.setId(user.getId());

                if(up.isUploaded()) {
                    up.save(employee.getId());
                }

                EmployeeService.updateEmployee(employee);
                UserService.updateUser(u);

                ArrayList<EmployeeSkill> employeeSkills = EmployeeSkillService.getEmployeeSkillByEmployee(employeeId);
                for(EmployeeSkill es : employeeSkills) {
                    EmployeeSkillService.deleteEmployeeSkillsById(es.getId());
                }

                for(CustomSlider c : sliders) {
                    Skill skill = SkillService.findSkillByName(c.getName()).get(0);

                    EmployeeSkill employeeSkill = new EmployeeSkill((int) c.getValue(), employee, skill);
                    if(c.getValue() != 0) {

                        EmployeeSkillService.saveEmployeeSkill(employeeSkill);

                        HistoryEmployeeSkill hes = new HistoryEmployeeSkill(new Date(), (int) c.getValue(), employee, skill);
                        HistoryEmployeeSkillService.saveHistoryEmployeeSkill(hes);
                    }
                }

                EmployeeModel.refresh();
                getUI().getNavigator().navigateTo("employeeView/" + employee.getId());
            }
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, true, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);
        footer.addComponent(save);
    }

    public void editEmployeeViewForUser() {
        setSpacing(true);
        setMargin(true);

        VerticalLayout header = new VerticalLayout();
        header.setMargin(false);
        header.setWidth("800px");
        addComponent(header);
        setComponentAlignment(header, Alignment.TOP_CENTER);

        up = new CustomImageUploader("./src/main/resources/profil/" + employee.getId());
        header.addComponent(up);

        final FormLayout form = new FormLayout();
        form.setMargin(false);
        form.setWidth("800px");
        form.addStyleName("light");
        addComponent(form);
        setComponentAlignment(form, Alignment.TOP_CENTER);

        Label section = new Label("Personal Info");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        firstName = new TextField("Name");
        firstName.setWidth("50%");
        firstName.setValue(employee.getFirstName());
        firstName.setReadOnly(true);
        form.addComponent(firstName);

        lastName = new TextField("Last Name");
        lastName.setWidth("50%");
        form.addComponent(lastName);
        lastName.setValue(employee.getLastName());
        lastName.setReadOnly(true);

        final TextField bd = new TextField("Birthday");
        lastName.setWidth("50%");
        bd.setValue(employee.getBirthday().toString());
        bd.setReadOnly(true);
        form.addComponent(bd);

        section = new Label("Contact Info");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        email = new TextField("Email");
        email.setWidth("50%");
        email.setRequired(true);
        form.addComponent(email);
        email.setValue(employee.getEmail());

        changePsdButton = new Button("Change Password");
        changePsdButton.setIcon(FontAwesome.EDIT);
        changePsdButton.addStyleName(ValoTheme.BUTTON_SMALL);
        changePsdButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        changePsdButton.addClickListener(new ClickListener() {
            private static final long serialVersionUID = 1L;

            public void buttonClick(ClickEvent event) {
                Window subWindow = new Window("Change Password");
                ChangePasswordPopup popup = new ChangePasswordPopup();

                UI.getCurrent().addWindow(popup);
            }
        });
        form.addComponent(changePsdButton);

        adress = new TextField("Adress");
        adress.setWidth("50%");
        form.addComponent(adress);
        adress.setValue(employee.getAddress());

        phone = new TextField("Phone");
        phone.setWidth("50%");
        form.addComponent(phone);
        phone.setRequired(true);
        phone.setValue(employee.getPhone());

        section = new Label("Employment Info");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        final TextField ed = new TextField("Employment Date");
        ed.setWidth("50%");
        ed.setValue(employee.getEmploymentDate().toString());
        ed.setReadOnly(true);
        form.addComponent(ed);

//		salary = new TextField("Salary");
        salary.setWidth("50%");
        form.addComponent(salary);
        salary.setValue(employee.getSalary() + "");
        salary.setReadOnly(true);

        section = new Label("Skills");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        addValidators();

        final VerticalLayout row = new VerticalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        form.addComponent(row);
        ArrayList<Skill> allSkills = new ArrayList<Skill>();
        ArrayList<EmployeeSkill> employeeSkills = EmployeeSkillService.getEmployeeSkillByEmployee(employee.getId());

        for(EmployeeSkill es : employeeSkills) {
            allSkills.add(SkillService.getSkillById(es.getSkill().getId()));

        }

        try {
            if(employeeSkills != null)
                for(EmployeeSkill empSkill : employeeSkills) {
                    if(empSkill.getLevel() != 0) {
                        ProgressBar b = new ProgressBar();
                        b.setCaption(allSkills.get((int) (empSkill.getSkill().getId() - 1)).getName() + " "
                                + empSkill.getLevel());

                        b.setWidth("70%");
                        b.setValue((float) (empSkill.getLevel() * 0.1));
                        row.addComponent(b);
                    }
                }
        } catch(Exception e) {

        }


        Page.getCurrent().getStyles().add(LoginPageCssHelper.createStyleForSliders());

        Button save = new Button("Save");
        save.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    validateFields();
                    saveEmployeeToDataBase();
                } catch(InvalidValueException e) {

                }

            }

            private void saveEmployeeToDataBase() {
                String mail = email.getValue();
                String addr = adress.getValue();
                String ph = phone.getValue();

                Employee employee = EmployeeService.getEmployeeById(employeeId);
                employee.setEmail(mail);
                employee.setAddress(addr);
                employee.setPhone(ph);

                User u = new User(mail, user.getPassword(), user.getRole());
                u.setId(user.getId());

                if(up.isUploaded()) {
                    up.save(employee.getId());
                }

                EmployeeService.updateEmployee(employee);
                UserService.updateUser(u);

                EmployeeModel.refresh();
                Notification.show("Your personal information have been actulised");
            }
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, true, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);
        footer.addComponent(save);
    }

    private void validateFields() {
        firstName.validate();
        lastName.validate();
        email.validate();
        phone.validate();
    }

    private void addValidators() {
        email.addValidator(new EmailValidator("Email address is not valid "));
        firstName.addValidator(new StringLengthValidator("At least 3 characters !", 3, 15, false));
        lastName.addValidator(new StringLengthValidator("At least 3 characters !", 3, 15, false));
        phone.addValidator(new RegexpValidator("[0-9]+", "Incorrect phone number !"));
        salary.addValidator(new RegexpValidator("^[1-9]\\d*$", "Incorrect salary format!"));

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
