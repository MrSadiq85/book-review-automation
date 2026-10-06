Feature: Get Recent Books Endpoint

  Background:
    Given the API is available at "http://localhost:8080"
    And the Accept header is set to "application/json"

  Scenario: Happy path - Successfully retrieve recent books with default parameters
    When User retrieves recent books with limit "1" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the response content type should be "application/json"
    And the response time should be less than 5000 millis
    And the response should contain data array with books
    And the response should contain metadata with total, limit, sortBy, and order
    And each book in data should have required fields: id, isbn, title, author, createdAt, updatedAt

  Scenario Outline: Retrieve recent books with limit parameter variations
    When User retrieves recent books with limit "<limit>" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the response content type should be "application/json"
    And the metadata limit should match the requested limit "<limit>"
    And the data array size should be less than or equal to "<limit>"

    Examples:
      | limit |
      | 1     |
      | 5     |

  Scenario Outline: Retrieve recent books with different sort orders
    When User retrieves recent books with limit "5" sorted by "createdAt" in order "<order>"
    Then the response status code should be 200
    And the response content type should be "application/json"
    And the metadata order should match "<order>"
    And the response should contain data array with books

    Examples:
      | order |
      | DESC  |
      | ASC   |

  Scenario: Query parameter validation - Zero limit edge case
    When User retrieves recent books with limit "0" sorted by "createdAt" in order "DESC"
    Then the response status code should be either 200 or 400
    And if status is 200, the data array should be empty
    And if status is 400, an error message should be returned

  Scenario: Query parameter validation - Negative limit edge case
    When User retrieves recent books with limit "-1" sorted by "createdAt" in order "DESC"
    Then the response status code should be either 200 or 400
    And if status is 400, an error message should be returned

