package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.domain.Quiz;
import application.demo.service.QuizService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class StartQuizView extends VerticalLayout implements View {

    public final static String NAME = "StartQuizView";

    private Employee currentEmployee;
    private List<Quiz> quizList = new ArrayList<>();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(event.getParameters() != null) {
            try {
                Quiz q = QuizService.getQuizById(Long.parseLong(event.getParameters()));
                System.out.println(q);
            } catch(NumberFormatException e) {
                e.printStackTrace();
                getUI().getNavigator().navigateTo(QuizView.NAME);

            } catch(NullPointerException e) {
                UI.getCurrent().getPage().setLocation("http://localhost:8080/");
                e.printStackTrace();
            }
        }
    }

    public StartQuizView() {

    }

}
