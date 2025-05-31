Feature: ReqRes API CRUD Operations
  Background:
    Given The API base URL is "https://reqres.in/api"

  @GET
  Scenario: Get list of users
    When I send a GET request to "/users?page=2"
    Then The response status code should be 200
    And The response should contain at least 6 users
    And The response should contain user with email "michael.lawson@reqres.in"

  @POST
  Scenario: Create new user
    When I send a POST request to "/users" with:
      """
      {
        "name": "John API",
        "job": "QA Engineer"
      }
      """
    Then The response status code should be 201
    And The response should contain created user ID
    And The response should contain name "John API"

  @PUT
  Scenario: Update existing user
    Given I have created a test user
    When I send a PUT request to "/users/<created_user_id>" with:
      """
      {
        "name": "John Updated",
        "job": "Senior QA"
      }
      """
    Then The response status code should be 200
    And The response should contain updated name "John Updated"

  @DELETE
  Scenario: Delete user
    Given I have created a test user
    When I send a DELETE request to "/users/<created_user_id>"
    Then The response status code should be 204
    And The user should be successfully deleted