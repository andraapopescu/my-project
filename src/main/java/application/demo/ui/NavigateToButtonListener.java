package application.demo.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;

public class NavigateToButtonListener implements Button.ClickListener {
	private static final long serialVersionUID = 1L;
	
	private String view;
	private Navigator navigator;

	public NavigateToButtonListener(String view, Navigator navigator) {
		this.view = view;
		this.navigator = navigator;

	}

	@Override
	public void buttonClick(Button.ClickEvent event) {
		navigator.navigateTo(view);
	}
}
