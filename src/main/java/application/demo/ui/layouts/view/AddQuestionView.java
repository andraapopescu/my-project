package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.domain.Question;
import application.demo.domain.Skill;
import application.demo.security.FilterLoginService;
import application.demo.service.QuestionService;
import application.demo.service.SkillService;
import application.demo.ui.layouts.search.SearchPageHelper;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.scheduling.annotation.Scheduled;
import scala.xml.Null;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionView extends VerticalLayout implements View {
    private static final long serialVersionUID = 1L;

    private static TextField forthVarField, firstVarField, secondVarField, thirdVarField;
    private static TextArea questionField;

    public final static String NAME = "AddQuestionView";
    private static Grid questionsGrid;
    private Question question;

    private static VerticalLayout vLeftLayout;
    private static VerticalLayout vRightLayout;
    private static HorizontalLayout hLayout;
    private static ComboBox variantsComboBox, specializationComboBox;

    List<String> variants = new ArrayList<>();

    public static boolean isSelected = false;

    private Employee currentEmployee;



    @Override
    public void enter( ViewChangeListener.ViewChangeEvent event ) {
        questionsGrid = createQuestionsGrid();

        vLeftLayout = createFormForAddingQuestions();
        vRightLayout = createLayoutForGrid();

        hLayout = new HorizontalLayout(vLeftLayout, vRightLayout);
        addComponent(hLayout);
        hLayout.setSpacing(true);

        this.setSpacing(true);
        this.setMargin(true);

        questionsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        questionsGrid.addSelectionListener(new SelectionEvent.SelectionListener() {

            @Override
            public void select(com.vaadin.event.SelectionEvent event) {
                if (questionsGrid.getSelectedRow() != null) {
                    question = (Question) questionsGrid.getSelectedRow();

                    Window subWindow = new Window("My Popup Window");
                    QuestionPopup details = new QuestionPopup(question);
                        UI.getCurrent().addWindow(details);
                    }
                }
        });

    }

    public AddQuestionView() {
        if(FilterLoginService.currentEmployee == null || !FilterLoginService.currentEmployee.getLastName().equals("admin")) {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/");
        } else {
            currentEmployee = FilterLoginService.currentEmployee;


        }
    }

    public VerticalLayout createFormForAddingQuestions() {
        VerticalLayout result = new VerticalLayout();

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("800px");
        form.addStyleName("light");
        addComponent(form);
        setComponentAlignment(form, Alignment.TOP_CENTER);

        Label section = new Label("Introduce a new question");
        section.addStyleName("h2");
        section.addStyleName("colored");
        form.addComponent(section);
        form.addComponent(new TextField());

        questionField = new TextArea("Question:");
        questionField.setWidth("50%");
        questionField.setHeight("200%");
        form.addComponent(questionField);
        questionField.setRequired(true);

        section = new Label("Some possible answers");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        firstVarField = new TextField("Variant 1 :");
        firstVarField.setWidth("50%");
        firstVarField.setRequired(true);
        form.addComponent(firstVarField);
        variants.add(firstVarField.getValue());

        secondVarField = new TextField("Variant 2 :");
        secondVarField.setWidth("50%");
        form.addComponent(secondVarField);
        secondVarField.setRequired(true);
        variants.add(secondVarField.getValue());

        thirdVarField = new TextField("Variant 3 :");
        thirdVarField.setWidth("50%");
        form.addComponent(thirdVarField);
        thirdVarField.setRequired(true);
        variants.add(thirdVarField.getValue());

        forthVarField = new TextField("Variant 4 :");
        forthVarField.setWidth("50%");
        form.addComponent(forthVarField);
        forthVarField.setRequired(true);
        variants.add(forthVarField.getValue());

        section = new Label("Choose the right answer");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        variantsComboBox = createComboBoxForVariants();
        form.addComponent(variantsComboBox);

        section = new Label("Choose the question specialization area");
        section.addStyleName("h3");
        section.addStyleName("colored");
        form.addComponent(section);

        specializationComboBox = createComboBoxForSpecialization();
        form.addComponent(specializationComboBox);

        final Button saveButton = new Button("Save");
        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addStyleName(ValoTheme.BUTTON_SMALL);
        saveButton.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                    if(areFieldsValid()) {

                        Question question = new Question(specializationComboBox.getValue().toString(), questionField.getValue().toString(),
                                getQuestionsRightAnswer(), firstVarField.getValue().toString(), secondVarField.getValue().toString(),
                                thirdVarField.getValue().toString(), forthVarField.getValue().toString(), null);
                        QuestionService.saveQuestion(question);

                        Notification.show("Your question have been registered!", Notification.Type.HUMANIZED_MESSAGE);
                        createQuestionsGrid();

                        UI.getCurrent().getNavigator().navigateTo(AddQuestionView.NAME);
                    }
            }
        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, false, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);

        form.addComponent(saveButton);
        form.setComponentAlignment(saveButton, Alignment.BOTTOM_CENTER);


        result.addComponent(form);
        result.setMargin(true);

        return result;
    }

    private Grid createQuestionsGrid() {
        Grid result = new Grid();

        result.setContainerDataSource(createQuestionsBeanContainer());
        result.setSizeFull();
        result.getColumn("id").setWidth(80);
        result.setColumns("specialization", "question");

        result.setWidthUndefined();

        return result;
    }

    private VerticalLayout createLayoutForGrid() {
        VerticalLayout result = new VerticalLayout();

        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setWidth("800px");
        form.addStyleName("light");
        addComponent(form);
        setComponentAlignment(form, Alignment.TOP_CENTER);

        Label section = new Label("All available questions");
        section.addStyleName("h2");
        section.addStyleName("colored");
        form.addComponent(section);

        HorizontalLayout footer = new HorizontalLayout();
        footer.setMargin(new MarginInfo(true, false, false, false));
        footer.setSpacing(true);
        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        form.addComponent(footer);

        result.addComponent(form);
        result.addComponent(questionsGrid);
        result.setMargin(true);
//        result.setSpacing(true);

        return result;
    }


    private BeanItemContainer<Question> createQuestionsBeanContainer() {
        BeanItemContainer<Question> result = new BeanItemContainer<Question>(Question.class);
        result.addAll(QuestionService.getAllQuestions());

        return result;
    }

    private void clearFormAfterCommit() {
        questionField.clear();
        firstVarField.clear();
        secondVarField.clear();
        thirdVarField.clear();
        forthVarField.clear();

        variantsComboBox.clear();
        specializationComboBox.clear();
    }

    public ComboBox createComboBoxForVariants() {
        ComboBox result = new ComboBox();
        result.setFilteringMode(FilteringMode.CONTAINS);

        result.addItems("Variant 1", "Variant 2", "Variant 3", "Variant 4");

        result.setWidth("330px");
        result.setNewItemsAllowed(false);
        result.setTextInputAllowed(true);

        return result;
    }

    public ComboBox createComboBoxForSpecialization() {
        ComboBox result = new ComboBox();
        result.setFilteringMode(FilteringMode.CONTAINS);

        if(SkillService.getAllSkills().size() != 0) {
            for(Skill skill : SkillService.getAllSkills()) {
                result.addItem(skill.getName());
            }
        }

        result.setWidth("330px");
        result.setNewItemsAllowed(true);
        result.setTextInputAllowed(true);

        return result;
    }

    private String getQuestionsRightAnswer() {
        try {
            String variant = variantsComboBox.getValue().toString();

            switch(variant) {
                case "Variant 1":
                    return firstVarField.getValue();
                case "Variant 2":
                    return secondVarField.getValue();
                case "Variant 3":
                    return thirdVarField.getValue();
                case "Variant 4":
                    return forthVarField.getValue();

                default:
                    return "";
            }
        } catch(Exception e) {
            Notification.show("You forgot to choose the correct answer!", Notification.Type.ERROR_MESSAGE);
            return "";
        }
    }

    public static boolean areFieldsValid() {
        try {
            questionField.validate();
            firstVarField.validate();
            secondVarField.validate();
            thirdVarField.validate();
            forthVarField.validate();
            variantsComboBox.getValue();

        } catch(Validator.InvalidValueException e) {
            Notification.show("Make sure that you have completed all mandatory fields marked with \"*\"!", Notification.Type.ERROR_MESSAGE);
            return false;
        }

        if(variantsComboBox.getValue() == null) {
            Notification.show("You forgot to choose the correct answer!", Notification.Type.ERROR_MESSAGE);
            return false;
        }

        if(specializationComboBox.getValue() == null || specializationComboBox.getValue().equals("")) {
            Notification.show("Please define a specialization area for your question!", Notification.Type.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

}
