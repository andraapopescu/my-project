package application.demo.ui.layouts.view;

import application.demo.domain.Question;
import application.demo.service.QuestionService;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionPopup extends Window {
    private static final long serialVersionUID = 1L;
    private Question question;

    private static TextField answer, specialization, variant;
    private List<TextField> variantFields = new ArrayList<>();
    private static TextArea questionArea;

    private VerticalLayout vLayout;
    List<String> variants = new ArrayList<>();

    public void closeWindow() {
        this.close();
    }

    public QuestionPopup(Question question) {
        this.question = question;
        variants = createVariantsList(question);
        vLayout = createViewForQuestion();
        vLayout.setSpacing(true);

        setContent(vLayout);

        this.center();
        this.setHeight("65%");
        this.setWidth("40%");
    }

    private List<String> createVariantsList( Question question ) {
        List<String> variants = new ArrayList<>();

        if(!question.getAnswer().equals(question.getVariant1())) {
            variants.add(question.getVariant1());
        }
        if(!question.getAnswer().equals(question.getVariant2())) {
            variants.add(question.getVariant2());
        }
        if(!question.getAnswer().equals(question.getVariant3())) {
            variants.add(question.getVariant3());
        }
        if(!question.getAnswer().equals(question.getVariant4())) {
            variants.add(question.getVariant4());
        }

        return variants;
    }

    private VerticalLayout createViewForQuestion() {
        VerticalLayout result = new VerticalLayout();

        final FormLayout form = new FormLayout();
        form.setMargin(false);
        form.setWidth("800px");
        form.addStyleName("light");

        Label section = new Label("Question Details");
        section.addStyleName("h2");
        section.addStyleName("colored");
        section.addStyleName("border");
        form.addComponent(section);
        form.addComponent(new TextField());

        questionArea = new TextArea("Question");
        questionArea.setWidth("50%");
        questionArea.setHeight("200%");
        questionArea.setValue(question.getQuestion());
        form.addComponent(questionArea);

        specialization = new TextField("Specialization Area");
        specialization.setWidth("50%");
        form.addComponent(specialization);
        specialization.setValue(question.getSpecialization());

        section = new Label("Correct Answer");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        answer = new TextField();
        answer.setWidth("50%");
        form.addComponent(answer);
        answer.setValue(question.getAnswer());
//        answer.setReadOnly(true);

        section = new Label("Other variants for answer");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        for(String var : variants) {
            variant = new TextField();
            variant.setWidth("50%");
            form.addComponent(variant);
            variant.setValue(var);
            variantFields.add(variant);
        }

        Button deleteButton = new Button("Delete");
        deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);
        deleteButton.addStyleName(ValoTheme.BUTTON_SMALL);
        Button updateButton = new Button("Update");
        updateButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        updateButton.addStyleName(ValoTheme.BUTTON_SMALL);

        deleteButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;


            @Override
            public void buttonClick(Button.ClickEvent event) {
                QuestionService.deleteQuestion(question.getId());
                Notification.show("This question has just been deleted!", Notification.Type.HUMANIZED_MESSAGE);
                closeWindow();

                UI.getCurrent().getNavigator().navigateTo(AddQuestionView.NAME);
            }
        });

        updateButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(areFieldsValid()) {
                    variantFields.add(answer);
                    Collections.shuffle(variantFields);
//                    Question updatedQuestion = new Question(specialization.getValue(), questionArea.getValue(), answer.getValue(),
//                            variantFields.get(0).getValue(), variantFields.get(1).getValue(), variantFields.get(2).getValue(), variantFields.get(3).getValue());

                    question.setSpecialization(specialization.getValue());
                    question.setQuestion(questionArea.getValue());
                    question.setAnswer(answer.getValue());
                    question.setVariant1(variantFields.get(0).getValue());
                    question.setVariant2(variantFields.get(1).getValue());
                    question.setVariant3(variantFields.get(2).getValue());
                    question.setVariant4(variantFields.get(3).getValue());

                    QuestionService.updateQuestion(question);

                    Notification.show("This question has just been updated!", Notification.Type.HUMANIZED_MESSAGE);
                    closeWindow();

                    UI.getCurrent().getNavigator().navigateTo(AddQuestionView.NAME);
                }


            }
        });

        HorizontalLayout hLayout = new HorizontalLayout(updateButton, deleteButton);
        hLayout.setSpacing(true);
        hLayout.setMargin(true);

        result.addComponent(form);
        result.addComponent(hLayout);
        result.setComponentAlignment(hLayout, Alignment.BOTTOM_CENTER);
        result.setComponentAlignment(form, Alignment.TOP_CENTER);
        result.setSpacing(true);
        result.setMargin(true);

        return result;

    }

    private boolean areFieldsValid() {
        if(questionArea.getValue().equals("") || answer.getValue().equals("") || specialization.getValue().equals("") ||
                variantFields.get(0).getValue().equals("") || variantFields.get(1).getValue().equals("") ||variantFields.get(2).getValue().equals("") ) {
            Notification.show("Make sure that you have completed all fields while editing!", Notification.Type.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

}
