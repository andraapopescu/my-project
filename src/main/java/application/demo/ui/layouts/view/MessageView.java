package application.demo.ui.layouts.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.demo.service.MessageService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import application.demo.domain.Employee;
import application.demo.domain.Message;
import application.demo.security.FilterLoginService;

public class MessageView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public final static String NAME = "MessageView";
	private static Grid messagesGrid;
	private static VerticalLayout vLayout;
	private static HorizontalLayout hLayout;
	
	public static boolean isSelected = false;

	private static List<Message> messages = new ArrayList<Message>();
	
	private Employee currentEmployee;

	@Override
	public void enter(ViewChangeEvent event) {

	}

	public MessageView() {
		if (FilterLoginService.currentEmployee == null) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080/");
		} else {
			currentEmployee = FilterLoginService.currentEmployee;
			messages = MessageService.getMessageByEmployee(currentEmployee.getId());
			vLayout = new VerticalLayout();

			Label section = new Label(getTextForLabel());
			section.addStyleName("h2");
			section.addStyleName("colored");
			vLayout.addComponent(section);

			section = new Label();
			vLayout.addComponent(section);
			messagesGrid = createMessagesGrid();
			
			messagesGrid.addSelectionListener(new SelectionListener() {
				
				@Override
				public void select(SelectionEvent event) {
					if(messagesGrid.getSelectedRow() != null) {
						Window subWindow = new Window("Sent Message");
						MessagePopop popup = new MessagePopop((Message) messagesGrid.getSelectedRow());
						
						UI.getCurrent().addWindow(popup);
					}
					
				}
			});
			
			vLayout.addComponent(messagesGrid);
			vLayout.setComponentAlignment(messagesGrid, Alignment.MIDDLE_CENTER);

			vLayout.setSpacing(true);
			
			addComponent(vLayout);
			this.setSpacing(true);
			this.setMargin(true);

		}
	}

	public Grid createMessagesGrid() {
		Grid result = new Grid();
		Collections.reverse(messages);
		
		BeanItemContainer<Message> source = new BeanItemContainer<>(Message.class, messages);

		result.setContainerDataSource(source);
		result.setColumns("sentFrom", "date", "subject", "text");
		result.getColumn("text").setMaximumWidth(250); 
		result.getColumn("text").setMinimumWidth(250); 
		
		result.setSizeFull();

		return result;
	}

	public String getTextForLabel() {
		String result;

		if (currentEmployee.getLastName().equals("admin")) {
			result = " Welcome, administrator !";
		} else {
			result = " Welcome, " + currentEmployee.getFirstName() + "  " + currentEmployee.getLastName() + " !";
		}
		return result;
	}

}
