package application.demo.ui.components;

import com.vaadin.data.Property;

import application.demo.ui.layouts.search.SearchEmployeesBySkillsHelper;
import application.demo.ui.layouts.view.SearchEmployeesBySkillsView;

public class CustomSliderSearch extends CustomSlider {
	private static final long serialVersionUID = 1L;

	private SearchEmployeesBySkillsView parent;

	public CustomSliderSearch(String caption, int i, SearchEmployeesBySkillsView p) {
		super(caption, i);
		this.parent = p;
		slider.setHeight("150px");
		slider.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				value = (Double) event.getProperty().getValue();
				
				SearchEmployeesBySkillsHelper.getEmployeesBySkillAndValue(skillId,
						((Double) event.getProperty().getValue()).intValue());
				
				parent.update();
			}
		});
	}
}
