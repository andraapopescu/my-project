package application.demo.ui;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import application.demo.ui.login.LoginPageHelper;

@SpringUI
@Theme("valo")
@DesignRoot
public class LoginPage extends UI implements View {
	private static final long serialVersionUID = 1L;
	
		public static final String NAME = "firstPage";
		private VerticalLayout container;
		private VerticalLayout layout;

		@Override
		public void enter(ViewChangeEvent event) {

		}

		@Override
		protected void init(VaadinRequest request) {
			container = LoginPageHelper.createLoginContainerLayout();
			layout = LoginPageHelper.createLoginPageLayout();

			layout.addComponent(container);
			layout.setComponentAlignment(container, Alignment.TOP_CENTER);

			setContent(layout);

			LoginPageHelper.addCss(Page.getCurrent().getStyles());

		}

	}


