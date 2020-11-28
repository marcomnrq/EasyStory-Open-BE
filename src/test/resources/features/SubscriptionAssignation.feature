Feature: Subscription Assignation

  Scenario: Subscription Completed
    Given i am a user in the application and i already subscribed to another user
    When i send a get request to "api/users/1/subscriptions"
    Then the status code should be a good 200

  Scenario: Subscription Failed
    Given i am a user in the application
    When i send a post request to "api/users/2/subscriptions/3"
    Then the status code should be a bad 400