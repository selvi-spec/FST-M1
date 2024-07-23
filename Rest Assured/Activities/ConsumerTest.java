package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    // Set the headers
    Map<String, String> headers = new HashMap<>(); // Change Object to String

    // Create the contract(Pact)
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        // Add the header
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        // Create Request Body
        DslPart requestBody = new PactDslJsonBody()
                .numberType("id", 7001)
                .stringType("firstName", "Jivan")
                .stringType("lastName", "Kajave")
                .stringType("email", "jivan@example.com");

        // Create Response Body
        DslPart responseBody = new PactDslJsonBody()
                .numberType("id", 7001)
                .stringType("firstName", "Jivan")
                .stringType("lastName", "Kajave")
                .stringType("email", "jivan@example.com");

        // Write the interactions
        return builder.given("POST Request")
                .uponReceiving("a request to create a user")
                .method("POST")
                .path("/api/users")
                .headers(headers) // Pass headers as is, no change required
                .body(requestBody)
                .willRespondWith()
                .status(201)
                .body(responseBody)
                .toPact();
    }

    // JUnit test function
    @Test
    @PactTestFor(providerName = "UserProvider", port ="8282")
    public void postRequestTest(){

        RestAssured.baseURI = "http://localhost:8282";
        RequestSpecification rq = RestAssured.given().headers(headers).when();

        // Create request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 7001);
        reqBody.put("firstName", "Jivan"); // Correct the key to match Pact
        reqBody.put("lastName", "Kajave"); // Correct the key to match Pact
        reqBody.put("email", "jivan@example.com");

        // Send POST request
        Response response = rq.body(reqBody).post("/api/users");
        // Assertion
        assert (response.getStatusCode() == 201);

    }
}
