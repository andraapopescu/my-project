package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.domain.Question;
import application.demo.domain.Quiz;
import application.demo.domain.QuizQuestion;
import application.demo.service.QuizQuestionService;
import application.demo.service.QuizService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

public class StartQuizView extends VerticalLayout implements View {

    public final static String NAME = "StartQuizView";

    private Employee currentEmployee;
    private Quiz quiz;
    private List<QuizQuestion> quizQuestions;
    private List<Question> allQuestions = new ArrayList<>();
    private List<Question> reorderedQuestions = new ArrayList<>();

    private HorizontalLayout mainLayput = new HorizontalLayout();
    private List<OptionGroup> variantsList = new ArrayList<>();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            try {
                init(event);

                mainLayput.addComponent(createLeftLayout());
                mainLayput.addComponent(createRightLayout());

                mainLayput.setSpacing(true);
                mainLayput.setMargin(true);


                this.addComponent(mainLayput);
                this.setMargin(true);
                this.setSpacing(true);

            } catch(NumberFormatException e) {
                e.printStackTrace();
                getUI().getNavigator().navigateTo(QuizView.NAME);

            } catch(NullPointerException e) {
                UI.getCurrent().getPage().setLocation("http://localhost:8080/");
                e.printStackTrace();
            }
        }
    }

    private void init(ViewChangeListener.ViewChangeEvent event) {
        quiz = QuizService.getQuizById(Long.parseLong(event.getParameters()));
        currentEmployee = quiz.getEmployee();
        quizQuestions = QuizQuestionService.getQuizQuestionByQuiz(quiz.getId());

        for(QuizQuestion qq : quizQuestions) {
            allQuestions.add(qq.getQuestion());
        }
    }

    public StartQuizView() {

    }

    private VerticalLayout createLeftLayout() {
        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout vLayout;

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("700px");
        form.addStyleName("light");
        addComponent(form);

        int i = 1;
        int k = 1;

        if(allQuestions.size() != 0) {
            for(Question question : allQuestions) {
                vLayout = new VerticalLayout();

                Label section = new Label("Q" + k + " : " + question.getQuestion());
                section.addStyleName("h3");
                section.addStyleName("bold");
                vLayout.addComponent(section);

                OptionGroup variants = new OptionGroup("Choose the right answer");
                variants.addItems(question.getVariant1(), question.getVariant2(), question.getVariant3(), question.getVariant4());
                vLayout.addComponent(variants);
                variants.setId(String.valueOf(question.getId()));

                vLayout.setSpacing(true);
                vLayout.setMargin(true);

                if(i % 2 == 0) {
                    form.addComponent(vLayout);
                    leftLayout.addComponent(form);
                    k++;

                    reorderedQuestions.add(question);
                    variantsList.add(variants);
                }

                i++;
            }
        }

        Button submitButton = createSubmitButton();
        leftLayout.addComponent(new Label());
        leftLayout.addComponent(submitButton);
        leftLayout.setComponentAlignment(submitButton, Alignment.BOTTOM_RIGHT);

        leftLayout.setMargin(true);
        leftLayout.setSpacing(true);

        return leftLayout;
    }

    private VerticalLayout createRightLayout() {
        VerticalLayout rightLayput = new VerticalLayout();
        VerticalLayout vLayout;

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("700px");
        form.addStyleName("light");
        addComponent(form);

        int j = 1;
        int k = 6;

        if(allQuestions.size() != 0) {
            for(Question question : allQuestions) {
                vLayout = new VerticalLayout();

                Label section = new Label("Q" + k + " : " + question.getQuestion());
                section.addStyleName("h3");
                section.addStyleName("bold");
                vLayout.addComponent(section);

                OptionGroup variants = new OptionGroup("Choose the right answer");
                variants.addItems(question.getVariant1(), question.getVariant2(), question.getVariant3(), question.getVariant4());
                variants.setId(k + "");
                vLayout.addComponent(variants);



                vLayout.setSpacing(true);
                vLayout.setMargin(true);

                if(j % 2 != 0) {
                    form.addComponent(vLayout);
                    rightLayput.addComponent(form);
                    k++;

                    variantsList.add(variants);
                    reorderedQuestions.add(question);
                }

                j++;
            }
        }

        rightLayput.setMargin(true);
        rightLayput.setSpacing(true);

        return rightLayput;
    }

    private Button createSubmitButton() {
        Button submitButton = new Button("Submit Quiz");
        submitButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

                int i = 0;
                for(OptionGroup o : variantsList) {
                    if(reorderedQuestions.get(i).getAnswer().equals(o.getValue())) {
                        
                    }
                    i++;
                }
            }
        });

        return submitButton;
    }

}
