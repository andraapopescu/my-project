package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.domain.Question;
import application.demo.security.FilterLoginService;
import application.demo.service.EmployeeService;
import application.demo.service.QuestionService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateQuizView extends VerticalLayout implements View {

    public final static String NAME = "GenerateQuizView";
    private static final long serialVersionUID = 1L;

    private static Grid questionsGrid;

    private static VerticalLayout mainLayout;
    private static ComboBox candidatesComboBox;
    @Override
    public void enter( ViewChangeListener.ViewChangeEvent event ) {

    }

    public GenerateQuizView() {
        if(FilterLoginService.currentEmployee == null || !FilterLoginService.currentEmployee.getLastName().equals("admin")) {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/");
        }

        questionsGrid = createQuestionsGrid();

        mainLayout = createFormForAddingQuestions();
        mainLayout.setSpacing(true);

        questionsGrid.setSelectionMode(Grid.SelectionMode.MULTI);


        this.addComponent(mainLayout);
        this.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
        this.setMargin(true);
    }

    public VerticalLayout createFormForAddingQuestions() {
        VerticalLayout result = new VerticalLayout();

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("900px");
        form.addStyleName("light");
        addComponent(form);
        setComponentAlignment(form, Alignment.TOP_CENTER);

        Label section = new Label("Generate new quiz for candidate");
        section.addStyleName("h2");
        section.addStyleName("colored");
        section.addStyleName("bold");
        form.addComponent(section);
        form.addComponent(new TextField());

        section = new Label("Choose the candidate");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        candidatesComboBox = createComboBoxForCandidate();
        form.addComponent(candidatesComboBox);
        form.addComponent(new TextField());

        final Button generateQuiz = new Button("Generate Random Quiz");
        generateQuiz.addStyleName(ValoTheme.BUTTON_PRIMARY);
        generateQuiz.addStyleName(ValoTheme.BUTTON_SMALL);
        generateQuiz.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(candidatesComboBox.getValue() == null) {
                    Notification.show("You have to choose the candidate for the quiz!", Notification.Type.ERROR_MESSAGE);
                } else {
                    List<Question> allAvailableQuestions = QuestionService.getAllQuestions();
                    Collections.shuffle(allAvailableQuestions);

                    if(allAvailableQuestions.size() < 10) {
                        Notification.show("There are no enough questions to generate a quiz! You need at least 10.",
                                Notification.Type.ERROR_MESSAGE);
                    } else {
                        for(int i = 0; i < 10; i++) {
                            
                        }
                    }
                }
            }
        });

        form.addComponent(generateQuiz);

        section = new Label("Choose 10 from all available questions");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        VerticalLayout verticalLayout = createLayoutForGrid();
        form.addComponent(verticalLayout);

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, false, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        final Button submitButton = new Button("Submit Quiz");
        submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
        submitButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(questionsGrid.getSelectedRows() != null) {
                    System.out.println(questionsGrid.getSelectedRows().size());
                }
            }
        });

        form.addComponent(footer);
        form.addComponent(submitButton);

        result.addComponent(form);
        result.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
        result.setMargin(true);
        result.setSpacing(true);

        return result;
    }

    private Grid createQuestionsGrid() {
        Grid result = new Grid();

        result.setContainerDataSource(createQuestionsBeanContainer());
        result.setSizeFull();
        result.getColumn("id").setWidth(80);
        result.setColumns("specialization", "question", "answer");

        result.setWidthUndefined();
        result.setSelectionMode(Grid.SelectionMode.MULTI);

        return result;
    }

    private VerticalLayout createLayoutForGrid() {
        VerticalLayout result = new VerticalLayout();

        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        result.addComponent(footer);

        result.addComponent(questionsGrid);

        return result;
    }


    private BeanItemContainer<Question> createQuestionsBeanContainer() {
        BeanItemContainer<Question> result = new BeanItemContainer<Question>(Question.class);
        result.addAll(QuestionService.getAllQuestions());

        return result;
    }

    public ComboBox createComboBoxForCandidate() {
        ComboBox result = new ComboBox();
        result.setFilteringMode(FilteringMode.CONTAINS);

        for(Employee e : EmployeeService.getAllEmployees()) {
            if(!e.getLastName().equals("admin") || !e.getFirstName().equals("admin")) {
                result.addItem(e.getFirstName() + " " + e.getLastName());
            }
        }

        result.setWidth("330px");
        result.setNewItemsAllowed(false);
        result.setTextInputAllowed(true);

        return result;
    }

    public static boolean areFieldsValid() {
        return true;
    }
}
