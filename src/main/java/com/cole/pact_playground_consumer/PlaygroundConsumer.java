package com.cole.pact_playground_consumer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.json.JSONObject;

public class PlaygroundConsumer {
    private final String url;

    public PlaygroundConsumer(String url) {
        this.url = url;
    }

    public JSONObject returnResponse() throws UnirestException {
        HttpRequest getRequest = Unirest.get(url);

        HttpResponse<JsonNode> jsonNodeHttpResponse = getRequest.asJson();

        return jsonNodeHttpResponse.getBody().getObject();
    }
}