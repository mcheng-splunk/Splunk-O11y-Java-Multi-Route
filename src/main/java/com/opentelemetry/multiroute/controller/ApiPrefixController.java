package com.opentelemetry.multiroute.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.*;

@Controller
@RequestMapping("/api/endpoint")
public class ApiPrefixController  {

    private final static Logger logger = Logger.getLogger(ApiPrefixController.class.getName());
    private static int upperbound = 6;
    private int int_random;

    private boolean success;
    private Integer responseStatusCode;

    private HttpRequest request;

    private static final HttpClient client = HttpClient.newHttpClient();
    String[] uri = {"payment", "email", "stockavailability", "shipping", "refund", "feedback"};

    @Value("${remote.host.ip}")
    private String remoteHost;

    @Value("${remote.url}")
    private String remoteURL;

    @GetMapping
    public ModelAndView route(ModelMap model) {
        Random rand = new Random();
        // Setting the upper bound to generate the
        // random numbers in specific range
        int_random = rand.nextInt(upperbound);
        String endpointURL = "forward:/" + uri[int_random] ;
        logger.info("int_random: " + int_random + "endpointURL : " + endpointURL);
        return new ModelAndView(endpointURL, model);
    }

    @RequestMapping(value ={"/generic"})
    public ResponseEntity<String> router() throws MalformedURLException, IOException, URISyntaxException, InterruptedException {

        URL url = new URL("http://127.0.0.1:8080");
        HttpURLConnection transportLayer = (HttpURLConnection) url.openConnection();


        Random rand = new Random();
        int_random = rand.nextInt(upperbound);
        String endpointURL = "forward:/" + uri[int_random] ;
        logger.info("int_random: " + int_random + "endpointURL : " + endpointURL);


        if (rand.nextBoolean()){
             request = HttpRequest.newBuilder().GET()
                    .uri(URI.create("http://" + remoteHost + ":8080/login?id=" + endpointURL)).build();
        }else{
             request = HttpRequest.newBuilder().GET()
                    .uri(URI.create(remoteURL)).build();

        }
        transportLayer.setRequestProperty("Accept", "*/*");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode() + ": " + response.body());
        responseStatusCode = response.statusCode();
        logger.info(response.statusCode() + ": " + response.body());
        if (response.body().contains("Failed")) {
            success = false;
            return new ResponseEntity<>("Failed to login with ID=" + endpointURL, HttpStatus.BAD_REQUEST);
        } else {
            success = true;
            return new ResponseEntity<>("Successfully login with ID=" + endpointURL, HttpStatus.OK);
        }
    }
}