package com.cole.pact_playground_consumer;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaygroundController {
    @RequestMapping("/")
    public String hello() throws UnirestException {
        PlaygroundConsumer consumer = new PlaygroundConsumer("http://localhost:8080/");

        return consumer.returnResponse().toString();
    }
}