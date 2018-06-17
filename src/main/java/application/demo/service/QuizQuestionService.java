package application.demo.service;

import application.demo.domain.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class QuizQuestionService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

    public static QuizQuestion findById(long id) {
        QuizQuestion result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quizQuestion/" + id);
            result = mapper.readValue(u, QuizQuestion.class);

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

    public static ArrayList<QuizQuestion> getQuizQuestionByQuiz(long id) {
        ArrayList<QuizQuestion> result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/quizQuestion/quiz/" + id);
            result = mapper.readValue(u,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, QuizQuestion.class));

        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<QuizQuestion> getAllQuizQuestions() {
        ArrayList<QuizQuestion> result = new ArrayList<QuizQuestion>();
        URL u;
        try {
            u = new URL(REST_SERVICE_URI + "/quizQuestion/all");
            result = mapper.readValue(u,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, QuizQuestion.class));
        } catch(MalformedURLException e) {
//            e.printStackTrace();
        } catch(JsonParseException e) {
//            e.printStackTrace();
        } catch(JsonMappingException e) {
//            e.printStackTrace();
        } catch(IOException e) {
//            e.printStackTrace();
        }

        return result;
    }

    public static QuizQuestion save(QuizQuestion employeeSkill) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/quizQuestion/save",
                employeeSkill, EmployeeSkill.class);

        QuizQuestion result = new QuizQuestion();
        URL u;

        try {
            u = new URL(uri.toASCIIString());

            result = mapper.readValue(u, QuizQuestion.class);

        } catch(MalformedURLException e) {
            e.printStackTrace();
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void deleteQuizQuestionById(long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/quizQuestion/delete/" + id);
    }
}
