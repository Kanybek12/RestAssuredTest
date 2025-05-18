package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserSteps {

    private Response response;

    @Given("I send an authenticated GET request to {string}")
    public void sendAuthenticatedRequest(String endpoint) {
        response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        response.then()
                .spec(ResponseSpecs.successResponse())
                .statusCode(statusCode);
    }

    @Then("the response should contain email {string}")
    public void verifyEmail(String expectedEmail) {
        response.then()
                .body("data.email", equalTo(expectedEmail));
   //     logResponseField("Email", response.path("data.email"));
    }

    @Then("the response should contain first name {string}")
    public void verifyFirstName(String expectedFirstName) {
        response.then()
                .body("data.first_name", equalTo(expectedFirstName));
   //     logResponseField("First Name", response.path("data.first_name"));
    }

    @Then("the response should contain last name {string}")
    public void verifyLastName(String expectedLastName) {
        response.then()
                .body("data.last_name", equalTo(expectedLastName));
    //    logResponseField("Last Name", response.path("data.last_name"));
    }

    //Debug
/*    private void logResponseField(String fieldName, Object value) {
        System.out.println("@Debug - " + fieldName + ": " + value);
    }*/
}
