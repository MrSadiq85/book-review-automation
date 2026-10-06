# BDD Test Automation for GET Recent Books Endpoint

## Overview
This document describes comprehensive BDD automation tests for the GET recent books endpoint:
- **Endpoint:** `GET http://localhost:8080/api/v1/books/recent`
- **Accept Header:** `application/json`
- **Framework:** Cucumber + RestAssured + JUnit 5

## Test Files Location
- **Feature File:** `src/test/resources/features/api/GetRecentBooks.feature`
- **Step Implementation:** `src/test/java/org/epam/steps/api/GetRecentBooksSteps.java`
- **Step Definitions:** `src/test/java/org/epam/stepdefinitions/api/GetRecentBooksStepDefinitions.java`

## Test Coverage

### 1. Happy Path Tests
**Scenario:** Successfully retrieve recent books with default parameters
- Validates 200 response status code
- Verifies JSON content type
- Confirms response time is under 5000ms
- Checks data array and metadata presence
- Validates all required book fields

### 2. Query Parameter Variations

#### Limit Parameter Testing
- **Values Tested:** 1, 5, 10, 20
- **Validations:**
  - Metadata limit matches requested limit
  - Data array size does not exceed limit
  - Response status is 200

#### Sort Order Testing
- **Values Tested:** DESC, ASC
- **Validations:**
  - Metadata order field matches requested order
  - Response contains valid data array
  - Status code is 200

#### SortBy Parameter Testing
- **Values Tested:** createdAt, updatedAt
- **Validations:**
  - Metadata sortBy field matches
  - Valid JSON response
  - Status code is 200

### 3. Response Structure Validation

#### Data Array Validation
- Each book object contains all required fields
- Fields: id, isbn, title, author, createdAt, updatedAt
- All fields are non-null

#### Metadata Validation
- Structure includes: total, limit, sortBy, order
- Data types:
  - total: integer
  - limit: integer
  - sortBy: string
  - order: string

#### Data Type Validation
- **IDs:** Valid UUID format (8-4-4-4-12 hex pattern)
- **ISBNs:** Numeric strings
- **Timestamps:** Valid ISO 8601 format (e.g., "2026-10-06T11:00:41.1755468+05:30")
- **Metadata fields:** Correct types (integers for total/limit, strings for sortBy/order)

### 4. Edge Cases & Error Scenarios

#### Boundary Testing
- Large limit values (1000)
- Zero limit (0)
- Negative limit (-1)
- Expected responses: 200 or 400 with proper error message

#### Empty Results
- When no books available, metadata total is 0
- Data array is empty but structure is valid
- Metadata is still present and valid

#### Missing/Invalid Parameters
- Missing query parameters with default behavior
- Invalid sortBy parameter
- Invalid order parameter
- Expected: 400 with error message or graceful handling

#### Header Validation
- Accept header validation for JSON response
- Response content type matches Accept header

### 5. Data Consistency & Sorting

#### Sorting Validation
- When DESC order requested with createdAt sort
- Each book's createdAt >= next book's createdAt
- Validates proper descending order

#### Consistency Testing
- Multiple consecutive requests with same parameters
- Results should be consistent (same books returned)
- Useful for detecting race conditions or data mutations

### 6. Security & Data Privacy

#### No Sensitive Data Exposure
- Response does not contain: password, secret, token, apiKey, privateKey
- Only expected fields are returned
- No additional or unexpected fields in response

#### Field Validation
- Confirms only expected fields present
- No extra metadata or debug information
- Data structure matches API specification

## Test Scenarios Summary

| Scenario | Focus | Parameters | Assertions |
|----------|-------|-----------|-----------|
| Happy Path | Core functionality | limit=1, sortBy=createdAt, order=DESC | 200, JSON, data+metadata |
| Limit Variations | Boundary conditions | limit=[1,5,10,20] | Metadata matches, array size <= limit |
| Sort Orders | Different sort directions | order=[DESC,ASC] | Metadata matches, valid data |
| SortBy Fields | Different sort criteria | sortBy=[createdAt,updatedAt] | Metadata matches |
| Response Structure | JSON schema validation | limit=1, sortBy=createdAt, order=DESC | All fields present, correct types |
| Data Types | Value format validation | limit=1, sortBy=createdAt, order=DESC | UUID, numeric ISBN, ISO 8601 timestamps |
| Empty Results | No data scenarios | limit=1, sortBy=createdAt, order=DESC | Valid structure, total=0 |
| Large Limit | Boundary values | limit=1000 | 200, valid structure |
| Zero Limit | Edge case | limit=0 | 200 or 400 with error |
| Negative Limit | Invalid input | limit=-1 | 200 or 400 with error |
| Missing Params | Default behavior | no params | 200 or 400 |
| Invalid SortBy | Invalid parameter | sortBy=invalidField | 400 with error message |
| Invalid Order | Invalid parameter | order=INVALID | 400 with error message |
| JSON Validation | Response format | limit=1, sortBy=createdAt, order=DESC | Valid JSON |
| Consistency | Data stability | Same params, multiple calls | Consistent results |
| Security | Data privacy | limit=1, sortBy=createdAt, order=DESC | No sensitive fields |

## Running the Tests

### Run All Tests
```bash
./gradlew test
```

### Run Specific Feature File
```bash
./gradlew test --tests TestRunner --scan
```

### Run with Cucumber Options
The test runner is configured in `src/test/java/org/epam/runner/TestRunner.java`
- Generates HTML report at: `build/reports/cucumber-report.html`
- Glue path: `org/epam/stepdefinitions`

## Test Execution Flow

1. **Background Setup**
   - API base URI set to `http://localhost:8080`
   - Accept header set to `application/json`

2. **Test Execution**
   - Send GET request with specified query parameters
   - Capture response and metadata

3. **Assertions**
   - Verify status code
   - Validate response structure
   - Check data types and formats
   - Confirm business logic (sorting, limits)

4. **Report Generation**
   - Cucumber HTML report created
   - Detailed test execution logs available

## API Response Example
```json
{
  "data": [
    {
      "id": "bd81dd5b-3408-4248-8c59-c0ac9814bd37",
      "isbn": "1234578274",
      "title": "TestBook2",
      "author": "sid",
      "createdAt": "2026-10-06T11:00:41.1755468+05:30",
      "updatedAt": "2026-10-06T11:00:41.1755468+05:30"
    }
  ],
  "metadata": {
    "total": 1,
    "limit": 1,
    "sortBy": "createdAt",
    "order": "DESC"
  }
}
```

## Key Testing Principles Applied

1. **BDD Approach:** Tests written in human-readable Gherkin syntax
2. **Comprehensive Coverage:** Happy path, edge cases, errors, security
3. **Data Validation:** Schema, types, formats all verified
4. **Performance:** Response time assertions included
5. **Security:** Sensitive data checks implemented
6. **Consistency:** Data reliability testing included
7. **Maintainability:** Reusable step definitions for future tests

## Future Enhancements

- Add pagination testing (if offset parameter added)
- Add filtering capabilities testing
- Add authentication/authorization tests
- Performance benchmarking with load tests
- Add contract testing with Pact
- Add mutation testing

## Dependencies

- RestAssured 6.0.1
- Cucumber 8.0.4
- JUnit 5
- Hamcrest matchers
- SLF4J logging

