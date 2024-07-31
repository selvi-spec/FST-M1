package examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class FirstTest {
    // GET https://petstore.swagger.io/v2/pet/findByStatus?status=alive
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp(){
        //RequestSpecification
        requestSpec = new RequestSpecBuilder()
                // Set base URL
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .addHeader("Content-Type", "application/json")
                .build();

        //ResponseSpecification
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("status", CoreMatchers.equalTo("alive"))
                .expectResponseTime(lessThanOrEqualTo(3000L))
                .build();
    }

    // POST https://petstore.swagger.io/v2/pet
    @Test (priority=1)
    public void postRequest(){
        //Create Request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", "4001");
        reqBody.put("name", "Simba");
        reqBody.put("status", "alive");
        //Save the response
        Response response= given().spec(requestSpec).body(reqBody).when().post();
        //Assertions
        response.then().spec(responseSpec);
    }

    @Test (priority=2)
    public void getRequestWithQueryParam(){
        //Create a baseURI
        Response response=
                given(). //Request specifications
                    baseUri("https://petstore.swagger.io/v2/pet").
                        header("Content-Type", "application/json").
                        queryParam("status", "alive").
                        log().all().

                when().
                    get("/findByStatus");

        //Print Response Headers
        System.out.println(response.getHeaders());

        //Print Response Body
        System.out.println(response.getBody().asString());
        //System.out.println(response.getBody().asPrettyString());
        //Extract Individual Properties
        String petStatus = response.then().extract().path("[0]['status']");
        System.out.println("pet status is :" + petStatus);
        Assert.assertEquals(petStatus, "alive");
    }

    // GET https://petstore.swagger.io/v2/pet/{petId}
    @Test (priority=3)
    public void getRequestWithPathParam() {
        given().//Request Specifications
                baseUri("https://petstore.swagger.io/v2/pet").
                header("Content-Type", "application/json").
                pathParam("petId", 4001).
                log().all().

        when().
                get("/{petId}").
        then(). //Response Specifications
            statusCode(200).
            body("name",equalTo("Simba")).
            body("status",equalTo("alive")).
            log().all();

    }
}
