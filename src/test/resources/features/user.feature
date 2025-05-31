Feature: User API Tests *Test*

  @test
  Scenario: Get single user details
    Given I send an authenticated GET request to "/users/2"
    Then The response status code should be 200
    And The response should contain email "janet.weaver@reqres.in"
    And the response should contain first name "Janet"
    And The response should contain last name "Weaver"



