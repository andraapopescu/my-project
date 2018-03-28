package application.demo.service;

import application.demo.domain.Message;
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
public class MessageService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

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

}
