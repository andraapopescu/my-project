package application.demo.service;

import application.demo.domain.Message;
import application.demo.domain.Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class VariantService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<Variant> getAllVariants() {
        ArrayList<Variant> result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/variant/all");
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

    public static ArrayList<Variant> getVariantByQuiz(long id) {
        ArrayList<Variant> result = null;

        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/variant/quiz/" + id);
            result = mapper.readValue(u, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Variant.class));
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

    public static Variant getVariantById(long id) {
        Variant result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/variant/" + id);
            result = mapper.readValue(u, Variant.class);

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

    public static Variant saveVariant(Message message) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/variant/save",
                message, Message.class);

        Variant result = new Variant();
        URL u;

        try {
            u = new URL(uri.toASCIIString());

            result = mapper.readValue(u, Variant.class);

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
