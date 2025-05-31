package stepDefinition;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specifications.RequestSpecs;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReqresSteps {
    private Response response;
    private RequestSpecification request;
    private String baseUrl = "https://reqres.in/api";
    private String createdUserId;
    private Map<String, Object> requestBody = new HashMap<>();

    @Given("The API base URL is {string}")
    public void setBaseUrl(String url) {
        this.baseUrl = url;
        request = given().spec(RequestSpecs.authenticatedRequest()).baseUri(url);
    }

    // GET Steps
    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = request.when().get(endpoint);
    }

    @Then("The response should contain at least {int} users")
    public void verifyMinimumUsers(int minUsers) {
        response.then().body("data.size()", greaterThanOrEqualTo(minUsers));
    }

    @Then("The response should contain user with email {string}")
    public void verifyUserEmail(String email) {
        response.then().body("data.email", hasItem(email));
    }

    // POST Steps
    @When("I send a POST request to {string} with:")
    public void sendPostRequest(String endpoint, String body) {
        response = request
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint);

        createdUserId = response.jsonPath().getString("id");
    }

    @Then("The response should contain created user ID")
    public void verifyCreatedUserId() {
        response.then().body("id", notNullValue());
    }

    @Then("The response should contain name {string}")
    public void verifyResponseName(String name) {
        response.then().body("name", equalTo(name));
    }

    // PUT Steps
    @Given("I have created a test user")
    public void createTestUser() {
        Map<String, String> user = new HashMap<>();
        user.put("name", "Test User");
        user.put("job", "Tester");

        response = request
                .contentType("application/json")
                .body(user)
                .when()
                .post("/users");

        createdUserId = response.jsonPath().getString("id");
    }

    @When("I send a PUT request to {string} with:")
    public void sendPutRequest(String endpoint, String body) {
        String resolvedEndpoint = endpoint.replace("<created_user_id>", createdUserId);
        response = request
                .contentType("application/json")
                .body(body)
                .when()
                .put(resolvedEndpoint);
    }

    // DELETE Steps
    @When("I send a DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        String resolvedEndpoint = endpoint.replace("<created_user_id>", createdUserId);
        response = request.when().delete(resolvedEndpoint);
    }

    @Then("The user should be successfully deleted")
    public void verifyUserDeletion() {
        given()
                .baseUri(baseUrl)
                .when()
                .get("/users/" + createdUserId)
                .then()
                .statusCode(404);
    }
}
