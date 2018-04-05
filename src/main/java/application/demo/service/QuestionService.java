package application.demo.service;

import application.demo.domain.Question;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class QuestionService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<Question> getAllQuizzes() {
        ArrayList<Question> result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quiz/all");
            result = mapper.readValue(u,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Question.class));
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

    public static Question getQuizById( long id) {
        Question result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quiz/" + id);
            result = mapper.readValue(u, Question.class);

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

    public static Question saveVariant( Question question ) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/question/save", question, Question.class);

        Question result = new Question();
        URL u;

        try {
            u = new URL(uri.toASCIIString());
            result = mapper.readValue(u, Question.class);

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
