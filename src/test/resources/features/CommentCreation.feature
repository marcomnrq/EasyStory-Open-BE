Feature: Comment Creation
  As a writer
  I want to read the comments
  In order to make better posts

  Scenario: Comment was created
    Given i am a reader
    When i make a post request to path "api/users/1/posts/2/comments"
    Then the status response code should be 200

  Scenario: Comment was not created
    Given i am a reader
    And i don't put content in the comment
    When i make a post request to path "api/users/1/posts/2/comments"
    Then the status response code should be a bad 400
