package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import specifications.RequestSpecs;

import java.io.IOException;
import java.io.InputStream;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegistrationSteps {
    private Response response;
    private String requestPayload;

  /*  File jsonFile = new File(
            getClass().getClassLoader().getResource("testdata/auth.json").getFile());*/

    @Given("I use valid registration payload from JSON")
    public void useValidRegistrationPayload() throws IOException {

/*        System.out.println("Resource exists: " +
                (getClass().getResource("/testdata/auth.json") != null));*/

        InputStream is = getClass().getResourceAsStream("/testdata/auth.json");
        requestPayload = new String(is.readAllBytes());

    }

    @Given("I use invalid payload without password from JSON")
    public void iUseInvalidPayloadWithoutPasswordFromJSON() throws IOException {

        InputStream is = getClass().getResourceAsStream("/testdata/authUnsucces.json");
        requestPayload = new String(is.readAllBytes());

    }

    @Given("I use invalid payload without email from JSON")
    public void iUseInvalidPayloadWithoutEmailFromJSON() throws IOException {

        InputStream is = getClass().getResourceAsStream("/testdata/authUnsucces.json");
        requestPayload = new String(is.readAllBytes());
    }

    @When("I send a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .baseUri("https://reqres.in/api")
                .contentType("application/json")
                .body(requestPayload)  // Directly pass the File object
                .when()
                .post(endpoint);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain token")
    public void verifyToken() {
        response.then().body("token", notNullValue());
        String token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
    }

    @Then("the response should contain error {string}")
    public void verifyError(String errorMessage) {
        response.then().body("error", equalTo(errorMessage));
    }
}