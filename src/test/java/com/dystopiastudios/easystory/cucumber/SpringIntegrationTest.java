package com.dystopiastudios.easystory.cucumber;

import com.dystopiastudios.easystory.EasystoryApplication;
import io.cucumber.java.en.And;
import io.cucumber.spring.CucumberContextConfiguration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;



@CucumberContextConfiguration
@SpringBootTest(classes = EasystoryApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {

    public static HttpStatus response;

    @Autowired
    protected RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080";



    //POST
    public void makePost(String url, Object body) throws IOException {
        if (body != null){
            HttpEntity<Object> request = new HttpEntity<>(body);
            response = restTemplate
                    .exchange(BASE_URL+url, HttpMethod.POST, request, String.class).getStatusCode();
        }
        else {
            response = HttpStatus.BAD_REQUEST;
        }


    }
//GET
    public void makeGet(String url, Object body) throws IOException {
        if (body != null){
            HttpEntity<Object> request = new HttpEntity<>(body);
            response = restTemplate
                    .exchange(BASE_URL+url, HttpMethod.GET, request, String.class).getStatusCode();
        }
        else {
            response = HttpStatus.BAD_REQUEST;
        }


    }
}
