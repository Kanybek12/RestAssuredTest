Feature: Single User Details Retrieval
  As an API user
  I want to fetch a specific user's details
  So that I can verify the user information is correct

  Scenario: Get details of user with ID 2
    Given I send an authenticated GET request to "/users/2"
    Then the response status code should be 200
    And the response should contain email "janet.weaver@reqres.in"
    And the response should contain first name "Janet"
    And the response should contain last name "Weaver"

Feature: User Management - Get All Users
  As an API consumer
  I want to retrieve all users
  So that I can process their information

  Scenario: Get all users and extract only first names
    Given I send an authenticated GET request to "/users?page=2"
    Then the response status code should be 200
    And I extract and print all first names from the response