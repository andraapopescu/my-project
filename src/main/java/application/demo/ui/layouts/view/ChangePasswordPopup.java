package application.demo.ui.layouts.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.employee.Employee;
import application.demo.domain.user.User;
import application.demo.rest.RestConsumer;
import application.demo.security.FilterLoginService;
import application.demo.ui.MainPage;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import aj.org.objectweb.asm.Type;

public class ChangePasswordPopup extends Window {
	private static final long serialVersionUID = 1L;

	VerticalLayout vLayout = new VerticalLayout();
	PasswordField oldPassword, newPassword;
	Button okButton;

	String oldPsd, newPws;

	User user;

	public ChangePasswordPopup() {
		try {
			user = FilterLoginService.loggedUser;
		} catch (NullPointerException e) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080");
		}

		vLayout = createLayout();

		this.setContent(vLayout);
		this.setHeight("300px");
		this.setWidth("320px");

		this.center();
	}

	private VerticalLayout createLayout() {
		VerticalLayout result = new VerticalLayout();

		Label section = new Label("Enter your old password");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);
		result.setComponentAlignment(section, Alignment.MIDDLE_CENTER);

		oldPassword = new PasswordField();

		result.addComponent(oldPassword);
		result.setComponentAlignment(oldPassword, Alignment.MIDDLE_CENTER);

		section = new Label("Enter your new password");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);
		result.setComponentAlignment(section, Alignment.MIDDLE_CENTER);

		newPassword = new PasswordField();
		result.addComponent(newPassword);
		result.setComponentAlignment(newPassword, Alignment.MIDDLE_CENTER);

		okButton = new Button("OK");
		okButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		okButton.addStyleName(ValoTheme.BUTTON_SMALL);

		okButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					resetPassword();
					User u = new User(user.getUserName(), MainPage.hashingwithSHA(newPws), user.getRole());
					u.setId(user.getId());

					RestConsumer.saveUser(u);
					Notification.show("Your password has been changed!");

					close();
				} catch (Exception e) {

				}
			}
		});
		result.addComponent(okButton);
		result.setComponentAlignment(okButton, Alignment.MIDDLE_CENTER);

		result.setSpacing(true);
		result.setMargin(true);

		return result;
	}

	private void resetPassword() {
		try {
			if (!oldPassword.getValue().isEmpty()) {
				oldPsd = oldPassword.getValue();

				if (!MainPage.hashingwithSHA(oldPsd).equals(user.getPassword())) {
					Notification.show("Your old password is incorrect!", Notification.TYPE_ERROR_MESSAGE);
					oldPassword.setValue("");
				} else {
					if (!newPassword.getValue().isEmpty()) {
						newPws = newPassword.getValue();
					}
				}
			} else {
				Notification.show("You have to complete the fealds!", Notification.TYPE_ERROR_MESSAGE);
			}
		} catch (Exception e) {

		}
	}
}
