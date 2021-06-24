package com.cole.pact_playground_consumer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PlaygroundConsumerConfig {

    @Bean
    RestTemplate playgroundRestTemplate() {
        return new RestTemplateBuilder().rootUri("http://localhost:8080").build();
    }
}