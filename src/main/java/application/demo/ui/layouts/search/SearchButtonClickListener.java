package application.demo.ui.layouts.search;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;

import application.demo.domain.Employee;
import application.demo.ui.layouts.view.SearchView;

public class SearchButtonClickListener implements Button.ClickListener {
	private static final long serialVersionUID = 1L;
	
	private final TextField searchBox;
	private SearchView parent;

	SearchButtonClickListener(TextField searchBox, SearchView parent) {
		this.searchBox = searchBox;
		this.parent = parent;

	}

	@Override
	public void buttonClick(Button.ClickEvent event) {
		String inputText = searchBox.getValue().trim();

		BeanItemContainer<Employee> bic;
		if (inputText.isEmpty()) {
			bic = SearchPageHelper.CreateBeanContainerAllEmployees();
			SearchView.employeesGrid.setContainerDataSource(bic);

		} else if (inputText.contains("@")) {
			bic = SearchPageHelper.CreateBeanContainerByEmail(inputText);
			SearchView.employeesGrid.setContainerDataSource(bic);

		} else if (inputText.matches("[0-9]+")) {
			bic = SearchPageHelper.CreateBeanContainerByPhone(inputText);
			SearchView.employeesGrid.setContainerDataSource(bic);

		} else {
			bic = SearchPageHelper.CreateBeanContainerByName(inputText);
			SearchView.employeesGrid.setContainerDataSource(bic);

		}

		if (!parent.editing) {
			parent.setData(bic);
		}

	}

}
