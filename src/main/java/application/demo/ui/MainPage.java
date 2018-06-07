package application.demo.ui;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import application.demo.service.EmployeeService;
import application.demo.ui.layouts.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.Employee;
import application.demo.service.EmployeeModel;
import application.demo.service.SkillDbService;
import application.demo.domain.User;
import application.demo.security.FilterLoginService;
import application.demo.security.ServiceProvider;
import application.demo.ui.layouts.ValoMenuLayout;

@Theme("tests-valo")
@Title("Main Page")
@SpringUI(path = "/mainPage")
public class MainPage extends UI {
	private static final long serialVersionUID = 1L;

	Grid employeesGrid;
	static VerticalLayout container;
	static HorizontalLayout searchFormLayout;
	boolean editing = false;

	Employee selectedEmployee = null;

	User user;
	StreamResource resource;
	StreamSource source;

	boolean showState = false;

	@Autowired
	SkillDbService ss;

	private final class OnClickNavigateTo implements Button.ClickListener {
		private static final long serialVersionUID = 1L;

		private final String name;

		private OnClickNavigateTo(String name) {
			this.name = name;
		}

		public void buttonClick(final ClickEvent event) {
			navigator.navigateTo(name);
		}
	}

	private boolean testMode = false;

	ValoMenuLayout root = new ValoMenuLayout();

	ComponentContainer viewDisplay = root.getContentContainer();
	CssLayout menu = new CssLayout();
	CssLayout menuItemsLayout = new CssLayout();

	{
		menu.setId("testMenu");
	}

	private Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {

		///////// authentification ///////////
		try {
			if (FilterLoginService.loggedUser != null || FilterLoginService.currentEmployee != null) {
				setContent(root);
			} else {
				UI.getCurrent().getPage().setLocation("http://localhost:8080");
			}
		} catch (Exception e) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080");
		}

		if (request.getParameter("test") != null) {
			testMode = true;

			if (browserCantRenderFontsConsistently()) {
				getPage().getStyles().add(".v-app.v-app.v-app { font-family: Sans-Serif; }");
			}
		}

		if (getPage().getWebBrowser().isIE() && getPage().getWebBrowser().getBrowserMajorVersion() == 9) {
			menu.setWidth("320px");
		}

		if (!testMode) {
			Responsive.makeResponsive(this);
		}

		// System.out.println(hashingwithSHA("admin"));
		// System.out.println(hashingwithSHA("user"));

		getPage().setTitle("Demo");

		root.addLeftMenu(buildLeftMenu());

