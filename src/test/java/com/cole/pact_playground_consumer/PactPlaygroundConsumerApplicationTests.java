package com.cole.pact_playground_consumer;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

import org.json.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;

/**
 * Sometimes it is not convenient to use the ConsumerPactTest as it only allows one test per test class.
 * The DSL can be used directly in this case.
 */
public class PactPlaygroundConsumerApplicationTests {

	@Rule
	public PactProviderRule provider = new PactProviderRule("pact_playground_provider", this);

	@Pact(provider = "pact_playground_provider", consumer = "pact_playground_consumer")
	public RequestResponsePact pact(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");

		return builder
			.uponReceiving("a request for hello")
			.path("/")
			.method("GET")
			.willRespondWith()
			.status(200)
			.body(
				new PactDslJsonBody().stringValue("Welcome", "Welcome to pact playground!")
			)
			.toPact();
	}

	@Test
	@PactVerification(value = "pact_playground_provider", fragment = "pact")
	public void pactWithOurProvider() throws UnirestException {
		PlaygroundConsumer client = new PlaygroundConsumer(provider.getUrl() + "/");
		
		Object obj = client.returnResponse();

		JSONObject expectedObj = new JSONObject();
        expectedObj.put("Welcome", "Welcome to pact playground!");

		assertEquals(expectedObj.toString(), obj.toString());
	}

	@Pact(provider = "pact_playground_provider", consumer = "pact_playground_consumer")
	public RequestResponsePact productPact(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");

		return builder
			.uponReceiving("a request for product")
			.path("/products")
			.method("GET")
			.willRespondWith()
			.status(200)
			.body(
				new PactDslJsonBody().stringValue("Product", "Hat").stringValue("Product", "Glove").integerType("Price", 12)
			)
			.toPact();
	}

	@Test
	@PactVerification(value = "pact_playground_provider", fragment = "productPact")
	public void productPact() throws UnirestException {
		PlaygroundConsumer client = new PlaygroundConsumer(provider.getUrl() + "/products");

		Object obj = client.returnResponse();

		JSONObject expectedObj = new JSONObject();
		expectedObj.put("Product", "Hat");
		expectedObj.put("Product", "Glove");
		expectedObj.put("Price", 12);

		assertEquals(expectedObj.toString(), obj.toString());
	}

	@Pact(provider = "pact_playground_provider", consumer = "pact_playground_consumer")
	public RequestResponsePact thirdPact(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");

		return builder
			.uponReceiving("a request for one product")
			.path("/product")
			.method("GET")
			.willRespondWith()
			.status(200)
			.body(
				new PactDslJsonBody().stringValue("Product", "Hat")
			)
			.toPact();
	}

	@Test
	@PactVerification(value = "pact_playground_provider", fragment = "thirdPact")
	public void thirdPactTest() throws UnirestException {
		PlaygroundConsumer client = new PlaygroundConsumer(provider.getUrl() + "/product");

		Object obj = client.returnResponse();

		JSONObject expectedObj = new JSONObject();
		expectedObj.put("Product", "Hat");

		assertEquals(expectedObj.toString(), obj.toString());
	}

	public JSONObject getJSONBody() {
		JSONObject pactBody = new JSONObject();
		pactBody.put("Welcome", "Welcome to pact playground!");

		return pactBody;
	}
}
