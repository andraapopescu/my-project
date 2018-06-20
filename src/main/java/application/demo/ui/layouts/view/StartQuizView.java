package application.demo.ui.layouts.view;

import application.demo.domain.*;
import application.demo.service.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.*;

public class StartQuizView extends VerticalLayout implements View {

    public final static String NAME = "StartQuizView";

    private Employee currentEmployee;
    private Quiz quiz;
    private List<EmployeeSkill> employeeSkills;
    private List<QuizQuestion> quizQuestions;
    private List<Question> allQuestions = new ArrayList<>();
    private List<Question> reorderedQuestions = new ArrayList<>();

    private HorizontalLayout mainLayput = new HorizontalLayout();
    private List<OptionGroup> variantsList = new ArrayList<>();

    private int score = 0;

    Map<String, Integer> capabilitiesMap = new HashMap<>();

    Employee admin = EmployeeService.findEmployeeByLastName("admin").get(0);

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
        employeeSkills = EmployeeSkillService.getEmployeeSkillByEmployee(currentEmployee.getId());


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

                if(isOptionGroupComplete()) {

                    int i = 0;
                    for(OptionGroup o : variantsList) {
                        if(reorderedQuestions.get(i).getAnswer().equals(o.getValue())) {
                            Question q = reorderedQuestions.get(i);
                            score++;

                            if(capabilitiesMap.get(q.getSpecialization()) == null) {
                                capabilitiesMap.put(q.getSpecialization(), 1);
                            } else {
                                int value = capabilitiesMap.get(q.getSpecialization());
                                capabilitiesMap.put(q.getSpecialization(), value + 1);
                            }
                        }

                        i++;
                    }

                    writeMessageToAdmin();
                    QuizService.deleteQuiz(quiz);
                    getUI().getNavigator().navigateTo(QuizView.NAME);

                    Window subWindow = new Window("Quiz Confirmation");
                    QuizConfirmationPopup popup = new QuizConfirmationPopup();
                    UI.getCurrent().addWindow(popup);

                    calculateScore();
                } else {
                    Notification.show("Be sure that you have answered to all the questions!", Notification.Type.ERROR_MESSAGE);
                }
            }
        });



        return submitButton;
    }

    private boolean isOptionGroupComplete() {
        for(OptionGroup o : variantsList) {
            if(o.getValue() == null || o.getValue().equals("null")) {
                return false;
            }
        }

        return true;
    }

    private void writeMessageToAdmin() {
        String subject = currentEmployee.getLastName() + " " + currentEmployee.getFirstName() + " - " + quiz.getDescription();

        StringBuffer messageBody = new StringBuffer();
        messageBody.append(currentEmployee.getLastName() + " " + currentEmployee.getFirstName() + " had a SCORE of "
                + score + " point out of 10. ");

        if(score != 0) {
            messageBody.append("The result looks like this: ");

            for(String key : capabilitiesMap.keySet()) {
                String value = capabilitiesMap.get(key).toString();
                messageBody.append("For capability: ");
                messageBody.append(key.toUpperCase() + " - " + value + " pts, ");
            }

            messageBody.setLength(messageBody.length() - 2);
        }

        Message messageForAdmin = new Message("Quiz Report", new Date(), subject, messageBody.toString(), admin);
        MessageService.saveMessage(messageForAdmin);

    }

    // horror code, please don't judge
    private void calculateScore() {
        if(score != 0) {
            Map.Entry<String, Integer> entry = capabilitiesMap.entrySet().iterator().next();
            if(capabilitiesMap.keySet().size() == 1) {
                if(entry.getValue() >= 5 && entry.getValue() < 10) {
                    checkAndIncreaseLevelWith1pct(entry.getKey());
                } else if(entry.getValue() == 10) {
                    for(EmployeeSkill es : employeeSkills) {
                        if(es.getSkill().getName().equals(entry.getKey())) {
                            if(es.getLevel() < 9) {
                                // increase with 2
                                EmployeeSkillService.deleteEmployeeSkillsById(es.getId());
                                EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(es.getLevel() + 2,
                                        es.getEmployee(), es.getSkill()));

                                HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(),
                                        es.getLevel() + 2, currentEmployee, es.getSkill()));

                            } else if(es.getLevel() == 9) {
                                // increase with 1
                                EmployeeSkillService.deleteEmployeeSkillsById(es.getId());
                                EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(es.getLevel() + 1,
                                        es.getEmployee(), es.getSkill()));

                                HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(),
                                        es.getLevel() + 1, currentEmployee, es.getSkill()));
                            }
                        }
                    }
                }
            } else if(capabilitiesMap.keySet().size() == 2) {
                for(String key : capabilitiesMap.keySet()) {
                    if(capabilitiesMap.get(key) >= 5) {
                        checkAndIncreaseLevelWith1pct(key);
                    }
                }
            } else if(capabilitiesMap.keySet().size() == 3) {
                for(String key : capabilitiesMap.keySet()) {
                    if(capabilitiesMap.get(key) >= 3) {
                        checkAndIncreaseLevelWith1pct(key);
                    }
                }
            } else if(capabilitiesMap.keySet().size() > 3 && score == 10) {
                List<Skill> skills = SkillService.findSkillByName("technical_expert");
                Skill technicalExpertSkill;

                if(skills.size() == 0) {
                    SkillService.saveSkill(new Skill("technical_expert"));
                    technicalExpertSkill = SkillService.findSkillByName("technical_expert").get(0);

                    EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(2, currentEmployee, technicalExpertSkill));
                    HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(),
                            2, currentEmployee, technicalExpertSkill));
                } else {
                    for(EmployeeSkill es : employeeSkills) {
                        Skill skill = skills.get(0);

                        if(es.getSkill().getName().equals(skill.getName())) {
                            if(es.getLevel() < 9) {
                                EmployeeSkillService.deleteEmployeeSkillsById(es.getId());
                                EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(es.getLevel() + 2, currentEmployee, skill));

                                HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(),
                                        es.getLevel() + 2, currentEmployee, skill));
                            } else if(es.getLevel() == 9) {
                                EmployeeSkillService.deleteEmployeeSkillsById(es.getId());
                                EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(10, currentEmployee, skill));

                                HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(), 10, currentEmployee, skill));
                            }
                        } else {
                            EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(2, currentEmployee, skill));
                            HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(), 2, currentEmployee, skill));
                        }
                    }
                }
            }
        }
    }

    private void checkAndIncreaseLevelWith1pct(String key) {
        for(EmployeeSkill es : employeeSkills) {
            if(es.getSkill().getName().equals(key)) {
                if(es.getLevel() < 10) {
                    //increase with 1
                    EmployeeSkillService.deleteEmployeeSkillsById(es.getId());
                    EmployeeSkillService.saveEmployeeSkill(new EmployeeSkill(es.getLevel() + 1,
                            es.getEmployee(), es.getSkill()));

                    HistoryEmployeeSkillService.saveHistoryEmployeeSkill(new HistoryEmployeeSkill(new Date(),
                            es.getLevel() + 1, currentEmployee, es.getSkill()));
                }
            }
        }
    }
}