Feature: validate book creation by POST

  Scenario Outline: Validate book creation with valid data
    When User creates a book
      | isbn   | <isbn>   |
      | title  | <title>  |
      | author | <author> |
    Then the response status code should be 201 response time less than 5000 millis and content type should be "application/json"
    And User deletes the book with id "<id>"
    Then the response status code should be 204 response time less than 5000 millis and content type should be ""

    Examples:
      | title                 | author              | isbn          | id      |
      | The Great Gatsby      | F. Scott Fitzgerald | 9780743273565 | dynamic |
      | To Kill a Mockingbird | Harper Lee          | 9780061120084 | dynamic |
      | 1984                  | George Orwell       | 9780451524935 | dynamic |