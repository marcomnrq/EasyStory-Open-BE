Feature: bookmark create
  Scenario: Create a new bookmark
    When the client calls /bookmark
    Then The bookmark was create succesfully
