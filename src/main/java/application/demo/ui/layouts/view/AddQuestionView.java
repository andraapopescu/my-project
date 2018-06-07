package application.demo.ui.layouts.view;

import application.demo.domain.Employee;
import application.demo.security.FilterLoginService;
import application.demo.service.EmployeeService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionView extends VerticalLayout implements View {
    private static final long serialVersionUID = 1L;

    private static TextField forthVarField, firstVarField, secondVarField, thirdVarField;
    private static TextArea questionField;

    public final static String NAME = "AddQuestionView";
    private static Grid questionsGrid;

    private static VerticalLayout vLeftLayout;
    private static VerticalLayout vRightLayout;
    private static HorizontalLayout hLayout;
    private static ComboBox comboBox;

    List<String> variants = new ArrayList<>();

    public static boolean isSelected = false;

    private Employee currentEmployee;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    public AddQuestionView() {
        if (FilterLoginService.currentEmployee == null || !FilterLoginService.currentEmployee.getLastName().equals("admin")) {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/");
        } else {
            currentEmployee = FilterLoginService.currentEmployee;
            vLeftLayout = createFormForAddingQuestions();
            vRightLayout = new VerticalLayout();

            hLayout = new HorizontalLayout(vLeftLayout, vRightLayout);
            addComponent(hLayout);

            this.setSpacing(true);
            this.setMargin(true);
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

//        forthVarField = new TextField("Last Name");
//        forthVarField.setWidth("50%");
//        form.addComponent(forthVarField);
//        forthVarField.setRequired(true);


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

        comboBox = createComboBox();
        form.addComponent(comboBox);

        Button saveButton = new Button("Save");
        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addStyleName(ValoTheme.BUTTON_SMALL);
//        saveButton.addClickListener(new Button.ClickListener() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                if (user.getRole().equals("admin")) {
//                    try {
//                        EmployeeService.findEmployeeByEmail(comboBox.getValue().toString()).get(0);
//                        createMessage();
//                    } catch (IndexOutOfBoundsException e) {
//                        sendMessage();
//                    }
//                } else {
//                    createMessage();
//                }
//            }
//        });

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

    public ComboBox createComboBox() {
        ComboBox result = new ComboBox();
        result.setFilteringMode(FilteringMode.CONTAINS);

        result.addItem(firstVarField.getValue());
        result.addItem(secondVarField.getValue());
        result.addItem(thirdVarField.getValue());
        result.addItem(forthVarField.getValue());

        result.setWidth("330px");
        result.setNewItemsAllowed(false);
        result.setTextInputAllowed(true);

        return result;
    }

}
