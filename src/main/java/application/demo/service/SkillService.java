package application.demo.service;

import application.demo.domain.Skill;
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
public class SkillService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

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
//            e.printStackTrace();
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

    public static void deleteSkill(long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/skill/deleteSkill/" + id);
    }
}
