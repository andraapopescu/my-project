package application.demo.ui.login;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page.Styles;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import application.demo.security.ServiceProvider;

public class LoginPageHelper {

	public static TextField username;
	public static PasswordField password;
	public static Button login;

	public static VerticalLayout createLoginContainerLayout() {
		setLoginFields();

		VerticalLayout result = new VerticalLayout();
		result.setWidth("350px");
		result.setHeight("240px");
		result.addStyleName("loginForm");
		result.addComponents(username, password, login);
		result.setComponentAlignment(username, Alignment.TOP_CENTER);
		result.setComponentAlignment(password, Alignment.TOP_CENTER);
		result.setComponentAlignment(login, Alignment.BOTTOM_RIGHT);

		return result;

	}

	private static void setLoginFields() {
		username = new TextField("Username");
		password = new PasswordField("Password");
		login = new Button("Login");

		username.setIcon(FontAwesome.USER);
		password.setIcon(FontAwesome.LOCK);
		login.setIcon(FontAwesome.UNLOCK);

		password.addStyleName("passwordField");
		login.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ServiceProvider.getInstance().getLoginService().login(username.getValue(), password.getValue());
			}
		});
		
		login.setClickShortcut(KeyCode.ENTER);
	}
	
	public static VerticalLayout createLoginPageLayout() {
		VerticalLayout result = new VerticalLayout();
		result = new VerticalLayout();
		result.setSizeFull();
		result.addStyleName("bigContainer");
		
		return result;
	}

	public static void addCss(Styles style) {
		style.add(LoginPageCssHelper.createStyleForLoginForm());
		style.add(LoginPageCssHelper.createStyleForContainer());
	}
	
}
