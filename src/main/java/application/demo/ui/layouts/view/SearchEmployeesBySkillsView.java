package application.demo.ui.layouts.view;

import java.util.ArrayList;
import java.util.Random;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import application.demo.domain.employee.EmployeeModel;
import application.demo.domain.skills.Skill;
import application.demo.rest.RestConsumer;
import application.demo.ui.components.CustomSlider;
import application.demo.ui.components.CustomSliderSearch;
import application.demo.ui.components.SkillsPanel;
import application.demo.ui.layouts.search.SearchEmployeesBySkillsHelper;
import application.demo.ui.login.LoginPageCssHelper;

public class SearchEmployeesBySkillsView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "searchEmployeesBySkills";
	VerticalLayout content;

	public SearchEmployeesBySkillsView() {

		if (EmployeeModel.isNull()) {
			return;
		}

		HorizontalLayout header = new HorizontalLayout();
		header.setSpacing(true);

		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills = RestConsumer.getAllSkills();

		for (Skill s : skills) {
			CustomSliderSearch slider = new CustomSliderSearch(s.getName(), (int) s.getId(), this);
			slider.addStyleName("sslider");
			header.addComponent(slider);
		}

//		ArrayList<String> colors = new ArrayList<String>(skills.size());
//		Random rand = new Random();
		
		for (int i = 0; i < skills.size(); i++) {

			int R = (int) (Math.random() * 256 * 0.7);
			int G = (int) (Math.random() * 256 * 0.7);
			int B = (int) (Math.random() * 256 * 0.7);

			String c = R + "," + G + "," + B;

			int R2 = (int) (R + (0.5 * (255 - R)));
			int G2 = (int) (G + (0.5 * (255 - G)));
			int B2 = (int) (B + (0.5 * (255 - B)));

			String c2 = R2 + "," + G2 + "," + B2;

			String skillN = skills.get(i).getName();

			if (skillN.contains("+")) {
				skillN = "cplusplus";
			}

			Page.getCurrent().getStyles().add(LoginPageCssHelper.createStyleForSkillLabels(skillN, c, c2));
		}

		Page.getCurrent().getStyles().add(LoginPageCssHelper.createStyleForSliders());

		addComponent(header);
		setComponentAlignment(header, Alignment.MIDDLE_CENTER);
		
		content = SearchEmployeesBySkillsHelper.createContentAllEmployees();

		Panel p = new Panel();
		p.setSizeFull();
		p.setHeight("400px");
		// addComponent(content);
		p.setContent(content);
		addComponent(p);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (EmployeeModel.isNull()) {
			event.getNavigator().navigateTo(AddEmployeeView.NAME);
		}
	}

	public void update() {
		content.removeAllComponents();

		ArrayList<SkillsPanel> panels = new ArrayList<SkillsPanel>();
		panels = SearchEmployeesBySkillsHelper.createContent();

		for (SkillsPanel s : panels) {
			content.addComponent(s);
		}
	}
}
