
Feature: User Registration API Tests

  @positive
  Scenario: Successful registration with valid data
    Given I use valid registration payload from JSON
    When I send a POST request to "/register"
    Then the response status code should be 200
    And the response should contain token

  @negative
  Scenario: Registration without password
    Given I use invalid payload without password from JSON
    When I send a POST request to "/register"
    Then the response status code should be 400
    And the response should contain error "Missing password"

  @negative
  Scenario: Registration without email
    Given I use invalid payload without email from JSON
    When I send a POST request to "/register"
    Then the response status code should be 400
    And the response should contain error "Missing password"