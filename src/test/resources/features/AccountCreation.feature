Feature: Account Creation

  Scenario: Account was created succesfully
    Given i am a user in the register view and filled the data
    When i make a post request to this url "api/users"
    Then the result status should be a response of 200

  Scenario: Could not create account
    Given i am a user in the register view an filled the data incorrectly
    When i make a post request to this url "api/users"
    Then the result status should be a bad response of 400