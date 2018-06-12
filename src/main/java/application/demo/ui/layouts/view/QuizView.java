package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.domain.Message;
import application.demo.security.FilterLoginService;
import application.demo.service.MessageService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andra.popescu on 4/3/2018.
 */
public class QuizView extends VerticalLayout implements View {

    public final static String NAME = "QuizView";
    private static VerticalLayout vLayout;

    private Employee currentEmployee;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    public QuizView() {
        if (FilterLoginService.currentEmployee == null) {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/");
        } else {
            currentEmployee = FilterLoginService.currentEmployee;
//            messages = MessageService.getMessageByEmployee(currentEmployee.getId());
            vLayout = new VerticalLayout();

            Label section = new Label(getTextForLabel());
            section.addStyleName("h2");
            section.addStyleName("colored");
            vLayout.addComponent(section);

            section = new Label();
            vLayout.addComponent(section);
//            messagesGrid = createMessagesGrid();
//
//            messagesGrid.addSelectionListener(new SelectionEvent.SelectionListener() {
//
//                @Override
//                public void select(SelectionEvent event) {
//                    if(messagesGrid.getSelectedRow() != null) {
//                        Window subWindow = new Window("Sent Message");
//                        MessagePopop popup = new MessagePopop((Message) messagesGrid.getSelectedRow());
//
//                        UI.getCurrent().addWindow(popup);
//                    }
//
//                }
//            });

//            vLayout.addComponent(messagesGrid);
//            vLayout.setComponentAlignment(messagesGrid, Alignment.MIDDLE_CENTER);
//
//            vLayout.setSpacing(true);
//
//            addComponent(vLayout);
//            this.setSpacing(true);
//            this.setMargin(true);

        }
    }

//    public Grid createMessagesGrid() {
//        Grid result = new Grid();
//        Collections.reverse(messages);
//
//        BeanItemContainer<Message> source = new BeanItemContainer<>(Message.class, messages);
//
//        result.setContainerDataSource(source);
//        result.setColumns("sentFrom", "date", "subject", "text");
//        result.getColumn("text").setMaximumWidth(250);
//        result.getColumn("text").setMinimumWidth(250);
//
//        result.setSizeFull();
//
//        return result;
//    }

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
