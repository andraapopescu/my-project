package application.demo.service;

import application.demo.domain.Employee;
import application.demo.domain.EmployeeSkill;
import application.demo.domain.HistoryEmployeeSkill;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import static application.demo.service.EmployeeSkillService.deleteEmployeeSkillsById;
import static application.demo.service.EmployeeSkillService.getEmployeeSkillByEmployee;
import static application.demo.service.EmployeeSkillService.getSkillHistoryByEmployee;
import static application.demo.service.HistoryEmployeeSkillService.deleteHistoryById;

public class EmployeeService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

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

    public static void deleteEmployee(Employee employee) {
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<EmployeeSkill> employeeSkills = getEmployeeSkillByEmployee(employee.getId());
        ArrayList<HistoryEmployeeSkill> historyEmployeeSkills = getSkillHistoryByEmployee(employee.getId());

        for(HistoryEmployeeSkill hes : historyEmployeeSkills) {
            deleteHistoryById(hes.getId());
        }

        for(EmployeeSkill es : employeeSkills) {
            deleteEmployeeSkillsById(es.getId());
        }

        restTemplate.delete(REST_SERVICE_URI + "/employee/deleteEmployee/" + employee.getId());
    }

}
