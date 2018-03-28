package application.demo.service;

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

/**
 * Created by andra.popescu on 3/27/2018.
 */
public class HistoryEmployeeSkillService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<HistoryEmployeeSkill> getHistoryEmployeeSkillById( long id) {
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

    public static void deleteHistoryById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/history/deleteHistory/" + id);
    }
}
