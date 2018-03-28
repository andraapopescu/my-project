package application.demo.ui.layouts.search;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import application.demo.service.EmployeeService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.VerticalLayout;

import application.demo.domain.Employee;
import application.demo.service.EmployeeModel;
import application.demo.ui.components.SkillsPanel;



public class SearchEmployeesBySkillsHelper {

	private static HashMap<Integer, Integer> ValidEmployees = new HashMap<Integer, Integer>();

	private static HashMap<Integer, ArrayList<Integer>> SelectedEmployeesBySkillId = new HashMap<Integer, ArrayList<Integer>>(); // skilldId
	static ArrayList<Integer> remainingEmployees; // employees
	private static int MAXIM = 0;


	public static void getEmployeesBySkillAndValue(int skillId, int value) {
		ArrayList<Integer> idsOfEmployeesBySkill = null;

		idsOfEmployeesBySkill = EmployeeModel.SelectEmployeesBySkillAndValue(skillId, value);

		if (SelectedEmployeesBySkillId.containsKey(skillId) && !SelectedEmployeesBySkillId.get(skillId).isEmpty()) {
			// daca exista deja lista de skiluri , scadem -1 din ValidEmployees
			// pt fiecare angajat
			for (int i = 0; i < SelectedEmployeesBySkillId.get(skillId).size(); i++) {
				if (ValidEmployees.containsKey(SelectedEmployeesBySkillId.get(skillId).get(i))
						&& ValidEmployees.get(SelectedEmployeesBySkillId.get(skillId).get(i)) > 0)
					ValidEmployees.put(SelectedEmployeesBySkillId.get(skillId).get(i),
							ValidEmployees.get(SelectedEmployeesBySkillId.get(skillId).get(i)) - 1);

			}
		}

		// adugam +i la fiecare angajat gasit in lista de skiluri
		if (!idsOfEmployeesBySkill.isEmpty()) {
			for (int i = 0; i < idsOfEmployeesBySkill.size(); i++) {
				if (ValidEmployees.containsKey(idsOfEmployeesBySkill.get(i))) {
					ValidEmployees.put(idsOfEmployeesBySkill.get(i),
							ValidEmployees.get(idsOfEmployeesBySkill.get(i)) + 1);

				} else
					ValidEmployees.put(idsOfEmployeesBySkill.get(i), 1);


			}
		}

		SelectedEmployeesBySkillId.put(skillId, idsOfEmployeesBySkill);
		MAXIM = SelectedEmployeesBySkillId.size();

		remainingEmployees = new ArrayList<Integer>();
		Iterator it = ValidEmployees.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (Integer.parseInt(pair.getValue().toString()) == MAXIM) {
				remainingEmployees.add(Integer.parseInt(pair.getKey().toString()));
			}
		}
	}

	public static BeanItemContainer<Employee> getGridSource() {
		BeanItemContainer<Employee> result = new BeanItemContainer<Employee>(Employee.class);
		ArrayList<Employee> list = EmployeeService.getMultipleEmployees(remainingEmployees);

		if (!list.isEmpty())
			result.addAll(list);

		return result;

	}

	public static ArrayList<SkillsPanel> createContent() {
		 ArrayList<SkillsPanel> result = new  ArrayList<SkillsPanel>();
		ArrayList<Employee> list = EmployeeModel.getMultipleEmployees(remainingEmployees);
		
		if(!list.isEmpty() && list != null)
		for (Employee e : list) {
			SkillsPanel s = new SkillsPanel(e);
			result.add(s);
		}

		return result;

	}
	public static VerticalLayout createContentAllEmployees() {
		VerticalLayout result = new VerticalLayout();
		ArrayList<Employee> list = EmployeeModel.getAllEmployees();
		for (Employee e : list) {
			SkillsPanel s = new SkillsPanel(e);
			result.addComponent(s);
		}

		return result;

	}
}
