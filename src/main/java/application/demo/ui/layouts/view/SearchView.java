package application.demo.ui.layouts.view;

import application.demo.service.EmployeeService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.Employee;
import application.demo.service.EmployeeModel;
import application.demo.service.EmployeeSkillDbService;
import application.demo.security.FilterLoginService;
import application.demo.ui.components.Pdf;
import application.demo.ui.layouts.SecondMenuLayout;
import application.demo.ui.layouts.search.SearchPageHelper;

public class SearchView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public final static String NAME = "searchPage";

	public static Grid employeesGrid;
	private VerticalLayout container;
	private HorizontalLayout searchFormLayout;
	public boolean editing = false;

	private static Employee selectedEmployee = null;
	private static StreamResource resource;
	private static StreamSource source;

	private static Window pdfWindow;

	boolean showState = false;

	EmployeeSkillDbService ess;

	SecondMenuLayout root = new SecondMenuLayout();
	ComponentContainer viewDisplay = root.getContentContainer();

	public SearchView() {
		setSizeFull();
		addStyleName(ValoTheme.UI_WITH_MENU);
		addComponent(root);

		try {
			if (EmployeeModel.isNull()) {
				UI.getCurrent().getNavigator().navigateTo(AddEmployeeView.NAME);
				employeesGrid = new Grid();
				
			} else if(FilterLoginService.loggedUser.getRole().equals("admin")) {
				employeesGrid = SearchPageHelper.createEmployeesGrid();
			} else {
				employeesGrid = SearchPageHelper.createEmployeeGridForUser();
			}
			
			
			searchFormLayout = SearchPageHelper.createSearchFormLayout(this);
			
			container = new VerticalLayout();
			container.setMargin(true);
			container.setSizeFull();
			
			employeesGrid.setSelectionMode(SelectionMode.SINGLE);
			container.addComponents(searchFormLayout, employeesGrid);
			container.setExpandRatio(employeesGrid, 1);

			viewDisplay.addComponent(container);

			// addComponent(container);

		} catch(NullPointerException e) {
			UI.getCurrent().getPage().setLocation("http://localhost:8080");
		}
	
	}


	public static void setPDF(Employee employee) {
		selectedEmployee = employee;
		
		source = new Pdf(selectedEmployee);
		resource = new StreamResource(source, selectedEmployee.getFirstName() 
				+ System.currentTimeMillis() + ".pdf");
	}

	public void endEdit(Employee e) {
		int Selectedindex = employeesGrid.getContainerDataSource().indexOfId(employeesGrid.getSelectedRow());

		employeesGrid.getContainerDataSource().removeItem(employeesGrid.getSelectedRow());
		employeesGrid.getContainerDataSource().addItemAt(Selectedindex, e);

		EmployeeService.updateEmployee(e);
		EmployeeModel.refresh();
	}

	public static void showWindow() {
		pdfWindow = new Window("Print CV");
		pdfWindow.setIcon(FontAwesome.FILE_PDF_O);
		pdfWindow.setWidth("70%");
		pdfWindow.setHeight("90%");
		pdfWindow.setResizable(false);
		pdfWindow.setDraggable(true);
		pdfWindow.setModal(true);
		pdfWindow.setCloseShortcut(KeyCode.ESCAPE, null);

		BrowserFrame embedded = new BrowserFrame();
		embedded.setWidth("100%");
		embedded.setHeight("99%");
		resource.setMIMEType("application/pdf");

		embedded.setSource(resource);
		pdfWindow.setContent(embedded);
		UI.getCurrent().addWindow(pdfWindow);
	}

	public Grid getEmployeesGrid() {
		return employeesGrid;
	}


	public void setDataSource(BeanItemContainer<Employee> bic) {
		employeesGrid.setContainerDataSource(bic);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
