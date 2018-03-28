package application.demo.service;


import application.demo.domain.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class UserService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

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
}
