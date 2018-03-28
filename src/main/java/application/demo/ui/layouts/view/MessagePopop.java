package application.demo.ui.layouts.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import application.demo.service.EmployeeService;
import application.demo.service.MessageService;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.Employee;
import application.demo.domain.User;
import application.demo.domain.Message;
import application.demo.security.FilterLoginService;

public class MessagePopop extends Window {
	private static final long serialVersionUID = 1L;
	private User user;

	private static TextField sendTo, subject;
	private static TextArea text;
	private Button sendButton;
	private Message message;
	private VerticalLayout vLayout;

	private static ComboBox comboBox;
	
	SimpleDateFormat sdf = new SimpleDateFormat( "E , d MMMMM yyyy , hh:mm ");

	public MessagePopop(User user) {
		this.user = user;

		vLayout = createNewMessageLayout();
		vLayout.setSpacing(true);

		setContent(vLayout);

		this.center();
		this.setHeight("90%");
		this.setWidth("50%");
	}

	public MessagePopop(Message message) {
		this.message = message;

		vLayout = createSelectedMessageLayout(message);
		vLayout.setSpacing(true);

		setContent(vLayout);

		this.center();
		this.setHeight("100%");
		this.setWidth("50%");

	}

	private VerticalLayout createSelectedMessageLayout(Message message) {
		VerticalLayout result = new VerticalLayout();

		Label section = new Label("Sent from");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		TextField sentFrom = new TextField();
		sentFrom.setValue(message.getSentFrom());
		sentFrom.setWidth("50%");
		sentFrom.setReadOnly(true);
		result.addComponent(sentFrom);

		section = new Label("Date when the message was sent");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		TextField date = new TextField();
		date.setValue(sdf.format(message.getDate()));
		date.setWidth("50%");
		date.setReadOnly(true);
		result.addComponent(date);

		section = new Label("Subject");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		TextField subject = new TextField();
		subject.setValue(message.getSubject());
		subject.setWidth("50%");
		subject.setReadOnly(true);
		result.addComponent(subject);

		section = new Label("Your message");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		TextArea text = new TextArea();
		text.setValue(message.getText());
		text.setWidth("50%");
		text.setReadOnly(true);
		result.addComponent(text);

		result.setSpacing(true);
		result.setMargin(true);

		return result;
	}

	private VerticalLayout createNewMessageLayout() {
		VerticalLayout result = new VerticalLayout();

		Label section = new Label("Send to:");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		comboBox = createComboBox();
		result.addComponent(comboBox);

		section = new Label("Subject");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		subject = new TextField();
		subject.setWidth("50%");
		result.addComponent(subject);

		section = new Label("Message");
		section.addStyleName("h3");
		section.addStyleName("colored");
		result.addComponent(section);

		text = new TextArea();
		text.setWidth("50%");
		text.setHeight("500%");
		result.addComponent(text);

		sendButton = new Button("Send");
		sendButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		sendButton.addStyleName(ValoTheme.BUTTON_SMALL);
		sendButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (user.getRole().equals("admin")) {
					try {
						EmployeeService.findEmployeeByEmail(comboBox.getValue().toString()).get(0);
						createMessage();
					} catch (IndexOutOfBoundsException e) {
						sendMessage();
					}
				} else {
					createMessage();
				}
			}
		});

		result.addComponent(sendButton);
		result.setComponentAlignment(sendButton, Alignment.BOTTOM_LEFT);
		result.setMargin(true);
		result.setSpacing(true);

		return result;
	}

	public ComboBox createComboBox() {
		ComboBox result = new ComboBox();
		result.setFilteringMode(FilteringMode.CONTAINS);

		for (Employee e : EmployeeService.getAllEmployees()) {
			result.addItem(e.getEmail());
		}

		result.removeItem(FilterLoginService.currentEmployee.getEmail());

		result.setWidth("330px");
		result.setNewItemsAllowed(true);
		result.setTextInputAllowed(true);

		return result;
	}

	private void createMessage() {
		message = new Message();
		Employee employee;

		if (!comboBox.getValue().toString().isEmpty()) {
			try {
				employee = EmployeeService.findEmployeeByEmail(comboBox.getValue().toString()).get(0);

				if (subject.getValue().isEmpty()) {
					Notification.show("You may not want to send a message without a subject");
				}
				if (text.getValue().isEmpty()) {
					Notification.show("You may not want to send a message without any text");
				}

				String from = "Admin";

				if (FilterLoginService.currentEmployee != null && 
						!FilterLoginService.currentEmployee.getFirstName().equals("admin") ) {
					from = FilterLoginService.currentEmployee.getFirstName() + " "
							+ FilterLoginService.currentEmployee.getLastName();
				}

				message = new Message(from, new Date(), subject.getValue(), text.getValue(), employee);
				System.err.println(message);

				MessageService.saveMessage(message);
				Notification.show("Message SENT !! ");
				this.close();

			} catch (NullPointerException e) {
				Notification.show("The email you introduced is not correct!");
				comboBox.setValue("");
			} catch (IndexOutOfBoundsException s) {
				Notification.show("The email you introduced is not correct!");
				comboBox.setValue("");
			}
		}

	}

	// clasa noua
	public void sendMessage() {
		final String username = "licenta.portalgestiunepersonal@gmail.com";
		final String password = "aplicatie";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			javax.mail.Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(javax.mail.Message.RecipientType.TO,
					InternetAddress.parse(comboBox.getValue().toString()));

			message.setSubject(subject.getValue());
			message.setText(text.getValue());

			Transport.send(message);
			Notification.show("Email SENT !! ");
			this.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
