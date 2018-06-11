package application.demo.ui.layouts.search;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import application.demo.domain.Employee;
import application.demo.service.EmployeeModel;
import application.demo.ui.layouts.view.SearchView;


public class SearchPageHelper {
	public static TextField searchBox;

	public static HorizontalLayout createSearchFormLayout(SearchView parent) {
		searchBox = new TextField();
		
		HorizontalLayout result = new HorizontalLayout();
		
		Button searchButton = new Button("search");
		searchButton.setIcon(FontAwesome.SEARCH);
		searchButton.setClickShortcut(KeyCode.ENTER);
		
		searchButton.addClickListener(new SearchButtonClickListener(searchBox, parent));

		searchBox.setInputPrompt("Search for employee data");
		searchBox.setWidth("100%");

		result.setWidth("100%");
		result.addComponents(searchBox, searchButton);
		result.setExpandRatio(searchBox, 1);
		result.setHeight("55px");
		result.setSpacing(true);
		
		return result;
	}

	public static Grid createEmployeesGrid() {
		Grid result = new Grid();

		result.setContainerDataSource(CreateBeanContainerAllEmployees());
		result.setSizeFull();
		result.getColumn("id").setWidth(80);
		result.setColumnOrder("id", "firstName", "lastName","email", "phone");
		
		for (Column c : result.getColumns()) {
			c.setHidable(true);
		}
		
		result.getColumn("birthday").setHidden(true);
		result.getColumn("salary").setHidden(true);
		result.getColumn("employmentDate").setHidden(true);
		
		return prelucrateEmployeeGrid(result);
		
	}
	
	public static Grid createEmployeeGridForUser() {
		Grid result = new Grid();

		result.setContainerDataSource(CreateBeanContainerAllColleagues());
		result.setSizeFull();
		result.getColumn("id").setWidth(80);
		result.setColumns("firstName", "lastName","email", "phone", "birthday");
		
		for (Column c : result.getColumns()) {
			c.setHidable(true);
		}
		
		result.getColumn("birthday").setHidden(true);
		
		return result;
	}

	public static BeanItemContainer<Employee> CreateBeanContainerByPhone(String phone) {
		BeanItemContainer<Employee> result = new BeanItemContainer<Employee>(Employee.class);
		result.addAll(EmployeeModel.getEmployeesByPhoneNumber(phone));

		return result;

	}

	public static BeanItemContainer<Employee> CreateBeanContainerAllEmployees() {
		BeanItemContainer<Employee> result = new BeanItemContainer<Employee>(Employee.class);
		result.addAll(EmployeeModel.getAllEmployees());

		return result;

	}
	
	public static BeanItemContainer<Employee> CreateBeanContainerAllColleagues() {
		BeanItemContainer<Employee> result = new BeanItemContainer<Employee>(Employee.class);
		result.addAll(EmployeeModel.getAllColleagues());

		return result;
	}

	public static BeanItemContainer<Employee> CreateBeanContainerByEmail(String email) {
		BeanItemContainer<Employee> result = new BeanItemContainer<Employee>(Employee.class);
		result.addAll(EmployeeModel.getEmployeesByEmail(email));

		return result;

	}

	public static BeanItemContainer<Employee> CreateBeanContainerByName(String name) {
		BeanItemContainer<Employee> result = new BeanItemContainer<Employee>(Employee.class);
		result.addAll(EmployeeModel.getEmployeesByName(name));

		return result;

	}

	private static Grid prelucrateEmployeeGrid(Grid result) {
		result.setColumnReorderingAllowed(true);
		
		for (Column c : result.getColumns()) {
			c.setHidable(true);
		}
		
		result.getColumn("address").setHidden(true);
		result.getColumn("birthday").setHidden(true);
		result.getColumn("employmentDate").setHidden(true);
		result.getColumn("salary").setHidden(true);
		result.getColumn("id").setHidden(true);

		return result;
	}

}
