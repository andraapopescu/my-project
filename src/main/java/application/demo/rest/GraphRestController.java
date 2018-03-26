package application.demo.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.demo.domain.employee_skill.EmployeeSkill;
import application.demo.domain.history_employee_skill.HistoryEmployeeSkill;
import application.demo.domain.skills.Skill;
import ch.qos.logback.access.pattern.RequestMethodConverter;

@RestController
@RequestMapping("/getData")
public class GraphRestController {

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	private String getData(@PathVariable("id") long id) {
		String results = "[ ";

		ArrayList<HistoryEmployeeSkill> history = RestConsumer.getHistoryEmployeeSkillById(id);

		Set<Date> dates = new HashSet<Date>();
		HashMap<String, Integer> skillMap = new HashMap<String, Integer>();

		for (HistoryEmployeeSkill h : history) {
			dates.add(trim(h.getDate()));
			skillMap.put(h.getSkill().getName(), 0);
		}
		List<Date> datesList = new ArrayList<Date>(dates);
		Collections.sort(datesList);
		for (int i = 0; i < datesList.size(); i++) {
			Date myDate = trim(datesList.get(i));
			Map<String, Integer> skillMapForDate = new HashMap<String, Integer>(skillMap);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String line = " { \"Date\" : \"" + sdf.format(myDate) + "\", ";

			for (HistoryEmployeeSkill h : history) {
				Iterator i1 = skillMapForDate.entrySet().iterator();
				while (i1.hasNext()) {
					Map.Entry<String, Integer> pair = (Entry<String, Integer>) i1.next();
					if (trim(h.getDate()).getTime() == trim(myDate).getTime()
							&& pair.getKey().equalsIgnoreCase(h.getSkill().getName())) {
						pair.setValue(h.getLevel());
					}
				}
			}
			Iterator i2 = skillMapForDate.entrySet().iterator();
			while (i2.hasNext()) {
				Map.Entry<String, Integer> pair = (Entry<String, Integer>) i2.next();
				line = line + "\"" + pair.getKey() + "\" : " + pair.getValue() + ", ";
			}

			line = line.substring(0, line.length() - 2);
			line = line + " },";

			results = results + line;
		}

		results = results.substring(0, results.length() - 1);
		results = results + " ]";

		return results;
	}

	public static Date trim(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	@RequestMapping(value = "/skillStatistics", method = RequestMethod.GET)
	private String getSkillStatistics() {
		String result = " [ ";

		ArrayList<EmployeeSkill> employees = RestConsumer.getAllEmployeeSkills();

		ArrayList<Skill> skills = RestConsumer.getAllSkills();
		ArrayList<String> skillsName = new ArrayList<String>();
		for (Skill s : skills) {
			skillsName.add(s.getName());
		}
		for (String skill : skillsName) {
			result = result + " {\"label\" : " + " \" " + skill + " \"" + " , \"data\" : [ " ;
				for (EmployeeSkill e : employees) {
					if(e.getSkill().getName().equals(skill)) {
						result = result + " { \"x\" : " + " \"" + e.getEmployee().getFirstName() + " " +  
								 e.getEmployee().getLastName()+ "\" , "	+ " \"y\" : " + e.getLevel() + " } , " ;
					}
				}
				
			result = result.substring(0, result.length()-2);
			result = result + " ] } , ";
		}
		
		result = result.substring(0, result.length()-4);
		result = result + " } ]";
		
		return result;
	}
	
//	@RequestMapping(value = "/skillStatistics", method = RequestMethod.GET)
//	private String getMaxValueForSkills() {
//		String result = "";
//		
//		return result;
//		}

}
