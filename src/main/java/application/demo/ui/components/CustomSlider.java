package application.demo.ui.components;

import com.vaadin.data.Property;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Slider;

public class CustomSlider extends CustomComponent {
	private static final long serialVersionUID = 1L;
	
	Slider slider;
	String name;
	double value = 0.0;
	int skillId;

	public CustomSlider(String caption, int i ) {
		this.skillId = i;
		this.name = caption;
		slider = new Slider(caption);
		slider.setValue(0.0);
		slider.setHeight("200px");
		slider.setMax(10);
		slider.addStyleName("ticks");
		slider.setOrientation(SliderOrientation.VERTICAL);

		slider.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				value = (Double) event.getProperty().getValue();
				slider.setCaption(name + " " + String.valueOf(value));
				
			}

		});

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setWidth("100%");
		hl.addComponent(slider);
		hl.setComponentAlignment(slider, Alignment.MIDDLE_CENTER);

		setCompositionRoot(hl);
	}

	public Slider getSlider() {
		return slider;
	}

	public void setSlider(Slider slider) {
		this.slider = slider;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public double getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public void setValue(double value) {
		this.value = value;
	}
	

}
