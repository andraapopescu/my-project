package application.demo.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import application.demo.domain.EmployeeSkill;
import application.demo.service.EmployeeSkillService;
import application.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import application.demo.domain.Employee;
import application.demo.domain.User;
import application.demo.service.EmployeeService;

public class FilterLoginService implements LoginService {

    public static Employee currentEmployee;
    public static ArrayList<EmployeeSkill> employeeWishSkills;
    public static User loggedUser;

    private final ArrayList<LoginListener> listeners = new ArrayList<LoginService.LoginListener>();

    public User getCurrentUser() {
        return loggedUser;
    }

    @Override
    public void login(String username, String password) {
        for(User u : UserService.getAllUsers()) {
            String encryptedPassword = encryptPassword(password);
            try {
                if(u.getUserName().equals(username) && encryptedPassword.equals(u.getPassword())) {
                    loggedUser = u;
                    if(loggedUser.getRole().equals("user")) {
                        currentEmployee = EmployeeService.findEmployeeByEmail(loggedUser.getUserName()).get(0);
                        employeeWishSkills = EmployeeSkillService.getEmployeeSkillByEmployee(currentEmployee.getId());

                        System.out.println(employeeWishSkills);
                    } else {
                        currentEmployee = EmployeeService.findEmployeeByLastName("admin").get(0);
                    }

                    this.fireUserLoggedIn();

                } else {
                    fireUserLoginFailed();
                }
            } catch(Exception e) {
                UI.getCurrent().getPage().setLocation("http://localhost:8080");
            }
        }
    }

    public String encryptPassword(String password) {
        String result = "";
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if
            // needed

            byte[] digest = md.digest();
            for(byte theByte : digest) {
                result = result + Integer.toHexString(theByte);
            }

        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void fireUserLoginFailed() {
        LoginEvent event = new LoginEvent(this, null);

        for(LoginListener loginListener : listeners) {
            loginListener.userLoginFailed(event);
        }

        Notification.show("Wrong name or password!");
    }

    protected void fireUserLoggedIn() {
        LoginEvent event = new LoginEvent(this, this.getCurrentUser());

        for(LoginListener loginListener : listeners) {
            loginListener.userLoggedIn(event);
        }

        UI.getCurrent().getPage().setLocation("http://localhost:8080/mainPage");
    }

    @Override
    public void logOut() {
        UI.getCurrent().getPage().setLocation("http://localhost:8080");
        loggedUser = null;
    }

    @Override
    public void addLoginListener(LoginListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeLoginListener(LoginListener listener) {
        this.listeners.remove(listener);
    }

}
