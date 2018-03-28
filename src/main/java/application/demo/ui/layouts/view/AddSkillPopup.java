package application.demo.ui.layouts.view;

import java.util.ArrayList;

import application.demo.service.SkillService;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.Skill;

public class AddSkillPopup extends Window {
	private static final long serialVersionUID = 1L;
	private VerticalLayout vLayout = new VerticalLayout();
	private TextField skillNameField;

	private Button addButton = new Button("Add");
	private Skill skill = new Skill();
	private ComboBox comboBox;
	private ArrayList<String> skills = getSkillsNames();
	private Button deleteButton = new Button("Delete");
	
	
	public AddSkillPopup() {
		comboBox = createComboBox();
		skillNameField = new TextField();	
		
		Label text = new Label("Existing skill's list: ");
		text.addStyleName("h2");
		text.addStyleName("colored");
		
		vLayout.addComponent(text);
		
		comboBox = createComboBox();
		vLayout.addComponent(comboBox);
		
		deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
//		verifyComboboxItems();
		
		deleteButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if(comboBox.getValue().toString().isEmpty()) {
					Notification.show("Select a skill from the list to delete!");
				} else {
					deleteButton.setEnabled(true);
					Skill skill = SkillService.findSkillByName(comboBox.getValue().toString()).get(0);
					SkillService.deleteSkill(skill.getId());
					
					comboBox.removeItem(comboBox.getValue());
				}
			}
		});
		
		vLayout.addComponent(deleteButton);
		
		text = new Label("New Skill: ");
		text.addStyleName("h2");
		text.addStyleName("colored");
		
		vLayout.addComponent(text);
		vLayout.addComponent(skillNameField);
		
		addButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(!skillNameField.getValue().isEmpty()) {
					if(skills.contains(skillNameField.getValue()))	{
						Notification.show("The skill you introduces is already in the list!");
					} else {
						skill.setName(skillNameField.getValue());
						SkillService.saveSkill(skill);
						
						comboBox.addItem(skill.getName());
						skills.add(skill.getName());
						skillNameField.setValue("");;
					}
				} else {
					Notification.show("Your input is null");
				}
		
			}
		});
		
		addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		addButton.addStyleName(ValoTheme.BUTTON_SMALL);
		vLayout.addComponent(addButton);
		
		vLayout.setSpacing(true);
		vLayout.setMargin(true);
		
		setWidth("500px");
		setHeight("400px");
		
		
		setContent(vLayout);
		center();
		
		
	}
	
	public ComboBox createComboBox() {
		ComboBox result = new ComboBox();
		result.setFilteringMode(FilteringMode.CONTAINS);
		
		result.addItems(skills);
		
		result.setWidth("330px");
		result.setNewItemsAllowed(true);
		result.setTextInputAllowed(false);

		return result;
	}
	
	public ArrayList<String> getSkillsNames() {
		ArrayList<String> result = new ArrayList<>();
		for(Skill s : SkillService.getAllSkills()) {
			result.add(s.getName());
		}
		
		return result;
	}

	public void verifyComboboxItems() {
		if(!comboBox.getValue().toString().isEmpty()) {
			deleteButton.setEnabled(true);
		} else {
			deleteButton.setEnabled(false);
		}
	}
}
