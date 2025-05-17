package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;


import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorizationSteps {

    private Response response;
    private String requestBody;

    File jsonFile = new File(
            getClass().getClassLoader().getResource("testdata/auth.json").getFile());

    @Given("I have registration data from file {string}")
    public void load_data_from_file(File filePath) {
        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/auth.json").getFile());
    }


    @When("I submit the registration request")
    public void submit_registration() {
        response = given()
                .body(requestBody)
                .when()
                .post("/register");
    }

    @Then("I should receive a {int} status code")
    public void verify_status_code(int expectedCode) {
        response.then().statusCode(expectedCode);
    }

    @Then("the response should contain a user ID")
    public void verify_user_id() {
        response.then().body("id", notNullValue());
    }

    @Then("the response should contain error {string}")
    public void verify_error_message(String error) {
        response.then().body("error", equalTo(error));
    }
}
