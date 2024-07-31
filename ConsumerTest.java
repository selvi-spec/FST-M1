package project;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    Map<String, String> headers = new HashMap<>();

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        headers.put("Content-Type", "application/json");
        DslPart reqAndResBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");
        return builder.given("POST Request").uponReceiving("a request to create a user")
                .method("POST")
                .path("/api/users")
                .headers(headers)
                .body(reqAndResBody)
                .willRespondWith()
                .status(201)
                .body(reqAndResBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void postRequestTest(){
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 1152);
        reqBody.put("firstName", "Dimple");
        reqBody.put("lastName", "Badiye");
        reqBody.put("email", "test@example.com");

        given().baseUri("http://localhost:8282").headers(headers).body(reqBody).when()
                .post("/api/users").then().statusCode(201)
                .log().all();
    }
}
