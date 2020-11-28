Feature: Visualize Qualification

  Scenario: Qualifications found
    Given i am a user in the qualification view
    When i make a get request to url "api/posts/1/qualifications"
    Then i should receive a status code as 200

  Scenario: Qualifications not found
    Given i am a user in the qualifications view
    When i make a get request to url "api/posts/1/qualifications"
    Then i should receive a bad status code as 400