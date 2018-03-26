package application.demo.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.demo.domain.employee.Employee;
import application.demo.domain.employee_skill.EmployeeSkill;
import application.demo.domain.history_employee_skill.HistoryEmployeeSkill;
import application.demo.domain.message.Message;
import application.demo.domain.news.News;
import application.demo.domain.skills.Skill;
import application.demo.domain.user.User;

public class RestConsumer {
	public static String REST_SERVICE_URI = "http://localhost:8080";
	public static ObjectMapper mapper = new ObjectMapper();
	
	/* EMPLOYEE - GET */
	public static ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> result = null;
		URL u;
		try {
			u = new URL("http://localhost:8080/employee/all");
			result = mapper.readValue(u, 
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	public static Employee getEmployeeById(long id) {
		Employee result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/employee/" + id);
			result = mapper.readValue(u, Employee.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<Employee> getMultipleEmployees(ArrayList<Integer> ids){
		 RestTemplate restTemplate = new RestTemplate();

		 ArrayList<Employee> result = new ArrayList<Employee>();
		 
		 Employee[] emp = restTemplate.postForObject(REST_SERVICE_URI + "/employee/getMultipleEmployees/", ids, Employee[].class);
		 
		 for(Employee e: emp){
			 result.add(e);
		 }
		 
		 return result;
		 
	 }
	
	public static ArrayList<Employee> findEmployeeByEmail(String email) {
		ArrayList<Employee> result = null;
		
		URL u;
		try {
			u = new URL(REST_SERVICE_URI + "/employee/email/" + "?email=" + email);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<User> findUserByUsername(String username) {
		ArrayList<User> result = null;
		
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/user/username/" + "?username=" + username);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<Employee> findEmployeeByLastName(String lastName) {
		ArrayList<Employee> result = null;
		
		URL u;
		try {
			u = new URL(REST_SERVICE_URI + "/employee/lastName/" + lastName);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	/* USER - GET */
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> result = null;
		URL u;

		try {
			u = new URL("http://localhost:8080/user/all");
			result = mapper.readValue(u, mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static User getUserById(long id) {
		User result = null;
		URL u;

		try {
			u = new URL("http://localhost:8080/user/" + id);
			result = mapper.readValue(u,User.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/* SKILL - GET */
	public static ArrayList<Skill> getAllSkills() {
		ArrayList<Skill> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/skill/all");
			result = mapper.readValue(u, 
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Skill.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Skill getSkillById(long id) {
		Skill result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/skill/" + id);
			result = mapper.readValue(u, Skill.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<Skill> findSkillByName(String name) {
		ArrayList<Skill> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/skill/name/" + name);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Skill.class));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/*	EMPLOYEE_SKILL - GET */
	public static ArrayList<EmployeeSkill> getEmployeeSkillByEmployee(long id) {
		ArrayList<EmployeeSkill> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/employeeSkills/employee/" + id);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, EmployeeSkill.class));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<EmployeeSkill> getEmployeeSkillBySkill(String skillName) {
		ArrayList<EmployeeSkill> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/employeeSkills/skill/" + skillName);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, EmployeeSkill.class));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<HistoryEmployeeSkill> getSkillHistoryByEmployee(long id) {
		ArrayList<HistoryEmployeeSkill> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/history/employee/" + id);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, HistoryEmployeeSkill.class));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ArrayList<EmployeeSkill> getEmployeeSkillById(long id) {
		ArrayList<EmployeeSkill> result = null;
		URL u;
		try {
			u = new URL(REST_SERVICE_URI + "/employeeSkills/" + id);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, EmployeeSkill.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	public static ArrayList<HistoryEmployeeSkill> getHistoryEmployeeSkillById(long id) {
		ArrayList<HistoryEmployeeSkill> result = null;
		URL u;
		try {
			u = new URL(REST_SERVICE_URI + "/history/employee/" + id);
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, HistoryEmployeeSkill.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}
	
	public static ArrayList<EmployeeSkill> getAllEmployeeSkills() {
		ArrayList<EmployeeSkill> result = new ArrayList<EmployeeSkill>();
		URL u;
		try {
			u = new URL(REST_SERVICE_URI + "/employeeSkills/all");
			result = mapper.readValue(u,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, EmployeeSkill.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/* MESSAGE - GET */
	public static ArrayList<Message> getAllMessages() {
		ArrayList<Message> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/message/all");
			result = mapper.readValue(u, 
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, Message.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public static ArrayList<Message> getMessageByEmployee(long id) {
		ArrayList<Message> result = null;
		
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/message/employee/" + id);
			result = mapper.readValue(u, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Message.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Message getMessageById(long id) {
		Message result = null;
		URL u;
		
			try {
				u = new URL(REST_SERVICE_URI + "/message/" + id);
				result = mapper.readValue(u, Message.class);
			
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
	}
			
	/* News - GET */
	public static ArrayList<News> getAllNews() {
		ArrayList<News> result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/news/all");
			 result = mapper.readValue(u, mapper.getTypeFactory().
					 constructCollectionType(ArrayList.class, News.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static News getNewsById(long id) {
		News result = null;
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/news/" + id);
			result = mapper.readValue(u, News.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public static ArrayList<News> getNewsByEmployee(long id) {
		ArrayList<News> result = null;
		
		URL u;
		
		try {
			u = new URL(REST_SERVICE_URI + "/news/employee/" + id);
			result = mapper.readValue(u, mapper.getTypeFactory().
					constructCollectionType(ArrayList.class, News.class));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* POST */
	public static Employee saveEmployee(Employee employee) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/employee/addEmployee", 
				employee, Employee.class);
						
		Employee result = new Employee();
		URL u;
		
		try {
			u = new URL(uri.toASCIIString());
			
			result = mapper.readValue(u, Employee.class);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void updateEmployee(Employee employee) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(REST_SERVICE_URI + "/employee/updateEmployee/", employee);
	}
	
	public static void updateUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(REST_SERVICE_URI + "/user/updateUser/", user);
	}
	
	public static User saveUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/saveUser", user, User.class);

		User result = null;
		URL u;

		try {
			u = new URL(uri.toASCIIString());
			result = mapper.readValue(u,  User.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static Skill saveSkill(Skill skill) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/skill/addSkill", 
				skill, Skill.class);
		
		Skill result = null;
		URL u;
		
		try {
			u = new URL(uri.toASCIIString());
			result = mapper.readValue(u, Skill.class);		
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	public static EmployeeSkill saveEmployeeSkill(EmployeeSkill employeeSkill) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/employeeSkills/saveEmployeeSkill", 
				employeeSkill, EmployeeSkill.class);
						
		EmployeeSkill result = new EmployeeSkill();
		URL u;
		
		try {
			u = new URL(uri.toASCIIString());
			
			result = mapper.readValue(u, EmployeeSkill.class);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static HistoryEmployeeSkill saveHistoryEmployeeSkill(HistoryEmployeeSkill history) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/history/saveHistory", 
				history, HistoryEmployeeSkill.class);
						
		HistoryEmployeeSkill result = new HistoryEmployeeSkill();
		URL u;
		
		try {
			u = new URL(uri.toASCIIString());
			
			result = mapper.readValue(u, HistoryEmployeeSkill.class);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Message saveMessage(Message message) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/message/save", 
				message, Message.class);
						
		Message result = new Message();
		URL u;
		
		try {
			u = new URL(uri.toASCIIString());
			
			result = mapper.readValue(u, Message.class);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;

	}

	public static News saveNews(News news) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/news/saveNews", 
				news, News.class);
						
		News result = new News();
		URL u;
		
		try {
			u = new URL(uri.toASCIIString());
			result = mapper.readValue(u, News.class);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/* delete */
	public static void deleteSkill(long id) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/skill/deleteSkill/" + id);
	}
	
	public static void deleteEmployeeSkillsById(long id) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/employeeSkills/deleteEmployeeSkillsById/" + id);
	}
	
	public static void deleteHistoryById(long id) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/history/deleteHistory/" + id);
	}
	
	public static void deleteEmployee(Employee employee) {
		RestTemplate restTemplate = new RestTemplate();
		
		ArrayList<EmployeeSkill> employeeSkills = getEmployeeSkillByEmployee(employee.getId());
		
		for(EmployeeSkill es : employeeSkills) {
			deleteEmployeeSkillsById(es.getId());
		}
		
		restTemplate.delete(REST_SERVICE_URI + "/employee/deleteEmployee/" + employee.getId());
	}

}
