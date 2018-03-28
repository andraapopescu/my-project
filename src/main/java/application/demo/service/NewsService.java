package application.demo.service;

import application.demo.domain.News;
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
public class NewsService {

    public static String REST_SERVICE_URI = "http://localhost:8080";
    public static ObjectMapper mapper = new ObjectMapper();

    public static ArrayList<News> getAllNews() {
        ArrayList<News> result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/news/all");
            result = mapper.readValue(u, mapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, News.class));
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

    public static News getNewsById(long id) {
        News result = null;
        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/news/" + id);
            result = mapper.readValue(u, News.class);
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

    public static ArrayList<News> getNewsByEmployee(long id) {
        ArrayList<News> result = null;

        URL u;

        try {
            u = new URL(REST_SERVICE_URI + "/news/employee/" + id);
            result = mapper.readValue(u, mapper.getTypeFactory().
                    constructCollectionType(ArrayList.class, News.class));
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

    public static News saveNews(News news) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/news/saveNews",
                news, News.class);

        News result = new News();
        URL u;

        try {
            u = new URL(uri.toASCIIString());
            result = mapper.readValue(u, News.class);

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
