package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.domain.Quiz;
import application.demo.security.FilterLoginService;
import application.demo.service.QuizService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizView extends VerticalLayout implements View {

    public final static String NAME = "QuizView";

    private Employee currentEmployee;
    private List<Quiz> quizList = new ArrayList<>();
    private HorizontalLayout mainLayout;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    public QuizView() {
        if(FilterLoginService.currentEmployee == null) {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/");
        } else {
            currentEmployee = FilterLoginService.currentEmployee;
            initiateQuizzes();

            this.addComponent(createVerticalLeftLayout());
            this.setSpacing(true);
            this.setMargin(true);

        }
    }

    private void initiateQuizzes() {
        List<Quiz> expiredQuizzed = new ArrayList<>();
        if(currentEmployee != null) {
            quizList = QuizService.getQuizByEmployee(currentEmployee.getId());

            for(Quiz q : quizList) {
                if(!q.getExpirationDate().after(new Date())) {
                    expiredQuizzed.add(q);
                }
            }

            if(expiredQuizzed.size() != 0) {
                quizList.removeAll(expiredQuizzed);
            }
        }
    }

    private VerticalLayout createVerticalLeftLayout() {
        VerticalLayout result = new VerticalLayout();

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("900px");
        form.addStyleName("light");
        addComponent(form);
        setComponentAlignment(form, Alignment.MIDDLE_LEFT);

        if(quizList == null || quizList.size() == 0) {

            Label section = new Label("There are no available quizzes for you right now");
            section.addStyleName("h1");
            section.addStyleName("colored");
            section.addStyleName("bold");
            form.addComponent(section);
            form.addComponent(new TextField());

            form.setSpacing(true);
            form.setMargin(true);

            result.addComponent(form);
            result.setComponentAlignment(form, Alignment.MIDDLE_CENTER);

            return result;
        }

        Label section = new Label(DECLARATION_OF_INDEPENDE);
        section.addStyleName("h2");
        section.addStyleName("colored");
        section.addStyleName("bold");
        form.addComponent(section);
        form.addComponent(new TextField());

        section = new Label("Instructions");
        section.addStyleName("h2");
        section.addStyleName("colored");
        section.addStyleName("bold");
        form.addComponent(section);
        form.addComponent(new TextField());

        TextArea instructionsArea = new TextArea();
        instructionsArea.setValue(INSTRUCTIONS);
        instructionsArea.setHeight("280px");
        instructionsArea.setWidth("1000px");
        section.addStyleName("bold");
        instructionsArea.isReadOnly();
        form.addComponent(instructionsArea);
        form.addComponent(new TextField());

        section = new Label("Available quizzes:");
        section.addStyleName("h2");
        section.addStyleName("colored");
        section.addStyleName("bold");
        form.addComponent(section);

        form.addComponent(addQuizzesToForm());

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, false, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        result.addComponent(form);
        result.setComponentAlignment(form, Alignment.BOTTOM_LEFT);

        return result;
    }

    private VerticalLayout addQuizzesToForm() {
        VerticalLayout result = new VerticalLayout();

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("900px");
        form.addStyleName("light");
        addComponent(form);

        if(quizList != null && quizList.size() != 0) {
            for(Quiz q : quizList) {
                if(q.getExpirationDate().after(new Date())) {
                    VerticalLayout vLayout = new VerticalLayout();

                    Label section = new Label("Quiz description");
                    section.addStyleName("h4");
                    section.addStyleName("bold");
                    vLayout.addComponent(section);

                    section = new Label(q.getDescription());
                    section.addStyleName("h3");
                    section.addStyleName("colored");
                    section.addStyleName("bold");
                    vLayout.addComponent(section);

                    TextField dueTime = new TextField("Attempt due:");
                    dueTime.setValue(q.getExpirationDate().toString());
                    dueTime.setReadOnly(true);
                    dueTime.setWidth("50%");
                    vLayout.addComponent(dueTime);

                    Button startQuizBtn = new Button("Start Now");
                    startQuizBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
                    startQuizBtn.addStyleName(ValoTheme.BUTTON_SMALL);
                    startQuizBtn.addClickListener(new Button.ClickListener() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            getUI().getNavigator()
                                    .navigateTo(StartQuizView.NAME + "/" + q.getId());
                        }
                    });
                    vLayout.addComponent(startQuizBtn);
                    vLayout.setSpacing(true);
                    vLayout.setMargin(true);

                    HorizontalLayout footer = new HorizontalLayout();
                    footer.setMargin(new MarginInfo(true, false, false, false));
                    footer.setSpacing(true);
                    footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

//                vLayout.addComponent(footer);
                    form.addComponent(vLayout);
                    result.addComponent(form);
                }
            }
        }

        result.setSpacing(true);

        return result;
    }

    private static final String DECLARATION_OF_INDEPENDE = "Declaration of Independent Quizzes";
    private static final String INSTRUCTIONS = "The quizzes consist of questions carefully designed to help out HR team to asses " +
            "your comprehension of the information presented on the topics covered in the questions. There will be data " +
            "collected on the website regarding your responses.\n" + "\n" +
            "Each question in the quiz is of \"single-choice answer\" format and it can cover one or more specific areas " +
            "of software development. Read each question carefully and click on the bullet that you think is associated " +
            "with the right response. You have to complete the quiz in the specified time in order not to have it saved " +
            "without being able to respond to all questions. \n" +
            "\n" +
            "After responding to the all questions provided in your quiz, click on \"Submit\" button on the button of the page. " +
            "Doing so, our answers will be saved and analyzed and you can exit the quiz. The total score for the quiz is " +
            "based on your responses to all questions. If you respond incorrectly to a question, your quiz score will " +
            "reflect it appropriately. Your quiz will be graded and will be taken into consideration on evaluating your " +
            "professional level for a specific capability or your knowledge for a certain job position. ";

}
