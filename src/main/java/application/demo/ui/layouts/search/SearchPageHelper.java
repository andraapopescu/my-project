package application.demo.ui.layouts.search;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import application.demo.domain.employee.Employee;
import application.demo.domain.employee.EmployeeModel;
import application.demo.rest.RestConsumer;
import application.demo.security.FilterLoginService;
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
		
//		Employee e = FilterLoginService.currentEmployee;
//		
//		List<Employee> list = RestConsumer.getAllEmployees();
//		System.err.println(list.size() + " 1");
//		
//		BeanItemContainer<Employee> container = new BeanItemContainer<>(Employee.class, list);
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

//	private static Grid setColumnFiltering(boolean filtered, Grid result) {
//		HeaderRow filteringHeader = null;
//		if (filtered && filteringHeader == null) {
//			filteringHeader = result.appendHeaderRow();
//			List<Column> arr = result.getColumns();
//			TextField[] filterFields = new TextField[7];
//
//			for (int i = 0; i < arr.size(); i++) {
//				String columnId = arr.get(i).getPropertyId().toString();
//				filterFields[i] = getColumnFilter(columnId, result);
//				filteringHeader.getCell(columnId).setComponent(filterFields[i]);
//				filteringHeader.getCell(columnId).setStyleName("filter-header");
//			}
//		} else if (!filtered && filteringHeader != null) {
//			result.removeHeaderRow(filteringHeader);
//			filteringHeader = null;
//		}
//		return result;
//	}

//	private static TextField getColumnFilter(final Object columnId, final Grid emp) {
//		TextField result = new TextField();
//		result.setWidth("100%");
//		result.addStyleName(ValoTheme.TEXTFIELD_TINY);
//		result.setInputPrompt("Filter");
//		result.addTextChangeListener(new TextChangeListener() {
//			private static final long serialVersionUID = 1L;
//			
//			SimpleStringFilter filter = null;
//
//			@Override
//			public void textChange(TextChangeEvent event) {
//				Filterable f = (Filterable) emp.getContainerDataSource();
//				if (filter != null) {
//					f.removeContainerFilter(filter);
//				}
//				filter = new SimpleStringFilter(columnId, event.getText(), true, true);
//				f.addContainerFilter(filter);
//				emp.cancelEditor();
//			}
//		});
//
//		return result;
//	}
	


}
