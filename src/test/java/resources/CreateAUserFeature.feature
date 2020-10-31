Feature: Register a user
  As a client
  I want to register my user
  To be able to use the application

  Scenario: The client has registered his user successfully
    Given a client in the register user's view
    And the information entered is correct
    When the client clicks the register button
    And make a post request to "/users"
    Then the result received has a OK status code of 200

  Scenario: The client has not registered his user
    Given a client in the register user's view
    And the information entered is incorrect
    When the client clicks the register button
    And make a post request to "/users"
    Then the system asks to correct the wrong data
    And the result received has a BAD status code of 400