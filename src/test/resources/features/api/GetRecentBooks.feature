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
      | 10    |
      | 20    |

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

  Scenario Outline: Retrieve recent books with different sortBy parameters
    When User retrieves recent books with limit "5" sorted by "<sortBy>" in order "DESC"
    Then the response status code should be 200
    And the response content type should be "application/json"
    And the metadata sortBy should match "<sortBy>"

    Examples:
      | sortBy    |
      | createdAt |
      | updatedAt |

  Scenario: Validate response structure and data types
    When User retrieves recent books with limit "1" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the response should be a valid JSON object
    And the data array should contain valid book objects
    And all book IDs should be valid UUIDs
    And all book ISBNs should be numeric strings
    And all book timestamps should be in valid ISO 8601 format
    And the metadata total should be an integer
    And the metadata limit should be an integer
    And the metadata sortBy should be a string
    And the metadata order should be a string

  Scenario: Retrieve recent books with empty results
    When User retrieves recent books with limit "1" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the response content type should be "application/json"
    And the response should have valid metadata structure
    And if data array is empty, metadata total should be 0

  Scenario: Query parameter validation - Boundary testing with large limit
    When User retrieves recent books with limit "1000" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the metadata limit should match the requested limit "1000"
    And the data array size should be a non-negative integer

  Scenario: Query parameter validation - Zero limit edge case
    When User retrieves recent books with limit "0" sorted by "createdAt" in order "DESC"
    Then the response status code should be either 200 or 400
    And if status is 200, the data array should be empty
    And if status is 400, an error message should be returned

  Scenario: Query parameter validation - Negative limit edge case
    When User retrieves recent books with limit "-1" sorted by "createdAt" in order "DESC"
    Then the response status code should be either 200 or 400
    And if status is 400, an error message should be returned

  Scenario: Response contains metadata with correct values
    When User retrieves recent books with limit "5" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the metadata should have the following structure:
      | field  | type    |
      | total  | integer |
      | limit  | integer |
      | sortBy | string  |
      | order  | string  |
    And metadata total should be greater than or equal to 0
    And metadata limit should equal the requested limit "5"
    And metadata sortBy should equal "createdAt"
    And metadata order should equal "DESC"

  Scenario: Books in response are sorted correctly by createdAt in DESC order
    When User retrieves recent books with limit "10" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And if data array has more than one book, each book's createdAt should be greater than or equal to the next book's createdAt

  Scenario: API handles missing query parameters gracefully
    When User retrieves books with default query parameters
    Then the response status code should be either 200 or 400
    And if status is 400, an appropriate error message should be provided

  Scenario: API handles invalid sortBy parameter
    When User retrieves recent books with limit "5" sorted by "invalidField" in order "DESC"
    Then the response status code should be either 200 or 400
    And if status is 400, an error message should indicate invalid sortBy parameter

  Scenario: API handles invalid order parameter
    When User retrieves recent books with limit "5" sorted by "createdAt" in order "INVALID"
    Then the response status code should be either 200 or 400
    And if status is 400, an error message should indicate invalid order parameter

  Scenario: Accept header validation - JSON response format
    When User retrieves recent books with limit "1" sorted by "createdAt" in order "DESC"
    And the Accept header is "application/json"
    Then the response status code should be 200
    And the response content type should be "application/json"
    And the response body should be valid JSON

  Scenario: Consistency check - Multiple consecutive requests return consistent data
    When User retrieves recent books with limit "5" sorted by "createdAt" in order "DESC"
    And User stores the response data
    And User retrieves recent books with limit "5" sorted by "createdAt" in order "DESC" again
    Then the response status code should be 200
    And the second response should contain the same books as the first response

  Scenario: Retrieve books and validate no sensitive data is exposed
    When User retrieves recent books with limit "1" sorted by "createdAt" in order "DESC"
    Then the response status code should be 200
    And the response should not contain sensitive fields like password, secret, or token
    And the response should only contain expected book fields: id, isbn, title, author, createdAt, updatedAt
