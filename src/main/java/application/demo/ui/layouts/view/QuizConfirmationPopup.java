package application.demo.ui.layouts.view;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class QuizConfirmationPopup extends Window {
	private static final long serialVersionUID = 1L;

	VerticalLayout vLayout = new VerticalLayout();

	public QuizConfirmationPopup() {

		vLayout = createLayout();
		vLayout.setMargin(true);
		vLayout.setSpacing(true);

		this.setContent(vLayout);

		this.setHeight("200px");
		this.setWidth("900px");

		this.center();
	}

	private VerticalLayout createLayout() {
		VerticalLayout result = new VerticalLayout();
		result.addComponent(new Label());

		Label textArea = new Label();
		textArea.setValue("You have just submitted your quiz. " +
				"Your answers are already saved and you will receive later a feedback about your score!");
		textArea.addStyleName("h2");
		textArea.addStyleName("colored");
		textArea.addStyleName("bold");
		textArea.setSizeFull();
		textArea.addStyleName(ValoTheme.TEXTFIELD_ALIGN_CENTER);
		result.addComponent(textArea);
		result.setComponentAlignment(textArea, Alignment.MIDDLE_CENTER);

		result.setSpacing(true);
		result.setMargin(true);

		return result;
	}

}