		try {
			if (FilterLoginService.loggedUser.getRole().equals("admin")) {
				root.addRightMenu(buildRightMenu());
			}

		} catch (Exception e) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080");
		}

		addStyleName(ValoTheme.UI_WITH_MENU);

		navigator = new Navigator(this, viewDisplay);

		navigator.addView(SearchView.NAME, SearchView.class);
		navigator.addView(NewsView.NAME, NewsView.class);
		navigator.addView(EmployeeView.NAME, EmployeeView.class);
		navigator.addView(AddEmployeeView.NAME, AddEmployeeView.class);
		navigator.addView(EditEmployeeView.NAME, EditEmployeeView.class);
		navigator.addView(SearchEmployeesBySkillsView.NAME, SearchEmployeesBySkillsView.class);
		navigator.addView(MessageView.NAME, MessageView.class);
		navigator.addView(AddQuestionView.NAME, AddQuestionView.class);
		navigator.addView(QuizView.NAME, QuizView.class);

		final String f = Page.getCurrent().getUriFragment();
		if (f == null || f.equals("")) {
			try {
				if (FilterLoginService.loggedUser.getRole().equals("admin")) {
					System.out.println(FilterLoginService.loggedUser.getRole());
					navigator.navigateTo(SearchView.NAME);

				} else {
					navigator.navigateTo(NewsView.NAME);
				}

			} catch (Exception e) {
				UI.getCurrent().getPage().setLocation("http://localhost:8080");
			}

		}
		navigator.setErrorView(NewsView.class);
		// navigator.navigateTo(NewsView.NAME);

		navigator.addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event) {
				return true;
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event) {
			}
		});
	}

	private boolean browserCantRenderFontsConsistently() {

		return getPage().getWebBrowser().getBrowserApplication().contains("PhantomJS")
				|| (getPage().getWebBrowser().isIE() && getPage().getWebBrowser().getBrowserMajorVersion() <= 9);
	}

	static boolean isTestMode() {
		return ((MainPage) getCurrent()).testMode;
	}

	/////////// HASHING PASSWORD ////////////////////

	public static String hashingwithSHA(String string) {
		String password = new String();
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA-256");
			String text = string;

			md.update(text.getBytes("UTF-8"));
			byte[] digest = md.digest();
			password = "";
			for (byte theByte : digest) {
				password = password + Integer.toHexString(theByte);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return password;
	}

	public Component buildRightMenu() {
		final CssLayout menu = new CssLayout();
		menu.addStyleName("large-icons");

		final Label logo = new Label();
		logo.setContentMode(ContentMode.HTML);
		logo.setValue(FontAwesome.USERS.getHtml());
		logo.setSizeUndefined();
		logo.setPrimaryStyleName("valo-menu-logo");
		menu.addComponent(logo);

		Button b = new Button("Employees");
		b.setIcon(FontAwesome.TABLE);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new OnClickNavigateTo(SearchView.NAME));

		menu.addComponent(b);

		b = new Button("View Employee");
		b.setIcon(FontAwesome.EYE);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (SearchView.employeesGrid.getSelectedRow() != null) {
					Employee employee = (Employee) SearchView.employeesGrid.getSelectedRow();
					getUI().getNavigator().navigateTo(EmployeeView.NAME + "/" + employee.getId());
				} else {
					Notification.show("There must be an employee selected!");
					navigator.navigateTo(SearchView.NAME);
				}
			}
		});

		menu.addComponent(b);

		b = new Button("Add employee");
		b.setIcon(FontAwesome.PLUS_CIRCLE);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.setId(AddEmployeeView.NAME);
		b.addClickListener(new OnClickNavigateTo(AddEmployeeView.NAME));

		menu.addComponent(b);

		b = new Button("Edit employee");
		b.setIcon(FontAwesome.EDIT);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					if (SearchView.employeesGrid.getSelectedRow() != null) {
						Employee employeeToEdit = (Employee) SearchView.employeesGrid.getSelectedRow();

						getUI().getNavigator().navigateTo(EditEmployeeView.NAME + "/" + employeeToEdit.getId());
					} else {
						Notification.show("There must be an employee selected!");
						navigator.navigateTo(SearchView.NAME);
					}

				} catch (NullPointerException e) {
					UI.getCurrent().getPage().setLocation("http://localhost:8080");
				}
			}
		});

		menu.addComponent(b);

		b = new Button("Search by Skills");
		b.setIcon(FontAwesome.SEARCH);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new OnClickNavigateTo(SearchEmployeesBySkillsView.NAME));

		menu.addComponent(b);

		b = new Button("Add Skill");
		b.setIcon(FontAwesome.PLUS_SQUARE);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Window subWindow = new Window("Add New Skill");
				AddSkillPopup popup = new AddSkillPopup();

				UI.getCurrent().addWindow(popup);
			}
		});

		menu.addComponent(b);

		b = new Button("Delete Employee");
		b.setIcon(FontAwesome.SCISSORS);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (SearchView.employeesGrid.getSelectedRow() != null) {
					Employee employeeToDelete = (Employee) SearchView.employeesGrid.getSelectedRow();

					EmployeeService.deleteEmployee(employeeToDelete);

					SearchView.employeesGrid.getContainerDataSource()
							.removeItem(SearchView.employeesGrid.getSelectedRow());
					EmployeeModel.refresh();

				} else {
					Notification.show("There must be an employee selected!");
					navigator.navigateTo(SearchView.NAME);
				}
			}
		});

		menu.addComponent(b);

		return menu;
	}

	public Component buildLeftMenu() {
		final CssLayout menu = new CssLayout();
		menu.addStyleName("valo-menu-items");

		final Label logo = new Label();
		logo.setContentMode(ContentMode.HTML);
		logo.setValue(FontAwesome.USERS.getHtml());
		logo.setSizeUndefined();
		logo.setPrimaryStyleName("valo-menu-logo");
		menu.addComponent(logo);

		Button quiz = new Button("Question");
		quiz.setIcon(FontAwesome.GRADUATION_CAP);
		quiz.setPrimaryStyleName("valo-menu-item");
		quiz.setHtmlContentAllowed(true);
		quiz.addClickListener(new OnClickNavigateTo(AddQuestionView.NAME));
		menu.addComponent(quiz);

		Button statistics = new Button("Statistics");
		statistics.setIcon(FontAwesome.SORT_AMOUNT_DESC);
		statistics.setPrimaryStyleName("valo-menu-item");
		statistics.setHtmlContentAllowed(true);
		statistics.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getPage().setLocation("http://localhost:8080/statistics");
				
			}
		});

		menu.addComponent(statistics);
		
		Button skillsStatistics = new Button("Skills' Statistics");
		skillsStatistics.setIcon(FontAwesome.BAR_CHART_O);
		skillsStatistics.setPrimaryStyleName("valo-menu-item");
		skillsStatistics.setHtmlContentAllowed(true);
		skillsStatistics.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getPage().setLocation("http://localhost:8080/skillStatistics");
				
			}
		});
		
		menu.addComponent(skillsStatistics);
		
		Button profile = new Button("Profile");
		profile.setIcon(FontAwesome.USER);
		profile.setPrimaryStyleName("valo-menu-item");
		profile.setHtmlContentAllowed(true);
		profile.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					getUI().getNavigator()
							.navigateTo(EditEmployeeView.NAME + "/" + FilterLoginService.currentEmployee.getId());
				} catch (NullPointerException e) {
					UI.getCurrent().getPage().setLocation("http://localhost:8080/");
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});

		menu.addComponent(profile);

		Button colleagues = new Button("Colleagues");
		colleagues.setIcon(FontAwesome.USERS);
		colleagues.setPrimaryStyleName("valo-menu-item");
		colleagues.setHtmlContentAllowed(true);
		colleagues.addClickListener(new OnClickNavigateTo(SearchView.NAME));
		menu.addComponent(colleagues);

		Button b = new Button("News Feed");
		b.setIcon(FontAwesome.INFO_CIRCLE);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new OnClickNavigateTo(NewsView.NAME));
		menu.addComponent(b);

		b = new Button("Inbox Messages");
		b.setIcon(FontAwesome.COMMENTS);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new OnClickNavigateTo(MessageView.NAME));
		menu.addComponent(b);

		b = new Button("New Message");
		b.setIcon(FontAwesome.PLUS);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Window subWindow = new Window("New Message");
				MessagePopop popup = new MessagePopop(FilterLoginService.loggedUser);

				UI.getCurrent().addWindow(popup);

			}
		});

		menu.addComponent(b);

		b = new Button("Print CV");
		b.setIcon(FontAwesome.PRINT);
		b.setPrimaryStyleName("valo-menu-item");
		b.setHtmlContentAllowed(true);
		b.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (FilterLoginService.loggedUser.getRole().equals("admin")) {
					if (SearchView.employeesGrid.getSelectedRow() != null) {
						Employee selectedEmployee = (Employee) SearchView.employeesGrid.getSelectedRow();

						SearchView.setPDF(selectedEmployee);
						SearchView.showWindow();
					} else {
						Notification.show("There must be an employee selected!");
						navigator.navigateTo(SearchView.NAME);
					}
				} else {
					SearchView.setPDF(FilterLoginService.currentEmployee);
					SearchView.showWindow();
				}
			}
		});

		menu.addComponent(b);

		Button logOutButton = new Button("Log out");
		logOutButton.setIcon(FontAwesome.ARROW_CIRCLE_O_LEFT);
		logOutButton.setPrimaryStyleName("valo-menu-item");
		logOutButton.setHtmlContentAllowed(true);
		logOutButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("LOG OUT");
				ServiceProvider.getInstance().getLoginService().logOut();
			}
		});
		menu.addComponent(logOutButton);

		try {
			if (FilterLoginService.loggedUser.getRole().equals("admin")) {
				profile.setVisible(false);
				colleagues.setVisible(false);
			} else {
				statistics.setVisible(false);
				skillsStatistics.setVisible(false);
			}
		} catch (Exception e) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080/");
		}

		return menu;
	}
}
