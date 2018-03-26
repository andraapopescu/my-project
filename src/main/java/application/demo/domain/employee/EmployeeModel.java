package application.demo.domain.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import application.demo.domain.employee_skill.EmployeeSkill;
import application.demo.rest.RestConsumer;
import application.demo.security.FilterLoginService;

public class EmployeeModel {

	public static ArrayList<Employee> allEmployees = RestConsumer.getAllEmployees();
	public static HashMap<Integer, Employee> employeesMap = new HashMap<Integer, Employee>();
	public static ArrayList<EmployeeSkill> allEmployeeSkills = RestConsumer.getAllEmployeeSkills();

	static {
		if (allEmployees != null)
			for (Employee e : allEmployees) {
				employeesMap.put((int) e.getId(), e);
			}
	}

	public static void refresh() {
		allEmployees = RestConsumer.getAllEmployees();
		allEmployeeSkills = RestConsumer.getAllEmployeeSkills();

		if (allEmployees != null)
			for (Employee e : allEmployees) {
				employeesMap.put((int) e.getId(), e);
			}
	}

	public static ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> result = new ArrayList<Employee>();
		for (Employee e : allEmployees) {
			if (!e.getFirstName().equals("admin")) {
				result.add(e);
			}
		}

		return result;
	}

	public static ArrayList<Employee> getAllColleagues() {
		ArrayList<Employee> result = new ArrayList<Employee>();
		Employee selectedEmployee = FilterLoginService.currentEmployee;

		if (selectedEmployee != null) {
			for (Employee e : allEmployees) {
				if ((e.getId() != selectedEmployee.getId()) && !(e.getFirstName().equals("admin"))) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public static ArrayList<Employee> getEmployeesByPhoneNumber(String phone) {
		ArrayList<Employee> result = new ArrayList<Employee>();

		phone = phone.trim();

		for (Employee e : allEmployees) {
			if (e.getPhone() != null) {
				if (e.getPhone().contains(phone)) {
					System.out.println(e.getFirstName() + "  " + e.getPhone());
					result.add(e);
				}
			}
		}

		return result;

	}

	public static ArrayList<Employee> getEmployeesByEmail(String email) {
		ArrayList<Employee> result = new ArrayList<Employee>();

		email = email.toLowerCase().trim();

		for (Employee e : allEmployees) {
			if (e.getEmail().toLowerCase().contains(email)) {
				result.add(e);
			}
		}

		return result;
	}

	public static ArrayList<Employee> getEmployeesByName(String name) {

		String[] words = name.split("[ ]+");
		for (int i = 0; i < words.length; i++) {
			words[i].toLowerCase();
		}

		ArrayList<Employee> result = new ArrayList<Employee>();
		name = name.toLowerCase().trim();

		for (Employee e : allEmployees) {

			for (String word : words) {
				if (e.getFirstName().toLowerCase().trim().contains(word)
						|| e.getLastName().trim().toLowerCase().contains(word)
						|| e.getEmail().trim().toLowerCase().contains(word)) {
					if (!result.contains(e)) {
						result.add(e);
					}
				}
			}
		}

		return result;
	}

	public static boolean isNull() {
		if (allEmployees == null)
			return true;
		else
			return false;
	}

	public static ArrayList<Employee> getMultipleEmployees(ArrayList<Integer> remainingEmployees) {
		ArrayList<Employee> result = new ArrayList<Employee>();

		for (Integer i : remainingEmployees) {
			result.add(employeesMap.get(i));
		}

		return result;
	}

	public static ArrayList<Integer> SelectEmployeesBySkillAndValue(int skillId, int value) {

		ArrayList<Integer> result = new ArrayList<Integer>();
		HashSet<Integer> set = new HashSet<Integer>();
		if (value == 0) {
			for (EmployeeSkill e : allEmployeeSkills) {
				set.add((int) e.getEmployee().getId());
			}

			result.addAll(set);

			return result;
		}

		for (EmployeeSkill e : allEmployeeSkills) {

			if (e.getSkill().getId() == skillId && e.getLevel() >= value)
				result.add((int) e.getEmployee().getId());
		}

		return result;
	}

}
