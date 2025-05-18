package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.ApiConfig;

import entities.CustomResponse;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {

    @BeforeAll                                   //* If put in RequestSpecs
    public static void setup() {
        ApiConfig.setup();
        System.out.println("🚀 Test started");
    }

    @DisplayName("Get a single user *Test*")
    @Test
    public void GetSingleUser() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .extract()
                .response();

        String email = response.path("data.email");
        System.out.println("@Mail: " + email);
    }

    @DisplayName("Get all users and extract only names")
    @Test
    public void GetAllUsersOnlyNames() throws JsonProcessingException {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users?page=2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .statusCode(200)
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        customResponse.getData().forEach(user -> {
            System.out.println(user.getFirst_name());
        });
    }

    @DisplayName("Get all users and extract only IDs")
    @Test
    public void GetAllUsersOnlyIds() throws JsonProcessingException {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users?page=2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .statusCode(200)
                .extract()
                .response();

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        //Assertion for null and empty field
        assertThat(customResponse.getData())
                .as("Users list should not be null or empty")
                .isNotNull()
                .isNotEmpty();

        List<Integer> Ids = new ArrayList<>();

        for (int i = 0; i < customResponse.getData().size(); i++) {
            Ids.add(customResponse.getData().get(i).getId());
        }
        System.out.println("User IDs: " + Ids);
    }

    @DisplayName("Single user not found 404")
    @Test
    public void SingleUserNotFound() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users/23")
                .then()
                .spec(ResponseSpecs.singleUserNotFound())
                .statusCode(404)
                .extract().response();

        System.out.println(response.statusCode());
    }

    @DisplayName("List of JSON data")
    @Test
    public void ListOfResources() throws JsonProcessingException {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/unknown")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.getBody().asString(), CustomResponse.class);

        int pageNumber = response.path("page");
        int perNumber = response.path("per_page");
        int totalUsers = response.path("total");
        int total_pages = response.path("total_pages");

        CustomResponse.Support support = customResponse.getSupport();

        System.out.println("Page: " + pageNumber);
        System.out.println("Total users: " + perNumber);
        System.out.println("Page: " + totalUsers);
        System.out.println("Total users: " + total_pages);
        System.out.println("Support: " + support.getUrl() + "\nText: " + support.getText());
    }

    @DisplayName("Single Of Resources")
    @Test
    public void SingleOfResources() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/unknown/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        String name = response.path("data.name");
        int id = response.path("data.id");

        assertEquals("fuchsia rose", name);
        assertEquals(2, id);
        System.out.println("Name: " + name + " ID: " + id);
    }

    @DisplayName("Single Of Resources not found")
    @Test
    public void SingleUserNotFoundResources() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/unknown/23")
                .then()
                .spec(ResponseSpecs.singleUserNotFound())
                .statusCode(404)
                .extract().response();

        System.out.println(response.statusCode());
    }


    //Method POST
    @DisplayName("Create user using method POST")
    @Test
    public void CreateUser() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/users.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/users")
                .then()
                .spec(ResponseSpecs.createdResponse())
                .extract().response();

        System.out.println("Resource exists: " +
                (getClass().getResource("/testdata/users.json") != null));

        // Additional assertions or logging
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @DisplayName("Update user using method PUT")
    @Test
    public void UpdateUser() {
        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/updUser.json").getFile());  //There is not file

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .put("/users/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @DisplayName("Delete user using method DELETE")
    @Test
    public void DeleteUser() {

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .delete("/users/2")
                .then()
                .spec(ResponseSpecs.deleteUser())
                .extract().response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @DisplayName("Register user using method POST")
    @Test
    public void RegisterUser() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/auth.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/register")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        System.out.println("Resource exists: " +
                (getClass().getResource("/testdata/auth.json") != null));

        // Additional assertions or logging
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.asPrettyString());
    }

    @DisplayName("Register user unsuccessful using method POST")
    @Test
    public void RegisterUserUnsuccessful() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/test.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/register")
                .then()
                .spec(ResponseSpecs.MissingAndBadRequest())
                .extract().response();

        String errorMessage = response.jsonPath().getString("error");
        assertEquals("Missing password", errorMessage);
        System.out.println("Error message: "+errorMessage);
    }

    @DisplayName("Login user using method POST")
    @Test
    public void LoginUser() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/login.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/login")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        System.out.println("Resource exists: " +
                (getClass().getResource("/testdata/login.json") != null));

        String token = response.jsonPath().getString("token");
        System.out.println("Token: "+token);
    }

    @DisplayName("Login user unsuccessful using method POST")
    @Test
    public void LoginUserUnsuccessful() {

        File jsonFile = new File(
                getClass().getClassLoader().getResource("testdata/test.json").getFile());

        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .body(jsonFile)
                .post("/login")
                .then()
                .spec(ResponseSpecs.MissingAndBadRequest())
                .extract().response();

        String errorMessage = response.jsonPath().getString("error");
        assertEquals("Missing password", errorMessage);
        System.out.println("Error message: "+errorMessage);
    }

    @DisplayName("Delayed response with data")
    @Test
    public void DelayedResponse() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .queryParam("delay", 3)  // Adds ?delay=3 to the URL
                .when()
                .get("/users")
                .then()
                .time(lessThan(5000L))  // Assert response time < 5 seconds
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        int pageNumber = response.path("page");
        int perNumber = response.path("per_page");
        int totalUsers = response.path("total");
        int total_pages = response.path("total_pages");

        System.out.println("Response time: " + response.time() + " ms");
        System.out.println("Page: " + pageNumber);
        System.out.println("Total users: " + perNumber);
        System.out.println("Page: " + totalUsers);
        System.out.println("Total users: " + total_pages);

    }

    @DisplayName("Single user *test*")
    @Test
    public void testGetSingleUser() {
        Response response = given()
                .spec(RequestSpecs.authenticatedRequest())
                .when()
                .get("/users/2")
                .then()
                .spec(ResponseSpecs.successResponse())
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("janet.weaver@reqres.in", response.jsonPath().getString("data.email"));
        assertEquals("Janet", response.jsonPath().getString("data.first_name"));
        assertEquals("Weaver", response.jsonPath().getString("data.last_name"));
    }

    @AfterAll
    public static void tearDown() {
        ApiConfig.shutdown();
    }
}