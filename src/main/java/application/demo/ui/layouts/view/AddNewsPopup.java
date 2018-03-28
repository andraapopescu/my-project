package application.demo.ui.layouts.view;


import java.util.Date;

import application.demo.service.NewsService;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.Employee;
import application.demo.domain.News;
import application.demo.security.FilterLoginService;

public class AddNewsPopup extends Window {
	private static final long serialVersionUID = 1L;
	
	Label sectionSubject, sectionContent;
	private TextField subject = new TextField();
	private TextArea content = new TextArea();
	private Employee employee;
	private Button submitButton = new Button("Submit");
	
	private VerticalLayout vLayout = new VerticalLayout();
	
	public AddNewsPopup() {
		try {
			employee = FilterLoginService.currentEmployee;
			
			vLayout = createLayout();
			
			submitButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					if( !subject.getValue().equals("") || !subject.getValue().equals("")) {
						News n = new News(subject.getValue(), subject.getValue(), new Date(), 
								employee.getFirstName()+ " " + employee.getLastName(), employee);
						
						NewsService.saveNews(n);
					}
					
					UI.getCurrent().getPage().setLocation("http://localhost:8080/mainPage");
					close();
					
				}
			});
			
			setContent(vLayout);
			setWidth("410px");
			setHeight("480px");
			
			this.center();
			
		} catch(Exception e) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080");
		}
	}
	
	public VerticalLayout createLayout() {
		VerticalLayout result = new VerticalLayout();
		
		sectionSubject = new Label("   News' Subject");
		sectionSubject.addStyleName("h2");
		sectionSubject.addStyleName("colored");
		result.addComponent(sectionSubject);
		result.setComponentAlignment(sectionSubject, Alignment.MIDDLE_CENTER);
		
		subject.setSizeFull();
		result.addComponent(subject);
		result.setComponentAlignment(subject, Alignment.MIDDLE_CENTER);
		
		Label sectionContent = new Label("   News' Content");
		sectionContent.addStyleName("h2");
		sectionContent.addStyleName("colored");
		result.addComponent(sectionContent);
		result.setComponentAlignment(sectionContent, Alignment.MIDDLE_CENTER);
		
		content.setSizeFull();
		result.addComponent(content);
		result.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
		
		Label label = new Label();
		result.addComponent(label);
		
		result.addComponent(submitButton);
		submitButton.setIcon(FontAwesome.CHECK_SQUARE);
		submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		subject.addStyleName(ValoTheme.BUTTON_SMALL);
		result.setComponentAlignment(submitButton, Alignment.MIDDLE_CENTER);
		
		result.setMargin(true);
		result.setSpacing(true);
		
		return result;
	}
	
}
