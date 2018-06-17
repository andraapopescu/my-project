package application.demo.service;


import application.demo.domain.Quiz;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class QuizService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<Quiz> getAllQuizzes() {
        ArrayList<Quiz> result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quiz/all");
            result = mapper.readValue(u,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quiz.class));
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

    public static ArrayList<Quiz> getQuizByEmployee(long id) {
        ArrayList<Quiz> result = null;

        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quiz/employee/" + id);
            result = mapper.readValue(u, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quiz.class));
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

    public static Quiz getQuizById(long id) {
        Quiz result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quiz/" + id);
            result = mapper.readValue(u, Quiz.class);

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

    public static Quiz saveQuiz(Quiz quiz) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/quiz/save", quiz, Quiz.class);

        Quiz result = new Quiz();
        URL u;

        try {
            u = new URL(uri.toASCIIString());
            result = mapper.readValue(u, Quiz.class);

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
