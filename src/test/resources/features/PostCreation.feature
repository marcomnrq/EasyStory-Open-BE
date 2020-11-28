Feature: Create a new Post
  As a user
  I want to create a new post
  So that readers can give me feedback

  Scenario: The post was created successfully
    Given i am a user and entered the correct data
    When i make a post request to "/api/users/1/posts"
    Then the response result received should be 200

  Scenario: The post could not be created
    Given i am a user and entered the incorrect data
    When i make a post request to "/api/users/3/posts"
    Then the response result received should be a bad 400