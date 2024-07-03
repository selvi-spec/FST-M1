import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class GitHub_RestAssured_Project {
    // Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;
    int sshId;
    String autho = "Bearer <token>";

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                // Set base URL
                .setBaseUri("https://api.github.com")
                // Build request specification
                .build();

    }

    @Test(priority=1)
    // Test case using a DataProvider
    public void addD() {

        String payload = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGtS1w90M0l7nPl091eHowEEcAAzc4pFMMBBLHGfcQXh azuread\\\\ektasuman@IBM-CHV7NM3\"}";

     Response response = given().spec(requestSpec) // Use requestSpec
                .body(payload).basePath("user/keys").headers("Authorization", autho) // Send request body
                .when().post(); // Send POST request

        sshId =response.then().extract().path("id");
        // Assertions
        System.out.println("Status Line: " + response.statusLine());
        Assert.assertEquals(response.statusCode()/*actual*/, 201, "check for code");
        System.out.println("Response Body: " + response.body().asString());
        sshId= response.then().extract().path("id");
    }


    @Test(priority=2)
    public void getDetails() {
        Response response = given().spec(requestSpec).basePath("/user/keys").headers("Authorization", autho) // Use requestSpec
                .pathParam("keyId", sshId) // Add path parameter
                .when().get("/{keyId}"); // Send GET request

        // Print response
        Reporter.log(response.asPrettyString());
        // Assertions
        Assert.assertEquals(response.statusCode()/*actual*/, 200, "check for code");

    }

    @Test(priority=3)
    public void deleteDetails() {
        Response response = given().spec(requestSpec).basePath("/user/keys").headers("Authorization", autho) // Use requestSpec
                .pathParam("keyId", sshId) // Add path parameter
                .when().delete("/{keyId}"); // Send GET request

        // Print response
        Reporter.log(response.asPrettyString());
        // Assertions
        Assert.assertEquals(response.statusCode()/*actual*/, 204, "check for code");
    }
}
